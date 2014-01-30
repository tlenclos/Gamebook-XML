package Model;

import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Element;
import org.w3c.dom.NodeList;


public class GameHistory {
	
	public int StoryId;
	public List<Integer> Steps;
	
	public GameHistory()
	{
		Steps = new ArrayList<Integer>();
	}
	
	public GameHistory(Element gameElement)
	{
		this();
		StoryId = Integer.parseInt(gameElement.getAttribute("storyId"));

		NodeList stepList = gameElement.getElementsByTagName("step");
		for(int i = 0; i < stepList.getLength(); i++)
		{
			Element actStepElement = (Element)stepList.item(i);
			Steps.add(Integer.parseInt(actStepElement.getAttribute("id")));
		}
	}
	
	public void AddStep(int stepId)
	{
		Steps.add(stepId);
	}
	
	public String toXML()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("<gameHistory storyId=\"" + StoryId + "\">");
		
		for(Integer actStep : Steps)
			builder.append("<step id=\"" + actStep + "\"/>");
		
		builder.append("</gameHistory>");
		
		return builder.toString();
	}
}
