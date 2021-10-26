package gui;

import java.awt.BorderLayout;
import java.awt.EventQueue;
import java.awt.Frame;

import javax.swing.JFrame;
import javax.swing.JPanel;
import javax.swing.border.EmptyBorder;
import com.toedter.calendar.JCalendar;

import businessLogic.BLFacade;
import configuration.UtilDate;
import domain.AdminUser;
import domain.Event;

import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JTextField;
import javax.swing.JButton;
import java.awt.event.ActionListener;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;
import java.util.ResourceBundle;
import java.awt.event.ActionEvent;
import java.beans.PropertyChangeListener;
import java.text.DateFormat;
import java.beans.PropertyChangeEvent;
import java.awt.Font;
import java.awt.Color;
import java.awt.SystemColor;

public class CreateEventGUI extends JFrame {

	private JPanel contentPane;
	private JTextField textEvento;
	private static BLFacade facade = LoginGUI.getBusinessLogic();
	private Calendar calendarAct = null;
	private Calendar calendarAnt = null;

	private AdminUser userlog = null;
	private JButton btnAtras;
	private JButton btnCrearEvento = new JButton("Crear evento");

	public static void setBusinessLogic(BLFacade pfacade) {
		facade = pfacade;
	}

	public static BLFacade getBusinessLogic() {
		return facade;
	}

	public CreateEventGUI(AdminUser au) {

		userlog = au;

		setTitle("Crear evento");
		setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
		setBounds(100, 100, 585, 407);
		contentPane = new JPanel();
		contentPane.setBackground(Color.WHITE);
		contentPane.setBorder(new EmptyBorder(5, 5, 5, 5));
		setContentPane(contentPane);
		contentPane.setLayout(null);

		JCalendar jCalendar1 = new JCalendar();
		jCalendar1.getDayChooser().getDayPanel().setBackground(Color.WHITE);
		jCalendar1.addPropertyChangeListener(new PropertyChangeListener() {
			public void propertyChange(PropertyChangeEvent evt) {
				if (evt.getPropertyName().equals("locale")) {
					jCalendar1.setLocale((Locale) evt.getNewValue());
				} else if (evt.getPropertyName().equals("calendar")) {
					calendarAnt = (Calendar) evt.getOldValue();
					calendarAct = (Calendar) evt.getNewValue();

					int monthAnt = calendarAnt.get(Calendar.MONTH);
					int monthAct = calendarAct.get(Calendar.MONTH);
					if (monthAct != monthAnt) {
						if (monthAct == monthAnt + 2) {
							// Si en JCalendar estÃ¡ 30 de enero y se avanza al mes siguiente, devolverÃ­a 2
							// de marzo (se toma como equivalente a 30 de febrero)
							// Con este cÃ³digo se dejarÃ¡ como 1 de febrero en el JCalendar
							calendarAct.set(Calendar.MONTH, monthAnt + 1);
							calendarAct.set(Calendar.DAY_OF_MONTH, 1);
						}

						jCalendar1.setCalendar(calendarAct);

					}

				}

			}
		});
		jCalendar1.setBounds(163, 30, 266, 175);
		contentPane.add(jCalendar1);

		JLabel lblNewLabel = new JLabel("Introduzca fecha:");
		lblNewLabel.setBounds(41, 115, 103, 14);
		contentPane.add(lblNewLabel);

		JLabel lblNewLabel_1 = new JLabel("Introduzca partido:");
		lblNewLabel_1.setBounds(41, 242, 113, 14);
		contentPane.add(lblNewLabel_1);

		textEvento = new JTextField();
		textEvento.setBounds(163, 237, 266, 23);
		textEvento.setForeground(Color.GRAY);
		textEvento.setFont(new Font("Arial", Font.PLAIN, 16));
		contentPane.add(textEvento);
		textEvento.setColumns(10);
		btnCrearEvento.setBackground(SystemColor.textHighlight);
		btnCrearEvento.setForeground(Color.WHITE);
		btnCrearEvento.setFont(new Font("Dialog", Font.BOLD, 14));

		btnCrearEvento.setText(ResourceBundle.getBundle("Etiquetas").getString("CreateEvent"));
		btnCrearEvento.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent e) {
				Date date = UtilDate.trim(new Date(jCalendar1.getCalendar().getTime().getTime()));

				if (textEvento.getText().equals("")) {
					JOptionPane.showMessageDialog(getContentPane(), "El evento no puede estar vacío", "Error",
							JOptionPane.ERROR_MESSAGE);
				} else {

					Event ev = new Event(facade.getMaxIdInDB() + 1, textEvento.getText(), date);

					boolean exist = facade.existEvent(ev);

					if (exist == false) {

						boolean ins = facade.insertEvent(ev);

						if (ins) {
							JOptionPane.showMessageDialog(contentPane, "Evento añadido correctamente");

						} else {
							JOptionPane.showMessageDialog(contentPane, "No se ha podido añadir el evento");

						}

						Frame gui = new MainAdminGUI(userlog);
						gui.setAlwaysOnTop(true);
						gui.setVisible(true);
						close();
					} else {
						JOptionPane.showMessageDialog(contentPane, "Evento ya existente");
					}
				}

			}
		});
		btnCrearEvento.setBounds(220, 310, 145, 23);
		contentPane.add(btnCrearEvento);

		btnAtras = new JButton();
		btnAtras.setBackground(SystemColor.controlShadow);
		btnAtras.setForeground(Color.WHITE);
		btnAtras.setFont(new Font("Dialog", Font.BOLD, 14));
		btnAtras.setText(ResourceBundle.getBundle("Etiquetas").getString("Close"));

		btnAtras.addActionListener(new ActionListener() {
			public void actionPerformed(ActionEvent arg0) {

				Frame fr = new MainAdminGUI(userlog);
				fr.setAlwaysOnTop(true);
				fr.setVisible(true);
				dispose();

			}
		});
		btnAtras.setBounds(12, 310, 113, 23);
		contentPane.add(btnAtras);
	}

	public void close() {
		this.setVisible(false);
	}
}
