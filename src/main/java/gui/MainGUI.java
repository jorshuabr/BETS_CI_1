package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.util.Locale;
import java.util.ResourceBundle;

/**
 * @author Software Engineering teachers
 */
import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.RegularUser;

public class MainGUI extends JFrame {

	private static final long serialVersionUID = 1L;

	private JPanel jContentPane = null;
	private JButton jButtonQueryQueries = null;

	private static BLFacade appFacadeInterface = LoginGUI.getBusinessLogic();

	private RegularUser ru = null;
	protected JLabel jLabelSelectOption;
	private JRadioButton rdbtnNewRadioButton;
	private JRadioButton rdbtnNewRadioButton_1;
	private JRadioButton rdbtnNewRadioButton_2;
	private JPanel panel;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JButton btnModificar = new JButton();
	private JLabel lblSaldo;
	private RegularUser userlog = null;
	private JButton btnBet;

	private JButton btnHistorial;
	private JButton btnAnularApuesta;

	private JButton btnCargarSaldo = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("MainGUI.cargarSaldo"));

	private JLabel lblFotoPerfil = new JLabel("");

	private JLabel lblFotoCargar = new JLabel("");
	private JLabel lblFotoMakeBet = new JLabel("");
	private JLabel lblFotoHistorial = new JLabel("");
	private JLabel lblFotoAnular = new JLabel("");

	/**
	 * This is the default constructor
	 */
	public MainGUI(RegularUser ru) {
		super();

		userlog = ru;

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					// TODO Auto-generated catch block
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				// System.exit(1);
			}
		});

		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	/**
	 * This method initializes this
	 * 
	 * @return void
	 */
	private void initialize() {
		// this.setSize(271, 295);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		this.setSize(820, 650);
		this.setContentPane(getJContentPane());
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle")
				+ "                                                            " + userlog.getUserName());
	}

	/**
	 * This method initializes jContentPane
	 * 
	 * @return javax.swing.JPanel
	 */
	private JPanel getJContentPane() {
		if (jContentPane == null) {
			jContentPane = new JPanel();
			jContentPane.setBackground(Color.WHITE);
			jContentPane.setLayout(null);

			jContentPane.add(getLblNewLabel());
			btnModificar.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblFotoPerfil.setVisible(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lblFotoPerfil.setVisible(false);

				}
			});

			btnModificar.setBounds(45, 99, 422, 85);
			// jContentPane.add(getBoton3());
			jContentPane.add(getBoton2());
			jContentPane.add(getBtnBet());
			jContentPane.add(getPanel());

			btnHistorial = new JButton();
			btnHistorial.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblFotoHistorial.setVisible(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lblFotoHistorial.setVisible(false);

				}
			});
			btnHistorial.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {

					Frame reg = new HistorialApuestasGUI(userlog);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
					dispose();

				}
			});
			btnHistorial.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnHistorial.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnHistorial.setBounds(45, 269, 422, 83);
			jContentPane.add(btnHistorial);
			jContentPane.add(getBtnAnularApuesta());
			btnCargarSaldo.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent arg0) {
					lblFotoCargar.setVisible(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lblFotoCargar.setVisible(false);

				}
			});
			btnCargarSaldo.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {

					Frame vista = new RecargarSaldoGUI(userlog);
					vista.setAlwaysOnTop(true);
					vista.setVisible(true);
					dispose();

				}
			});

			btnCargarSaldo.setBounds(45, 436, 422, 83);
			jContentPane.add(btnCargarSaldo);

			ImageIcon icon = new ImageIcon("imagenes/miniatura.png");
			lblFotoCargar = new JLabel(icon);
			lblFotoCargar.setVisible(false);
			lblFotoCargar.setBounds(485, 245, 259, 155);
			jContentPane.add(lblFotoCargar);

			ImageIcon icon2 = new ImageIcon("imagenes/makebet.png");
			lblFotoMakeBet = new JLabel(icon2);
			lblFotoMakeBet.setVisible(false);
			lblFotoMakeBet.setBounds(485, 245, 259, 155);
			jContentPane.add(lblFotoMakeBet);

			ImageIcon icon3 = new ImageIcon("imagenes/anularbet.png");
			lblFotoAnular = new JLabel(icon3);
			lblFotoAnular.setVisible(false);
			lblFotoAnular.setBounds(485, 245, 259, 155);
			jContentPane.add(lblFotoAnular);

			ImageIcon icon4 = new ImageIcon("imagenes/historial.png");
			lblFotoHistorial = new JLabel(icon4);
			lblFotoHistorial.setVisible(false);
			lblFotoHistorial.setBounds(485, 245, 259, 155);
			jContentPane.add(lblFotoHistorial);

			ImageIcon icon5 = new ImageIcon("imagenes/perfil.png");
			lblFotoPerfil = new JLabel(icon5);
			lblFotoPerfil.setVisible(false);
			lblFotoPerfil.setBounds(485, 245, 259, 155);
			jContentPane.add(lblFotoPerfil);

			JPanel panel_1 = new JPanel();
			panel_1.setBackground(new Color(0, 0, 128));
			panel_1.setBounds(554, 34, 145, 34);
			jContentPane.add(panel_1);
			panel_1.setLayout(null);
			panel_1.add(getlblSaldo());
		}
		return jContentPane;
	}

	/**
	 * This method initializes boton1
	 * 
	 * @return javax.swing.JButton
	 */
	private JButton getBoton2() {

		btnModificar.setText(ResourceBundle.getBundle("Etiquetas").getString("Show/EditProfile"));
		btnModificar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Frame reg = new ModificarDatosGUI(userlog);
				reg.setAlwaysOnTop(true);
				reg.setVisible(true);
				dispose();
			}
		});

		return btnModificar;
	}

	/**
	 * This method initializes boton2
	 * 
	 * @return javax.swing.JButton
	 */
