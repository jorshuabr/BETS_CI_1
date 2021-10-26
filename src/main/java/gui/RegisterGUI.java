package gui;

import java.awt.Color;
import java.awt.Font;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.ZoneId;
import java.util.Calendar;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;

import businessLogic.BLFacade;
import domain.User;

public class RegisterGUI extends JFrame {
	private JLabel lblNombre;
	private JLabel lblApellidos;
	private JLabel lblFechaNacimiento;
	private JLabel lblEmail;
	private JLabel lblCuentaBancaria;
	private JLabel lblTelfono;
	private JLabel lblDireccin;
	private JTextField textUser;
	private JTextField textName;
	private JTextField textLastName;
	private JTextField textBirth;
	private JTextField textEmail;
	private JTextField textBank;
	private JTextField textPhoneNumber;
	private JTextField textAddress;
	private JPasswordField textPass;
	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private static User user;
	JButton buttonRegistrarse = new JButton("Registrarse");
	private JButton btnAtras;
	private JLabel lblInfoNombre;
	private JLabel lblInfoNombre_1;
	private JLabel lblIcono2;
	private JLabel lblInfoDir;

	public RegisterGUI() {
		getContentPane().setBackground(Color.WHITE);
		try {
			initialize();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public void initialize() throws ParseException {
		setTitle("Registro");
		getContentPane().setLayout(null);
		setBounds(100, 100, 763, 450);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);

		JLabel lblUser = new JLabel("Usuario:");
		lblUser.setBounds(5, 55, 100, 14);
		lblUser.setFont(new Font("Tahoma", Font.PLAIN, 13));
		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		getContentPane().add(lblUser);

		JLabel lblUser_1 = new JLabel("Contraseña:");
		lblUser_1.setBounds(5, 84, 100, 14);
		lblUser_1.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser_1.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblUser_1);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(285, 56, 100, 14);
		lblNombre.setHorizontalAlignment(SwingConstants.RIGHT);
		lblNombre.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblNombre);

