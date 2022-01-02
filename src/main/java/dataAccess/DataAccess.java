package dataAccess;

import java.util.ArrayList;
//hello
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.persistence.EntityManager;
import javax.persistence.EntityManagerFactory;
import javax.persistence.Persistence;
import javax.persistence.Query;
import javax.persistence.TypedQuery;
import javax.swing.JOptionPane;

import configuration.ConfigXML;
import configuration.UtilDate;
import domain.AdminUser;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;
import exceptions.IncorrectPassException;
import exceptions.QuestionAlreadyExist;
import exceptions.UserAlreadyExistException;
import exceptions.UserDoesNotExistException;

/**
 * It implements the data access to the objectDb database
 */
public class DataAccess {
	protected static EntityManager db;
	protected static EntityManagerFactory emf;

	ConfigXML c = ConfigXML.getInstance();

	public DataAccess(boolean initializeMode) {

		System.out.println("Creating DataAccess instance => isDatabaseLocal: " + c.isDatabaseLocal()
				+ " getDatabBaseOpenMode: " + c.getDataBaseOpenMode());

		open(initializeMode);

	}

	public DataAccess() {
		new DataAccess(false);
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

	/**
	 * This method creates a question for an event, with a question text and the
	 * minimum bet
	 * 
	 * @param event      to which question is added
	 * @param question   text of the question
	 * @param betMinimum minimum quantity of the bet
	 * @return the created question, or null, or an exception
	 * @throws QuestionAlreadyExist if the same question already exists for the
	 *                              event
	 */
	public Question createQuestion(Event event, String question, float betMinimum) throws QuestionAlreadyExist {
		System.out.println(">> DataAccess: createQuestion=> event= " + event + " question= " + question + " betMinimum="
				+ betMinimum);

		// he añadido este if de formaque que la transacción devuelva null en caso de
		// que se intente crear una pregunta con un evento que no existe o una pregunta
		// de valor null
		if (event == null || question == null) {
			return null;
		}

//	Find * @throws IllegalArgumentException if the first argument does 
//	     *         not denote an entity type or the second argument is
//	     *         is not a valid type for that entity's primary key or 
//	     *         is null
		Event ev = db.find(Event.class, event.getEventNumber());

		if (ev.DoesQuestionExists(question)) {
			throw new QuestionAlreadyExist(ResourceBundle.getBundle("Etiquetas").getString("ErrorQueryAlreadyExist"));
		}

		db.getTransaction().begin();
		Question q = ev.addQuestion(question, betMinimum);
		// db.persist(q);
		db.persist(ev); // db.persist(q) not required when CascadeType.PERSIST is added in questions
		// property of Event class
		// @OneToMany(fetch=FetchType.EAGER, cascade=CascadeType.PERSIST)
		db.getTransaction().commit();
		return q;

	}

	public Vector<Question> getAllQuestions() {
		System.out.println(">> DataAccess: getAllQuestions");
		Vector<Question> res = new Vector<Question>();
		TypedQuery<Question> query = db.createQuery("SELECT qu FROM Question qu", Question.class);
		List<Question> questions = query.getResultList();
		for (Question qu : questions) {
			System.out.println(qu.toString());
			res.add(qu);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the events of a given date
	 * 
	 * @param date in which events are retrieved
	 * @return collection of events
	 */
	public Vector<Event> getEvents(Date date) {
		System.out.println(">> DataAccess: getEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev WHERE ev.eventDate=?1", Event.class);
		query.setParameter(1, date);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	public Vector<Event> getAllEvents() {
		System.out.println(">> DataAccess: getAllEvents");
		Vector<Event> res = new Vector<Event>();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev", Event.class);
		List<Event> events = query.getResultList();
		for (Event ev : events) {
			System.out.println(ev.toString());
			res.add(ev);
		}
		return res;
	}

	/**
	 * This method retrieves from the database the dates a month for which there are
	 * events
	 * 
	 * @param date of the month for which days with events want to be retrieved
	 * @return collection of dates
	 */
	public Vector<Date> getEventsMonth(Date date) {
		System.out.println(">> DataAccess: getEventsMonth");
		Vector<Date> res = new Vector<Date>();

		Date firstDayMonthDate = UtilDate.firstDayMonth(date);
		Date lastDayMonthDate = UtilDate.lastDayMonth(date);

		TypedQuery<Date> query = db.createQuery(
				"SELECT DISTINCT ev.eventDate FROM Event ev WHERE ev.eventDate BETWEEN ?1 and ?2", Date.class);
		query.setParameter(1, firstDayMonthDate);
		query.setParameter(2, lastDayMonthDate);
		List<Date> dates = query.getResultList();
		for (Date d : dates) {
			System.out.println(d.toString());
			res.add(d);
		}
		return res;
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

	public boolean existQuestion(Event event, String question) {
		System.out.println(">> DataAccess: existQuestion=> event= " + event + " question= " + question);
		Event ev = db.find(Event.class, event.getEventNumber());
		return ev.DoesQuestionExists(question);

	}

	public void close() {
		db.close();
		System.out.println("DataBase closed");
	}

	public User login(String username, String pass) throws UserDoesNotExistException, IncorrectPassException {

		User usuario = db.find(User.class, username);

		if (usuario == null) {
			throw new exceptions.UserDoesNotExistException("El usuario no existe");
		}
		if (!pass.equals(usuario.getUserPass())) {
			throw new exceptions.IncorrectPassException("Contraseña incorrecta");
		}
		return usuario;

	}

	public boolean insertEvent(Event pEvento) {
		try {
			db.getTransaction().begin();
			db.persist(pEvento);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			e.printStackTrace();
			return false;
		}
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

	public int getNumberEvents() {
		db.getTransaction().begin();
		TypedQuery<Event> query = db.createQuery("SELECT ev FROM Event ev ", Event.class);
		return query.getResultList().size();
	}

	public boolean existEvent(Event event) {
		System.out.println(">> DataAccess: existEvent=> event= " + event);

		Vector<Event> eventosmismodia = getEvents(event.getEventDate());

		for (Event e : eventosmismodia) {
			if (e.getDescription().equals(event.getDescription())) {
				return true;
			}
		}

		return false;
	}

	public void deleteAllQuestions() {
		try {
			db.getTransaction().begin();
			db.createQuery("DELETE * FROM Question");
			db.getTransaction().commit();
			System.out.println("preguntas borradas de la DB");
		} catch (Exception e) {

		}

	}
	
	public boolean deleteEvent(Event evento) {
        try {
            db.getTransaction().begin();

            // se ha cmbiado de evento.getEventDate() a evento.getEventNumber()
            try {
                Event event1 = db.find(Event.class, evento.getEventNumber());
                if (event1 == null) {
                    return false;
                }
            } catch (IllegalArgumentException ex) {
                return false;
            }

            Query query1 = db.createQuery("DELETE FROM Event e WHERE e.getEventNumber()=?1");
            query1.setParameter(1, evento.getEventNumber());

            TypedQuery<Question> query2 = db.createQuery("SELECT qu FROM Question qu", Question.class);
            List<Question> preguntasDB = query2.getResultList();

            for (Question q : preguntasDB) {
                if (q.getEvent().equals(evento)) {
                    System.out.println("pregunta eliminada: " + q);
                    db.remove(q);
                } else {
                    System.out.println("pregunta NO ELIMINADA");
                }
            }

            int events = query1.executeUpdate();
            db.getTransaction().commit();
            System.out.println("Evento eliminado: " + evento);

            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }

    }
	

	public Forecast insertForecast(Question q, String forecast, float fee) {
		System.out.println(">> DataAccess: insertForecast=> question= " + q + " forecast= " + forecast + " fee=" + fee);

		try {
			db.getTransaction().begin();
			Question qe = db.find(Question.class, q.getQuestionNumber());
			if (qe.DoesForecastExists(forecast))
				return null;
			//Forecast f = new Forecast(getMaxIdForecastInDB() + 1, forecast, fee, qe);
			Forecast f = new Forecast(forecast, fee,qe);
			qe.addForecast(f);
			db.persist(qe);
			db.getTransaction().commit();
			return f;
		} catch (Exception e) {
			e.printStackTrace();
			return null;
		}

	}

	public int getNumberForecasts() {
		db.getTransaction().begin();
		TypedQuery<Forecast> query = db.createQuery("SELECT f FROM Forecast f ", Forecast.class);
		return query.getResultList().size();
	}

	public Vector<Forecast> getForecasts() {
		db.getTransaction().begin();
		Vector<Forecast> res = new Vector<Forecast>();
		TypedQuery<Forecast> query = db.createQuery("SELECT f FROM Forecast f ", Forecast.class);
		List<Forecast> forecasts = query.getResultList();
		for (Forecast f : forecasts) {
			System.out.println(f.toString());
			res.add(f);
		}
		return res;
	}

	public Vector<Forecast> getForecasts(Question pregunta) {
		db.getTransaction().begin();
		Vector<Forecast> res = new Vector<Forecast>();
		TypedQuery<Forecast> query = db.createQuery("SELECT f FROM Forecast f WHERE f.getQuestion()=?1",
				Forecast.class);
		query.setParameter(1, pregunta);
		List<Forecast> forecasts = query.getResultList();
		for (Forecast f : forecasts) {
			System.out.println(f.toString());
			res.add(f);
		}
		return res;
	}

	public boolean existForecast(Forecast f) {
		System.out.println(">> DataAccess: existForecast=> forecast= " + f);
		return db.find(Forecast.class, f.getForecast()) != null ? true : false;

	}

	public boolean editarPerfilUsuario(String pContraseña, String pUsername, String pNombre, String pApellido,
			String pEmail, String pCuentaBancaria) {
		try {
			db.getTransaction().begin();
			RegularUser usuario = db.find(RegularUser.class, pUsername);
			usuario.setFirstName(pNombre);
			usuario.setLastName(pApellido);
			usuario.setEmail(pEmail);
			usuario.setBankAccount(pCuentaBancaria);
			usuario.setUserPass(pContraseña);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public boolean editarPerfilUsuarioSinPass(String pUsername, String pNombre, String pApellido, String pEmail,
			String pCuentaBancaria) {
		try {
			db.getTransaction().begin();
			RegularUser usuario = db.find(RegularUser.class, pUsername);
			usuario.setFirstName(pNombre);
			usuario.setLastName(pApellido);
			usuario.setEmail(pEmail);
			usuario.setBankAccount(pCuentaBancaria);
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}
	}

	public Vector<User> getAllUsers() {

		Vector<User> res = new Vector<User>();
		TypedQuery<User> query = db.createQuery("SELECT us FROM User us", User.class);
		List<User> users = query.getResultList();
		for (User us : users) {
			System.out.println(us.toString());
			res.add(us);
		}
		return res;

	}

	public Integer getMaxIdInDB() {

		Vector<Event> events = getAllEvents();

		Integer maxid = events.get(0).getEventNumber();

		for (Event e : events) {

			if (e.getEventNumber() > maxid) {
				maxid = e.getEventNumber();
			}
		}

		return maxid;
	}

	public Integer getMaxIdForecastInDB() {

		Vector<Forecast> res = new Vector<Forecast>();
		TypedQuery<Forecast> query = db.createQuery("SELECT fo FROM Forecast fo", Forecast.class);
		List<Forecast> forecasts = query.getResultList();

		return forecasts.get(forecasts.size() - 1).getForecastNumber();
	}
	
	public Forecast findForecast(int forecastNumber) {
		Forecast fore = db.find(Forecast.class, forecastNumber);
		return fore;
	}
	/**/
	public User findUser(String name) {
		User u = db.find(User.class, name);
		return u;
	}
	
	public float getBetMin(Question q) {
		return q.getBetMinimum();
	}
	
	public Forecast registrarForecast(String name, Float fee, Question q)  {
		db.getTransaction().begin();
		Forecast fo = new Forecast(name, fee, q);
		db.persist(fo);
		db.getTransaction().commit();
		return fo;
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
					//System.out.println(">> DataAccess: createApuesta=> answer= " + pSelectedForecast + " client= "
						//	+ clientdb.getUserName() + " amount=" + pselectedAmount + "€");
					try {
						db.getTransaction().begin();
						//Forecast fore = insertForecast(pSelectedForecast);
						Forecast fore = db.find(Forecast.class, pSelectedForecast.getForecastNumber());
						//Forecast fore = findForecast(pSelectedForecast.getForecastNumber());
						
						Bet ap = fore.addBet(pSelectedForecast, pselectedClient, pselectedAmount);
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

	public boolean doLogin(String username, String pass) {

		TypedQuery<RegularUser> query1 = db.createQuery("SELECT ru FROM RegularUser ru", RegularUser.class);
		List<RegularUser> regularusers = query1.getResultList();

		for (RegularUser ru : regularusers) {
			if (ru.getUserName().equals(username) && ru.getUserPass().equals(pass)) {
				return true;
			}
		}

		TypedQuery<AdminUser> query2 = db.createQuery("SELECT au FROM AdminUser au", AdminUser.class);
		List<AdminUser> adminusers = query2.getResultList();

		for (AdminUser au : adminusers) {
			if (au.getUserName().equals(username) && au.getUserPass().equals(pass)) {
				return true;
			}
		}

		return false;

	}

	public boolean isAdmin(String pusername, String ppassword) {
		TypedQuery<User> query = db
				.createQuery("SELECT us FROM User us WHERE us.getUserName()=?1 and us.getUserPass()=?2", User.class);
		query.setParameter(1, pusername);
		query.setParameter(2, ppassword);
		List<User> usuarios = query.getResultList();

		if (usuarios instanceof AdminUser) {
			return true;
		} else {
			return false;
		}
	}

	public RegularUser getRegularUserByUsername(String pusername) {
		System.out.println(">> DataAccess: getRegularUserByUsername");

		TypedQuery<RegularUser> query = db.createQuery("SELECT ru FROM RegularUser ru", RegularUser.class);
		List<RegularUser> regularusers = query.getResultList();

		// ArrayList<Cliente> result = new ArrayList<Cliente>();
		for (RegularUser ru : regularusers) {
			if (ru.getUserName().equals(pusername)) {
				return ru;
			}

		}
		return null;

	}

	public AdminUser getAdminUserByUsername(String pusername) {
		System.out.println(">> DataAccess: getAdminUserByUsername");

		TypedQuery<AdminUser> query = db.createQuery("SELECT au FROM AdminUser au", AdminUser.class);
		List<AdminUser> adminusers = query.getResultList();

		// ArrayList<Admin> result = new ArrayList<Admin>();
		for (AdminUser au : adminusers) {
			if (au.getUserName().equals(pusername)) {
				return au;
			}

		}
		return null;

	}

	public boolean closeEvent2(Event e, Question q, Forecast f) {

		try {

			db.getTransaction().begin();
			Event ev = db.find(Event.class, e);
			Question qe = db.find(Question.class, q);
			Forecast fe = db.find(Forecast.class, f);
			qe.setResult(f.getForecast());
			System.out.println(">> DataAccess: closeEvent=> Event:" + ev.getDescription() + " in question: "
					+ qe.getQuestion() + " with result: " + qe.getResult());

			Vector<Bet> bets = new Vector<Bet>(fe.getBets());

		} catch (Exception e2) {
		}
		return false;

	}
	
	public boolean closeEvent(Event e, Question q, Forecast f) {
		try {
			db.getTransaction().begin();
			Event ev = db.find(Event.class, e);
			Question qe = db.find(Question.class, q);
			Forecast fe = db.find(Forecast.class, f);
			qe.setResult(f.getForecast());
			System.out.println(">> DataAccess: closeEvent=> Event:" + ev.getDescription() + " in question: "
					+ qe.getQuestion() + " with result: " + qe.getResult());
			if (ev.getClosed()) {
				return false;
			} else {
				ev.setClosed(true);
			}
			Vector<Bet> bets = new Vector<Bet>(fe.getBets());
			for (Bet be : bets) {
				Bet bet = db.find(Bet.class, be);
				if (bet.getForecast() == fe) {
					if (bet.getForecast().equals(bet.getForecast().getQuestion().getResult())) {
						bet.setEstadoApuesta("Ganada");
					} else {
						bet.setEstadoApuesta("Perdida");
					}
					payUsers(bet);
				}
			}
			db.getTransaction().commit();
			return true;
		} catch (Exception ex) {
			ex.printStackTrace();
			return false;
		}

	}

	private boolean payUsers(Bet b) {

		try {
			Vector<User> usersToPay = new Vector<User>(getAllUsers());
			for (User au : usersToPay) {
				if (au instanceof RegularUser) {
					RegularUser u = (RegularUser) au;
					RegularUser us = db.find(RegularUser.class, u);
					Vector<Bet> userBets = new Vector<Bet>(u.getBets());
					for (Bet be : userBets) {
						Bet bett = db.find(Bet.class, be);
						if (bett.getUser().equals(us)) {
							float sumo = us.getBalance() + (be.getAmount() * b.getForecast().getFee());
							us.setBalance(us.getBalance() + (be.getAmount() * b.getForecast().getFee()));
							System.out.println("le sumo: " + sumo);
							System.out.println("Nuevo saldo: " + us.getBalance());
						}
					}
				}

			}
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean anularApuesta(Bet pApuesta) {

		try {
			db.getTransaction().begin();
			Bet apuesta = db.find(Bet.class, pApuesta);
			RegularUser cliente = db.find(RegularUser.class, pApuesta.getUser());

			Calendar fecha = new java.util.GregorianCalendar();
			int año = fecha.get(Calendar.YEAR);
			int mes = fecha.get(Calendar.MONTH);
			int dia = fecha.get(Calendar.DAY_OF_MONTH);

			if (apuesta.getForecast().getQuestion().getEvent().getEventDate()
					.compareTo(UtilDate.newDate(año, mes, dia)) > 0) { // posterior al argumento (actual)

				apuesta.setEstadoApuesta("Anulada");
				System.out.println("Saldo inicial: " + cliente.getBalance());
				cliente.setBalance(cliente.getBalance() + pApuesta.getAmount());
				System.out.println("Se ha devuelto " + pApuesta.getAmount());
				System.out.println("Saldo actualizado: " + cliente.getBalance());

				db.getTransaction().commit();

				System.out.println("Anulada");
				return true;
			} else {
				System.out.println("No anulada");
				return false;
			}
		} catch (Exception e) {
			return false;
		}

	}

	public Vector<Bet> getApuestasAbiertas(RegularUser pUser) {

		db.getTransaction().begin();
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.estadoApuesta=?1 AND b.user=?2", Bet.class);
		query.setParameter(1, "Pendiente");
		query.setParameter(2, pUser);
		Vector<Bet> ArrayListApuestas = new Vector<Bet>(query.getResultList());
		db.getTransaction().commit();
		return ArrayListApuestas;

	}

	public Vector<Bet> getApuestasCerradas(RegularUser pUser) {

		db.getTransaction().begin();
		TypedQuery<Bet> query = db.createQuery(
				"SELECT b FROM Bet b WHERE b.estadoApuesta=?1 OR b.estadoApuesta=?2 AND b.user=?2", Bet.class);
		query.setParameter(1, "Ganada");
		query.setParameter(2, "Perdida");
		query.setParameter(3, pUser);
		Vector<Bet> ArrayListApuestas = new Vector<Bet>(query.getResultList());
		db.getTransaction().commit();
		return ArrayListApuestas;

	}

	public Vector<Bet> getApuestasGanadas(RegularUser pUser) {

		db.getTransaction().begin();
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.estadoApuesta=?1 AND b.user=?2", Bet.class);
		query.setParameter(1, "Ganada");
		query.setParameter(2, pUser);
		Vector<Bet> ArrayListApuestas = new Vector<Bet>(query.getResultList());
		db.getTransaction().commit();
		return ArrayListApuestas;

	}

	public Vector<Bet> getApuestasPerdidas(RegularUser pUser) {

		db.getTransaction().begin();
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.estadoApuesta=?1 AND b.user=?2", Bet.class);
		query.setParameter(1, "Perdida");
		query.setParameter(2, pUser);
		Vector<Bet> ArrayListApuestas = new Vector<Bet>(query.getResultList());
		db.getTransaction().commit();
		return ArrayListApuestas;

	}

	public Vector<Bet> getApuestasAnuladas(RegularUser pUser) {

		db.getTransaction().begin();
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.estadoApuesta=?1 AND b.user=?2", Bet.class);
		query.setParameter(1, "Anulada");
		query.setParameter(2, pUser);
		Vector<Bet> ArrayListApuestas = new Vector<Bet>(query.getResultList());
		db.getTransaction().commit();
		return ArrayListApuestas;

	}

	public Vector<Bet> getApuestasByUser(RegularUser user) {

		db.getTransaction().begin();
		TypedQuery<Bet> query = db.createQuery("SELECT b FROM Bet b WHERE b.user=?1", Bet.class);
		query.setParameter(1, user);
		Vector<Bet> ArrayListApuestas = new Vector<Bet>(query.getResultList());
		db.getTransaction().commit();
		return ArrayListApuestas;

	}

	public boolean aplicarBonoBienvenida(String user) {

		try {
			db.getTransaction().begin();
			RegularUser usuario = db.find(RegularUser.class, user);
			usuario.setBalance(5f);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean recargarSaldo(String user, Float importe) {

		try {
			db.getTransaction().begin();
			RegularUser usuario = db.find(RegularUser.class, user);
			usuario.setBalance(usuario.getBalance() + importe);
			db.getTransaction().commit();
			return true;
		} catch (Exception e) {
			return false;
		}

	}

	public boolean definirResultados(Event pselectedEvent, Question pselectedQuestion, Forecast pselectedForecast) {

		db.getTransaction().begin();
		Forecast winnerf = db.find(Forecast.class, pselectedForecast.getForecastNumber());
		Question winnerq = db.find(Question.class, pselectedQuestion);
		winnerq.setResult(pselectedForecast.getForecast());
		winnerf.setWinnerf(true);
		db.getTransaction().commit();

		// TODAS LAS RESPUESTAS POSIBLES DE ESA PREGUNTA
		TypedQuery<Forecast> query0 = db.createQuery("SELECT fo FROM Forecast fo WHERE fo.question=?1", Forecast.class);
		query0.setParameter(1, winnerq);
		db.getTransaction().begin();
		ArrayList<Forecast> ArrayListRespuestas = new ArrayList<Forecast>(query0.getResultList());
		db.getTransaction().commit();

		try {

			db.getTransaction().begin();
			for (Forecast ans : ArrayListRespuestas) { // por cada forecast(prediccion/respuesta) mira todas las
														// apuestas
				TypedQuery<Bet> query1 = db.createQuery("SELECT be FROM Bet be WHERE be.forecast=?1", Bet.class);
				query1.setParameter(1, ans);
				ArrayList<Bet> ArrayListApuestas = new ArrayList<Bet>(query1.getResultList());
				if (ArrayListApuestas.isEmpty()) { // chekamos si tiene o no apuestas
					System.out.println("No bets for this answer.");
				} else {
					for (Bet bet : ArrayListApuestas) { // por cada acuesta de ese forecast

						if (bet.getEstadoApuesta().equals("Anulada") == false) {

							if (bet.getForecast().getForecastNumber() == pselectedForecast.getForecastNumber()) {
								bet.setEstadoApuesta("Ganada");// SET ACIERTO

								User cliente = bet.getUser();
								RegularUser ru = (RegularUser) cliente;
								float saldoCliente = ru.getBalance();
								float total = saldoCliente + bet.getAmount() * pselectedForecast.getFee();

								System.out.println("\nAcredita al cliente " + ru.getUserName() + " un total de "
										+ bet.getAmount() * pselectedForecast.getFee() + "â‚¬ (" + bet.getAmount()
										+ "â‚¬ x " + pselectedForecast.getFee() + ")");

								ru.setBalance(total);

							} else {

								bet.setEstadoApuesta("Perdida");// SET FALLO
							}

						}
					}
				}
			}

			winnerq.hasResult(true);
			System.out.println(
					"\n// Apuestas sobre pregunta '" + winnerq.getQuestion() + "' resueltas.\nPregunta cerrada. //");
			db.getTransaction().commit();

		} catch (Exception e) {
			JOptionPane.showMessageDialog(null, "DataAccess >> Asignar Resultados y Pagos >> Catch: " + e.getMessage()); // FIX-ME!
																															// Comentar
																															// la
																															// lÃ­nea
			return false;
		}

		// CIERRE DE EVENTO
		try {

			// SI NO HAY PREGUNTAS ABIERTAS, CIERRA EVENTO
			Event ev = db.find(Event.class, pselectedEvent);

			db.getTransaction().begin();
			TypedQuery<Question> query = db.createQuery("SELECT q FROM Question q WHERE q.event=?1 AND q.hasResult=?2",
					Question.class);
			query.setParameter(1, ev);
			query.setParameter(2, false);

			ArrayList<Question> ArrayListQuestions = new ArrayList<Question>(query.getResultList());

			if (ArrayListQuestions.isEmpty()) {
				ev.setClosed(true);
				// JOptionPane.showMessageDialog(null, "ebento serrado");
			}
			// db.getTransaction().commit();
			db.getTransaction().commit();

			return true;

		} catch (Exception e) {
			e.printStackTrace();
			JOptionPane.showMessageDialog(null, "DataAccess >> Cerrar evento >> Catch");
			return false;
		}
	}

	public Vector<Question> getOpenedQuestions(Event ev) {
		db.getTransaction().begin();
		Event ev1 = db.find(Event.class, ev);
		TypedQuery<Question> query = db.createQuery("SELECT q from Question q WHERE q.hasResult=?1 AND q.event=?2",
				Question.class);
		query.setParameter(1, false);
		query.setParameter(2, ev1);
		ArrayList<Question> ArrayListQuestions = new ArrayList<Question>(query.getResultList());
		Vector<Question> queries = new Vector<Question>();
		for (int f = 0; f < ArrayListQuestions.size(); f++) {
			queries.add(ArrayListQuestions.get(f));
			System.out.println(ArrayListQuestions.get(f));
		}
		db.getTransaction().commit();
		return queries;

	}

	public boolean getEstadoEvento(Event ev) {

		db.getTransaction().begin();
		Event ev1 = db.find(Event.class, ev);
		boolean result = ev1.getClosed();
		db.getTransaction().commit();

		return result;

	}

	public Vector<Event> getEventosMedioCerrados() {

		db.getTransaction().begin();
		TypedQuery<Event> query = db.createQuery("SELECT e from Event e", Event.class);
		boolean controlAbierta = false;
		boolean controlCerrada = false;

		Vector<Event> vectorEventos = new Vector<Event>(query.getResultList());
		Vector<Event> resultado = new Vector<Event>();
		for (Event e : vectorEventos) {
			Vector<Question> preguntas = e.getQuestions();
			for (Question q : preguntas) {
				if (q.hasResult()) { // si la pregunta esta cerrada

					controlCerrada = true;
				} else if (q.hasResult() == false) { // si la pregunta esta abierta
					controlAbierta = true;
				}
			}

			if (controlAbierta && controlCerrada && (e.getClosed() == false)) {
				resultado.add(e);
			}
			controlAbierta = false;
			controlCerrada = false;
		}
		db.getTransaction().commit();

		return resultado;

	}

}
