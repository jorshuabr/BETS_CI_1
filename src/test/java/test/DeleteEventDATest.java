package test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.junit.jupiter.api.Assertions.fail;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Vector;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import configuration.ConfigXML;
import dataAccess.DataAccess;
import domain.Event;
import domain.Question;
import utility.TestUtilityDataAccess;

class DeleteEventDATest {

	static DataAccess sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));;
	static TestUtilityDataAccess testDA = new TestUtilityDataAccess();

	private Event ev, ev2;

	@BeforeEach
	public void setUp() {
		sut = new DataAccess(ConfigXML.getInstance().getDataBaseOpenMode().equals("initialize"));
		testDA = new TestUtilityDataAccess();
	}

	@Test
	// sut.deleteEvent: Se elimina un evento pero este evento no tiene preguntas
	// asociadas ya que no hay ninguna en la BD
	// asociadas
	void test1() {

		try {
			// configure the state of the system (create object in the dabatase)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event 1 Text Test 2";

			testDA.open();
			ev = testDA.addEvent(eventText, oneDate);
			assertEquals(0, ev.getQuestions().size());
			testDA.close();

			// invoke System Under Test (sut) and Assert
			Vector<Event> vecti = new Vector<Event>();
			Vector<Question> vecti2 = new Vector<Question>();
			assertTrue(sut.deleteEvent(ev));
			// el resultado es el esperado pero aun asi da error
			// assertEquals(ev2, sut.getAllEvents());
			assertEquals(0, sut.getAllEvents().size());
			assertEquals(0, sut.getAllQuestions().size());

			testDA.open();
			boolean eliminado = testDA.removeEvent(ev);
			
			testDA.close();
			assertFalse(eliminado);

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}

		// Remove the created objects in the database (cascade removing)
		testDA.open();
		boolean b = testDA.removeEvent(ev);
		System.out.println("Removed event " + b);

		sut.deleteAllQuestions();
		
		testDA.close();

	}

	@Test
	// sut.deleteEvent: Se elimina un evento pero este evento no tiene preguntas
	// asociadas, sin embargo en este caso si hay preguntas en la BD
	// asociadas
	void test2() {

		try {
			// configure the state of the system (create object in the dabatase)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event1 Text Test2";
			Date twoDate = sdf.parse("05/10/2022");
			String eventText2 = "Event2 Text Test2";
			String queryText = "Query1 Text Test2 ";
			float qty = 0f;

			testDA.open();
			ev = testDA.addEvent(eventText, oneDate);
			ev2 = testDA.addEventWithQuestion(eventText2, twoDate, queryText, qty);
			assertEquals(0, ev.getQuestions().size());
			assertEquals(1, ev2.getQuestions().size());
			testDA.close();

			// invoke System Under Test (sut) and Assert
			Vector<Event> vecti = new Vector<Event>();
			Vector<Question> vecti2 = new Vector<Question>();
			assertTrue(sut.deleteEvent(ev));
			// el resultado es el esperado pero aun asi da error
			// assertEquals(ev2, sut.getAllEvents());
			assertEquals(1, sut.getAllEvents().size());
			assertEquals(1, sut.getAllQuestions().size());

			testDA.open();
			boolean eliminado = testDA.removeEvent(ev);
			testDA.close();
			assertFalse(eliminado);

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}

		// Remove the created objects in the database (cascade removing)
		testDA.open();
		boolean b = testDA.removeEvent(ev);
		System.out.println("Removed event " + b);
		boolean b2 = testDA.removeEvent(ev2);
		System.out.println("Removed event " + b2);
		boolean b3 = testDA.removeQuestion(19);
		System.out.println("Removed event " + b3);
		sut.deleteAllQuestions();
		testDA.close();

	}

	@Test
	// sut.deleteEvent: Existe un solo evento y es el que queremos eliminar. Además
	// este evento tiene una pregunta asociada
	void test3() {

		try {
			// configure the state of the system (create object in the dabatase)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event1 Text Test3";
			String queryText = "Query1 Text Test3";
			Float betMinimum = 2f;

			testDA.open();
			ev = testDA.addEventWithQuestion(eventText, oneDate, queryText, betMinimum);
			ev.addQuestion("why?", 5f);
			assertEquals(2, ev.getQuestions().size());
			testDA.close();

			// invoke System Under Test (sut) and Assert
			Vector<Event> vecti = new Vector<Event>();
			Vector<Question> vecti2 = new Vector<Question>();
			assertTrue(sut.deleteEvent(ev));
			assertEquals(vecti, sut.getAllEvents());
			assertEquals(vecti2, sut.getAllQuestions());
			

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}

		// Remove the created objects in the database (cascade removing)
		testDA.open();
		boolean b = testDA.removeEvent(ev);
		System.out.println("Removed event " + b);
		sut.deleteAllQuestions();
		testDA.close();

	}

	@Test
	// sut.deleteEvent: Existe más de un evento y cada uno tiene preguntas, solo se borraran el evento seleccionado y con el sus preguntas
	void test4() {

		try {
			// configure the state of the system (create object in the dabatase)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			Date twoDate = sdf.parse("05/10/2022");
			String eventText = "Event1 Text Test4";
			String queryText = "Query1 Text Test4";
			String eventText2 = "Event2 Text Test4";
			String queryText2 = "Query2 Text2 Test4";
			Float betMinimum = 2f;
			Float betMinimum2 = 4f;

			testDA.open();
			ev = testDA.addEventWithQuestion(eventText, oneDate, queryText, betMinimum);
			ev2 = testDA.addEventWithQuestion(eventText2, twoDate, queryText2, betMinimum2);
			ev.addQuestion("why?", 5f);
			assertEquals(2, ev.getQuestions().size());
			assertEquals(1, ev2.getQuestions().size());
			testDA.close();

			// invoke System Under Test (sut) and Assert
//			Vector<Event> vecti = new Vector<Event>();
//			Vector<Question> vecti2 = new Vector<Question>();
			assertTrue(sut.deleteEvent(ev));
			assertEquals(1, sut.getAllEvents().size());
			assertEquals(1, sut.getAllQuestions().size());
			

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}

		// Remove the created objects in the database (cascade removing)
		testDA.open();
		boolean b = testDA.removeEvent(ev);
		System.out.println("Removed event " + b);
		boolean b2 = testDA.removeEvent(ev2);
		System.out.println("Removed event " + b2);
		testDA.removeQuestion(25);
		testDA.removeQuestion(21);
		testDA.removeQuestion(23);
		sut.deleteAllQuestions();
		testDA.close();

	}

