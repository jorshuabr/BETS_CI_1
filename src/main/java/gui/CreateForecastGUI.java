package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
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

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;

import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.AdminUser;
import domain.Question;

public class CreateForecastGUI extends JFrame {
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

	private String[] columnNamesEvents = new String[] { ResourceBundle.getBundle("Etiquetas").getString("EventN"),
			ResourceBundle.getBundle("Etiquetas").getString("Event"),

	};
	private String[] columnNamesQueries = new String[] { ResourceBundle.getBundle("Etiquetas").getString("QueryN"),
			ResourceBundle.getBundle("Etiquetas").getString("Query")

	};
	private JTextField textForecast;
	private Question que;
	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private JTextField textFee;
	private Vector<Question> queries;
	private AdminUser userlog = null;

	private JButton btnCreateForecast = new JButton();
	private final JLabel lblNewLabel_1 = new JLabel(""); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblNewLabel_2 = new JLabel("Cuota mínima 1"); //$NON-NLS-1$ //$NON-NLS-2$
	public static final int TAMAÑO_1 = 10;

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public CreateForecastGUI(AdminUser au) {
		getContentPane().setBackground(Color.WHITE);
		userlog = au;

		try {
			jbInit();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	private void jbInit() throws Exception {

		this.getContentPane().setLayout(null);
		this.setSize(new Dimension(700, 550));
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainAdminGUI.jButtonCreateForecast.text"));

		jLabelEventDate.setBounds(new Rectangle(40, 15, 140, 25));
		jLabelQueries.setBounds(138, 211, 406, 14);
		jLabelEvents.setBounds(295, 19, 259, 16);

		this.getContentPane().add(jLabelEventDate, null);
		this.getContentPane().add(jLabelQueries);
		this.getContentPane().add(jLabelEvents);
		jButtonClose.setForeground(Color.WHITE);
		jButtonClose.setBackground(SystemColor.controlShadow);
		jButtonClose.setFont(new Font("Dialog", Font.BOLD, 14));

		jButtonClose.setBounds(new Rectangle(40, 449, 130, 30));

		jButtonClose.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				jButton2_actionPerformed(e);
			}
		});

		this.getContentPane().add(jButtonClose, null);
		jCalendar1.getDayChooser().getDayPanel().setBackground(Color.WHITE);

		jCalendar1.setBounds(new Rectangle(40, 50, 225, 150));

		datesWithEventsCurrentMonth = facade.getEventsMonth(jCalendar1.getDate());
		CreateQuestionGUI.paintDaysWithEvents(jCalendar1, datesWithEventsCurrentMonth);

		// Code for JCalendar
		this.jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			@Override
			public void propertyChange(PropertyChangeEvent propertychangeevent) {

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
																												// in
																												// JTable
					} catch (Exception e1) {

						jLabelQueries.setText(e1.getMessage());
					}

				}
			}
		});

		this.getContentPane().add(jCalendar1, null);

		scrollPaneEvents.setBounds(new Rectangle(292, 50, 346, 150));

		scrollPaneQueries.setBounds(new Rectangle(138, 236, 406, 116));
		tableEvents.setBackground(Color.WHITE);

		tableEvents.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent e) {
				int i = tableEvents.getSelectedRow();
				domain.Event ev = (domain.Event) tableModelEvents.getValueAt(i, 2); // obtain ev object
				queries = ev.getQuestions();

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
				int i = tableQueries.getSelectedRow();
				que = queries.get(i);
				btnCreateForecast.setEnabled(true);
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

		tableQueries.setModel(tableModelQueries);
		tableQueries.getColumnModel().getColumn(0).setPreferredWidth(25);
		tableQueries.getColumnModel().getColumn(1).setPreferredWidth(268);

		this.getContentPane().add(scrollPaneEvents, null);
		this.getContentPane().add(scrollPaneQueries, null);

		textForecast = new JTextField();
		textForecast.setBounds(new Rectangle(100, 211, 429, 20));
		textForecast.setForeground(Color.GRAY);
		textForecast.setFont(new Font("Arial", Font.PLAIN, 16));
		textForecast.setBounds(223, 363, 225, 25);
		getContentPane().add(textForecast);

		JLabel lblForecast = new JLabel("Forecast:");
		lblForecast.setBounds(new Rectangle(25, 211, 75, 20));
		lblForecast.setBounds(148, 364, 75, 20);
		getContentPane().add(lblForecast);
		btnCreateForecast.setForeground(Color.WHITE);
		btnCreateForecast.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCreateForecast.setBackground(SystemColor.textHighlight);

		btnCreateForecast.setEnabled(false);
		btnCreateForecast
				.setText(ResourceBundle.getBundle("Etiquetas").getString("MainAdminGUI.jButtonCreateForecast.text"));

		btnCreateForecast.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				System.out.println(que.toString());
				if (textFee.getText().equals("")) {

					JOptionPane.showMessageDialog(getContentPane(), "La cuota no puede estar vacía", "Error",
							JOptionPane.ERROR_MESSAGE);

				} else {
					float fee = Float.parseFloat(textFee.getText());

					String f = textForecast.getText();

					if (f.equals("")) {
						JOptionPane.showMessageDialog(getContentPane(), "El pronóstico no puede estar vacío", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (fee < 1f) {
						JOptionPane.showMessageDialog(getContentPane(), "La cuota no puede ser inferior a 1", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {
						boolean inserted = facade.insertForecast(que, f, fee);
						if (inserted) {
							JOptionPane.showMessageDialog(getContentPane(), "Pronóstico añadido correctamente");
							textForecast.setText("");
							textFee.setText("");
						} else {
							JOptionPane.showMessageDialog(getContentPane(), "Pronóstico ya existente");
							textForecast.setText("");
							textFee.setText("");

						}
					}
				}
			}
		});
		btnCreateForecast.setBounds(new Rectangle(100, 275, 130, 30));
		btnCreateForecast.setBounds(313, 449, 169, 30);
		getContentPane().add(btnCreateForecast);

		JLabel lblNewLabel = new JLabel("Cuota:");
		lblNewLabel.setBounds(466, 369, 46, 14);
		getContentPane().add(lblNewLabel);

		textFee = new JTextField();
		textFee.setBounds(new Rectangle(100, 211, 429, 20));
		textFee.setForeground(Color.GRAY);
		textFee.setFont(new Font("Arial", Font.PLAIN, 16));
		textFee.setBounds(516, 363, 97, 25);
		getContentPane().add(textFee);
		lblNewLabel_1.setIcon(new ImageIcon("C:\\Users\\Bryan\\Desktop\\Workspace\\ISBets21BRYMAUJONUNA\\info.png"));
		lblNewLabel_1.setBounds(560, 322, 30, 30);

		getContentPane().add(lblNewLabel_1);
		lblNewLabel_2.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, TAMAÑO_1));
		lblNewLabel_2.setForeground(Color.GRAY);
		lblNewLabel_2.setBounds(586, 331, 90, 13);

		getContentPane().add(lblNewLabel_2);

	}

	private void jButton2_actionPerformed(ActionEvent e) {
		Frame gui = new MainAdminGUI(userlog);
		gui.setAlwaysOnTop(true);
		gui.setVisible(true);
		this.setVisible(false);
	}
}
