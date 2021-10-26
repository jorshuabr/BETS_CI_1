package gui;

import java.awt.Font;
import java.awt.Frame;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;
import javax.swing.SwingConstants;
import javax.swing.border.EmptyBorder;

import businessLogic.BLFacade;
import domain.AdminUser;
import domain.RegularUser;
import java.awt.Color;
import java.awt.SystemColor;

public class LoginGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textUser;
	private JPasswordField textPass;
	private static BLFacade facade;
	private static RegularUser userlog;

	private JLabel lblUser = new JLabel("Usuario:");
	private JLabel lblPass = new JLabel("Contraseña:");

	private JButton buttonLogin = new JButton("Login");
	private JButton buttonRegister = new JButton("Registrarse");
	private JButton btnInvitado;

	public LoginGUI() {
		super();

		addWindowListener(new WindowAdapter() {
			@Override
			public void windowClosing(WindowEvent e) {
				try {
					// if (ConfigXML.getInstance().isBusinessLogicLocal()) facade.close();
				} catch (Exception e1) {
					System.out.println(
							"Error: " + e1.toString() + " , probably problems with Business Logic or Database");
				}
				System.exit(1);
			}
		});

		initialize();
		// this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
	}

	public void initialize() {
		setTitle("Login");

		setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
		setBounds(100, 100, 460, 300);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		lblUser.setHorizontalAlignment(SwingConstants.RIGHT);
		lblUser.setBounds(65, 66, 100, 14);
		contentPane.add(lblUser);

		lblPass.setHorizontalAlignment(SwingConstants.RIGHT);
		lblPass.setBounds(89, 106, 76, 14);
		contentPane.add(lblPass);

		textUser = new JTextField("");
		textUser.setForeground(Color.GRAY);
		textUser.setFont(new Font("Arial", Font.PLAIN, 16));
		textUser.setBounds(175, 57, 135, 30);
		contentPane.add(textUser);
		textUser.setColumns(10);

		textPass = new JPasswordField("");
		textPass.setForeground(Color.GRAY);
		textPass.setFont(new Font("Arial", Font.PLAIN, 16));
		textPass.setBounds(175, 97, 135, 30);
		contentPane.add(textPass);
		textPass.setColumns(10);
		buttonLogin.setForeground(Color.WHITE);
		buttonLogin.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonLogin.setBackground(SystemColor.textHighlight);

		buttonLogin.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent arg0) {
				String pass = String.valueOf(textPass.getPassword());
				String userName = textUser.getText();
				if (pass.equals("") || userName.equals("")) {
					JOptionPane.showMessageDialog(contentPane, "Usuario o contraseña no introducida", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {
					boolean b = facade.doLogin(userName, pass);
					if (b) {

						// Check user permissions
						if (facade.isAdmin(userName, pass)) {

							AdminUser au = facade.getAdminUserByUsername(userName);
							JFrame a = new MainAdminGUI(au);
							// a.setLocationRelativeTo(null);
							a.setVisible(true);
							textUser.setText("");
							textPass.setText("");

						} else {

							RegularUser ru = facade.getRegularUserByUsername(userName);
							JFrame a = new MainGUI(ru);
							// a.setLocationRelativeTo(null);
							a.setVisible(true);
							textUser.setText("");
							textPass.setText("");
						}
					} else {

						JOptionPane.showMessageDialog(null, "Usuario y contraseña no coinciden", "LOGIN INFO",
								JOptionPane.ERROR_MESSAGE, null);
					}
				}
			}
		});

		buttonLogin.setBounds(97, 155, 89, 27);
		contentPane.add(buttonLogin);
		buttonRegister.setFont(new Font("Dialog", Font.BOLD, 14));
		buttonRegister.setForeground(Color.WHITE);
		buttonRegister.setBackground(SystemColor.textHighlight);

		buttonRegister.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(ActionEvent e) {

				Frame reg = new RegisterGUI();
				reg.setAlwaysOnTop(true);
				reg.setVisible(true);
				// dispose();
			}
		});

		buttonRegister.setBounds(220, 155, 135, 27);
		contentPane.add(buttonRegister);

		btnInvitado = new JButton("Invitado");
		btnInvitado.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {
				Frame reg = new FindQuestionsInvitadoGUI();
				reg.setAlwaysOnTop(true);
				reg.setVisible(true);

			}
		});
		btnInvitado.setForeground(Color.WHITE);
		btnInvitado.setFont(new Font("Dialog", Font.BOLD, 14));
		btnInvitado.setBackground(SystemColor.textHighlight);
		btnInvitado.setBounds(145, 204, 120, 27);
		contentPane.add(btnInvitado);

	}

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public void setRegularUserLog(RegularUser user) {
		LoginGUI.userlog = user;
	}

	public static RegularUser getUserLog() {
		return userlog;
	}
}
