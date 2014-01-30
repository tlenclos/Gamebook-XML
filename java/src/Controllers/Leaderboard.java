package Controllers;

import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;

import User.User;
import Views.LeaderboardView;
import WebService.WebServiceConnection;
import WebService.WebServiceConnection.WebServiceConnectionDelegate;
import WebService.WebServiceRequest;
import XMLParser.UsersXMLParser;

public class Leaderboard implements WebServiceConnectionDelegate
{
	WebServiceConnection connection;
	LeaderboardView listView;
	
	public static Leaderboard instance = null;
	
	public Leaderboard()
	{
		reload();
		
		instance = this;
	}
	
	public void reload()
	{
		WebServiceRequest request = WebServiceRequest.UsersRequestXML();
		connection = new WebServiceConnection(request,this);
	}
	

	@Override
	public void webServiceDidRetrieveJSON(JSONObject json) {
		try
		{
			boolean success = json.getBoolean("success");
			String filename = json.getString("filename");
			if(success == true && filename != null)
			{
				ArrayList<User> users = UsersXMLParser.parseWithFilename(filename);
				if( listView == null)
				{
					listView = new LeaderboardView(users);
				}
				else
				{
					listView.users = users;
					listView.reload();
				}
				
				listView.setVisible(true);
			}			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
		
	}
}
