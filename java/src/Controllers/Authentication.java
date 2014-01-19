package Controllers;

import org.json.JSONException;
import org.json.JSONObject;

import User.User;
import Views.AuthenticationView;
import WebService.WebServiceConnection;
import WebService.WebServiceRequest;
import WebService.WebServiceConnection.WebServiceConnectionDelegate;

public class Authentication implements WebServiceConnectionDelegate
{
	AuthenticationView authenticationView = null;
	WebServiceConnection webServiceConnection = null;
	User user = null;
	
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
		if( webServiceConnection == null )
		{
			user = new User(username,password);
			WebServiceRequest request =  WebServiceRequest.AuthenticationRequestForUser(user);
			webServiceConnection = new WebServiceConnection(request, this);
		}
	}

	@Override
	public void webServiceDidRetrieveJSON(JSONObject json)
	{
		boolean success = false;
		String message = null;
		try {
			if( json != null )
			{
				success = json.getBoolean("success");
				message = json.getString("message");
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
		if( success )
		{
			user.setAsDefault();
			new StoriesList();
			authenticationView.close();
		}
		else
		{
			if(message == null)
				message = "Unknown Error";
			
			authenticationView.showMessageDialog(message);
			authenticationView.stopProgress();
		}
		
		webServiceConnection = null;
	}
}
