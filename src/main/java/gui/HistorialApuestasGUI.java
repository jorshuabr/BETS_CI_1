package gui;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Component;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Frame;
import java.awt.Image;
import java.awt.Rectangle;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.SwingConstants;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;
import javax.swing.text.TableView.TableRow;

import businessLogic.BLFacade;
import domain.Bet;
import domain.RegularUser;

import javax.swing.JScrollBar;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.awt.print.PrinterException;
import java.awt.event.ActionEvent;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JSeparator;
import java.awt.SystemColor;

public class HistorialApuestasGUI extends JFrame {

	private JScrollPane scrollPaneApuestas = new JScrollPane();
	private JScrollPane scrollPaneApuestas2 = new JScrollPane();

	private JTable tabla;
	private JTable tabla2;

	private String[] nombresColumnas = { "Fecha", "Evento", "Pregunta", "Apuesta a ", "Cant. apostada", "Cuota",
			"Estado" };
	private String[] nombresColumnas2 = { "Fecha", "Evento", "Pregunta", "Apuesta a", "Estado", "Pron. ganador",
			"Cant. apostada", "Cuota", "Ganado (Beneficio)" };

//	private String[][] datosFilas = {
//
//			{ "Bryan", "Sanchez", "23", "Español", "170" }, { "Mauri", "Contreras", "22", "Peruano", "150" },
//			{ "Melisa", "Fernandez", "21", "Español", "150" }, { "Jorshua", "Dickensen", "23", "Peruano", "160" },
//			{ "Leire", "Cartagena", "20", "Español", "150" }, { "Kofi", "Darko", "21", "Africano", "160" },
//
//	};

	private DefaultTableModel tableModelApuestas = new DefaultTableModel(null, nombresColumnas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private DefaultTableModel tableModelApuestas2 = new DefaultTableModel(null, nombresColumnas2) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};
//	private String estadoApuesta = "";

	private RegularUser userlog;

	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private JLabel lblInfo;

	public HistorialApuestasGUI(RegularUser ru) {
		getContentPane().setBackground(Color.WHITE);

		userlog = ru;
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnHistorial.text")
				+ "                                                                                                                                          " +  userlog.getUserName());
				
		
		getContentPane().setLayout(null);
		this.setSize(1388, 641);

		Vector<Bet> apuestasUsuario = facade.getApuestasByUser(userlog);

		/* TABLA 1 APUESTAS ABIERTAS */

		// Introducir datos de las dos tablas a los tablemodel, dependiendo de si la
		// apuesta esta cerrada o no
		for (Bet b : apuestasUsuario) {

//			if (b.getForecast().getForecast().equals(b.getForecast().getQuestion().getResult())) {
//				estadoApuesta = "Ganada";
//			} else {
//				estadoApuesta = "Perdida";
//			}

			if (b.getForecast().getQuestion().getResult() == null && b.getEstadoApuesta().equals("Anulada") == false) { // PRIMERA
																														// TABLA
																														// APUESTAS
																														// ABIERTAS
				Vector<Object> row = new Vector<Object>();
				row.add(b.getForecast().getQuestion().getEvent().getEventDate().toString().substring(0, 11));
				row.add(b.getForecast().getQuestion().getEvent().getDescription());
				row.add(b.getForecast().getQuestion().getQuestion());
				row.add(b.getForecast().getForecast());
				row.add(b.getAmount());
				row.add(b.getForecast().getFee());
				row.add(b.getEstadoApuesta());

				tableModelApuestas.addRow(row);
			} else { // SEGUNDA TABLA APUESTAS CERRADAS
				Vector<Object> row = new Vector<Object>();
				row.add(b.getForecast().getQuestion().getEvent().getEventDate().toString().substring(0, 11));
				row.add(b.getForecast().getQuestion().getEvent().getDescription());
				row.add(b.getForecast().getQuestion().getQuestion());
				row.add(b.getForecast().getForecast());
				row.add(b.getEstadoApuesta());
				row.add(b.getForecast().getQuestion().getResult());
				row.add(b.getAmount());
				row.add(b.getForecast().getFee());

				if (b.getEstadoApuesta().equals("Perdida")) {
					row.add("");

				} else if (b.getEstadoApuesta().equals("Anulada")) {

				} else {

					if (Float.toString((b.getAmount() * b.getForecast().getFee() - b.getAmount())).equals("0.0")) {
						row.add(b.getAmount() * b.getForecast().getFee());

					} else {
						row.add(b.getAmount() * b.getForecast().getFee() + " ("
								+ Float.toString((b.getAmount() * b.getForecast().getFee() - b.getAmount())) + ") ");

					}
				}
				tableModelApuestas2.addRow(row);
			}

		}