//	private JButton getBoton3() {
//		if (jButtonQueryQueries == null) {
//			jButtonQueryQueries = new JButton();
//			jButtonQueryQueries.setText(ResourceBundle.getBundle("Etiquetas").getString("QueryQueries"));
//			jButtonQueryQueries.addActionListener(new java.awt.event.ActionListener() {
//				@Override
//				public void actionPerformed(java.awt.event.ActionEvent e) {
//					JFrame a = new FindQuestionsGUI();
//					a.setAlwaysOnTop(true);
//					a.setVisible(true);
//					a.setVisible(true);
//				}
//			});
//		}
//		return jButtonQueryQueries;
//	}

	private JLabel getLblNewLabel() {
		if (jLabelSelectOption == null) {
			jLabelSelectOption = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
			jLabelSelectOption.setBounds(0, 12, 536, 97);
			jLabelSelectOption.setFont(new Font("Tahoma", Font.BOLD, 13));
			jLabelSelectOption.setForeground(Color.BLACK);
			jLabelSelectOption.setHorizontalAlignment(SwingConstants.CENTER);
		}
		return jLabelSelectOption;
	}

	private JRadioButton getRdbtnNewRadioButton() {
		if (rdbtnNewRadioButton == null) {
			rdbtnNewRadioButton = new JRadioButton("English");
			rdbtnNewRadioButton.setBackground(Color.WHITE);
			rdbtnNewRadioButton.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("en"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton);
		}
		return rdbtnNewRadioButton;
	}

	private JRadioButton getRdbtnNewRadioButton_1() {
		if (rdbtnNewRadioButton_1 == null) {
			rdbtnNewRadioButton_1 = new JRadioButton("Euskara");
			rdbtnNewRadioButton_1.setBackground(Color.WHITE);
			rdbtnNewRadioButton_1.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Locale.setDefault(new Locale("eus"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_1);
		}
		return rdbtnNewRadioButton_1;
	}

	private JRadioButton getRdbtnNewRadioButton_2() {
		if (rdbtnNewRadioButton_2 == null) {
			rdbtnNewRadioButton_2 = new JRadioButton("Castellano");
			rdbtnNewRadioButton_2.setBackground(Color.WHITE);
			rdbtnNewRadioButton_2.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {
					Locale.setDefault(new Locale("es"));
					System.out.println("Locale: " + Locale.getDefault());
					redibujar();
				}
			});
			buttonGroup.add(rdbtnNewRadioButton_2);
		}
		return rdbtnNewRadioButton_2;
	}

	private JPanel getPanel() {
		if (panel == null) {
			panel = new JPanel();
			panel.setBackground(Color.WHITE);
			panel.setBounds(0, 543, 536, 40);
			panel.add(getRdbtnNewRadioButton_1());
			panel.add(getRdbtnNewRadioButton_2());
			panel.add(getRdbtnNewRadioButton());
		}
		return panel;
	}

	private void redibujar() {
		jLabelSelectOption.setText(ResourceBundle.getBundle("Etiquetas").getString("SelectOption"));
		btnBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet"));
		btnModificar.setText(ResourceBundle.getBundle("Etiquetas").getString("Show/EditProfile"));
		btnHistorial.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnHistorial.text"));
		lblSaldo.setText(
				ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblSaldo") + " " + userlog.getBalance() + "€");
		this.setTitle(ResourceBundle.getBundle("Etiquetas").getString("MainTitle"));
		btnAnularApuesta.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnAnularApuesta.text"));
		btnCargarSaldo.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.cargarSaldo"));

	}

	public void close() {
		this.setVisible(false);
	}

	private JButton getBtnBet() {
		if (btnBet == null) {
			btnBet = new JButton();
			btnBet.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblFotoMakeBet.setVisible(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lblFotoMakeBet.setVisible(false);

				}
			});
			btnBet.setBounds(45, 184, 422, 85);
			btnBet.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent arg0) {
					Frame reg = new CreateBetGUI(userlog);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
					dispose();
				}
			});
			btnBet.setText(ResourceBundle.getBundle("Etiquetas").getString("MakeBet")); //$NON-NLS-1$ //$NON-NLS-2$
		}
		return btnBet;
	}

	private JLabel getlblSaldo() {
		if (lblSaldo == null) {
			lblSaldo = new JLabel(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.lblSaldo") + " "
					+ userlog.getBalance() + "€");
			lblSaldo.setBounds(8, 5, 205, 19);
			lblSaldo.setFont(new Font("Tahoma", Font.BOLD, 14));
			lblSaldo.setForeground(Color.WHITE);
		}
		return lblSaldo;
	}

	private JButton getBtnAnularApuesta() {
		if (btnAnularApuesta == null) {
			btnAnularApuesta = new JButton();
			btnAnularApuesta.addMouseListener(new MouseAdapter() {
				@Override
				public void mouseEntered(MouseEvent e) {
					lblFotoAnular.setVisible(true);
				}

				@Override
				public void mouseExited(MouseEvent e) {
					lblFotoAnular.setVisible(false);

				}
			});
			btnAnularApuesta.addActionListener(new ActionListener() {
				@Override
				public void actionPerformed(ActionEvent e) {

					Frame reg = new AnularApuestaGUI(userlog);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
					dispose();

				}
			});
			btnAnularApuesta.setText(ResourceBundle.getBundle("Etiquetas").getString("MainGUI.btnAnularApuesta.text")); //$NON-NLS-1$ //$NON-NLS-2$
			btnAnularApuesta.setBounds(45, 352, 422, 83);
		}
		return btnAnularApuesta;
	}
}
// @jve:decl-index=0:visual-constraint="0,0"
