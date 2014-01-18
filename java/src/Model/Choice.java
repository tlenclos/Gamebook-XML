package Model;

import org.json.JSONException;
import org.json.JSONObject;

public class Choice
{
	public String id;
	public String description;
	
	public Choice(JSONObject json)
	{
		try
		{
			id = json.getString("id");
			description = json.getString("description");
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	public Choice()
	{
		//
	}
}
