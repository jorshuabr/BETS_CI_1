package dataAccess;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import configuration.ConfigXML;
import domain.Bet;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;
import exceptions.UserAlreadyExistException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccessBriones2 {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccessBriones2(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	/**
	 * This is the data access method that initializes the database with some events
	 * and questions. This method is invoked by the business logic (constructor of
	 * BLFacadeImplementation) when the option "initialize" is declared in the tag
	 * dataBaseOpenMode of resources/config.xml file
	 */
	public void initializeDB() {

		db.getTransaction().begin();
		try {

			Calendar today = Calendar.getInstance();

			int month = today.get(Calendar.MONTH);
			month += 1;
			int year = today.get(Calendar.YEAR);
			if (month == 12) {
				month = 0;
				year += 1;
			}

			db.getTransaction().commit();
			System.out.println("Db initialized");
		} catch (Exception e) {
			e.printStackTrace();
		}
	}


	public void open(boolean initializeMode) {

		System.out.println("Opening DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		String fileName = c.getDbFilename();
		if (initializeMode) {
			fileName = fileName + ";drop";
			System.out.println("Deleting the DataBase");
		}

		if (c.isDatabaseLocal()) {
			emf = Persistence.createEntityManagerFactory("objectdb:" + fileName);
			db = emf.createEntityManager();
		} else {
			Map<String, String> properties = new HashMap<String, String>();
			properties.put("javax.persistence.jdbc.user", c.getUser());
			properties.put("javax.persistence.jdbc.password", c.getPassword());

			emf = Persistence.createEntityManagerFactory(
					"objectdb://" + c.getDatabaseNode() + ":" + c.getDatabasePort() + "/" + fileName, properties);

			db = emf.createEntityManager();
		}

	}



	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}



	public boolean validoUsuario(String puser) throws UserAlreadyExistException {

		User usuarioBD = db.find(User.class, puser);
		if (usuarioBD == null) {
			return true;
		} else {
			throw new UserAlreadyExistException("Ese usuario ya existe");
		}

	}

	public RegularUser registrar(String user, String pass, String name, String lastName, String birthDate, String email,
			String account, Integer numb, String address, float balance) throws UserAlreadyExistException {
		db.getTransaction().begin();
		RegularUser u = new RegularUser(user, pass, name, lastName, birthDate, email, account, numb, address, balance);

		boolean b = validoUsuario(user);

		if (b) {
			db.persist(u);
			db.getTransaction().commit();
		}

		return u;
	}

	public void deleteUser(String name) {
		try {
			db.getTransaction().begin();
			Query query = db.createQuery("DELETE FROM User u WHERE u.userName='" + name + "'");
			int deletedUsers = query.executeUpdate();
			System.out.println("Usuarios borrados: " + deletedUsers);
			db.getTransaction().commit();
			System.out.println("usuario borrado de la DB");
		} catch (Exception e) {

		}

	}
	
	public int createApuesta(Forecast pSelectedForecast, RegularUser pselectedClient, Float pselectedAmount) {
		// VALIDACIÓN DE NÚMERO POSITIVO
		if (pselectedAmount < 0) {
			// 4 - NÚMERO NEGATIVO
			return 4;
		} else {
			// VALIDACIÓN DE MONTANTE MAYOR AL MÍNIMO
			Question q = pSelectedForecast.getQuestion();
			if (pselectedAmount < q.getBetMinimum()) {
				// 3 - NO ALCANZA APUESTA MÍNIMA
				return 3;
			} else {
				RegularUser clientdb = db.find(RegularUser.class, pselectedClient.getUserName());
				// VALIDACIÓN DE SALDO EN CUENTA
				if (pselectedAmount >= clientdb.getBalance()) {
					// 2 - FALTA DE SALDO
					return 2;
				} else {
					try {
						db.getTransaction().begin();
						//Forecast fore = insertForecast(pSelectedForecast);
						//Forecast fore = db.find(Forecast.class, pSelectedForecast);
						//Forecast fore = findForecast(pSelectedForecast.getForecastNumber());
						
						Bet ap = pSelectedForecast.addBet(pSelectedForecast, pselectedClient, pselectedAmount);
						clientdb.addBet(ap);
						db.persist(ap);
						clientdb.setBalance(clientdb.getBalance() - pselectedAmount);
						db.persist(clientdb);
						db.getTransaction().commit();

						// 0 - APUESTA CREADA
						return 0;

					} catch (Exception ex) {

						// 1 - ERROR DE INGRESO DE APUESTA
						return 1;
					}

				}

			}
		}

	}



}
