package gui;

import java.awt.Frame;
import java.awt.SystemColor;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.ResourceBundle;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JCheckBox;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.border.Border;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.RegularUser;
import java.awt.Color;
import java.awt.Font;
import javax.swing.ImageIcon;
import javax.swing.UIManager;

public class ModificarDatosGUI extends JFrame {

	private JPanel contentPane;
	private JTextField fieldUsername;
	private JPasswordField fieldContraseña;
	private JPasswordField fieldContraseña2;
	private JPasswordField fieldContraseña3;
	private JCheckBox checkContraseña;
	private JTextField fieldNombre;
	private JTextField fieldApellido;
	private JTextField fieldEmail;
	private JTextField fieldCuentaBancaria;
	private JLabel lblUsername;
	private JLabel lblContraseña;
	private JLabel lblNombre;
	private JLabel lblApellido;
	private JLabel lblEmail;
	private JLabel lblCuentaBancaria;
	private JLabel lblContraseña2;
	private JLabel lblContraseña3;
	private JButton buttonCancelar;
	private JButton buttonModificar;
	private JButton buttonGuardar;
	private static BLFacade facade = LoginGUI.getBusinessLogic();

	private RegularUser userlog = null;
	private JTextField fieldSaldo;
	private JLabel lblSaldo;

	public ModificarDatosGUI(RegularUser ru) {

		userlog = ru;

		setTitle(ResourceBundle.getBundle("Etiquetas").getString("Show/EditProfile"));

		getContentPane().setLayout(null);
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 10, 730, 566);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setForeground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		Border border = BorderFactory.createLineBorder(Color.red, 1);
		Border border2 = BorderFactory.createLineBorder(SystemColor.textHighlight, 1);

		ImageIcon icon = new ImageIcon("imagenes/info.png");
		JLabel lblNewLabel = new JLabel(icon);
		lblNewLabel.setBounds(21, 0, 37, 52);
		contentPane.add(lblNewLabel);
		
		

		

		JLabel lblInfo = new JLabel("Aquí podrás ver el perfil de usuario y modificar los campos con borde azul."); //$NON-NLS-1$ //$NON-NLS-2$
		lblInfo.setBounds(56, 20, 486, 16);
		contentPane.add(lblInfo);

		lblSaldo = new JLabel("Saldo:");
		lblSaldo.setBounds(10, 311, 133, 13);
		contentPane.add(lblSaldo);

		fieldUsername = new JTextField();
		fieldUsername.setForeground(Color.GRAY);
		fieldUsername.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldUsername.setBounds(249, 66, 262, 29);
		fieldUsername.setBackground(UIManager.getColor("TextField.inactiveBackground"));
		contentPane.add(fieldUsername);
		fieldUsername.setColumns(10);
		fieldUsername.setEditable(false);
		fieldUsername.setText(userlog.getUserName());

		fieldContraseña = new JPasswordField();
		fieldContraseña.setForeground(Color.GRAY);
		fieldContraseña.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldContraseña.setBounds(249, 105, 262, 29);
		contentPane.add(fieldContraseña);
		fieldContraseña.setColumns(10);
		fieldContraseña.setEditable(false);
		fieldContraseña.setText(userlog.getUserPass());

		fieldContraseña2 = new JPasswordField();
		fieldContraseña2.setBounds(249, 144, 262, 29);
		fieldContraseña2.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldContraseña2.setForeground(Color.GRAY);
		fieldContraseña2.setVisible(false);
		fieldContraseña2.setBorder(border2);

		fieldContraseña3 = new JPasswordField();
		fieldContraseña3.setBounds(249, 183, 262, 29);
		fieldContraseña3.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldContraseña3.setForeground(Color.GRAY);
		fieldContraseña3.setBorder(border2);

		checkContraseña = new JCheckBox("Mostrar contraseña");
		checkContraseña.setBackground(Color.WHITE);

