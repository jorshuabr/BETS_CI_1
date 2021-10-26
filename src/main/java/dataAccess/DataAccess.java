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

//			Event ev1 = new Event(1, "Atlético-Athletic", UtilDate.newDate(year, month, 17));
//			Event ev2 = new Event(2, "Eibar-Barcelona", UtilDate.newDate(year, month, 17));
//			Event ev3 = new Event(3, "Getafe-Celta", UtilDate.newDate(year, month, 17));
//			Event ev4 = new Event(4, "Alavés-Deportivo", UtilDate.newDate(year, month, 17));
//			Event ev5 = new Event(5, "Español-Villareal", UtilDate.newDate(year, month, 17));
//			Event ev6 = new Event(6, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 17));
//			Event ev7 = new Event(7, "Malaga-Valencia", UtilDate.newDate(year, month, 17));
//			Event ev8 = new Event(8, "Girona-Leganés", UtilDate.newDate(year, month, 17));
//			Event ev9 = new Event(9, "Real Sociedad-Levante", UtilDate.newDate(year, month, 17));
//			Event ev10 = new Event(10, "Betis-Real Madrid", UtilDate.newDate(year, month, 17));
//
//			Event ev11 = new Event(11, "Atletico-Athletic", UtilDate.newDate(year, month, 1));
//			Event ev12 = new Event(12, "Eibar-Barcelona", UtilDate.newDate(year, month, 1));
//			Event ev13 = new Event(13, "Getafe-Celta", UtilDate.newDate(year, month, 1));
//			Event ev14 = new Event(14, "Alavés-Deportivo", UtilDate.newDate(year, month, 1));
//			Event ev15 = new Event(15, "Español-Villareal", UtilDate.newDate(year, month, 1));
//			Event ev16 = new Event(16, "Las Palmas-Sevilla", UtilDate.newDate(year, month, 1));
//
//			Event ev17 = new Event(17, "Málaga-Valencia", UtilDate.newDate(year, month + 1, 28));
//			Event ev18 = new Event(18, "Girona-Leganés", UtilDate.newDate(year, month + 1, 28));
//			Event ev19 = new Event(19, "Real Sociedad-Levante", UtilDate.newDate(year, month + 1, 28));
//			Event ev20 = new Event(20, "Betis-Real Madrid", UtilDate.newDate(year, month + 1, 28));
//
//			Event ev21 = new Event(21, "Universidad-Mauri", UtilDate.newDate(year, month, 22));
//			Event ev22 = new Event(22, "Universidad-Bryan", UtilDate.newDate(year, month, 20));
//			Event ev23 = new Event(23, "Universidad-Melisa", UtilDate.newDate(year, month, 21));
//			Event ev24 = new Event(24, "Universidad-Jorshua", UtilDate.newDate(year, month, 23));
//			Event ev25 = new Event(25, "Ermua - Eibar", UtilDate.newDate(year, month, 24));
//			Event ev26 = new Event(26, "Ermua - Donostia", UtilDate.newDate(year, month, 25));
//			Event ev27 = new Event(27, "Grandes - Pequeñas", UtilDate.newDate(year, month, 26));
//			Event ev28 = new Event(28, "Baloncesto - Basketball", UtilDate.newDate(year, month, 27));
//			Event ev29 = new Event(29, "Cristiano - Messi", UtilDate.newDate(year, month, 20));
//			Event ev30 = new Event(30, "Vinicius - Messi", UtilDate.newDate(year, month, 20));
//			Event ev31 = new Event(31, "Yo - Tu", UtilDate.newDate(year, month, 21));
//			Event ev32 = new Event(32, "La perra - La guarra", UtilDate.newDate(year, month, 21));
//			Event ev33 = new Event(33, "Madrid - Barcelona", UtilDate.newDate(year, month, 22));
//			Event ev34 = new Event(34, "Cibeles - Canaletas", UtilDate.newDate(year, month, 22));
//			Event ev35 = new Event(35, "Playa - Montaña", UtilDate.newDate(year, month, 23));
//			Event ev36 = new Event(36, "Betis - Sevilla", UtilDate.newDate(year, month, 23));
//			Event ev37 = new Event(37, "Eibar - Alaves", UtilDate.newDate(year, month, 22));
//			Event ev38 = new Event(38, "Manchester United - Manchester City", UtilDate.newDate(year, month, 21));
//			Event ev39 = new Event(39, "Fifa - Uefa", UtilDate.newDate(year, month, 20));
//			Event ev40 = new Event(40, "Juventus - Napoli", UtilDate.newDate(year, month, 23));
//			Event ev41 = new Event(41, "Psg - Manchester City", UtilDate.newDate(year, month, 20));
//			Event ev42 = new Event(42, "Leganes - Espanyol", UtilDate.newDate(year, month, 23));
//			Event ev43 = new Event(43, "Liverpool - Everton", UtilDate.newDate(year, month, 21));
//			Event ev44 = new Event(44, "Bayern Munich - Schalke", UtilDate.newDate(year, month, 22));
//			Event ev45 = new Event(45, "Bayer Leverkusen - Friburgo", UtilDate.newDate(year, month, 20));
//			Event ev46 = new Event(46, "Castellon - Lugo", UtilDate.newDate(year, month, 21));
//			Event ev47 = new Event(47, "Albacete - Real Madrid", UtilDate.newDate(year, month, 22));
//
//			Question q7;
//			Question q8;
//			Question q9;
//			Question q10;
//			Question q11;
//			Question q12;
//			Question q13;
//			Question q14;
//			Question q15;
//			Question q16;
//			Question q17;
//			Question q18;
//			Question q19;
//			Question q20;
//			Question q21;
//			Question q22;
//			Question q23;
//			Question q24;
//			Question q25;
//			Question q26;
//			Question q27;
//			Question q28;
//			Question q29;
//			Question q30;
//			Question q31;
//			Question q32;
//			Question q33;
//			Question q34;
//			Question q35;
//			Question q36;
//			Question q37;
//			Question q38;
//			Question q39;
//			Question q40;
//			Question q41;
//			Question q42;
//			Question q43;
//			Question q44;
//			Question q45;
//			Question q46;
//			Question q47;
//			Question q48;
//			Question q49;
//			Question q50;
//			Question q51;
//			Question q52;
//			Question q53;
//			Question q54;
//			Question q55;
//			Question q56;
//			Question q57;
//			Question q58;
//			Question q59;
//			Question q60;
//			Question q61;
//			Question q62;
//			Question q63;
//			Question q64;
//			Question q65;
//			Question q66;
//			Question q67;
//			Question q68;
//			Question q69;
//			Question q70;
//
//			q7 = ev21.addQuestion("¿Quién ganará?", 1);
//			q8 = ev22.addQuestion("¿Quién ganará?", 1);
//			q9 = ev23.addQuestion("¿Quién ganará?", 1);
//			q10 = ev24.addQuestion("¿Quién ganará?", 1);
//			q11 = ev25.addQuestion("¿Quién ganará?", 1);
//			q12 = ev25.addQuestion("¿Quién marcará primero?", 1);
//			q13 = ev25.addQuestion("¿Quién marcará más goles?", 1);
//			q14 = ev26.addQuestion("¿Cuál es más grande?", 1);
//			q15 = ev26.addQuestion("¿Cuál es más bonito?", 1);
//			q16 = ev26.addQuestion("¿Cuál tiene playa?", 1);
//			q17 = ev27.addQuestion("¿Grandes o pequeñas?", 1);
//			q18 = ev27.addQuestion("¿Cuál es más común?", 1);
//			q19 = ev27.addQuestion("¿De cuál has probado más?", 1);
//			q20 = ev28.addQuestion("¿Cómo se dice en castellano?", 1);
//			q21 = ev28.addQuestion("¿Cómo lo diría Sergio Ramos?", 1);
//			q22 = ev28.addQuestion("¿Cuál suele gustar más?", 1);
//			q23 = ev29.addQuestion("¿Quién es mejor?", 1);
//			q24 = ev29.addQuestion("¿Quíen cobra más?", 1);
//			q25 = ev29.addQuestion("¿Quién está jugando en España?", 1);
//			q26 = ev30.addQuestion("¿Quién es mejor?", 1);
//			q27 = ev30.addQuestion("¿Quién es argentino?", 1);
//			q28 = ev30.addQuestion("¿Quién juega en el Madrid?", 1);
//			q29 = ev31.addQuestion("¿Quién eres tú?", 1);
//			q30 = ev31.addQuestion("¿Quién será ingeniero?", 1);
//			q31 = ev31.addQuestion("¿Quién aprobará IS?", 1);
//			q32 = ev32.addQuestion("¿Cuál frecuenta más la esquina?", 1);
//			q33 = ev32.addQuestion("¿Cuál es la más sexy?", 1);
//			q34 = ev32.addQuestion("¿Cuál es más frecuente en las esquinas?", 1);
//			q35 = ev33.addQuestion("¿Cuál tiene playa?", 1);
//			q36 = ev33.addQuestion("¿Donde reside el gobierno de España?", 1);
//			q37 = ev33.addQuestion("¿Cuál es más poblada?", 1);
//			q38 = ev34.addQuestion("¿Cuál está en Madrid?", 1);
//			q39 = ev34.addQuestion("¿Cuál está en Cataluña?", 1);
//			q40 = ev34.addQuestion("¿A dónde suele ir el Madrid?", 1);
//			q41 = ev35.addQuestion("¿Cuál prefieres?", 1);
//			q42 = ev35.addQuestion("¿Cuál tiene agua?", 1);
//			q43 = ev35.addQuestion("¿A donde sueles ir más?", 1);
//			q44 = ev36.addQuestion("¿Qué equipo tiene más aficionados?", 1);
//			q45 = ev36.addQuestion("¿Qué equipo viste de blanco y rojo?", 1);
//			q46 = ev36.addQuestion("¿Cuál es una ciudad?", 1);
//			q47 = ev37.addQuestion("¿Qué equipo es de Guipuzcoa?", 1);
//			q48 = ev37.addQuestion("¿Cuántos goles se marcarán en el partido?", 1);
//			q49 = ev37.addQuestion("¿Quién marcará primero?", 1);
//			q50 = ev38.addQuestion("¿Qué equipo tiene más trofeos?", 1);
//			q51 = ev38.addQuestion("¿Cuántos goles se marcarán en el partido?", 1);
//			q52 = ev38.addQuestion("¿Qué equipo es más antiguo?", 1);
//			q53 = ev39.addQuestion("¿Cuál es más corrupta?", 1);
//			q54 = ev39.addQuestion("¿Cuál es más antigua?", 1);
//			q55 = ev40.addQuestion("¿Dónde juega Cristiano Ronaldo?", 1);
//			q56 = ev40.addQuestion("¿Cuál tiene más trofeos?", 1);
//			q57 = ev40.addQuestion("¿Quién ganara la liga este año?", 1);
//			q58 = ev41.addQuestion("¿Qué equipo es francés?", 1);
//			q59 = ev41.addQuestion("¿Qué equipo ganará la champions?", 1);
//			q60 = ev42.addQuestion("¿Qué equipo es de Madrid?", 1);
//			q61 = ev42.addQuestion("¿Qué equipo es de Cataluña?", 1);
//			q62 = ev43.addQuestion("¿Quién ganará?", 1);
//			q63 = ev43.addQuestion("¿Cuál tiene más trofeo?", 1);
//			q64 = ev44.addQuestion("¿Quién tiene más trofeos?", 1);
//			q65 = ev44.addQuestion("¿Quién ganará?", 1);
//			q66 = ev45.addQuestion("¿Quién tiene más ligas ganadas?", 1);
//			q67 = ev45.addQuestion("¿Cuál ha estado más temporadas en primera?", 1);
//			q68 = ev46.addQuestion("¿Quién ha estado más en primera?", 1);
//			q69 = ev46.addQuestion("¿Quién quedará mejor en liga?", 1);
//			q70 = ev47.addQuestion("¿Quién ganará?", 1);
//
//			Forecast f1 = new Forecast(1, "Mauri", 1.2f, q7);
//			Forecast f2 = new Forecast(2, "Universidad", 1.3f, q7);
//			Forecast f3 = new Forecast(3, "Bryan", 1.2f, q8);
//			Forecast f4 = new Forecast(4, "Universidad", 1.2f, q8);
//			Forecast f5 = new Forecast(5, "Melisa", 1.4f, q9);
//			Forecast f6 = new Forecast(6, "Universidad", 1.2f, q9);
//			Forecast f7 = new Forecast(7, "Jorshua", 1.3f, q10);
//			Forecast f8 = new Forecast(8, "Universidad", 1.8f, q10);
//			Forecast f9 = new Forecast(9, "Ermua", 1.8f, q11);
//			Forecast f10 = new Forecast(10, "Eibar", 1.2f, q11);
//			Forecast f11 = new Forecast(11, "Ermua", 1.9f, q12);
//			Forecast f12 = new Forecast(12, "Eibar", 1.2f, q12);
//			Forecast f13 = new Forecast(13, "Ermua", 1.6f, q13);
//			Forecast f14 = new Forecast(14, "Eibar", 1.5f, q13);
//			Forecast f15 = new Forecast(15, "Ermua", 1.4f, q14);
//			Forecast f16 = new Forecast(16, "Donostia", 1.1f, q14);
//			Forecast f17 = new Forecast(17, "Ermua", 1.2f, q15);
//			Forecast f18 = new Forecast(18, "Donostia", 2f, q15);
//			Forecast f19 = new Forecast(19, "Ermua", 2f, q16);
//			Forecast f20 = new Forecast(20, "Donostia", 1.8f, q16);
//			Forecast f21 = new Forecast(21, "Grandes", 2.1f, q17);
//			Forecast f22 = new Forecast(22, "Pequeñas", 2.4f, q17);
//			Forecast f23 = new Forecast(23, "Grandes", 2.7f, q18);
//			Forecast f24 = new Forecast(24, "Pequeñas", 2.7f, q18);
//			Forecast f25 = new Forecast(25, "Grandes", 1.2f, q19);
//			Forecast f26 = new Forecast(26, "Pequeñas", 1.2f, q19);
//			Forecast f27 = new Forecast(27, "Baloncesto", 1.2f, q20);
//			Forecast f28 = new Forecast(28, "Basketball", 1.9f, q20);
//			Forecast f29 = new Forecast(29, "Baloncesto", 1.2f, q21);
//			Forecast f30 = new Forecast(30, "Basketball", 1.2f, q21);
//			Forecast f31 = new Forecast(31, "Baloncesto", 1.2f, q22);
//			Forecast f32 = new Forecast(32, "Basketball", 1.6f, q22);
//			Forecast f33 = new Forecast(33, "Cristiano", 1.2f, q23);
//			Forecast f34 = new Forecast(34, "Messi", 1.2f, q23);
//			Forecast f35 = new Forecast(35, "Cristiano", 1.2f, q24);
//			Forecast f36 = new Forecast(36, "Messi", 1.4f, q24);
//			Forecast f37 = new Forecast(37, "Cristiano", 1.2f, q25);
//			Forecast f38 = new Forecast(38, "Messi", 1.3f, q25);
//			Forecast f39 = new Forecast(39, "Vinicius", 1.2f, q26);
//			Forecast f40 = new Forecast(40, "Messi", 1.2f, q26);
//			Forecast f41 = new Forecast(41, "Vinicius", 2.5f, q27);
//			Forecast f42 = new Forecast(42, "Messi", 1.2f, q27);
//			Forecast f43 = new Forecast(43, "Vinicius", 1.2f, q28);
//			Forecast f44 = new Forecast(44, "Messi", 1.2f, q28);
//			Forecast f45 = new Forecast(45, "Yo", 1.2f, q29);
//			Forecast f46 = new Forecast(46, "Tu", 2.6f, q29);
//			Forecast f47 = new Forecast(47, "Yo", 1.2f, q30);
//			Forecast f48 = new Forecast(48, "Tu", 2.8f, q30);
//			Forecast f49 = new Forecast(49, "Yo", 1.2f, q31);
//			Forecast f50 = new Forecast(50, "Tu", 2.7f, q31);
//			Forecast f51 = new Forecast(51, "La perra", 1.2f, q32);
//			Forecast f52 = new Forecast(52, "La guarra", 1.2f, q32);
//			Forecast f53 = new Forecast(53, "La perra", 1.2f, q33);
//			Forecast f54 = new Forecast(54, "La guarra", 2.3f, q33);
//			Forecast f55 = new Forecast(55, "La perra", 1.2f, q34);
//			Forecast f56 = new Forecast(56, "La guarra", 2.8f, q34);
//			Forecast f57 = new Forecast(57, "Madrid", 1.2f, q35);
//			Forecast f58 = new Forecast(58, "Barcelona", 1.2f, q35);
//			Forecast f59 = new Forecast(59, "Madrid", 3f, q36);
//			Forecast f60 = new Forecast(60, "Barcelona", 1.2f, q36);
//			Forecast f61 = new Forecast(61, "Madrid", 3.2f, q37);
//			Forecast f62 = new Forecast(62, "Barcelona", 1.2f, q37);
//			Forecast f63 = new Forecast(63, "Cibeles", 1.2f, q38);
//			Forecast f64 = new Forecast(64, "Canaletas", 1.2f, q38);
//			Forecast f65 = new Forecast(65, "Cibeles", 3.6f, q39);
//			Forecast f66 = new Forecast(66, "Canaletas", 1.2f, q39);
//			Forecast f67 = new Forecast(67, "Cibeles", 1.2f, q40);
//			Forecast f68 = new Forecast(68, "Canaletas", 3.5f, q40);
//			Forecast f69 = new Forecast(69, "Playa", 1.2f, q41);
//			Forecast f70 = new Forecast(70, "Montaña", 1.2f, q41);
//			Forecast f71 = new Forecast(71, "Playa", 1.2f, q42);
//			Forecast f72 = new Forecast(72, "Montaña", 3.7f, q42);
//			Forecast f73 = new Forecast(73, "Playa", 1.2f, q43);
//			Forecast f74 = new Forecast(74, "Montaña", 1.2f, q43);
//			Forecast f75 = new Forecast(75, "Betis", 3.9f, q44);
//			Forecast f76 = new Forecast(76, "Sevilla", 1.2f, q44);
//			Forecast f77 = new Forecast(77, "Betis", 1.2f, q45);
//			Forecast f78 = new Forecast(78, "Sevilla", 1.2f, q45);
//			Forecast f79 = new Forecast(79, "Betis", 1.2f, q46);
//			Forecast f80 = new Forecast(80, "Sevilla", 3.7f, q46);
//			Forecast f81 = new Forecast(81, "Eibar", 1.2f, q47);
//			Forecast f82 = new Forecast(82, "Alaves", 1.2f, q47);
//			Forecast f83 = new Forecast(83, "0", 1.2f, q48);
//			Forecast f84 = new Forecast(84, "1", 3.5f, q48);
//			Forecast f85 = new Forecast(85, "2", 1.2f, q48);
//			Forecast f86 = new Forecast(86, "3", 1.2f, q48);
//			Forecast f87 = new Forecast(87, "4", 1.2f, q48);
//			Forecast f88 = new Forecast(88, "5", 4f, q48);
//			Forecast f89 = new Forecast(89, "Eibar", 1.2f, q49);
//			Forecast f90 = new Forecast(90, "Alaves", 1.2f, q49);
//			Forecast f91 = new Forecast(91, "Manchester United", 4.2f, q50);
//			Forecast f92 = new Forecast(92, "Manchester City", 1.2f, q50);
//			Forecast f93 = new Forecast(93, "0", 1.2f, q51);
//			Forecast f94 = new Forecast(94, "1", 4.7f, q51);
//			Forecast f95 = new Forecast(95, "2", 1.2f, q51);
//			Forecast f96 = new Forecast(96, "3", 5.6f, q51);
//			Forecast f97 = new Forecast(97, "4", 1.2f, q51);
//			Forecast f98 = new Forecast(98, "5", 1.2f, q51);
//			Forecast f99 = new Forecast(99, "Manchester United", 3.7f, q52);
//			Forecast f100 = new Forecast(100, "Manchester City", 1.2f, q52);
//			Forecast f101 = new Forecast(101, "Fifa", 1.2f, q53);
//			Forecast f102 = new Forecast(102, "Uefa", 4.8f, q53);
//			Forecast f103 = new Forecast(103, "Fifa", 1.2f, q54);
//			Forecast f104 = new Forecast(104, "Uefa", 4.9f, q54);
//			Forecast f105 = new Forecast(105, "Juventus", 1.2f, q55);
//			Forecast f106 = new Forecast(106, "Napoli", 1.2f, q55);
//			Forecast f107 = new Forecast(107, "Juventus", 4.2f, q56);
//			Forecast f108 = new Forecast(108, "Napoli", 1.2f, q56);
//			Forecast f109 = new Forecast(109, "Juventus", 4.6f, q57);
//			Forecast f110 = new Forecast(110, "Napoli", 1.2f, q57);
//			Forecast f111 = new Forecast(111, "Psg", 1.2f, q58);
//			Forecast f112 = new Forecast(112, "Manchester City", 1.2f, q58);
//			Forecast f113 = new Forecast(113, "Psg", 1.2f, q59);
//			Forecast f114 = new Forecast(114, "Manchester City", 1.2f, q59);
//			Forecast f115 = new Forecast(115, "Leganes", 4.4f, q60);
//			Forecast f116 = new Forecast(116, "Espanyol", 1.2f, q60);
//			Forecast f117 = new Forecast(117, "Leganes", 1.2f, q61);
//			Forecast f118 = new Forecast(118, "Espanyol", 4.1f, q61);
//			Forecast f119 = new Forecast(119, "Liverpool", 5f, q62);
//			Forecast f120 = new Forecast(120, "Everton", 1.2f, q62);
//			Forecast f121 = new Forecast(121, "Liverpool", 1.2f, q63);
//			Forecast f122 = new Forecast(122, "Everton", 1.9f, q63);
//			Forecast f123 = new Forecast(123, "Bayern Munich", 1.2f, q64);
//			Forecast f124 = new Forecast(124, "Schalke", 2.9f, q64);
//			Forecast f125 = new Forecast(125, "Bayern Munich", 1.2f, q65);
//			Forecast f126 = new Forecast(126, "Schalke", 1.2f, q65);
//			Forecast f127 = new Forecast(127, "Bayer Leverkusen", 1.2f, q66);
//			Forecast f128 = new Forecast(128, "Friburgo", 1.2f, q66);
//			Forecast f129 = new Forecast(129, "Bayer Leverkusen", 1.2f, q67);
//			Forecast f130 = new Forecast(130, "Friburgo", 1.2f, q67);
//			Forecast f131 = new Forecast(131, "Castellon", 2.7f, q68);
//			Forecast f132 = new Forecast(132, "Lugo", 1.2f, q68);
//			Forecast f133 = new Forecast(133, "Castellon", 1.2f, q69);
//			Forecast f134 = new Forecast(134, "Lugo", 3.9f, q69);
//			Forecast f135 = new Forecast(135, "Albacete", 1.2f, q70);
//			Forecast f136 = new Forecast(136, "Real Madrid", 5.9f, q70);
//
//			q7.addForecast(f1);
//			q7.addForecast(f2);
//			q8.addForecast(f3);
//			q8.addForecast(f4);
//			q9.addForecast(f5);
//			q9.addForecast(f6);
//			q10.addForecast(f7);
//			q10.addForecast(f8);
//			q11.addForecast(f9);
//			q11.addForecast(f10);
//			q12.addForecast(f11);
//			q12.addForecast(f12);
//			q13.addForecast(f13);
//			q13.addForecast(f14);
//			q14.addForecast(f15);
//			q14.addForecast(f16);
//			q15.addForecast(f17);
//			q15.addForecast(f18);
//			q16.addForecast(f19);
//			q16.addForecast(f20);
//			q17.addForecast(f21);
//			q17.addForecast(f22);
//			q18.addForecast(f23);
//			q18.addForecast(f24);
//			q19.addForecast(f25);
//			q19.addForecast(f26);
//			q20.addForecast(f27);
//			q20.addForecast(f28);
//			q21.addForecast(f29);
//			q21.addForecast(f30);
//			q22.addForecast(f31);
//			q22.addForecast(f32);
//			q23.addForecast(f33);
//			q23.addForecast(f34);
//			q24.addForecast(f35);
//			q24.addForecast(f36);
//			q25.addForecast(f37);
//			q25.addForecast(f38);
//			q26.addForecast(f39);
//			q26.addForecast(f40);
//			q27.addForecast(f41);
//			q27.addForecast(f42);
//			q28.addForecast(f43);
//			q28.addForecast(f44);
//			q29.addForecast(f45);
//			q29.addForecast(f46);
//			q30.addForecast(f47);
//			q30.addForecast(f48);
//			q31.addForecast(f49);
//			q31.addForecast(f50);
//			q32.addForecast(f51);
//			q32.addForecast(f52);
//			q33.addForecast(f53);
//			q33.addForecast(f54);
//			q34.addForecast(f55);
//			q34.addForecast(f56);
//			q35.addForecast(f57);
//			q35.addForecast(f58);
//			q36.addForecast(f59);
//			q36.addForecast(f60);
//			q37.addForecast(f61);
//			q37.addForecast(f62);
//			q38.addForecast(f63);
//			q38.addForecast(f64);
//			q39.addForecast(f65);
//			q39.addForecast(f66);
//			q40.addForecast(f67);
//			q40.addForecast(f68);
//			q41.addForecast(f69);
//			q41.addForecast(f70);
//			q42.addForecast(f71);
//			q42.addForecast(f72);
//			q43.addForecast(f73);
//			q43.addForecast(f74);
//			q44.addForecast(f75);
//			q44.addForecast(f76);
//			q45.addForecast(f77);
//			q45.addForecast(f78);
//			q46.addForecast(f79);
//			q46.addForecast(f80);
//			q47.addForecast(f81);
//			q47.addForecast(f82);
//			q48.addForecast(f83);
//			q48.addForecast(f84);
//			q48.addForecast(f85);
//			q48.addForecast(f86);
//			q48.addForecast(f87);
//			q48.addForecast(f88);
//			q49.addForecast(f89);
//			q49.addForecast(f90);
//			q50.addForecast(f91);
//			q50.addForecast(f92);
//			q51.addForecast(f93);
//			q51.addForecast(f94);
//			q51.addForecast(f95);
//			q51.addForecast(f96);
//			q51.addForecast(f97);
//			q51.addForecast(f98);
//			q52.addForecast(f99);
//			q52.addForecast(f100);
//			q53.addForecast(f101);
//			q53.addForecast(f102);
//			q54.addForecast(f103);
//			q54.addForecast(f104);
//			q55.addForecast(f105);
//			q55.addForecast(f106);
//			q56.addForecast(f107);
//			q56.addForecast(f108);
//			q57.addForecast(f109);
//			q57.addForecast(f110);
//			q58.addForecast(f111);
//			q58.addForecast(f112);
//			q59.addForecast(f113);
//			q59.addForecast(f114);
//			q60.addForecast(f115);
//			q60.addForecast(f116);
//			q61.addForecast(f117);
//			q61.addForecast(f118);
//			q62.addForecast(f119);
//			q62.addForecast(f120);
//			q63.addForecast(f121);
//			q63.addForecast(f122);
//			q64.addForecast(f123);
//			q64.addForecast(f124);
//			q65.addForecast(f125);
//			q65.addForecast(f126);
//			q66.addForecast(f127);
//			q66.addForecast(f128);
//			q67.addForecast(f129);
//			q67.addForecast(f130);
//			q68.addForecast(f131);
//			q68.addForecast(f132);
//			q69.addForecast(f133);
//			q69.addForecast(f134);
//			q70.addForecast(f135);
//			q70.addForecast(f136);
//
//			User useradmin = new AdminUser("admin", "admin", "Pepe", "Lopez");
//			User usuario = new RegularUser("mauri", "Mauri1?", "Mauri", "Contreras", "01/01/1997", "mauri@gmail.com",
//					"ES11 1111 1111 1111", 123456789, "", 80);
//			User usuario2 = new RegularUser("bryan", "Bryan1?", "Bryan", "Sánchez", "01/01/1997", "bryan@gmail.com", "",
//					123454_321, "", 50);
//			db.persist(usuario);
//			db.persist(usuario2);
//			db.persist(useradmin);
//			Question q1;
//			Question q2;
//			Question q3;
//			Question q4;
//			Question q5;
//			Question q6;
//
//			if (Locale.getDefault().equals(new Locale("es"))) {
//				q1 = ev1.addQuestion("¿Quién ganará el partido?", 1);
//				q2 = ev1.addQuestion("¿Quién meterá el primer gol?", 2);
//				q3 = ev11.addQuestion("¿Quién ganará el partido?", 1);
//				q4 = ev11.addQuestion("¿Cuántos goles se marcarán?", 2);
//				q5 = ev17.addQuestion("¿Quién ganará el partido?", 1);
//				q6 = ev17.addQuestion("¿Habrá goles en la primera parte?", 2);
//			} else if (Locale.getDefault().equals(new Locale("en"))) {
//				q1 = ev1.addQuestion("Who will win the match?", 1);
//				q2 = ev1.addQuestion("Who will score first?", 2);
//				q3 = ev11.addQuestion("Who will win the match?", 1);
//				q4 = ev11.addQuestion("How many goals will be scored in the match?", 2);
//				q5 = ev17.addQuestion("Who will win the match?", 1);
//				q6 = ev17.addQuestion("Will there be goals in the first half?", 2);
//			} else {
//				q1 = ev1.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q2 = ev1.addQuestion("Zeinek sartuko du lehenengo gola?", 2);
//				q3 = ev11.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q4 = ev11.addQuestion("Zenbat gol sartuko dira?", 2);
//				q5 = ev17.addQuestion("Zeinek irabaziko du partidua?", 1);
//				q6 = ev17.addQuestion("Golak sartuko dira lehenengo zatian?", 2);
//
//			}
//
//			db.persist(ev1);
//			db.persist(ev2);
//			db.persist(ev3);
//			db.persist(ev4);
//			db.persist(ev5);
//			db.persist(ev6);
//			db.persist(ev7);
//			db.persist(ev8);
//			db.persist(ev9);
//			db.persist(ev10);
//			db.persist(ev11);
//			db.persist(ev12);
//			db.persist(ev13);
//			db.persist(ev14);
//			db.persist(ev15);
//			db.persist(ev16);
//			db.persist(ev17);
//			db.persist(ev18);
//			db.persist(ev19);
//			db.persist(ev20);
//			db.persist(ev21);
//			db.persist(ev22);
//			db.persist(ev23);
//			db.persist(ev24);
//			db.persist(ev25);
//			db.persist(ev26);
//			db.persist(ev27);
//			db.persist(ev28);
//			db.persist(ev29);
//			db.persist(ev30);
//			db.persist(ev31);
//			db.persist(ev32);
//			db.persist(ev33);
//			db.persist(ev34);
//			db.persist(ev35);
//			db.persist(ev36);
//			db.persist(ev37);
//			db.persist(ev38);
//			db.persist(ev39);
//			db.persist(ev40);
//			db.persist(ev41);
//			db.persist(ev42);
//			db.persist(ev43);
//			db.persist(ev44);
//			db.persist(ev45);
//			db.persist(ev46);
//			db.persist(ev47);
//
//			db.persist(q1);
//			db.persist(q2);
//			db.persist(q3);
//			db.persist(q4);
//			db.persist(q5);
//			db.persist(q6);
//			db.persist(q7);
//			db.persist(q8);
//			db.persist(q9);
//			db.persist(q10);
//			db.persist(q11);
//			db.persist(q12);
//			db.persist(q13);
			
