package Model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class GameHistory {
	
	public String StoryId;
	public List<String> Steps;
	
	public GameHistory()
	{
		Steps = new ArrayList<String>();
	}
	
	public GameHistory(Element gameElement)
	{
		this();
		StoryId = gameElement.getAttribute("storyId");

		NodeList stepList = gameElement.getElementsByTagName("step");
		for(int i = 0; i < stepList.getLength(); i++)
		{
			Element actStepElement = (Element)stepList.item(i);
			Steps.add(actStepElement.getAttribute("id"));
		}
	}
	
	public void AddStep(String stepId)
	{
		Steps.add(stepId);
	}
	
	public String toXML()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("<gameHistory storyId=\"" + StoryId + "\">");
		
		for(String actStep : Steps)
			builder.append("<step id=\"" + actStep + "\"/>");
		
		builder.append("</gameHistory>");
		
		return builder.toString();
	}
}