		checkContraseña.setVisible(false);
		checkContraseña.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {

				if (checkContraseña.isSelected()) {

					fieldContraseña2.setEchoChar((char) 0);
					fieldContraseña3.setEchoChar((char) 0);

				} else {

					fieldContraseña2.setEchoChar('•');
					fieldContraseña3.setEchoChar('•');

				}

			}
		});
		checkContraseña.setBounds(519, 168, 171, 21);
		contentPane.add(checkContraseña);

		fieldNombre = new JTextField();
		fieldNombre.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldNombre.setForeground(Color.GRAY);
		fieldNombre.setText((String) null);
		fieldNombre.setEditable(false);
		fieldNombre.setColumns(10);
		fieldNombre.setBounds(249, 144, 262, 29);
		contentPane.add(fieldNombre);
		fieldNombre.setText(userlog.getFirstName());

		fieldApellido = new JTextField();
		fieldApellido.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldApellido.setForeground(Color.GRAY);
		fieldApellido.setText((String) null);
		fieldApellido.setEditable(false);
		fieldApellido.setColumns(10);
		fieldApellido.setBounds(249, 183, 262, 29);
		contentPane.add(fieldApellido);
		fieldApellido.setText(userlog.getLastName());

		fieldEmail = new JTextField();
		fieldEmail.setForeground(Color.GRAY);
		fieldEmail.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldEmail.setText((String) null);
		fieldEmail.setEditable(false);
		fieldEmail.setColumns(10);
		fieldEmail.setBounds(249, 222, 262, 29);
		contentPane.add(fieldEmail);
		fieldEmail.setText(userlog.getEmail());

		fieldCuentaBancaria = new JTextField();
		fieldCuentaBancaria.setForeground(Color.GRAY);
		fieldCuentaBancaria.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldCuentaBancaria.setText((String) null);
		fieldCuentaBancaria.setEditable(false);
		fieldCuentaBancaria.setColumns(10);
		fieldCuentaBancaria.setBounds(249, 261, 262, 29);
		contentPane.add(fieldCuentaBancaria);
		fieldCuentaBancaria.setText(userlog.getBankAccount());

		lblUsername = new JLabel("Nombre de usuario:");
		lblUsername.setBounds(10, 74, 133, 13);
		contentPane.add(lblUsername);

		lblContraseña = new JLabel("Contraseña:");
		lblContraseña.setBounds(10, 111, 133, 13);
		contentPane.add(lblContraseña);

		lblContraseña2 = new JLabel("Contraseña nueva:");
		lblContraseña2.setBounds(10, 150, 133, 13);
		lblContraseña2.setVisible(false);
		contentPane.add(lblContraseña2);

		lblContraseña3 = new JLabel("Repite contraseña nueva:");
		lblContraseña3.setBounds(10, 189, 183, 13);
		lblContraseña3.setVisible(false);

		contentPane.add(lblContraseña3);

		lblNombre = new JLabel("Nombre:");
		lblNombre.setBounds(10, 150, 133, 13);
		contentPane.add(lblNombre);

		lblApellido = new JLabel("Apellido:");
		lblApellido.setBounds(10, 189, 133, 13);
		contentPane.add(lblApellido);

		lblEmail = new JLabel("Email:");
		lblEmail.setBounds(10, 228, 133, 13);
		contentPane.add(lblEmail);

		lblCuentaBancaria = new JLabel("Cuenta bancaria:");
		lblCuentaBancaria.setBounds(10, 267, 133, 13);
		contentPane.add(lblCuentaBancaria);

		fieldSaldo = new JTextField();
		fieldSaldo.setForeground(Color.GRAY);
		fieldSaldo.setFont(new Font("Arial", Font.PLAIN, 16));
		fieldSaldo.setText((String) null);
		fieldSaldo.setEditable(false);
		fieldSaldo.setColumns(10);
		fieldSaldo.setBounds(249, 306, 262, 29);
		contentPane.add(fieldSaldo);
		fieldSaldo.setText(Float.toString(userlog.getBalance()) + " €");

		buttonModificar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ModificarDatosGUI.modificar"));
		buttonModificar.setBounds(563, 463, 127, 29);
		contentPane.add(buttonModificar);
		buttonModificar.setBackground(SystemColor.textHighlight);
		buttonModificar.setForeground(Color.WHITE);
		buttonModificar.setFont(new Font("Dialog", Font.BOLD, 14));

		buttonCancelar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("Close"));
		buttonCancelar.setBounds(16, 463, 127, 29);
		contentPane.add(buttonCancelar);
		buttonCancelar.setBackground(SystemColor.controlShadow);
		buttonCancelar.setForeground(Color.WHITE);
		buttonCancelar.setFont(new Font("Dialog", Font.BOLD, 14));

		buttonGuardar = new JButton(ResourceBundle.getBundle("Etiquetas").getString("ModificarDatosGUI.guardar"));
		buttonGuardar.setBounds(563, 463, 127, 29);
		contentPane.add(buttonGuardar);
		buttonGuardar.setForeground(Color.WHITE);
		buttonGuardar.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonGuardar.setBackground(SystemColor.textHighlight);

		buttonGuardar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				boolean resul = false;
				boolean resul2 = false;
				if (fieldContraseña2.getText().equals("")) {

					resul2 = facade.editarPerfilUsuarioSinPass(fieldUsername.getText(), fieldNombre.getText(),
							fieldApellido.getText(), fieldEmail.getText(), fieldCuentaBancaria.getText());

				} else {

					if (fieldContraseña2.getText().equals(fieldContraseña3.getText())) {

						if (RegisterGUI.validoContraseña(fieldContraseña2.getText())) {

							resul = facade.editarPerfilUsuario(fieldContraseña2.getText(), fieldUsername.getText(),
									fieldNombre.getText(), fieldApellido.getText(), fieldEmail.getText(),
									fieldCuentaBancaria.getText());

						} else {
							JOptionPane.showMessageDialog(getContentPane(), "Contraseña no válida!", "Error",
									JOptionPane.ERROR_MESSAGE);
						}

					} else {
						JOptionPane.showMessageDialog(getContentPane(), "Las contraseñas no coinciden!", "Error",
								JOptionPane.ERROR_MESSAGE);

					}

				}

				if (resul || resul2) {
					JOptionPane.showMessageDialog(getContentPane(),
							"Cambios guardados correctamente. \nSerá redirigido al menú principal...");
					RegularUser usuarioactualizado = facade.getRegularUserByUsername(fieldUsername.getText());
					Frame reg = new MainGUI(usuarioactualizado);
					reg.setAlwaysOnTop(true);
					reg.setVisible(true);
					dispose();

				}

			}
		});
		buttonGuardar.setVisible(false);
		buttonGuardar.setEnabled(false);

		buttonCancelar.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {
				Frame reg = new MainGUI(userlog);
				reg.setAlwaysOnTop(true);
				reg.setVisible(true);
				dispose();
				dispose();
			}
		});

		buttonModificar.addActionListener(new ActionListener() {

			@Override
			public void actionPerformed(ActionEvent e) {

				fieldUsername.setBorder(border);
				fieldContraseña.setBorder(border);
				fieldNombre.setBorder(border2);
				fieldApellido.setBorder(border2);
				fieldEmail.setBorder(border2);
				fieldCuentaBancaria.setBorder(border2);
				fieldSaldo.setBorder(border);
				checkContraseña.setVisible(true);

				fieldContraseña2.setVisible(true);
				fieldContraseña3.setVisible(true);

				contentPane.add(fieldContraseña2);
				fieldContraseña2.setColumns(10);
				fieldContraseña2.setEditable(true);

				contentPane.add(fieldContraseña3);
				fieldContraseña3.setColumns(10);
				fieldContraseña3.setEditable(true);

				fieldNombre.setBounds(249, 222, 262, 29);
				fieldApellido.setBounds(249, 261, 262, 29);
				fieldEmail.setBounds(249, 300, 262, 29);
				fieldCuentaBancaria.setBounds(249, 339, 262, 29);
				fieldSaldo.setBounds(249, 380, 262, 29);

				fieldNombre.setEditable(true);
				fieldApellido.setEditable(true);
				fieldEmail.setEditable(true);
				fieldCuentaBancaria.setEditable(true);

				lblContraseña2.setVisible(true);

				lblContraseña3.setVisible(true);

				lblNombre.setBounds(10, 228, 133, 13);
				lblApellido.setBounds(10, 267, 133, 13);
				lblEmail.setBounds(10, 306, 133, 13);
				lblCuentaBancaria.setBounds(10, 345, 133, 13);
				lblSaldo.setBounds(10, 389, 133, 13);

				buttonModificar.setEnabled(false);
				buttonModificar.setVisible(false);
				buttonGuardar.setVisible(true);
				buttonGuardar.setEnabled(true);
			}
		});

	}
}