		lblApellidos = new JLabel("Apellidos:");
		lblApellidos.setBounds(285, 96, 100, 14);
		lblApellidos.setHorizontalAlignment(SwingConstants.RIGHT);
		lblApellidos.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblApellidos);

		lblFechaNacimiento = new JLabel("Fecha nacimiento:");
		lblFechaNacimiento.setBounds(268, 133, 117, 14);
		lblFechaNacimiento.setHorizontalAlignment(SwingConstants.RIGHT);
		lblFechaNacimiento.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblFechaNacimiento);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(285, 173, 100, 14);
		lblEmail.setHorizontalAlignment(SwingConstants.RIGHT);
		lblEmail.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblEmail);

		lblCuentaBancaria = new JLabel("Cuenta bancaria:");
		lblCuentaBancaria.setBounds(285, 213, 100, 14);
		lblCuentaBancaria.setHorizontalAlignment(SwingConstants.RIGHT);
		lblCuentaBancaria.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblCuentaBancaria);

		lblTelfono = new JLabel("Teléfono:");
		lblTelfono.setBounds(285, 252, 100, 14);
		lblTelfono.setHorizontalAlignment(SwingConstants.RIGHT);
		lblTelfono.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblTelfono);

		lblDireccin = new JLabel("Dirección:");
		lblDireccin.setBounds(285, 292, 100, 14);
		lblDireccin.setHorizontalAlignment(SwingConstants.RIGHT);
		lblDireccin.setFont(new Font("Tahoma", Font.PLAIN, 13));
		getContentPane().add(lblDireccin);

		textUser = new JTextField();
		textUser.setBounds(115, 51, 131, 20);
		textUser.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textUser.setColumns(10);
		getContentPane().add(textUser);

		textName = new JTextField();
		textName.setBounds(395, 55, 131, 20);
		textName.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textName.setColumns(10);
		getContentPane().add(textName);

		textLastName = new JTextField();
		textLastName.setBounds(395, 95, 131, 20);
		textLastName.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textLastName.setColumns(10);
		getContentPane().add(textLastName);

		textBirth = new JTextField();
		textBirth.setBounds(395, 132, 131, 20);
		textBirth.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textBirth.setColumns(10);
		getContentPane().add(textBirth);

		textEmail = new JTextField();
		textEmail.setBounds(395, 172, 159, 20);
		textEmail.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textEmail.setColumns(10);
		getContentPane().add(textEmail);

		textBank = new JTextField();
		textBank.setBounds(395, 212, 153, 20);
		textBank.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textBank.setColumns(10);
		getContentPane().add(textBank);

		textPhoneNumber = new JTextField();
		textPhoneNumber.setBounds(395, 251, 131, 20);
		textPhoneNumber.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textPhoneNumber.setColumns(10);
		getContentPane().add(textPhoneNumber);

		textAddress = new JTextField();
		textAddress.setBounds(395, 291, 227, 20);
		textAddress.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textAddress.setColumns(10);
		getContentPane().add(textAddress);

		textPass = new JPasswordField();
		textPass.setBounds(115, 82, 131, 19);
		textPass.setFont(new Font("Liberation Sans", Font.PLAIN, 13));
		textPass.setColumns(10);
		getContentPane().add(textPass);
		buttonRegistrarse.setForeground(Color.WHITE);
		buttonRegistrarse.setBackground(SystemColor.textHighlight);
		buttonRegistrarse.setFont(new Font("Dialog", Font.BOLD, 14));

		buttonRegistrarse.setBounds(440, 348, 140, 23);
		getContentPane().add(buttonRegistrarse);

		JLabel lblNewLabel = new JLabel("dd/mm/aaaa");
		lblNewLabel.setForeground(Color.GRAY);
		lblNewLabel.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblNewLabel.setBounds(530, 132, 79, 16);
		getContentPane().add(lblNewLabel);

		JLabel lblEsxxXxxxXxxx = new JLabel("ESXX XXXX XXXX XXXX ");
		lblEsxxXxxxXxxx.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblEsxxXxxxXxxx.setForeground(Color.GRAY);
		lblEsxxXxxxXxxx.setBounds(553, 212, 146, 16);
		getContentPane().add(lblEsxxXxxxXxxx);

		JLabel lblXxxxxxxxx = new JLabel("(+34) XXXXXXXXX");
		lblXxxxxxxxx.setForeground(Color.GRAY);
		lblXxxxxxxxx.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblXxxxxxxxx.setBounds(530, 251, 146, 16);
		getContentPane().add(lblXxxxxxxxx);

		JLabel lblAlMenos = new JLabel("Al menos:");
		lblAlMenos.setForeground(Color.GRAY);
		lblAlMenos.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));

		lblAlMenos.setBounds(81, 113, 146, 16);
		getContentPane().add(lblAlMenos);

		JLabel lblCaracteres = new JLabel("8 caracteres");
		lblCaracteres.setForeground(Color.GRAY);
		lblCaracteres.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));

		lblCaracteres.setBounds(101, 133, 146, 16);
		getContentPane().add(lblCaracteres);

		JLabel lblUnaMayscula = new JLabel("Una mayúscula");
		lblUnaMayscula.setForeground(Color.GRAY);
		lblUnaMayscula.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));

		lblUnaMayscula.setBounds(101, 152, 146, 16);
		getContentPane().add(lblUnaMayscula);

		JLabel lblUnaMin = new JLabel("Una minúscula");
		lblUnaMin.setForeground(Color.GRAY);
		lblUnaMin.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));

		// lblUnaMin.setFont(new Font("Dialog", Font.BOLD, 10));
		lblUnaMin.setBounds(102, 171, 146, 16);
		getContentPane().add(lblUnaMin);

		JLabel lblUnNmero = new JLabel("Un número");
		lblUnNmero.setForeground(Color.GRAY);
		lblUnNmero.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));

		// lblUnNmero.setFont(new Font("Dialog", Font.BOLD, 10));
		lblUnNmero.setBounds(101, 190, 146, 16);
		getContentPane().add(lblUnNmero);

		JLabel lblUnCaracterEspecial = new JLabel("Un caracter especial");
		lblUnCaracterEspecial.setForeground(Color.GRAY);
		lblUnCaracterEspecial.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));

		// lblUnCaracterEspecial.setFont(new Font("Dialog", Font.BOLD, 10));
		lblUnCaracterEspecial.setBounds(101, 208, 146, 16);
		getContentPane().add(lblUnCaracterEspecial);

		JLabel lblSinEspaciosEn = new JLabel("Sin espacios en blanco");
		lblSinEspaciosEn.setForeground(Color.GRAY);
		lblSinEspaciosEn.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 10));

		// lblSinEspaciosEn.setFont(new Font("Dialog", Font.BOLD, 10));
		lblSinEspaciosEn.setBounds(101, 229, 146, 16);
		getContentPane().add(lblSinEspaciosEn);

		JLabel lblgmailcom = new JLabel("debe ser @gmail.com");
		lblgmailcom.setForeground(Color.GRAY);
		lblgmailcom.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));

		// lblgmailcom.setFont(new Font("Dialog", Font.BOLD, 12));
		lblgmailcom.setBounds(559, 174, 146, 16);
		getContentPane().add(lblgmailcom);

		btnAtras = new JButton("Atrás");
		btnAtras.setBackground(SystemColor.controlShadow);
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAtras.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

