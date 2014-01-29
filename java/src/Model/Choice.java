package Model;

import org.json.JSONException;
import org.json.JSONObject;
import org.w3c.dom.Element;
import org.w3c.dom.Node;

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
	
	public Choice(Node choiceNode)
	{
		Element e = (Element)choiceNode;
		id = e.getAttribute("gotostep");
		description = choiceNode.getFirstChild().getNodeValue();
	}
	
	public Choice()
	{
		//
	}
}
