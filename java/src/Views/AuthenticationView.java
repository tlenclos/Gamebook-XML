package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;
import javax.swing.JLabel;
import javax.swing.JPasswordField;
import javax.swing.JTextField;

import Controllers.Authentication;

public class AuthenticationView extends MainView
{	
	private static final long serialVersionUID = 1L;
	
	JTextField usernameTextField;
	JPasswordField passwordTextField;
	JButton logInButton;
	
	public AuthenticationView(final Authentication auth)
	{
		usernameTextField = new JTextField(25);
		passwordTextField = new JPasswordField(25);
		logInButton = new JButton("Connect");
		logInButton.addActionListener(new ActionListener() {
			
			@Override
			public void actionPerformed(ActionEvent arg0) {
				auth.authenticateWithUsernamePassword(usernameTextField.getText(),String.valueOf(passwordTextField.getPassword()));
			}
		});
		
		JLabel usernameLabel = new JLabel("Username/Email :");
		JLabel passwordLabel = new JLabel("Password :");
		
		super.setupView("Authentication");
		
		super.putGBC(0, 7, 5, 1);
		add(usernameLabel,gbc);
		super.putGBC(0, 8, 5, 1);
		add(usernameTextField,gbc);
		
		super.putGBC(0, 10, 5, 1);
		add(passwordLabel,gbc);
		super.putGBC(0, 11, 5, 1);
		add(passwordTextField,gbc);
		
		super.putGBC(0, 12, 5, 1);
		add(logInButton,gbc);
	}
}
