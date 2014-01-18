package Controllers;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;

import Model.Story;
import Views.StoriesListView;
import WebService.WebServiceConnection;
import WebService.WebServiceConnection.WebServiceConnectionDelegate;
import WebService.WebServiceRequest;

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
        Iterator<?> keys = json.keys();
        ArrayList<Story> stories = new ArrayList<Story>();

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
        
        listView = new StoriesListView(stories,this);
        listView.setVisible(true);
	}
	
	public void onSelectStory(Story story)
	{
		new StoryGame(story);
		listView.close();
	}
}
