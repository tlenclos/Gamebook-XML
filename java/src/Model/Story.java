package Model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class Story
{
	public String id;
	public String title;
	public String language;
	public ArrayList<Step> steps = new ArrayList<Step>();
	
	public Story(JSONObject json)
	{
		try
		{
			id = json.getString("id");
			title = json.getString("title");
			language = json.getString("lang");
			id = json.getString("id");
			
			JSONArray jsonSteps = json.getJSONArray("steps");
			
			for(int i=0; i<jsonSteps.length(); i++)
			{
				JSONObject jsonStep = jsonSteps.getJSONObject(i);
				steps.add(new Step(jsonStep));
			}
			
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	public Step getStepWithId(int id)
	{
		for (Step step : steps)
		{
			if(step.id == id)
			{
				return step;
			}
		}
		
		return null;
	}
}
