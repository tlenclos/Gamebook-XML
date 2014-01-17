package Game;

import View.AuthenticationView;

public class Authentication
{
	public Authentication()
	{
		AuthenticationView a = new AuthenticationView();
		a.setVisible(true);
	}
	
	public static void main(String[] args)
	{
		new Authentication();
	}
}