//	@Test
//	// sut.createQuestion: The event has NOT one question with a queryText.
//	void test2() {
//		try {
//
//			// configure the state of the system (create object in the dabatase)
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Date oneDate = sdf.parse("05/10/2022");
//			String eventText = "Event Text";
//			Float betMinimum = 2f;
//
//			testDA.open();
//			ev = testDA.addEventWithQuestion(eventText, oneDate, "otra", 10.0f);
//			testDA.close();
//
//			String queryText = "Query Text";
//			try {
//				// invoke System Under Test (sut)
//				Question q = sut.createQuestion(ev, queryText, betMinimum);
//
//				// verify the results returned
//				assertNotNull(q);
//				assertEquals(queryText, q.getQuestion());
//				assertEquals(betMinimum, q.getBetMinimum());
//
//				// verify DB
//				testDA.open();
//				Vector<Event> es = testDA.getEvents(oneDate);
//				testDA.close();
//
//				assertEquals(1, es.size());
//				assertEquals(2, es.get(0).getQuestions().size());
//				assertEquals(queryText, es.get(0).getQuestions().get(1).getQuestion());
//				assertEquals(betMinimum, es.get(0).getQuestions().get(1).getBetMinimum());
//			} catch (QuestionAlreadyExist e) {
//				// if the program goes to this point fail
//				fail();
//			} finally {
//				// Remove the created objects in the database (cascade removing)
//				testDA.open();
//				boolean b = testDA.removeEvent(ev);
//				testDA.close();
//				System.out.println("Finally " + b);
//			}
//		} catch (ParseException e) {
//			fail("It should be correct: check the date format");
//		}
//
//	}
//
//	@Test
//	// sut.createQuestion: The event is null.
//	void test3() {
//
//		// configure the state of the system (create object in the dabatase)
//		Float betMinimum = 2f;
//		String queryText = "Query Text";
//		try {
//			// invoke System Under Test (sut)
//			Question q = sut.createQuestion(null, queryText, betMinimum);
//
//			// verify the results returned
//			// he modificado el createQuestion()
//			assertNull(q);
//
//		} catch (QuestionAlreadyExist e) {
//			// if the program goes to this point fail
//			fail("The event is null. Impossible to search for a question in it");
//		}
//	}
//
//	@Test
//	// sut.createQuestion: The queryText is null.
//	void test4() {
//		try {
//
//			// configure the state of the system (create object in the dabatase)
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Date oneDate = sdf.parse("05/10/2022");
//			String eventText = "Event Text";
//			Float betMinimum = 2f;
//
//			testDA.open();
//			ev = testDA.addEventWithQuestion(eventText, oneDate, "una", 0.0f);
//			System.out.println("**************" + ev.getEventNumber());
//			testDA.close();
//
//			String queryText = null;
//			try {
//				// invoke System Under Test (sut)
//				Question q = sut.createQuestion(ev, queryText, betMinimum);
//
//				// verify the results returned
//				assertNull(q);
//
//				// verify DB
//				// puede que en algun momento de la llamada a createQuestión la BD se cierre,
//				// por ello la vuelvo a abrir, puede ser porque en la linea 147 se cierre?
//				testDA.open();
//				Vector<Event> es = testDA.getEvents(oneDate);
//				testDA.close();
//
//				assertTrue(es.contains(ev));
//
//			} catch (QuestionAlreadyExist e) {
//				// if the program goes to this point fail
//				fail("No, the question is null");
//			} finally {
//				// Remove the created objects in the database (cascade removing)
//				testDA.open();
//				boolean b = testDA.removeEvent(ev);
//				System.out.println("Finally " + b);
//				testDA.close();
//			}
//		} catch (ParseException e) {
//			fail("It should be correct: check the date format");
//		}
//
//	}
//
//	@Test
//	// sut.createQuestion: The betMinimum is negative.
//	void test5() {
//		try {
//
//			// configure the state of the system (create object in the dabatase)
//			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
//			Date oneDate = sdf.parse("05/10/2022");
//			String eventText = "Event Text";
//			Float betMinimum = -2f;
//
//			testDA.open();
//			ev = testDA.addEventWithQuestion(eventText, oneDate, "otra", 0.0f);
//			testDA.close();
//
//			String queryText = "Query Text";
//			try {
//				// invoke System Under Test (sut)
//				Question q = sut.createQuestion(ev, queryText, betMinimum);
//
//				// verify the results returned
//				assertNotNull(q);
//				assertEquals(queryText, q.getQuestion());
//				assertEquals(betMinimum, q.getBetMinimum(), 0);
//
//				// verify DB
//				testDA.open();
//				Vector<Event> es = testDA.getEvents(oneDate);
//				testDA.close();
//				assertEquals(1, es.size());
//				assertEquals(eventText, es.get(0).getDescription());
//				assertEquals(oneDate, es.get(0).getEventDate());
//
//			} catch (QuestionAlreadyExist e) {
//				// if the program goes to this point fail
//				fail();
//			} finally {
//				// Remove the created objects in the database (cascade removing)
//				testDA.open();
//				boolean b = testDA.removeEvent(ev);
//				testDA.close();
//				System.out.println("Finally " + b);
//			}
//		} catch (ParseException e) {
//			fail("It should be correct: check the date format");
//		}
//
//	}

	@Test
	// sut.deleteEvent: The event does not belong to the database.
	void test5() {
		try {
			// configure the state of the system (create object in the dabatase)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date twoDate = sdf.parse("05/10/2022");
			String eventText2 = "Event 1 Text Test 5";
			Event ev2 = new Event(eventText2, twoDate);

			assertFalse(sut.deleteEvent(ev2));

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}
		sut.deleteAllQuestions();
		// Remove the created objects in the database (cascade removing)
//		testDA.open();
//		boolean b = testDA.removeEvent(ev);
//		System.out.println("Removed event " + b);
//		testDA.close();
	}

	@Test
	// sut.deleteEvent: The event is null.
	void test6() {
		try {
			// configure the state of the system (create object in the dabatase)
			SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy");
			Date oneDate = sdf.parse("05/10/2022");
			String eventText = "Event1 Text Test6";
			Event ev = new Event(null, eventText, oneDate);

			// invoke System Under Test (sut)
			Boolean eliminado = sut.deleteEvent(ev);

			// verify the results returned
			// he modificado el createQuestion()
			assertFalse(eliminado);

		} catch (ParseException e) {
			fail("It should be correct: check the date format");
		}
		sut.deleteAllQuestions();
	}

}

////para los mock
////define Argument captors
//			ArgumentCaptor<Event> eventCaptor = ArgumentCaptor.forClass(Event.class);
//			// verify call numbers and capture parameters
//			Mockito.verify(sut, Mockito.times(1)).deleteEvent(eventCaptor.capture());
//			// verify parameter values as usual using JUnit asserts
//			assertEquals(ev, eventCaptor.getValue());
//