//			db.persist(q14);
//			db.persist(q15);
//			db.persist(q16);
//			db.persist(q17);
//			db.persist(q18);
//			db.persist(q19);
//			db.persist(q20);
//			db.persist(q21);
//			db.persist(q22);
//			db.persist(q23);
//			db.persist(q24);
//			db.persist(q25);
//			db.persist(q26);
//			db.persist(q27);
//			db.persist(q28);
//			db.persist(q29);
//			db.persist(q30);
//			db.persist(q31);
//			db.persist(q32);
//			db.persist(q33);
//			db.persist(q34);
//			db.persist(q35);
//			db.persist(q36);
//			db.persist(q37);
//			db.persist(q38);
//			db.persist(q39);
//			db.persist(q40);
//			db.persist(q41);
//			db.persist(q42);
//			db.persist(q43);
//			db.persist(q44);
//			db.persist(q45);
//			db.persist(q46);
//			db.persist(q47);
//			db.persist(q48);
//			db.persist(q49);
//			db.persist(q50);
//			db.persist(q51);
//			db.persist(q52);
//			db.persist(q53);
//			db.persist(q54);
//			db.persist(q55);
//			db.persist(q56);
//			db.persist(q57);
//			db.persist(q58);
//			db.persist(q59);
//			db.persist(q60);
//			db.persist(q61);
//			db.persist(q62);
//			db.persist(q63);
//			db.persist(q64);
//			db.persist(q65);
//			db.persist(q66);
//			db.persist(q67);
//			db.persist(q68);
//			db.persist(q69);
//			db.persist(q70);
//
//			db.persist(f1);
//			db.persist(f2);
//			db.persist(f3);
//			db.persist(f4);
//			db.persist(f5);
//			db.persist(f6);
//			db.persist(f7);
//			db.persist(f8);
//			db.persist(f9);
//			db.persist(f10);
//			db.persist(f11);
//			db.persist(f12);
//			db.persist(f13);
//			db.persist(f14);
//			db.persist(f15);
//			db.persist(f16);
//			db.persist(f17);
//			db.persist(f18);
//			db.persist(f19);
//			db.persist(f20);
//			db.persist(f21);
//			db.persist(f22);
//			db.persist(f23);
//			db.persist(f24);
//			db.persist(f25);
//			db.persist(f26);
//			db.persist(f27);
//			db.persist(f28);
//			db.persist(f29);
//			db.persist(f30);
//			db.persist(f31);
//			db.persist(f32);
//			db.persist(f33);
//			db.persist(f34);
//			db.persist(f35);
//			db.persist(f36);
//			db.persist(f37);
//			db.persist(f38);
//			db.persist(f39);
//			db.persist(f40);
//			db.persist(f41);
//			db.persist(f42);
//			db.persist(f43);
//			db.persist(f44);
//			db.persist(f45);
//			db.persist(f46);
//			db.persist(f47);
//			db.persist(f48);
//			db.persist(f49);
//			db.persist(f50);
//			db.persist(f51);
//			db.persist(f52);
//			db.persist(f53);
//			db.persist(f54);
//			db.persist(f55);
//			db.persist(f56);
//			db.persist(f57);
//			db.persist(f58);
//			db.persist(f59);
//			db.persist(f60);
//			db.persist(f61);
//			db.persist(f62);
//			db.persist(f63);
//			db.persist(f64);
//			db.persist(f65);
//			db.persist(f66);
//			db.persist(f67);
//			db.persist(f68);
//			db.persist(f69);
//			db.persist(f70);
//			db.persist(f71);
//			db.persist(f72);
//			db.persist(f73);
//			db.persist(f74);
//			db.persist(f75);
//			db.persist(f76);
//			db.persist(f77);
//			db.persist(f78);
//			db.persist(f79);
//			db.persist(f80);
//			db.persist(f81);
//			db.persist(f82);
//			db.persist(f83);
//			db.persist(f84);
//			db.persist(f85);
//			db.persist(f86);
//			db.persist(f87);
//			db.persist(f88);
//			db.persist(f89);
//			db.persist(f90);
//			db.persist(f91);
//			db.persist(f92);
//			db.persist(f93);
//			db.persist(f94);
//			db.persist(f95);
//			db.persist(f96);
//			db.persist(f97);
//			db.persist(f98);
//			db.persist(f99);
//			db.persist(f100);
//			db.persist(f101);
//			db.persist(f102);
//			db.persist(f103);
//			db.persist(f104);
//			db.persist(f105);
//			db.persist(f106);
//			db.persist(f107);
//			db.persist(f108);
//			db.persist(f109);
//			db.persist(f110);
//			db.persist(f111);
//			db.persist(f112);
//			db.persist(f113);
//			db.persist(f114);
//			db.persist(f115);
//			db.persist(f116);
//			db.persist(f117);
//			db.persist(f118);
//			db.persist(f119);
//			db.persist(f120);
//			db.persist(f121);
//			db.persist(f122);
//			db.persist(f123);
//			db.persist(f124);
//			db.persist(f125);
//			db.persist(f126);
//			db.persist(f127);
//			db.persist(f128);
//			db.persist(f129);
//			db.persist(f130);
//			db.persist(f131);
//			db.persist(f132);
//			db.persist(f133);
//			db.persist(f134);
//			db.persist(f135);
//			db.persist(f136);

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