		// OVERRIDE DE METODO PARA QUE SE AJUSTEN AUTOMATICAMENTE LAS COLUMNAS DE LA
		// TABLA SEGUN EL CONTENIDO
		tabla = new JTable(tableModelApuestas) {
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

		// COLOR DE LAS FILAS DE LA TABLA GRIS Y BLANCO
		tabla.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 == 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});

		scrollPaneApuestas.setBounds(new Rectangle(35, 148, 958, 119));
		scrollPaneApuestas.setViewportView(tabla);
		this.getContentPane().add(scrollPaneApuestas);

		/* TABLA 2 APUESTAS CERRADAS */

		// OVERRIDE DE METODO PARA QUE SE AJUSTEN AUTOMATICAMENTE LAS COLUMNAS DE LA
		// TABLA SEGUN EL CONTENIDO
		tabla2 = new JTable(tableModelApuestas2) {
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

		// COLOR DE LAS FILAS DE LA TABLA GRIS Y BLANCO
		tabla2.setDefaultRenderer(Object.class, new DefaultTableCellRenderer() {
			@Override
			public Component getTableCellRendererComponent(JTable table, Object value, boolean isSelected,
					boolean hasFocus, int row, int column) {
				final Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row,
						column);

				c.setBackground(row % 2 == 0 ? new Color(233, 233, 233) : Color.WHITE);

				return c;
			}
		});

		scrollPaneApuestas2.setBounds(new Rectangle(35, 347, 1089, 119));
		scrollPaneApuestas2.setViewportView(tabla2);

		getContentPane().add(scrollPaneApuestas2);

		/* BOTON ATRAS, BOTONES DESCARGAR TABLA Y LABELS */

		JButton btnAtras = new JButton(
				ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnAtras.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setBackground(SystemColor.controlShadow);

		
		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				JFrame a = new MainGUI(userlog);
				a.setAlwaysOnTop(true);
				a.setVisible(true);
				dispose();

			}
		});
		btnAtras.setBounds(35, 537, 99, 26);
		getContentPane().add(btnAtras);

		JLabel lblApuestasAbiertas = new JLabel("Apuestas abiertas");
		lblApuestasAbiertas.setBounds(49, 120, 147, 16);
		getContentPane().add(lblApuestasAbiertas);

		JLabel lblApuestasCerradas = new JLabel("Apuestas cerradas");
		lblApuestasCerradas.setBounds(49, 314, 147, 16);
		getContentPane().add(lblApuestasCerradas);

		JButton btnDescargarTabla = new JButton("Descargar tabla");
		btnDescargarTabla.setFont(new Font("Dialog", Font.BOLD, 14));
		btnDescargarTabla.setForeground(Color.WHITE);
		btnDescargarTabla.setBackground(SystemColor.textHighlight);
		btnDescargarTabla.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {

				try {
					System.out.println("IMPRIMIDA LA TABLA");
					setAlwaysOnTop(false);
					tabla.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDescargarTabla.setBounds(1003, 193, 159, 26);
		getContentPane().add(btnDescargarTabla);

		JButton btnDescargarTabla2 = new JButton("Descargar tabla");
		btnDescargarTabla2.setForeground(Color.WHITE);
		btnDescargarTabla2.setFont(new Font("Dialog", Font.BOLD, 14));
		btnDescargarTabla2.setBackground(SystemColor.textHighlight);
		btnDescargarTabla2.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				try {
					System.out.println("IMPRIMIDA LA TABLA 2");
					setAlwaysOnTop(false);
					tabla2.print();
				} catch (PrinterException e1) {
					e1.printStackTrace();
				}
			}
		});
		btnDescargarTabla2.setBounds(1136, 401, 165, 26);
		getContentPane().add(btnDescargarTabla2);

		lblInfo = new JLabel(
				"Aquí podrás encontrar el historial de tus apuestas, tanto las que están en curso como las que están cerradas.");
		lblInfo.setBounds(100, 44, 748, 16);
		getContentPane().add(lblInfo);

		JSeparator separator = new JSeparator();
		separator.setBounds(94, 72, 631, 15);
		getContentPane().add(separator);

		ImageIcon icon = new ImageIcon("imagenes/info.png");
		JLabel lblNewLabel = new JLabel(icon);
		lblNewLabel.setBounds(68, 37, 30, 30);
		getContentPane().add(lblNewLabel);
		
		
		
		
		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 128));
		panel.setBounds(895, 37, 141, 30);
		getContentPane().add(panel);
		
				JLabel lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblSaldo") //$NON-NLS-1$ //$NON-NLS-2$
						+ " " + userlog.getBalance() + "€");
				panel.add(lblSaldo);
				lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 14));
				lblSaldo.setForeground(Color.WHITE);

	}
}
