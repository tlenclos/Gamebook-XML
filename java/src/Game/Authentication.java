package Game;

import View.AuthenticationView;

public class Authentication
{
	AuthenticationView authenticationView = null;
	public Authentication()
	{
		authenticationView = new AuthenticationView(this);
		authenticationView.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Authentication();
	}
	
	public void authenticateWithUsernamePassword(String username, String password)
	{
		authenticationView.close();
	}
}
