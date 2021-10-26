package gui;

import java.awt.Dimension;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.beans.PropertyChangeEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultComboBoxModel;
import javax.swing.JButton;
import javax.swing.JComboBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.AdminUser;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.User;
import java.awt.Color;
import java.awt.Component;
import java.awt.SystemColor;
import java.awt.Font;

public class CloseEventGUI extends JFrame {
	private static final long serialVersionUID = 1L;

	private final JLabel jLabelEventDate = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("EventDate"));
	private final JLabel jLabelQueries = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Queries"));
	private final JLabel jLabelEvents = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("Events"));

	private JButton jButtonClose = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));

	// Code for JCalendar
	private JCalendar jCalendar1 = new JCalendar();
	private Calendar calendarAnt = null;
	private Calendar calendarAct = null;
	private JScrollPane scrollPaneEvents = new JScrollPane();
	private JScrollPane scrollPaneQueries = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();

	private DefaultTableModel tableModelEvents;
	private DefaultTableModel tableModelQueries;
	private DefaultTableModel tableModelForecasts;

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};

	private String[] columnNamesForecasts = new String[] { ResourceBundle.getBundle("Etiquetas").getString("ForecastN"),
			ResourceBundle.getBundle("Etiquetas").getString("Forecast"), "Fee"

	};
	private Question ques;
	private Forecast fo;
	private static BLFacade facade = LoginGUI.getBusinessLogic();

	private User userlog = null;
	private DefaultComboBoxModel<Object> forecastsitos = new DefaultComboBoxModel<Object>();
	private Vector<Forecast> seleccionados = new Vector<Forecast>();
	private Forecast winnerF = null;
	private Vector<Question> queries;
	private Vector<Forecast> forecasts;

	private JComboBox comboBox = new JComboBox();

	private JButton btnCloseQuestion = new JButton("Cerrar Pregunta");

	private JLabel lblNoForecasts = new JLabel("No hay pronósticos");

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public CloseEventGUI(User u) {
		getContentPane().setBackground(Color.WHITE);

		userlog = u;

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {
		
		
		

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(800, 560));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainAdminGUI.buttonCloseEvent.text"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(10, 211, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setForeground(Color.WHITE);
		jButtonClose.setFont(new Font("Dialog", Font.BOLD, 14));
		jButtonClose.setBackground(SystemColor.controlShadow);

		jButtonClose.setBounds(new Rectangle(40, 428, 130, 30));

		jButtonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		
		tableEvents.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 != 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});
		
		
		tableQueries.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 != 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});
		
		
		this.getContentPane().add(jButtonClose, null);
		jCalendar1.getDayChooser().getDayPanel().setBackground(Color.WHITE);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);
		lblNoForecasts.setForeground(Color.RED);
		lblNoForecasts.setBackground(Color.WHITE);

		lblNoForecasts.setBounds(400, 325, 130, 13);
		getContentPane().add(lblNoForecasts);
		lblNoForecasts.setVisible(false);
		btnCloseQuestion.setForeground(Color.WHITE);
		btnCloseQuestion.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCloseQuestion.setBackground(SystemColor.textHighlight);

		
		btnCloseQuestion.setEnabled(false);
		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

				btnCloseQuestion.setEnabled(false);
				lblNoForecasts.setVisible(false);
				
				if (propertychangeevent.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) propertychangeevent.getNewValue());
				} else if (propertychangeevent.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) propertychangeevent.getOldValue();
					calendarAct = (Calendar) propertychangeevent.getNewValue();
					DateFormat dateformat1 = DateFormat.getDateInstance(1, jCalendar1.getLocale());
//					jCalendar1.setCalendar(calendarAct);
					Date firstDay = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);

					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

						BLFacade facade = LoginGUI.getBusinessLogic();

						datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
					}

					CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

					try {
						tableModelEvents.setDataVector(null, columnNamesEvents);
						tableModelEvents.setColumnCount(3); // another column added to allocate ev objects

						BLFacade facade = LoginGUI.getBusinessLogic();

						Vector<domain.Event> events = facade.getEvents(firstDay);

						if (events.isEmpty())
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("NoEvents") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						else
							jLabelEvents.setText(ResourceBundle.getBundle("Etiquetas").getString("Events") + ": "
									+ dateformat1.format(calendarAct.getTime()));
						for (domain.Event ev : events) {
							if (!ev.getClosed()) {
								Vector<Object> row = new Vector<Object>();

								System.out.println("Events " + ev);

								row.add(ev.getEventNumber());
								row.add(ev.getDescription());
								row.add(ev); // ev object added in order to obtain it with
												// tableModelEvents.getValueAt(i,2)
								tableModelEvents.addRow(row);
							}
						}
						tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
						tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); // not
																												// shown
						tableModelQueries.setRowCount(0);
						comboBox.removeAllItems();
																												// JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));

		scrollPaneQueries.setBounds(new Rectangle(10, 236, 372, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				
				btnCloseQuestion.setEnabled(false);
				lblNoForecasts.setVisible(false);
				
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				queries = facade.getOpenedQuestions(ev);

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
					if (q.getResult() == null) {
						Vector<Object> row = new Vector<Object>();
						row.add(q.getQuestionNumber());
						row.add(q.getQuestion());
						tableModelQueries.addRow(row);
					}
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});

		tableQueries.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				lblNoForecasts.setVisible(false);


				tableQueries.removeAll();
				btnCloseQuestion.setEnabled(true);
				tableModelForecasts.setRowCount(0);
				int i = tableQueries.getSelectedRow();
				System.out.println(i);
				if (queries.isEmpty() == false) {
					ques = queries.get(i);
				}

