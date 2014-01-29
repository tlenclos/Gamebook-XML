package Model;

import java.util.ArrayList;
import java.util.Iterator;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

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
		NodeList stepChildren = stepNode.getChildNodes();
		for(int i=0;i<stepChildren.getLength();i++)
		{
			Node n = stepChildren.item(i);
			if(n.getNodeName().equals("id") && n.getFirstChild() != null)
			{
				id = Integer.parseInt(n.getFirstChild().getNodeValue());
			}
			else if(n.getNodeName().equals("description"))
			{
				description = n.getFirstChild().getNodeValue();
			}
			else if(n.getNodeName().equals("question"))
			{
				question = n.getFirstChild().getNodeValue();
			}
			else if(n.getNodeName().equals("actions"))
			{
				NodeList choiceNodes = n.getChildNodes();
				for(int j=0;j<choiceNodes.getLength();j++)
				{
					Node choiceNode = choiceNodes.item(j);
					Choice c = new Choice(choiceNode);
					choices.add(c);
				}
			}			
		}
	}
}
