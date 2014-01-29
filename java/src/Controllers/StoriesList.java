package Controllers;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import Model.Story;
import Views.StoriesListView;
import WebService.WebServiceConnection;
import WebService.WebServiceConnection.WebServiceConnectionDelegate;
import WebService.WebServiceRequest;
import XMLParser.StoriesXMLParser;

public class StoriesList implements WebServiceConnectionDelegate
{
	WebServiceConnection connection;
	StoriesListView listView;
	
	public StoriesList()
	{
		WebServiceRequest request = WebServiceRequest.StoriesRequest();
		connection = new WebServiceConnection(request,this);
	}

	@Override
	public void webServiceDidRetrieveJSON(JSONObject json)
	{
		//get xml files
		ArrayList<String> files = new ArrayList<String>();
		JSONArray jsonArray = null;
		try
		{
			jsonArray = json.getJSONArray("stories");			
			if (jsonArray != null)
			{
				for (int i=0;i<jsonArray.length();i++)
					files.add(jsonArray.get(i).toString());
			}
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		} 		
		
		//xml parser		
		ArrayList<Story> stories = StoriesXMLParser.parseWithFiles(files);
		
		
		// json parser (deprecated) : 
		/*
        ArrayList<Story> stories = new ArrayList<Story>();
		Iterator<?> keys = json.keys();
        
        while( keys.hasNext() )
        {
        	try
        	{
    			String key = (String)keys.next();
            	if( json.get(key) instanceof JSONObject )
				{
					JSONObject storyJson = json.getJSONObject(key);
					stories.add(new Story(storyJson));
				}
			}
        	catch (JSONException e)
        	{
				e.printStackTrace();
			}
        }
        
        */
        
        listView = new StoriesListView(stories,this);
        listView.setVisible(true);
	}
	
	public void onSelectStory(Story story)
	{
		new StoryGame(story);
		listView.close();
	}
}
