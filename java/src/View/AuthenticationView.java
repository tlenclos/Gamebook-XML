package View;

import javax.swing.JButton;
import javax.swing.JTextField;

public class AuthenticationView extends MainView
{	
	private static final long serialVersionUID = 1L;
	
	JTextField usernameTextField;
	JTextField passwordTextField;
	JButton logInButton;
	
	public AuthenticationView()
	{
		super.setupView("Authentication");
		
		
	}
}
