package Model;

import java.io.File;
import java.io.FileWriter;
import java.util.ArrayList;
import java.util.List;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.NodeList;

import Lib.XmlHelper;

public class History {
	public String UserName;
	public List<GameHistory> Games;
	
	public History()
	{
		Games = new ArrayList<GameHistory>();
	}
	
	public History(Element historyElement)
	{
		this();
		UserName = historyElement.getAttribute("username");

		NodeList gameList = historyElement.getElementsByTagName("gameHistory");
		for(int i = 0; i < gameList.getLength(); i++)
		{
			Element actGameElement = (Element)gameList.item(i);
			Games.add(new GameHistory(actGameElement));
		}
	}
	
	public boolean AddGame(GameHistory game)
	{
		if (FindGameByStoryId(game.StoryId) != null)
		{
			//already had saved that game
			return false;
		}
		Games.add(game);
		return true;
	}
	
	
	public void RemoveGame(String storyId)
	{
		GameHistory game = FindGameByStoryId(storyId);
		if (game != null)
			RemoveGame(game);
	}
	public void RemoveGame(GameHistory game)
	{
		Games.remove(game);
	}
	
	public GameHistory FindGameByStoryId(String storyId)
	{
		for(GameHistory actGame : Games)
		{
			if (actGame.StoryId.equalsIgnoreCase(storyId))
				return actGame;
		}
		return null;
	}
	
	public String toXML()
	{
		StringBuilder builder = new StringBuilder();
		
		builder.append("<history username=\"" + UserName + "\">");
		
		for(GameHistory actGame : Games)
			builder.append(actGame.toXML());
		
		builder.append("</history>");
		
		return builder.toString();
	}
	
	public static History loadHistoryForUser(String username)
	{
		Document xml = XmlHelper.loadFromFile("history." + username + ".xml");
		if (xml == null)
			return null;
		
		History history = new History(xml.getDocumentElement());
		
		if (!history.UserName.equalsIgnoreCase(username))
			return null;
		
		return history;
	}
	
	public static boolean saveHistory(History history)
	{
		try
		{
			File file = new File("history." + history.UserName + ".xml");
			if (file.exists())
				file.delete();
			
			file.createNewFile();
			
			FileWriter writer = new FileWriter(file);
			writer.write(history.toXML());
			writer.flush();
			writer.close();
			
			return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