//						System.out.println(que.toString());
//						Vector<Forecast> forecasts = que.getForecasts();
//						for (domain.Forecast f : forecasts) {
//							Vector<Object> row = new Vector<Object>();
//							row.add(f.getForecastNumber());
//							row.add(f.getForecast());
//							row.add(f.getFee());
//							tableModelForecasts.addRow(row);
//						}

				comboBox.removeAllItems();
				forecasts = ques.getForecasts();
				
				if (forecasts.isEmpty()) {
					lblNoForecasts.setVisible(true);
					btnCloseQuestion.setEnabled(false);
				}
				else {
				
				for (domain.Forecast f : forecasts) {
					forecastsitos.addElement(f.getForecast());
					// System.out.println(f.getQuestion().getQuestion());
				}
				comboBox.setModel(forecastsitos);

//						tableForecasts.addMouseListener(new MouseAdapter() {
//							@Override
//							public void mouseClicked(MouseEvent e) {
//								int i = tableForecasts.getSelectedRow();
//								if (forecasts.isEmpty() == false) {
//									bet = forecasts.get(i);
//
//								}
//								System.out.println(bet.toString());
//							}
//						});
				}
			}
		});

		scrollPaneEvents.setViewportView(tableEvents);
		tableModelEvents = new DefaultTableModel(null, columnNamesEvents) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableEvents.setModel(tableModelEvents);
		tableEvents.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableEvents.getColumnModel().getColumn(1).setPreferredWidth(268);

		scrollPaneQueries.setViewportView(tableQueries);
		tableModelQueries = new DefaultTableModel(null, columnNamesQueries) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
		tableModelForecasts = new DefaultTableModel(null, columnNamesForecasts);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);

//		JButton btnCloseBet = new JButton(
//				ResourceBundle.getBundle("Etiquetas").getString("CloseEventGUI.btnCloseBet.text")); //$NON-NLS-1$ //$NON-NLS-2$
//		btnCloseBet.addActionListener(new ActionListener() {
//			@Override
//			public void actionPerformed(ActionEvent e) {
//				System.out.println(que.toString());
//				boolean inserted = facade.closeEvent(que.getEvent(), que, bet);
//				if (inserted) {
//					JOptionPane.showMessageDialog(getContentPane(), "Evento cerrado correctamente");
//				} else {
//					JOptionPane.showMessageDialog(getContentPane(), "Error al cerrar evento");
//				}
//			}
//		});
//		btnCloseBet.setBounds(new Rectangle(100, 275, 130, 30));
//		btnCloseBet.setBounds(508, 406, 130, 30);
//		getContentPane().add(btnCloseBet);

		JLabel lblNewLabel = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("CloseBetGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(410, 252, 144, 14);
		getContentPane().add(lblNewLabel);
		comboBox.setBackground(Color.WHITE);

		comboBox.setBounds(410, 278, 327, 25);
		getContentPane().add(comboBox);

		btnCloseQuestion.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				
				
				System.out.println(".....cerrrando pregunta.....");
				int i = comboBox.getSelectedIndex();
				int j = tableQueries.getSelectedRow();
				winnerF = forecasts.get(i);
				System.out.println("La pregunta <<" + queries.get(j).getQuestion() + ">> con respuesta <<"
						+ winnerF.getForecast() + ">>" + " ha sido cerrado con éxito");
//				boolean boleanisa = facade.definirResultados(ques.getEvent(), ques, winnerF);
				facade.definirResultados(ques.getEvent(), ques, winnerF);
				JOptionPane.showMessageDialog(getContentPane(), "La pregunta <<" + queries.get(j).getQuestion() + ">>"
						+ " se ha cerrado con éxito con la respuesta <<" + winnerF.getForecast() + ">>");
//				queries.remove(j);
					comboBox.removeAllItems();
					tableModelQueries.removeRow(j);
					queries.remove(j);

				System.out.println("El evento esta cerrado: " + ques.getEvent().getClosed());
				
				boolean estadoEvento = facade.getEstadoEvento(ques.getEvent());
				if(estadoEvento) {
					JOptionPane.showMessageDialog(getContentPane(),
							"El evento <<" + ques.getEvent().getDescription() + ">> se ha cerrado con éxito");
					System.out.println("El evento esta cerrado: " + estadoEvento);

					btnCloseQuestion.setEnabled(false);
					

				}
				
//				if (ques.getEvent().getClosed()) {
//					JOptionPane.showMessageDialog(getContentPane(),
//							"El evento <<" + ques.getEvent().getDescription() + ">> se ha cerrado con éxito");
//					System.out.println("miau");
//					btnCloseQuestion.setEnabled(false);
//				}
			}
		});
		btnCloseQuestion.setBounds(364, 428, 166, 32);
		getContentPane().add(btnCloseQuestion);
		
		

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		
//		if (controlPreguntas) {
//			JOptionPane.showMessageDialog(getContentPane(),
//					"Te avisamos de que el evento no ha sido cerrado completamente");
//		}
		
		Vector<Event> eventosMedioCerrados = facade.getEventosMedioCerrados();
		
		if(eventosMedioCerrados.isEmpty()==false) {
			for (Event ee: eventosMedioCerrados) {
				JOptionPane.showMessageDialog(getContentPane(),
						"Te avisamos de que el evento " + ee.getDescription()+ " no ha sido cerrado completamente");
			}
			
		}
		
		eventosMedioCerrados.clear();
		
		
		Frame gui = new MainAdminGUI((AdminUser) userlog);
		gui.setAlwaysOnTop(true);
		gui.setVisible(true);
		dispose();
	}
}
