package test;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.Mockito;

import businessLogic.BLFacade;
import businessLogic.BLFacadeImplementation;
import dataAccess.DataAccess;
import domain.Event;

class DeleteEventMockTest {

	DataAccess dataAccess = Mockito.mock(DataAccess.class);
	Event mockedEvent = Mockito.mock(Event.class);

	BLFacade sut = new BLFacadeImplementation(dataAccess);

	@Test
	void test1() {
		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(true).when(dataAccess).deleteEvent(Mockito.any(Event.class));

			// invoke System Under Test (sut)
			Event ev1 = new Event("partido1", oneDate);
			assertTrue(sut.deleteEvent(ev1));
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}
	}

//	@Test
//	void test2() {
//		try {
//			// define paramaters
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Date oneDate = sdf.parse("05/10/2022");
//
//			// configure Mock
//			Mockito.doReturn(false).when(dataAccess).deleteEvent(Mockito.any(Event.class));
//
//			// invoke System Under Test (sut)
//			Event ev1 = new Event("partido1", UtilDate.newDate(2022, 12, 22));
//			assertFalse(sut.deleteEvent(ev1));
//		} catch (ParseException e) {
//			fail("It should be correct: check the date format");
//		}
//	}
//
//
	@Test
	void test4() {
		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			Event ev1 = new Event("partido1", oneDate);
			ev1.addQuestion("¿sí?", 10f);
			ev1.addQuestion("¿no?", 5f);

			// configure Mock
			Mockito.doReturn(true).when(dataAccess).deleteEvent(Mockito.any(Event.class));

			// invoke System Under Test (sut)
			assertTrue(sut.deleteEvent(ev1));
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}
	}

	@Test
	@DisplayName("Se intenta eliminar un evento que no pertenece a la BD")
	void test5() {

		try {
			// define paramaters
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");

			// configure Mock
			Mockito.doReturn(false).when(dataAccess).deleteEvent(Mockito.any(Event.class));

			// invoke System Under Test (sut)
			Event ev1 = new Event("yes", oneDate);

			assertFalse(sut.deleteEvent(ev1));
		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}

	}

	@Test
	@DisplayName("Se intenta eliminar un evento cuyo identificador es null")
	void test6() {

		// configure Mock
		Mockito.doReturn(false).when(dataAccess).deleteEvent(Mockito.any(Event.class));

		// invoke System Under Test (sut)
		Event ev1 = null;
		assertFalse(sut.deleteEvent(ev1));
	}

}
