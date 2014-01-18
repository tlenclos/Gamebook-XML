package Lib;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.HttpClientBuilder;

public class HTTPRequest
{
	private final static String USER_AGENT = "Mozilla/5.0";
	private static HttpClient client = null;
	
	public static String get(String url) throws Exception
	{
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		con.setRequestMethod("GET");
 
		BufferedReader in = new BufferedReader(new InputStreamReader(con.getInputStream()));
		String inputLine = null;
		StringBuffer response = new StringBuffer();
		
		while ((inputLine = in.readLine()) != null)
		{
			response.append(inputLine);
		}
		
		in.close();
 
		return response.toString();
	}
	
	public static String getWithDefaultApacheClient(String url) throws ClientProtocolException, IOException
	{
		if(client == null)
			client = HttpClientBuilder.create().build();
		HttpGet request = new HttpGet(url);
	 
		// add request header
		request.addHeader("User-Agent", USER_AGENT);
		HttpResponse response = client.execute(request);
	 
		BufferedReader rd = new BufferedReader(new InputStreamReader(response.getEntity().getContent()));
	 
		StringBuffer result = new StringBuffer();
		String line = "";
		while ((line = rd.readLine()) != null)
		{
			result.append(line);
		}
		
		rd.close();
		
		return result.toString();
	}
}
