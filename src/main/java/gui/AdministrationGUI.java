package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.EventQueue;
import java.awt.Frame;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.DefaultListModel;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JList;
import javax.swing.JOptionPane;

import businessLogic.BLFacade;
import domain.AdminUser;
import domain.Bet;
import domain.Event;
import domain.Forecast;
import domain.Question;
import domain.RegularUser;
import domain.User;

import javax.swing.JTextArea;
import java.beans.PropertyChangeListener;
import java.beans.PropertyChangeEvent;
import javax.swing.JScrollBar;
import javax.swing.JScrollPane;
import javax.swing.JSlider;
import javax.swing.JFormattedTextField;
import javax.swing.JSpinner;
import javax.swing.JTextPane;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.JLabel;
import javax.swing.ImageIcon;
import javax.swing.JSeparator;
import java.awt.Font;

public class AdministrationGUI extends JFrame {

	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private DefaultListModel modelopreguntas = new DefaultListModel();
	private Vector<Question> preguntas = new Vector<Question>();
	private DefaultListModel modeloPronosticos = new DefaultListModel();
	private Event eventoSeleccionado;
	private Question preguntaSeleccionada;
	private DefaultListModel modelousuarios = new DefaultListModel();
	private Vector<User> usuarios = facade.getAllUsers();
	private AdminUser userlog = null;
	private JButton btnAtras;
	private DefaultListModel modeloApuestas = new DefaultListModel();
	private Vector<Bet> apuestas = new Vector<Bet>();
	private JScrollPane scrollPaneApuestas = new JScrollPane();
	private JTable tablaApuestas;
	private String[] nombresColumnasApuestas = { "Fecha", "Evento", "Pregunta", "Apuesta a ", "Cant. apostada", "Cuota",
			"Estado", "Usuario" };