//				Frame reg = new LoginGUI();
//				reg.setAlwaysOnTop(true);
//				reg.setVisible(true);
				dispose();

			}
		});
		btnAtras.setBounds(54, 348, 79, 23);
		getContentPane().add(btnAtras);

		JLabel lblIcono = new JLabel(" ");
		lblIcono.setIcon(new ImageIcon("C:\\Users\\Bryan\\Desktop\\Workspace\\ISBets21BRYMAUJONUNA\\info.png"));
		lblIcono.setBounds(54, 106, 30, 30);
		getContentPane().add(lblIcono);

		lblInfoNombre = new JLabel("Sin números");
		lblInfoNombre.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblInfoNombre.setForeground(Color.GRAY);
		lblInfoNombre.setBounds(530, 55, 95, 16);
		getContentPane().add(lblInfoNombre);

		lblInfoNombre_1 = new JLabel("Sin números");
		lblInfoNombre_1.setForeground(Color.GRAY);
		lblInfoNombre_1.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 11));
		lblInfoNombre_1.setBounds(530, 95, 95, 16);
		getContentPane().add(lblInfoNombre_1);

		ImageIcon icon = new ImageIcon("imagenes/info.png");
		lblIcono2 = new JLabel(icon);
		lblIcono2.setBounds(640, 284, 30, 30);
		getContentPane().add(lblIcono2);

		lblInfoDir = new JLabel("Opcional");
		lblInfoDir.setBounds(665, 291, 55, 16);
		lblInfoDir.setFont(new Font("Tahoma", Font.BOLD | Font.ITALIC, 12));
		lblInfoDir.setForeground(Color.GRAY);

		getContentPane().add(lblInfoDir);
		buttonRegistrarse.addActionListener(new ActionListener() {
			@SuppressWarnings("deprecation")
			@Override
			public void actionPerformed(ActionEvent e) {

				try {
					boolean control = true;
					if (textUser.getText().equals("")) {
						JOptionPane.showMessageDialog(getContentPane(), "Introduce un nombre de usuario", "Error",
								JOptionPane.ERROR_MESSAGE);
						control = false;

					}

					if (validoNombre(textName.getText()) == false) {
						textName.setText("");

						JOptionPane.showMessageDialog(getContentPane(), "Nombre con solo letras", "Error",
								JOptionPane.ERROR_MESSAGE);
						control = false;

					}

					if (validoNombre(textLastName.getText()) == false) {
						textLastName.setText("");

						JOptionPane.showMessageDialog(getContentPane(), "Apellidos con solo letras", "Error",
								JOptionPane.ERROR_MESSAGE);
						control = false;

					}

					if (validofecha(textBirth.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "Fecha incorrecta", "Error",
								JOptionPane.ERROR_MESSAGE);
						control = false;

					}

					if (validoEmail(textEmail.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "Email incorrecto", "Error",
								JOptionPane.ERROR_MESSAGE);
						control = false;

					}

					if (validoContraseña(textPass.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "Contraseña incorrecta", "Error",
								JOptionPane.ERROR_MESSAGE);
						control = false;

					}
					if (textBank.getText().equals("")) {

						control = true;
					} else if (validoCuentaB(textBank.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "Cuenta Bancaria incorrecta", "Error",
								JOptionPane.ERROR_MESSAGE);
						control = false;

					}
					if (validoNumero(textPhoneNumber.getText()) == false) {
						JOptionPane.showMessageDialog(getContentPane(), "Numero de teléfono incorrecto", "Error",
								JOptionPane.ERROR_MESSAGE);
						control = false;

					}

					if (control) {
						facade.registrar(textUser.getText(), textPass.getText(), textName.getText(),
								textLastName.getText(), textBirth.getText(), textEmail.getText(), textBank.getText(),
								Integer.parseInt(textPhoneNumber.getText()), textAddress.getText(), 0);
						JOptionPane.showMessageDialog(getContentPane(), "Registrado correctamente el usuario: "
								+ textUser.getText() + "\n\nSerá redirigido al apartado para loguearse...");

						JOptionPane.showMessageDialog(getContentPane(),
								"ENHORABUENA!!!!!\nTe regalamos un bono de bienvenida de 5€ para que apuestes");
						facade.aplicarBonoBienvenida(textUser.getText());
						dispose();
					}

				} catch (exceptions.UserAlreadyExistException ex) {

					JOptionPane.showMessageDialog(getContentPane(), ex.getMessage(), "Error",
							JOptionPane.ERROR_MESSAGE);
				}

				catch (Exception o) {
					o.printStackTrace();

				}

			}

		});

	}

	/* *************************************************************** */
	/* FUNCIONES AUXILIARES DE COMPROBACIÓN DE CAMPOS PARA EL REGISTRO */
	/* *************************************************************** */

	// se verifica que el nombre sea solo formado por letras o espacios en blanco,
	// tambien usado para apellidos
	private boolean validoNombre(String nombre) {
		int longitud = nombre.toCharArray().length;

		for (int i = 0; i < longitud; i++) {
			if (!Character.isLetter(nombre.toCharArray()[i]) && !Character.isWhitespace(nombre.toCharArray()[i])) {
				return false;
			}
		}

		return true;
	}

	// se verifica que la fecha sea valida
	private boolean validofecha(String pfecha) {
		try {

			String[] parts = pfecha.split("\\/"); // se divide el string pfecha en partes separadas por un /
			if (parts.length != 3) { // se verifica que se el array surgido tiene tres elementos XX/XX/XXXX, sino
										// devuelve false
				return false;
			} else {
				int year; // año
				int month; // mes [1,...,12]
				int dayOfMonth; // día [1,...,31]
				dayOfMonth = Integer.parseInt(parts[0]); // se parsea a int la primera parte XX/xx/xxxx, de string a int
				month = Integer.parseInt(parts[1]); // se parsea a int la segunda parte xx/XX/xxxx, de string a int
				year = Integer.parseInt(parts[2]); // se parsea a int la tercera parte xx/xx/XXXX, de string a int
				if (year < 1900) { // se verifica que el año sea mayor a 1900
					throw new IllegalArgumentException("Año inválido.");
				}

				Calendar calendar = Calendar.getInstance(); // se combierte la fecha en un Calendar, que automaticamente
				// si al intentarlo no puede, lanza excepcion como fecha
				calendar.setLenient(false);
				calendar.set(Calendar.YEAR, year); // no valida
				calendar.set(Calendar.MONTH, month - 1); // [0,...,11] (la clase Calendar trata asi los meses 0-11)
				calendar.set(Calendar.DAY_OF_MONTH, dayOfMonth);
				Date date = calendar.getTime(); // date es la fecha en formato Calendar XXXX/XX/XX
				Date date2 = Date.from(LocalDate.now().atStartOfDay(ZoneId.systemDefault()).toInstant()); // fecha
																											// actual en
																											// formato
																											// Date
				if (date.after(date2)) { // se verifica que la fecha introducida no es posterior a la actual
					System.out.println("FECHA POSTERIOR A HOY");
					throw new IllegalArgumentException("Año inválido.");

				}
				SimpleDateFormat sdf = new SimpleDateFormat("dd/MM/yyyy"); // se transforma la fecha formato Calendar a
																			// formato Date
				System.out.println(sdf.format(date)); // dd/MM/yyyy //es decir, de XXXX/XX/XX a XX/XX/XXXX
				System.out.println("Fecha validada");
				System.out.println("La fecha actual es: " + LocalDate.now());
				return true;
			}

		} catch (Exception p) {
			System.out.println("FECHA NO VALIDA");
			return false;
		}

	}

	private boolean validoEmail(String email) {
		Pattern pattern = Pattern.compile("^[a-zA-Z0-9]+((\\_|\\.)[a-zA-Z0-9]+)?@gmail\\.com$");
		Matcher mat = pattern.matcher(email);
		if (mat.matches()) {
			System.out.println("Porfin metes un email correscto estupida");

		} else {
			System.out.println("Amiga que email de meirda es ese");
			return false;
		}
		return true;
	}

	public static boolean validoContraseña(String contraseña) {
		Pattern pattern = Pattern.compile("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[@#$%^&?+=])(?=\\S+$).{8,}$");
		Matcher mat = pattern.matcher(contraseña);
		if (mat.matches()) {
			System.out.println("Porfin metes una contraseña correcta estupida");

		} else {
			System.out.println("Amiga que contraseña de mierda es esa");
			return false;
		}
		return true;
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

	// +34(opcional) 123456789
	private boolean validoNumero(String numero) {
		Pattern pattern = Pattern.compile("^(\\+[0-9]+\\s)?[0-9]{9}$");
		Matcher mat = pattern.matcher(numero);
		if (mat.matches()) {
			System.out.println("El formato del numero de telefono cumple el formato");
		} else {
			System.out.println("El formato del numero de telofono es incorrecto");
			return false;
		}
		return true;
	}
}
