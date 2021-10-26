package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.util.ResourceBundle;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ButtonGroup;
import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JSeparator;
import javax.swing.JTextField;

import domain.RegularUser;

public class RecargarSaldoGUI extends JFrame {

	private RegularUser userlog;
	private RegularUser userActualizado;
	private final ButtonGroup buttonGroup = new ButtonGroup();
	private JTextField textNumTar;
	private JTextField textCadTar1;
	private JTextField textCadTar2;

	private JTextField textVerifTar;
	private JTextField textCuentaB;
	private JLabel labelNumTar = new JLabel("Número de tarjeta:");
	private JLabel labelCadTar = new JLabel("Caducidad:");
	private JLabel labelVerifTar = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("RecargarSaldoGUI.labelVerifTar.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel labelCuentaB = new JLabel("Número de cuenta:");
	private JRadioButton radioCuentaB = new JRadioButton("Cuenta bancaria");
	private JRadioButton radioTarjeta = new JRadioButton("Tarjeta de crédito");
	private JPanel panel = new JPanel();
	private JLabel lblPagarCon = new JLabel("");
	private JLabel lblIconoTarjetas = new JLabel();
	private final JLabel lblInfoCad = new JLabel();
	private final JLabel lblIcono2;
	private final JLabel lblIcono3;
	private final JLabel lblInfoCodSeg = new JLabel();
	private final JLabel lblImporte = new JLabel();
	private JTextField textImporte;
	private final JPanel panel_1 = new JPanel();
	private final JLabel lblSaldoActualizado = new JLabel();
	private final JLabel lblSaldoActual = new JLabel();
	private final JPanel panel_2 = new JPanel();
	private final JLabel lblSaldoActualInfo = new JLabel(); // $NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblSaldoPrevisto = new JLabel(); // $NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblUsername = new JLabel();
	private final JLabel lblNombre = new JLabel(); // $NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblNewLabel = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("RecargarSaldoGUI.lblNewLabel.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblNewLabel_1 = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("RecargarSaldoGUI.lblNewLabel_1.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private JLabel lblActualizarSaldo;
	private JButton btnCargarSaldo = new JButton(
			ResourceBundle.getBundle("Etiquetas").getString("MainGUI.cargarSaldo"));
	private JButton btnCancelar = new JButton("Cancelar");

	private businessLogic.BLFacade facade = LoginGUI.getBusinessLogic();
	private final JLabel lblNewLabel_2 = new JLabel(
			ResourceBundle.getBundle("Etiquetas").getString("RecargarSaldoGUI.lblNewLabel_2.text")); //$NON-NLS-1$ //$NON-NLS-2$
	private final JLabel lblErrorPrevio = new JLabel("Error: importe vacío");
	private JTextField texto;
	private Thread hilo;
	private Object objeto = new Object();
	private JLabel lblIconoTarjetas_1;

	public RecargarSaldoGUI(RegularUser ru) {
		getContentPane().setBackground(Color.WHITE);
		userlog = ru;
		userActualizado = ru;
		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setTitle("Recargar saldo"
				+ "                                                                                                                                        "
				+ userlog.getUserName());

		this.setSize(1400, 800);

		getContentPane().setLayout(null);

		JLabel lblInfo = new JLabel("Aquí podrás recargar tu saldo. Puedes elegir los siguientes métodos de pago:");
		lblInfo.setBounds(85, 51, 494, 13);
		getContentPane().add(lblInfo);

		JLabel lblTarjetaDeCrdito = new JLabel("⚫ Tarjeta de crédito\r\n");
		lblTarjetaDeCrdito.setToolTipText("");
		lblTarjetaDeCrdito.setBounds(85, 76, 494, 13);
		getContentPane().add(lblTarjetaDeCrdito);

		JLabel lblCuentaBancaria = new JLabel("⚫ Cuenta bancaria");
		lblCuentaBancaria.setToolTipText("");
		lblCuentaBancaria.setBounds(85, 98, 494, 13);
		getContentPane().add(lblCuentaBancaria);

		ImageIcon icon = new ImageIcon("imagenes/info.png");
		


		JSeparator separator = new JSeparator();
		separator.setBounds(85, 155, 457, 13);
		getContentPane().add(separator);
		radioTarjeta.setBackground(Color.WHITE);

		radioTarjeta.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				radioCuentaB.setForeground(Color.black);
				lblIconoTarjetas.setVisible(true);
				lblIconoTarjetas_1.setVisible(true);
				lblInfoCad.setVisible(true);
				lblIcono2.setVisible(true);
				lblIcono3.setVisible(true);
				lblInfoCodSeg.setVisible(true);
				textImporte.setVisible(true);
				lblImporte.setVisible(true);
				lblActualizarSaldo.setVisible(true);
				btnCargarSaldo.setVisible(true);
				btnCancelar.setVisible(true);

				lblPagarCon.setText("PAGAR CON TARJETA");
				radioTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
				radioTarjeta.setForeground(SystemColor.textHighlight);
				textCuentaB.setVisible(false);
				labelCuentaB.setVisible(false);
				textNumTar.setVisible(true);
				textCadTar1.setVisible(true);
				textCadTar2.setVisible(true);
				textVerifTar.setVisible(true);
				labelNumTar.setVisible(true);
				labelCadTar.setVisible(true);
				labelVerifTar.setVisible(true);

			}
		});
		radioTarjeta.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonGroup.add(radioTarjeta);
		radioTarjeta.setBounds(942, 133, 243, 21);
		getContentPane().add(radioTarjeta);
		radioCuentaB.setBackground(Color.WHITE);

		radioCuentaB.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				lblIconoTarjetas_1.setVisible(false);
				lblPagarCon.setText("PAGAR CON CUENTA BANCARIA");
				lblIconoTarjetas.setVisible(false);
				lblInfoCad.setVisible(false);
				lblIcono2.setVisible(false);
				lblIcono3.setVisible(false);
				lblInfoCodSeg.setVisible(false);
				textImporte.setVisible(true);
				lblImporte.setVisible(true);
				lblActualizarSaldo.setVisible(true);
				btnCargarSaldo.setVisible(true);
				btnCancelar.setVisible(true);

				radioTarjeta.setForeground(Color.black);
				radioCuentaB.setForeground(SystemColor.textHighlight);
				textCuentaB.setVisible(true);
				labelCuentaB.setVisible(true);
				textNumTar.setVisible(false);
				textCadTar1.setVisible(false);
				textCadTar2.setVisible(false);
				textVerifTar.setVisible(false);
				labelNumTar.setVisible(false);
				labelCadTar.setVisible(false);
				labelVerifTar.setVisible(false);
			}
		});
		radioCuentaB.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonGroup.add(radioCuentaB);
		radioCuentaB.setBounds(942, 98, 166, 21);
		getContentPane().add(radioCuentaB);

		panel.setBackground(Color.WHITE);
		panel.setBorder(null);
		panel.setForeground(Color.WHITE);
		panel.setBounds(787, 180, 522, 532);
		getContentPane().add(panel);
		panel.setLayout(null);

		ImageIcon icon5 = new ImageIcon("imagenes/tarjetas_credito2.png");
		
		
		

		textVerifTar = new JTextField();
		textVerifTar.setBounds(174, 281, 72, 39);
		panel.add(textVerifTar);
		textVerifTar.setVisible(false);

		textVerifTar.setForeground(Color.GRAY);
		textVerifTar.setFont(new Font("Arial", Font.PLAIN, 16));
		textVerifTar.setColumns(10);
		labelVerifTar.setBounds(30, 293, 160, 16);
		panel.add(labelVerifTar);
		labelCadTar.setBounds(30, 229, 160, 16);
		panel.add(labelCadTar);

		textCadTar1 = new JTextField();
		textCadTar1.setBounds(174, 217, 72, 39);
		panel.add(textCadTar1);
		textCadTar1.setVisible(false);

		textCadTar1.setForeground(Color.GRAY);
		textCadTar1.setFont(new Font("Arial", Font.PLAIN, 16));
		textCadTar1.setColumns(10);

		textCadTar2 = new JTextField();
		textCadTar2.setBounds(258, 217, 72, 39);
		panel.add(textCadTar2);
		textCadTar2.setVisible(false);

		textCadTar2.setForeground(Color.GRAY);
		textCadTar2.setFont(new Font("Arial", Font.PLAIN, 16));
		textCadTar2.setColumns(10);

		textNumTar = new JTextField();
		textNumTar.setBounds(174, 152, 300, 39);
		panel.add(textNumTar);
		textNumTar.setVisible(false);

		textNumTar.setForeground(Color.GRAY);
		textNumTar.setFont(new Font("Arial", Font.PLAIN, 16));
		textNumTar.setColumns(10);

		textCuentaB = new JTextField();
		textCuentaB.setBounds(174, 91, 300, 39);
		panel.add(textCuentaB);
		textCuentaB.setVisible(false);
		textCuentaB.setForeground(Color.GRAY);
		textCuentaB.setFont(new Font("Arial", Font.PLAIN, 16));
		textCuentaB.setColumns(10);
		labelCuentaB.setBounds(27, 103, 160, 16);
		panel.add(labelCuentaB);
		labelNumTar.setBounds(30, 164, 160, 16);
		panel.add(labelNumTar);
		lblPagarCon.setForeground(SystemColor.textHighlight);
		lblPagarCon.setFont(new Font("Dialog", Font.BOLD, 18));

		lblPagarCon.setBounds(24, 24, 349, 27);
		panel.add(lblPagarCon);
		lblInfoCad.setBounds(376, 230, 87, 16);
		panel.add(lblInfoCad);
		lblInfoCad.setText(ResourceBundle.getBundle("Etiquetas").getString("RecargarSaldoGUI.lblInfoCad.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblInfoCad.setVisible(false);
		lblInfoCad.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblInfoCad.setForeground(Color.GRAY);
		
		
		ImageIcon icon2 = new ImageIcon("imagenes/info.png");
		lblIcono2 = new JLabel(icon2);
		lblIcono2.setBounds(348, 215, 27, 44);
		lblIcono2.setVisible(false);
		panel.add(lblIcono2);
		
		

		
		
		
		
		
		
		

		ImageIcon icon3 = new ImageIcon("imagenes/info.png");
		lblIcono3 = new JLabel(icon3);
		lblIcono3.setBounds(268, 290, 30, 30);
		panel.add(lblIcono3);
		lblIcono3.setVisible(false);


		
		
		
		
		
		
		
		lblInfoCodSeg.setText(ResourceBundle.getBundle("Etiquetas").getString("RecargarSaldoGUI.lblInfoCodSeg.text")); //$NON-NLS-1$ //$NON-NLS-2$

		lblInfoCodSeg.setBounds(298, 293, 55, 16);
		lblInfoCodSeg.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblInfoCodSeg.setForeground(Color.GRAY);
		lblInfoCodSeg.setVisible(false);
		panel.add(lblInfoCodSeg);
		lblImporte.setForeground(SystemColor.textHighlight);
		lblImporte.setFont(new Font("Dialog", Font.BOLD, 20));
		lblImporte.setBounds(30, 382, 120, 34);
		lblImporte.setVisible(false);
		lblImporte.setText("Importe:");
		panel.add(lblImporte);

		textImporte = new JTextField();
//		textImporte.addKeyListener(new KeyAdapter() {
//			@Override
//			public void keyTyped(KeyEvent arg0) {
//
//				try {
//					Float cero = 0f;
//
//					Float suma = Float.parseFloat(textImporte.getText() + Character.toString(arg0.getKeyChar()));
//					lblSaldoActualizado.setText(suma.toString());
//					System.out.println("FLOAT");
//				} catch (Exception e) {
//					System.out.println("NO ES FLOAT");
//				}
//
//			}
//
//		});

		textImporte.setBounds(129, 382, 104, 40);
		panel.add(textImporte);
		textImporte.setVisible(false);
		textImporte.setForeground(Color.GRAY);
		textImporte.setFont(new Font("Arial", Font.PLAIN, 16));
		textImporte.setColumns(10);
		btnCargarSaldo.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (radioCuentaB.isSelected()) {
					if (validoCuentaB(textCuentaB.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "Cuenta bancaria incorrecta", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else {

						try {
							Float montante = Float.parseFloat(textImporte.getText());

							if (montante >= 1f) {
								if (facade.recargarSaldo(userlog.getUserName(), montante)) {
									JOptionPane.showMessageDialog(getContentPane(),
											"Saldo recargado con éxito: " + montante + " €");

									userActualizado = facade.getRegularUserByUsername(userlog.getUserName());
									lblSaldoActual.setText(userActualizado.getBalance().toString() + "  €");

								}
							} else {

								JOptionPane.showMessageDialog(getContentPane(),
										"El importe debe ser de un mínimo de 1 €", "Error", JOptionPane.ERROR_MESSAGE);
							}

						} catch (Exception e) {
							JOptionPane.showMessageDialog(getContentPane(), "El importe es incorrecto", "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					}
				} else if (radioTarjeta.isSelected()) {

					if (validoTarjeta(textNumTar.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "Tarjeta de crédito incorrecta", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (validoMes(textCadTar1.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "El mes debe ser entre 01 y 12", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (validoAño(textCadTar2.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "El año debe ser entre 21 y 28", "Error",
								JOptionPane.ERROR_MESSAGE);
					} else if (validoCVC(textVerifTar.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "El CVC debe ser un número de 3 cifras",
								"Error", JOptionPane.ERROR_MESSAGE);
					} else {

						try {
							Float montante = Float.parseFloat(textImporte.getText());

							if (montante >= 1f) {
								if (facade.recargarSaldo(userlog.getUserName(), montante)) {

									JOptionPane.showMessageDialog(getContentPane(),
											"Saldo recargado con éxito: " + montante + " €");

									userActualizado = facade.getRegularUserByUsername(userlog.getUserName());
									lblSaldoActual.setText(userActualizado.getBalance().toString() + "  €");
								}
							} else {
								JOptionPane.showMessageDialog(getContentPane(),
										"El importe debe ser de un mínimo de 1 €", "Error", JOptionPane.ERROR_MESSAGE);
							}

						} catch (Exception e) {
							JOptionPane.showMessageDialog(getContentPane(), "El importe es incorrecto", "Error",
									JOptionPane.ERROR_MESSAGE);
						}
					}

				}

			}
		});

		btnCargarSaldo.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCargarSaldo.setBounds(334, 471, 140, 27);
		panel.add(btnCargarSaldo);
		btnCargarSaldo.setVisible(false);
		btnCargarSaldo.setForeground(Color.WHITE);
		btnCargarSaldo.setBackground(SystemColor.textHighlight);
		btnCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (radioCuentaB.isSelected()) {
					textCuentaB.setText("");
					textImporte.setText("");
					lblErrorPrevio.setVisible(false);
				} else if (radioTarjeta.isSelected()) {
					textNumTar.setText("");
					textCadTar1.setText("");
					textCadTar2.setText("");
					textVerifTar.setText("");
					textImporte.setText("");
					lblErrorPrevio.setVisible(false);

				}
			}

		});

		btnCancelar.setForeground(Color.WHITE);
		btnCancelar.setBackground(SystemColor.controlShadow);
		btnCancelar.setVisible(false);
		btnCancelar.setFont(new Font("Dialog", Font.BOLD, 14));
		btnCancelar.setBounds(30, 472, 99, 26);
		panel.add(btnCancelar);

		lblActualizarSaldo = new JLabel("<HTML><U>Previo del futuro saldo</U></HTML>");
		lblActualizarSaldo.setFont(new Font("Dialog", Font.BOLD, 10));
		lblActualizarSaldo.setBounds(241, 394, 160, 16);
		lblActualizarSaldo.setVisible(false);

		panel.add(lblActualizarSaldo);
		lblErrorPrevio.setForeground(Color.RED);
		lblErrorPrevio.setFont(new Font("Dialog", Font.BOLD, 10));
		lblErrorPrevio.setBounds(376, 394, 120, 16);
		lblErrorPrevio.setVisible(false);

		panel.add(lblErrorPrevio);
		lblIconoTarjetas_1 = new JLabel(icon5);
		lblIconoTarjetas_1.setVisible(false);
		lblIconoTarjetas_1.setBounds(216, 74, 149, 30);
		panel.add(lblIconoTarjetas_1);
		lblActualizarSaldo.addMouseListener(new MouseAdapter() {
			@Override
			public void mouseClicked(MouseEvent arg0) {

				if (textImporte.getText().equals("")) {
					lblErrorPrevio.setVisible(true);
				} else {
					lblErrorPrevio.setVisible(false);

					Float importe = Float.parseFloat(textImporte.getText());
					Float suma = userActualizado.getBalance() + importe;
					lblSaldoActualizado.setText(suma.toString() + "  €");
				}

			}
		});

		JButton btnAtras = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		btnAtras.setBounds(47, 722, 99, 26);
		getContentPane().add(btnAtras);
		btnAtras.setBackground(SystemColor.controlShadow);
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setFont(new Font("Dialog", Font.BOLD, 14));
		panel_1.setBounds(58, 180, 522, 532);
		getContentPane().add(panel_1);
		panel_1.setBorder(null);
		panel_1.setBackground(Color.WHITE);
		panel_1.setLayout(null);
		panel_2.setBorder(null);
		panel_2.setBackground(SystemColor.textHighlight);
		panel_2.setBounds(0, 0, 522, 363);
		panel_1.add(panel_2);
		panel_2.setLayout(null);
		lblSaldoActual.setBounds(33, 26, 477, 125);
		panel_2.add(lblSaldoActual);
		lblSaldoActual.setForeground(Color.WHITE);
		lblSaldoActual.setFont(new Font("Dialog", Font.BOLD, 75));
		lblSaldoActual.setText(userlog.getBalance().toString() + "  €");
		lblSaldoActualizado.setBounds(33, 163, 498, 155);
		panel_2.add(lblSaldoActualizado);
		lblSaldoActualizado.setForeground(Color.WHITE);
		lblSaldoActualizado.setFont(new Font("Dialog", Font.BOLD, 75));
		lblSaldoActualInfo
				.setText(ResourceBundle.getBundle("Etiquetas").getString("RecargarSaldoGUI.lblSaldoActualInfo.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSaldoActualInfo.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSaldoActualInfo.setForeground(Color.WHITE);
		lblSaldoActualInfo.setBounds(33, 12, 174, 33);

		panel_2.add(lblSaldoActualInfo);
		lblSaldoPrevisto
				.setText(ResourceBundle.getBundle("Etiquetas").getString("RecargarSaldoGUI.lblSaldoPrevisto.text")); //$NON-NLS-1$ //$NON-NLS-2$
		lblSaldoPrevisto.setForeground(Color.WHITE);
		lblSaldoPrevisto.setFont(new Font("Dialog", Font.BOLD, 20));
		lblSaldoPrevisto.setBounds(33, 163, 174, 33);

		panel_2.add(lblSaldoPrevisto);
		lblUsername.setFont(new Font("Dialog", Font.BOLD, 16));
		lblUsername.setBounds(296, 402, 119, 22);
		lblUsername.setText(userlog.getUserName());

		panel_1.add(lblUsername);
		lblNombre.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNombre.setBounds(296, 466, 194, 22);
		lblNombre.setText(userlog.getFirstName() + " " + userlog.getLastName());
		panel_1.add(lblNombre);
		lblNewLabel.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel.setBounds(32, 399, 163, 29);

		panel_1.add(lblNewLabel);
		lblNewLabel_1.setFont(new Font("Dialog", Font.BOLD, 16));
		lblNewLabel_1.setBounds(32, 463, 180, 29);

		panel_1.add(lblNewLabel_1);

		JSeparator separator_1 = new JSeparator();
		separator_1.setBounds(32, 448, 446, 6);
		panel_1.add(separator_1);
		lblNewLabel_2.setBounds(85, 123, 149, 16);

		getContentPane().add(lblNewLabel_2);
		JLabel lblIcono = new JLabel(icon);
		lblIcono.setBounds(40, 37, 50, 27);
		getContentPane().add(lblIcono);

		btnAtras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				JFrame a = new MainGUI(userActualizado);
				a.setAlwaysOnTop(true);
				a.setVisible(true);
				dispose();
			}
		});

		labelNumTar.setVisible(false);
		labelCuentaB.setVisible(false);
		labelCadTar.setVisible(false);
		labelVerifTar.setVisible(false);

		if (userlog.getBankAccount().equals("") == false) {

			textCuentaB.setText(userlog.getBankAccount());

		} else {
			textCuentaB.setText("");

		}

	}

	// acepta cuentas bancarias del tipo: ES23 3434 2342 3423
	private boolean validoCuentaB(String cuentab) {
		Pattern pattern = Pattern.compile("^[A-Z]{2}[0-9]{2}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}$");
		Matcher mat = pattern.matcher(cuentab);
		if (mat.matches()) {
			System.out.println("El formato de la cuenta bancaria cumple el formato");
		} else {
			System.out.println("El formato de la cuenta bancaria es incorrecto");
			return false;
		}
		return true;
	}

	// acepta tarjetas de credito del tipo: 1111111111111111 o 1111 1111 1111 1111
	private boolean validoTarjeta(String tarjeta) {
		Pattern pattern = Pattern.compile("^([0-9]{16}|[0-9]{4}\\s[0-9]{4}\\s[0-9]{4}\\s[0-9]{4})$");
		Matcher mat = pattern.matcher(tarjeta);
		if (mat.matches()) {
			System.out.println("El formato de la tarjeta de credito cumple el formato");
		} else {
			System.out.println("El formato de la tarjeta de credito es incorrecto");
			return false;
		}
		return true;
	}

	private boolean validoMes(String mes) {

		try {
			Integer mesInt = Integer.parseInt(mes);
			if (mesInt < 1 || mesInt > 12) {

				return false;
			} else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}

	private boolean validoAño(String año) {

		try {
			Integer añoInt = Integer.parseInt(año);

			if (añoInt < 21 || añoInt > 28) {
				return false;

			}

			else {
				return true;
			}
		} catch (Exception e) {
			return false;
		}

	}

	private boolean validoCVC(String cod) {
		try {
			if (cod.charAt(0) == '0') {
				Integer codInt = Integer.parseInt(cod);
				int digitos = (int) (Math.log10(codInt) + 1);
				if (digitos != 2) {
					return false;
				} else {
					return true;
				}
			} else {
				Integer codInt = Integer.parseInt(cod);
				int digitos = (int) (Math.log10(codInt) + 1);
				if (digitos != 3) {
					return false;
				} else {
					return true;
				}
			}

		} catch (Exception e) {
			return false;
		}

	}

}