	private DefaultTableModel tableModelApuestas = new DefaultTableModel(null, nombresColumnasApuestas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private JScrollPane scrollPaneEventos = new JScrollPane();
	private JTable tablaEventos;
	private String[] nombresColumnasEventos = { "Id", "Fecha", "Evento" };

	private DefaultTableModel tableModelEventos = new DefaultTableModel(null, nombresColumnasEventos) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private String[] nombresColumnasPreguntas = { "Id", "Pregunta", "Evento" };
	private JScrollPane scrollPanePreguntas = new JScrollPane();
	private JTable tablaPreguntas;
	private DefaultTableModel tableModelPreguntas = new DefaultTableModel(null, nombresColumnasPreguntas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private String[] nombresColumnas = { "Username", "UserPass", "FirstName", "LastName ", "BirthDate", "Email",
			"BankAccount", "PhoneNumber", "Address", "Balance" };
	private JScrollPane scrollPaneUsuarios = new JScrollPane();
	private JTable tabla;
	private DefaultTableModel tableModelUsuarios = new DefaultTableModel(null, nombresColumnas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
	private JLabel lblInfo;
	private JLabel lblInfo2;
	private JLabel lblIcono;
	private JSeparator separator;
	private JLabel lblApuestas;
	private JLabel lblPreguntas;
	private JLabel lblUsuarios;
	private JLabel lblEventos;

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public AdministrationGUI(AdminUser u) {
		getContentPane().setBackground(Color.WHITE);

		userlog = u;

		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainAdminGUI.buttonAdmin.text"));
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1600, 650);
		getContentPane().setLayout(null);

		Vector<Event> eventos = facade.getAllEvents();

		for (Event e : eventos) {

			Vector<Object> row = new Vector<Object>();
			row.add(e.getEventNumber());
			row.add(e.getEventDate().toString().substring(0, 11));
			row.add(e.getDescription());

			tableModelEventos.addRow(row);

		}

		tablaEventos = new JTable(tableModelEventos) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}

		};

		tablaEventos.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 != 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});

		scrollPaneEventos.setBounds(new Rectangle(865, 165, 503, 119));
		scrollPaneEventos.setViewportView(tablaEventos);
		this.getContentPane().add(scrollPaneEventos);

		for (User user : usuarios) {
			if (user instanceof RegularUser) {
				Vector<Bet> apuestasUsuario = facade.getApuestasByUser((RegularUser) user);

				for (Bet bet : apuestasUsuario) {

					Vector<Object> row = new Vector<Object>();
					row.add(bet.getForecast().getQuestion().getEvent().getEventDate().toString().substring(0, 11));
					row.add(bet.getForecast().getQuestion().getEvent().getDescription());
					row.add(bet.getForecast().getQuestion().getQuestion());
					row.add(bet.getForecast().getForecast());
					row.add(bet.getAmount());
					row.add(bet.getForecast().getFee());
					row.add(bet.getEstadoApuesta());
					row.add(bet.getUser().getUserName());
					tableModelApuestas.addRow(row);

				}

			}

		}

		tablaApuestas = new JTable(tableModelApuestas) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}

		};

		tablaApuestas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 != 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});

		scrollPaneApuestas.setBounds(new Rectangle(40, 355, 774, 119));
		scrollPaneApuestas.setViewportView(tablaApuestas);
		this.getContentPane().add(scrollPaneApuestas);

		preguntas = facade.getAllQuestions();
		for (Question q : preguntas) {
			modelopreguntas.addElement(q);
		}

		btnAtras = new JButton("Atrás");
		btnAtras.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setBackground(SystemColor.controlShadow);
		btnAtras.setText(ResourceBundle.getBundle("Etiquetas").getString("Close"));

		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFrame a = new MainAdminGUI(userlog);
				a.setAlwaysOnTop(true);
				a.setVisible(true);
				dispose();

			}
		});
		btnAtras.setBounds(40, 550, 99, 26);
		getContentPane().add(btnAtras);
		modelousuarios.addElement("Usuarios:\n");
		modelousuarios.addElement("\n");
		usuarios = facade.getAllUsers();
		for (User e : usuarios) {
			modelousuarios.addElement(e.toString());
		}

		for (User us : usuarios) {

			Vector<Object> row = new Vector<Object>();

			if (us instanceof AdminUser) {

				row.add(us.getUserName());
				row.add(us.getUserPass());
				row.add(us.getFirstName());
				row.add(us.getLastName());
				tableModelUsuarios.addRow(row);

			} else if (us instanceof RegularUser) {
				row.add(us.getUserName());
				row.add(us.getUserPass());
				row.add(us.getFirstName());
				row.add(us.getLastName());
				row.add(((RegularUser) us).getBirthDate());
				row.add(((RegularUser) us).getEmail());
				row.add(((RegularUser) us).getBankAccount());
				row.add(((RegularUser) us).getPhoneNumb());
				row.add(((RegularUser) us).getAddress());
				row.add(((RegularUser) us).getBalance() + "€");

				tableModelUsuarios.addRow(row);
			}

		}
		tabla = new JTable(tableModelUsuarios) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}

		};

		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 != 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});

		scrollPaneUsuarios.setBounds(new Rectangle(40, 165, 774, 119));
		scrollPaneUsuarios.setViewportView(tabla);

		this.getContentPane().add(scrollPaneUsuarios);

		Vector<Question> preguntas = facade.getAllQuestions();

		for (Question q : preguntas) {

			Vector<Object> row = new Vector<Object>();
			row.add(q.getQuestionNumber());
			row.add(q.getQuestion());
			row.add(q.getEvent().getDescription());

			tableModelPreguntas.addRow(row);

		}

		tablaPreguntas = new JTable(tableModelPreguntas) {
			@Override
			public Component prepareRenderer(TableCellRenderer renderer, int row, int column) {
				Component component = super.prepareRenderer(renderer, row, column);
				int rendererWidth = component.getPreferredSize().width;
				TableColumn tableColumn = getColumnModel().getColumn(column);
				tableColumn.setPreferredWidth(
						Math.max(rendererWidth + getIntercellSpacing().width, tableColumn.getPreferredWidth()));
				return component;
			}

		};

		tablaPreguntas.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 != 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});

		scrollPanePreguntas.setBounds(new Rectangle(865, 355, 689, 113));
		scrollPanePreguntas.setViewportView(tablaPreguntas);
		this.getContentPane().add(scrollPanePreguntas);

		lblInfo = new JLabel("Panel de control del administrador.");
		lblInfo.setBounds(58, 34, 544, 16);
		getContentPane().add(lblInfo);

		lblInfo2 = new JLabel(
				"Se pueden ver todos los usuarios registrados, todas las apuestas realizadas, todos los eventos y todas las preguntas.");
		lblInfo2.setBounds(58, 63, 746, 16);
		getContentPane().add(lblInfo2);

		
		ImageIcon icon = new ImageIcon("imagenes/info.png");
		lblIcono = new JLabel(icon);
		lblIcono.setBounds(27, 27, 30, 30);
		getContentPane().add(lblIcono);
		
		separator = new JSeparator();
		separator.setBounds(58, 91, 672, 16);
		getContentPane().add(separator);

		lblApuestas = new JLabel("Apuestas:");
		lblApuestas.setBounds(40, 327, 73, 16);
		getContentPane().add(lblApuestas);

		lblPreguntas = new JLabel("Preguntas:");
		lblPreguntas.setBounds(865, 327, 73, 16);
		getContentPane().add(lblPreguntas);

		lblUsuarios = new JLabel("Usuarios:");
		lblUsuarios.setBounds(40, 137, 55, 16);
		getContentPane().add(lblUsuarios);

		lblEventos = new JLabel("Eventos:");
		lblEventos.setBounds(865, 137, 73, 16);
		getContentPane().add(lblEventos);

	}
}
