package Model;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Timer;
import java.util.TimerTask;

import Lib.FileManager;

import com.thoughtworks.xstream.XStream;

public class Game
{
	private static Game instance = null;
	public final static String kGameXMLFilename = "gameData.xml";
	
	XStream xstream = new XStream();	
	Timer timer = null;
	boolean isPaused;
	int seconds = 0;
	int lastKnownStepId = -1;
	int lastKnownStoryId = -1;
	
	protected Game()
	{
		
	}
	   
	public static Game getInstance()
	{
		if(instance == null)
	    {
			instance = new Game();
	    }
		return instance;
	}
	
	// game methods
	
	public void restart()
	{
		lastKnownStepId = -1;
		lastKnownStoryId = -1;
		seconds = 0;
		start();
	}
	
	public void start() //starts a new game
	{
		isPaused = false;
		if(timer == null)
			timer = new Timer();
		timer.scheduleAtFixedRate(new TimerTask()
		{
			  @Override
			  public void run()
			  {
				  if(isPaused == false)
					  seconds++;
			  }
		}, 1000, 1000);
		
		
	}
	
	public void pause()
	{
		isPaused = true;
	}
	
	public void resume()
	{
		isPaused = false;
	}
	
	public void stop()
	{
		isPaused = false;
		timer.cancel();
		timer = null;
	}
	
	public void save(int storyId, int stepId)
	{
		lastKnownStepId = stepId;
		lastKnownStoryId = storyId;
		File file = new File(kGameXMLFilename);
		String xml = xstream.toXML(this);
		
		try
		{
			if(file.exists() == false)
				file.createNewFile();
			FileManager.setContents(file, xml);
		}
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
	}
	
	public boolean hasLastGame()
	{
		File f = new File(kGameXMLFilename);
		return f.exists();
	}
	
	public void loadLastGame()
	{
		if( hasLastGame() )
		{
			File f = new File(kGameXMLFilename);
			String xml = FileManager.getContents(f);
			Game lastGame = (Game)xstream.fromXML(xml);
			
			seconds = lastGame.seconds;
			lastKnownStepId = lastGame.lastKnownStepId;
			lastKnownStoryId = lastGame.lastKnownStoryId;
			start();
		}
	}
	
	public String timeElapsed()
	{
		return String.valueOf(seconds);
	}
}
