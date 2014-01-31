package Model;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Lib.XmlHelper;

public class Step
{
	public int id;
	public String description;
	public String question;
	public ArrayList<Choice> choices = new ArrayList<Choice>();
	
	public Step(JSONObject json)
	{		
		try
		{
			id = json.getInt("id");
			description = json.getString("description");
			question = json.getString("question");
			
			JSONObject jsonChoices = json.getJSONObject("choices");
			Iterator<?> keys = jsonChoices.keys();
			while( keys.hasNext() )
	        {
				String key = (String)keys.next();
				String desc = jsonChoices.getString(key);
        		Choice c = new Choice();
        		c.id = key;
        		c.description = desc;
				choices.add(c);
	        }
		}
		catch (JSONException e)
		{
			e.printStackTrace();
		}
	}
	
	public Step(Node stepNode)
	{		
		id = XmlHelper.getNodeValueAsInt((Element)stepNode, "id");
		description = XmlHelper.getNodeValue((Element)stepNode, "description");
		question = XmlHelper.getNodeValue((Element)stepNode, "question");
		NodeList choiceList = ((Element)stepNode).getElementsByTagName("choice");
		for(int i=0;i<choiceList.getLength();i++)
		{
			Node choiceNode = choiceList.item(i);
			Choice c = new Choice(choiceNode);
			choices.add(c);
		}
	}
}
