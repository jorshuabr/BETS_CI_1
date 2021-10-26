package gui;

import businessLogic.BLFacade;
import configuration.UtilDate;

import com.toedter.calendar.JCalendar;

import domain.AdminUser;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;

import javax.swing.*;
import java.awt.*;
import java.awt.List;
import java.awt.event.*;
import java.beans.*;
import java.text.DateFormat;
import java.util.*;

import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

public class CreateBetGUI extends JFrame {
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
	private JScrollPane scrollPaneForecasts = new JScrollPane();

	private Vector<Date> datesWithEventsCurrentMonth = new Vector<Date>();

	private JTable tableEvents = new JTable();
	private JTable tableQueries = new JTable();
	private JTable tableForecasts = new JTable();

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
	private Question que;
	private Forecast forecast;
	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private JTextField textBet;

	private RegularUser userlog = null;
	private RegularUser newUserActualizado;
	private Vector<Forecast> forecasts;
	private Vector<Question> queries;
	private JLabel lblSaldo;

	private JButton btnCreateBet = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public CreateBetGUI(RegularUser u) {
		getContentPane().setBackground(Color.WHITE);
		userlog = u;
		newUserActualizado = u;

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(730, 550));

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MakeBet")
				+ "                                                " + userlog.getUserName());
		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(10, 211, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setFont(new Font("Dialog", Font.BOLD, 14));
		jButtonClose.setForeground(Color.WHITE);
		jButtonClose.setBackground(SystemColor.controlShadow);

		jButtonClose.setBounds(new Rectangle(57, 419, 130, 30));

		jButtonClose.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Frame gui = new MainGUI(newUserActualizado);
				gui.setAlwaysOnTop(true);
				gui.setVisible(true);
				dispose();

			}
		});

		this.getContentPane().add(jButtonClose, null);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent propertychangeevent) {
				btnCreateBet.setEnabled(false);


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
							// Si en JCalendar está 30 de enero y se avanza al mes siguiente, devolvería 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este código se dejará como 1 de febrero en el JCalendar
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

						// BLFacade facade = LoginGUI.getBusinessLogic();

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
						tableEvents.getColumnModel().removeColumn(tableEvents.getColumnModel().getColumn(2)); 
																												
							tableModelQueries.setRowCount(0);
							tableModelForecasts.setRowCount(0);
																												
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));

		scrollPaneQueries.setBounds(new Rectangle(10, 236, 372, 116));

		scrollPaneForecasts.setBounds(new Rectangle(410, 236, 204, 116));

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelForecasts.setRowCount(0);

				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				queries = ev.getQuestions();
				btnCreateBet.setEnabled(false);

				tableModelQueries.setDataVector(null, columnNamesQueries);

				if (queries.isEmpty())
					jLabelQueries.setText(
							ResourceBundle.getBundle("Etiquetas").getString("NoQueries") + ": " + ev.getDescription());
				else
					jLabelQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectedEvent") + " "
							+ ev.getDescription());

				for (domain.Question q : queries) {
					Vector<Object> row = new Vector<Object>();
					row.add(q.getQuestionNumber());
					row.add(q.getQuestion());
					tableModelQueries.addRow(row);
				}
				tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
				tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);
			}
		});
		tableQueries.addMouseListener(new MouseAdapter() {

			@Override
			public void mouseClicked(MouseEvent e) {
				tableModelForecasts.setRowCount(0);
				int i = tableQueries.getSelectedRow();
				que = queries.get(i);
				btnCreateBet.setEnabled(false);

				forecasts = que.getForecasts();
				System.out.println("TAMAÑO FORECASTS: " + forecasts.size());
				for (domain.Forecast f : forecasts) {
					Vector<Object> row = new Vector<Object>();
					row.add(f.getForecastNumber());
					row.add(f.getForecast());
					row.add(f.getFee());
					tableModelForecasts.addRow(row);
				}
				System.out.println(tableForecasts.getRowCount());
			}
		});
		tableForecasts.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableForecasts.getSelectedRow();
				forecast = forecasts.get(i);
				btnCreateBet.setEnabled(true);

				System.out.println(forecast.toString());
				textBet.setText(String.valueOf(que.getBetMinimum()));
			}
		});
		
		
		
	
		
		
		
		
		
		
		tableForecasts.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 != 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
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

		scrollPaneForecasts.setViewportView(tableForecasts);
		tableModelForecasts = new DefaultTableModel(null, columnNamesForecasts) {
			@Override
			public boolean isCellEditable(int row, int column) {
				return false;
			}
		};

		tableForecasts.setModel(tableModelForecasts);
		tableForecasts.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableForecasts.getColumnModel().getColumn(1).setPreferredWidth(100);
		tableForecasts.getColumnModel().getColumn(2).setPreferredWidth(25);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);
		this.getContentPane().add(scrollPaneForecasts);
		btnCreateBet.setForeground(Color.WHITE);
		btnCreateBet.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCreateBet.setBackground(SystemColor.textHighlight);

		btnCreateBet.setEnabled(false);
		btnCreateBet.addActionListener(new ActionListener() {

			public void actionPerformed(ActionEvent e) {
				System.out.println(que.toString());
				float betP = Float.parseFloat(textBet.getText());
				String b = textBet.getText();
				Forecast f = forecast;

				if (b.equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "El bet no puede estar vacío", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					int inserted = facade.createApuesta(forecast, userlog, betP);
					if (inserted == 0) {
						JOptionPane.showMessageDialog(getContentPane(), "Apuesta realizada correctamente" + "\n"
								+ "Has apostado a " + forecast.getForecast() + " con " + betP + "€");
						System.out.println("APUESTA CORRECTAMENTE REALIZADA");
						lblSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateBetGUI.lblSaldo_2.text")
								+ " " + (newUserActualizado.getBalance() - betP) + "€");
						textBet.setText(String.valueOf(que.getBetMinimum()));
					} else if (inserted == 4) {
						JOptionPane.showMessageDialog(getContentPane(), "No puedes apostar valores negativos");
						textBet.setText(String.valueOf(que.getBetMinimum()));
					} else if (inserted == 3) {
						JOptionPane.showMessageDialog(getContentPane(), "No puedes apostar por debajo de lo mínimo");
						textBet.setText(String.valueOf(que.getBetMinimum()));
					} else if (inserted == 2) {
						JOptionPane.showMessageDialog(getContentPane(), "No dispone de saldo suficiente");
						textBet.setText(String.valueOf(que.getBetMinimum()));
					}
					newUserActualizado = facade.getRegularUserByUsername(userlog.getUserName());

				}

			}
		});
		btnCreateBet.setBounds(new Rectangle(100, 275, 130, 30));
		btnCreateBet.setBounds(439, 419, 130, 30);
		getContentPane().add(btnCreateBet);

		JLabel lblNewLabel = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("CreateBetGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel.setBounds(268, 380, 26, 14);
		getContentPane().add(lblNewLabel);

		textBet = new JTextField();
		textBet.setHorizontalAlignment(SwingConstants.LEFT);
		textBet.setForeground(Color.GRAY);
		textBet.setFont(new Font("Arial", Font.PLAIN, 15));
		textBet.setToolTipText(ResourceBundle.getBundle("Etiquetas").getString("CreateBetGUI.textFee.toolTipText")); //$NON-NLS-1$ //$NON-NLS-2$
		textBet.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateBetGUI.textFee.text")); //$NON-NLS-1$ //$NON-NLS-2$
		textBet.setBounds(new Rectangle(100, 211, 429, 20));
		textBet.setBounds(293, 377, 89, 20);
		getContentPane().add(textBet);

		JLabel lblNewLabel_1 = new JLabel(
				ResourceBundle.getBundle("Etiquetas").getString("CreateBetGUI.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblNewLabel_1.setBounds(385, 380, 26, 14);
		getContentPane().add(lblNewLabel_1);
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 128));
		panel.setBounds(549, 15, 140, 25);
		getContentPane().add(panel);
		
				lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblSaldo") + " "
						+ userlog.getBalance() + "€");
				panel.add(lblSaldo);
				lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblSaldo.setForeground(Color.WHITE);

	}
}
