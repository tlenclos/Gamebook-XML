package Model;

import java.util.ArrayList;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
	
	public Story(Node xmlNode)
	{
		Element e = (Element)xmlNode;
		title = e.getAttribute("title");
		language = e.getAttribute("lang");
		id = e.getAttribute("id");
		
		NodeList stepNodes = xmlNode.getFirstChild().getChildNodes();
		for(int i=0;i<stepNodes.getLength();i++)
		{
			Node stepNode = stepNodes.item(i);
			Step step = new Step(stepNode);
			steps.add(step);			
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
