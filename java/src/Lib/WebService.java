package Lib;

import org.json.JSONObject;

public class WebService
{
	public static JSONObject getJSONObjectForUrl(String url)
	{
		try
		{
			String s = HTTPRequest.getWithDefaultApacheClient(url);
			JSONObject json = new JSONObject(s);			
			return json;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		
		return null;
	}
	
	public static String getStringForUrl(String url)
	{
		try
		{
			String s = HTTPRequest.getWithDefaultApacheClient(url);
			return s;
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}		
		
		return null;
	}
}
