package gui;

import java.awt.Color;
import java.awt.Component;
import java.awt.Font;
import java.awt.Rectangle;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JSeparator;
import javax.swing.JTable;
import javax.swing.table.DefaultTableCellRenderer;
import javax.swing.table.DefaultTableModel;
import javax.swing.table.TableCellRenderer;
import javax.swing.table.TableColumn;

import businessLogic.BLFacade;
import domain.Bet;
import domain.RegularUser;

public class AnularApuestaGUI extends JFrame {

	private JPanel contentPane;
	private JScrollPane scrollPaneApuestas = new JScrollPane();
	private JTable tabla;
	private String[] nombresColumnas = { "Fecha", "Evento", "Pregunta", "Apuesta a ", "Cant. apostada", "Cuota", "" };
	private DefaultTableModel tableModelApuestas = new DefaultTableModel(null, nombresColumnas) {
		@Override
		public boolean isCellEditable(int row, int column) {
			return false;
		}
	};

	private static BLFacade facade = LoginGUI.getBusinessLogic();

	private RegularUser userlog;
	private JButton btnAtras;
	private JLabel lblApuestas;
	private JLabel lblInfo;
	private JSeparator separator;
	private JLabel lblSaldo;
	private RegularUser newUserActualizado;
	private JLabel lblNewLabel_1;
	private static final String eti = "Etiquetas";

	public AnularApuestaGUI(RegularUser ru) {
		getContentPane().setBackground(Color.WHITE);
		userlog = ru;
		newUserActualizado = ru;

		setTitle(ResourceBundle.getBundle(eti).getString("MainGUI.btnAnularApuesta.text")
				+ "                                                                                                      "
				+ userlog.getUserName());

		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 1100, 500);
		getContentPane().setLayout(null);

		JButton btnEliminar = new JButton("Anular");
		btnEliminar.setName("Anular");
		Vector<Bet> apuestasUsuario = facade.getApuestasAbiertas(userlog);
		Vector<Bet> apuestasAbiertas = new Vector<Bet>();
		for (Bet bet : apuestasUsuario) {
			if (bet.getEstadoApuesta().equals("Pendiente")) {
				Vector<Object> row = new Vector<Object>();
				row.add(bet.getForecast().getQuestion().getEvent().getEventDate().toString().substring(0, 11));
				row.add(bet.getForecast().getQuestion().getEvent().getDescription());
				row.add(bet.getForecast().getQuestion().getQuestion());
				row.add(bet.getForecast().getForecast());
				row.add(bet.getAmount());
				row.add(bet.getForecast().getFee());
				row.addElement(btnEliminar);
				apuestasAbiertas.addElement(bet);
				tableModelApuestas.addRow(row);
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

		tabla.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent ev) {

				int column = tabla.getColumnModel().getColumnIndexAtX(ev.getX());
				int row = ev.getY() / tabla.getRowHeight();

				if (row < tabla.getRowCount() && row >= 0 && column < tabla.getColumnCount() && column >= 0) {
					Object value = tabla.getValueAt(row, column);
					if (value instanceof JButton) {
						((JButton) value).doClick();
						JButton boton = (JButton) value;
						try {
							int i = tabla.getSelectedRow();
							Bet apu = apuestasAbiertas.get(i);

//							int dialogButton = JOptionPane.YES_NO_OPTION;
//							JOptionPane.showConfirmDialog(getContentPane(), "¿Seguro que quieres anular la apuesta?",
//									"CUIDADO!", dialogButton);
//							if (dialogButton == JOptionPane.YES_OPTION) {
//								System.out.println("CLIC EN SI");

							System.out.println("Anulando la apuesta: " + apu.getForecast().getForecast() + " / "
									+ apu.getForecast().getQuestion() + " / "
									+ apu.getForecast().getQuestion().getEvent().getDescription() + "de la tabla");
							tableModelApuestas.removeRow(row);
							apuestasAbiertas.remove(i);
							if (facade.anularApuesta(apu)) {

								JOptionPane.showMessageDialog(getContentPane(),
										"La apuesta a " + apu.getForecast().getForecast()
												+ " ha sido anulada. \nEl importe de " + apu.getAmount() + "€"
												+ " será reintegrado inmediatamente...");

								System.out.println("APUESTA ANULADA");
								lblSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblSaldo")
										+ " " + (newUserActualizado.getBalance() + apu.getAmount()) + "€");
							}

//							}
//							if (dialogButton == JOptionPane.NO_OPTION) {
//								System.out.println("CLIC EN NO");
//								remove(dialogButton);
//							}

							newUserActualizado = facade.getRegularUserByUsername(userlog.getUserName());

						} catch (Exception e) {
						}

					}
				}

			}
		});

		tabla.setDefaultRenderer(Object.class, new Render());

		scrollPaneApuestas.setBounds(new Rectangle(40, 190, 996, 119));
		scrollPaneApuestas.setViewportView(tabla);
		this.getContentPane().add(scrollPaneApuestas);

		btnAtras = new JButton();
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAtras.setBackground(SystemColor.controlShadow);
		btnAtras.setText(ResourceBundle.getBundle("Etiquetas").getString("Close"));

		btnAtras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				RegularUser usuarioActualizado = facade.getRegularUserByUsername(userlog.getUserName());

				JFrame a = new MainGUI(usuarioActualizado);
				a.setAlwaysOnTop(true);
				a.setVisible(true);
				dispose();

			}
		});
		btnAtras.setBounds(52, 376, 99, 26);
		getContentPane().add(btnAtras);

		lblApuestas = new JLabel("Apuestas abiertas:");
		lblApuestas.setBounds(40, 156, 135, 16);
		getContentPane().add(lblApuestas);

		lblInfo = new JLabel(
				"Aquí podrás cancelar una apuesta que aún sigue en curso. Se te devolverá el dinero apostado.");
		lblInfo.setBounds(66, 48, 666, 47);
		getContentPane().add(lblInfo);

		separator = new JSeparator();
		separator.setBounds(64, 94, 535, 26);
		getContentPane().add(separator);

		JPanel panel = new JPanel();
		panel.setBackground(new Color(0, 0, 128));
		panel.setBounds(655, 26, 136, 36);
		getContentPane().add(panel);
		panel.setLayout(null);

		lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblSaldo") //$NON-NLS-1$ //$NON-NLS-2$
				+ " " + userlog.getBalance() + "€");
		lblSaldo.setBounds(10, 8, 197, 19);
		panel.add(lblSaldo);
		lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 14));
		lblSaldo.setForeground(Color.WHITE);

		ImageIcon icon = new ImageIcon("imagenes/info.png");
		lblNewLabel_1 = new JLabel(icon);
		lblNewLabel_1.setBounds(23, 48, 55, 40);
		getContentPane().add(lblNewLabel_1);

	}
}
