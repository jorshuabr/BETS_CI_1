package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import exceptions.UserAlreadyExistException;
import utility.TestUtilityDataAccess;

class CreateApuestaDATest {
	
	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();
	RegularUser user = new RegularUser("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);

	/**/
	@Test
	@DisplayName("Test3: ValorApuesta Negativo")
	public void test1(){
		float apuesta = -10f;
		int expected = 0;
		Forecast fo = new Forecast();
		int actual = sut.createApuesta(fo, user, apuesta);
		//int expected = testDA.comprobarCreateApuesta_test3(fo, user, apuesta);
		
		if (apuesta < 0) {
			expected = 4;
		}
		assertEquals(actual, expected);
		
	}
	
	@Test
	@DisplayName("Test4: ValorApuesta menor a valorApuestaMinima")
	public void test2() {
		Question q = new Question();
		q.setBetMinimum(10); //apuesta minima 10
		Forecast f = new Forecast("nameForecaste", 10, q);
		float apuesta = 3f;
		int res= 0;
		int actual = sut.createApuesta(f, user, apuesta);
		//int expected = testDA.comprobarCreateApuesta_test4(f, user, apuesta);
		
		if (apuesta < f.getQuestion().getBetMinimum()) {
			res = 3;
		}
		
		assertEquals(res, actual);
	}
	
	@Test
	@DisplayName("Test5: ValorApuesta mayor a SaldoUsuario")
	public void test3() throws UserAlreadyExistException {
		Question q = new Question();
		q.setBetMinimum(10); //apuesta minima 10
		Forecast f = new Forecast("nameForecaste", 10, q);
		sut.deleteUser("name");
		sut.registrar("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
		
		int actual = sut.createApuesta(f, user, 25f);
		int expected = 2;
		assertEquals(expected, actual);
		sut.deleteUser("name");
	}
	
	/**/
	@Test
	@DisplayName("Test9: Apuesta Creada")
	public void test4() throws UserAlreadyExistException {
		
		Question q = new Question();
		q.setBetMinimum(10);
		Forecast f = new Forecast("nombreForecast", (float)12, q);
		
		int actual = sut.createApuesta(f, user, 15f);
		int expected = 1;
		assertEquals(expected, actual);
	}
	
	
	@Test
	@DisplayName("Test10: Fallo en creación de apuesta, Forecast null")
	public void test5() {
		Question qu = new Question();
		qu.setBetMinimum(2); 
		
		assertThrows(Exception.class,() -> sut.createApuesta(null, user, 12f));
	}

	
}
