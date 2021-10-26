package test;

import static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mockito;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;

class CreateApuestaDAMockTest {
	
	static DataAccess da = Mockito.mock(DataAccess.class);
	BLFacadeImplementation sut = new BLFacadeImplementation(da);
	Forecast fo = Mockito.mock(Forecast.class);
	RegularUser user = new RegularUser("name", "pass", "fname", "lname", "31/01", "j@j.com", "1212", 684123123, "k.2.3", 20);
	
	@Test
	@DisplayName("Test3: ValorApuesta Negativo")
	public void test1(){
		int valor = 4;
		Mockito.doReturn(valor).when(da).createApuesta(Mockito.any(Forecast.class), 
				Mockito.any(RegularUser.class), Mockito.anyFloat());
		
		int actual = sut.createApuesta(fo, user, -10f);
		
		int expected = 4;
		assertEquals(expected, actual);
		
		ArgumentCaptor<Forecast> foreCaptor = ArgumentCaptor.forClass(Forecast.class);
		ArgumentCaptor<RegularUser> userCaptor = ArgumentCaptor.forClass(RegularUser.class);
		ArgumentCaptor<Float> floatCaptor = ArgumentCaptor.forClass(Float.class);
		
		Mockito.verify(da,
				Mockito.times(1)).createApuesta(foreCaptor.capture(), userCaptor.capture(), floatCaptor.capture()*-1f);
	}

	
	@Test
	@DisplayName("Test4: ValorApuesta menor a valorApuestaMinima")
	public void test2() { //devuelve 3
		
		int valor = 3;
		Mockito.doReturn(valor).when(da).createApuesta(Mockito.any(Forecast.class), Mockito.any(RegularUser.class), Mockito.any(Float.class));
		Question q = new Question();
		q.setBetMinimum(10);
		fo.setQuestion(q);
		int actual = sut.createApuesta(fo, user, 3f);
		
		int expected = 3;
		assertEquals(expected, actual);
		
		ArgumentCaptor<Forecast> foreCaptor = ArgumentCaptor.forClass(Forecast.class);
		ArgumentCaptor<RegularUser> userCaptor = ArgumentCaptor.forClass(RegularUser.class);
		ArgumentCaptor<Float> floatCaptor = ArgumentCaptor.forClass(Float.class);
		
		Mockito.verify(da,
				Mockito.times(2)).createApuesta(foreCaptor.capture(), userCaptor.capture(), floatCaptor.capture());	
	}

	@Test
	@DisplayName("Test5: ValorApuesta mayor a SaldoUsuario")
	public void test3() {
		int valor = 2;
		Mockito.doReturn(valor).when(da).createApuesta(Mockito.any(Forecast.class), Mockito.any(RegularUser.class), Mockito.any(Float.class));
		Question q = new Question();
		q.setBetMinimum(10);
		fo.setQuestion(q);
		
		int actual = sut.createApuesta(fo, user, 25f);
		
		int expected = 2;
		assertEquals(expected, actual);
		
		ArgumentCaptor<Forecast> foreCaptor = ArgumentCaptor.forClass(Forecast.class);
		ArgumentCaptor<RegularUser> userCaptor = ArgumentCaptor.forClass(RegularUser.class);
		ArgumentCaptor<Float> floatCaptor = ArgumentCaptor.forClass(Float.class);
		
		Mockito.verify(da,
				Mockito.times(3)).createApuesta(foreCaptor.capture(), userCaptor.capture(), floatCaptor.capture());
	}
	
	@Test
	@DisplayName("Test9: Apuesta Creada")
	public void test4() {
		int valor = 0;
		Mockito.doReturn(valor).when(da).createApuesta(Mockito.any(Forecast.class), Mockito.any(RegularUser.class), Mockito.any(Float.class));
		
		int actual = sut.createApuesta(fo, user, 15f);
		
		assertEquals(0, actual);
		
		ArgumentCaptor<Forecast> foreCaptor = ArgumentCaptor.forClass(Forecast.class);
		ArgumentCaptor<RegularUser> userCaptor = ArgumentCaptor.forClass(RegularUser.class);
		ArgumentCaptor<Float> floatCaptor = ArgumentCaptor.forClass(Float.class);
		
		Mockito.verify(da,
				Mockito.times(4)).createApuesta(foreCaptor.capture(), userCaptor.capture(), floatCaptor.capture());

	}

	@Test
	@DisplayName("Test10: Fallo en creación de apuesta")
	public void test5() {
		int valor = 1;
		Mockito.doReturn(valor).when(da).createApuesta(Mockito.any(Forecast.class), Mockito.any(RegularUser.class), Mockito.any(Float.class));
		ArgumentCaptor<Forecast> foreCaptor = ArgumentCaptor.forClass(Forecast.class);
		ArgumentCaptor<RegularUser> userCaptor = ArgumentCaptor.forClass(RegularUser.class);
		ArgumentCaptor<Float> floatCaptor = ArgumentCaptor.forClass(Float.class);
		
		
		int actual = sut.createApuesta(fo, user, 15f);
		assertEquals(1, actual);
		
		Mockito.verify(da,
				Mockito.times(5)).createApuesta(foreCaptor.capture(), userCaptor.capture(), floatCaptor.capture());
	}

}
