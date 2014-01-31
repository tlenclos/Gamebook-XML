package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


import Controllers.StoriesList;
import Controllers.StoryGame;
import Model.Story;

public class GameManagementView extends MainView  implements ActionListener
{
	private static GameManagementView _Instance = null;
	public static void show(Story story) 
	{
		if (_Instance == null)
		{
			_Instance= new GameManagementView(story);
		}
		else
		{
			_Instance.story = story;
			_Instance.storyGame = null;
		}
	
		_Instance.start();
	}
	public static void closeInstance()
	{
		if (_Instance == null)
			return;
		
		_Instance.stop();
	}
	
	
	private static final long serialVersionUID = 1L;
	
	JButton startButton;
	JButton restartButton;
	JButton stopButton;
	JButton resumeButton;
	
	Story story;
	StoryGame storyGame;
	
	private GameManagementView(Story story)
	{
		this.story = story;
		
		super.setupView("Game Manager");
		

		startButton = new JButton("Start Game");
		restartButton = new JButton("Restart Game");
		stopButton = new JButton("Stop Game");
		resumeButton = new JButton("Resume Game");
		
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		super.putGBC(0, 0, 1, 1);
		add(startButton,gbc);
		super.putGBC(0, 1, 1, 1);
		add(stopButton,gbc);
		super.putGBC(0, 2, 1, 1);
		add(restartButton,gbc);
		super.putGBC(0, 3, 1, 1);
		add(resumeButton,gbc);
		
		setSize(150, 250);
		setLocation(25,25);
		
		startButton.addActionListener(this);
		startButton.setActionCommand("start");
		restartButton.addActionListener(this);
		restartButton.setActionCommand("restart");
		stopButton.addActionListener(this);
		stopButton.setActionCommand("stop");
		resumeButton.addActionListener(this);
		resumeButton.setActionCommand("resume");
		
		setVisible(true);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String action = e.getActionCommand();
		
		if(action.equals("start"))
		{
			start();
		}
		else if(action.equals("resume"))
		{
			resume();
		}
		else if(action.equals("restart"))
		{
			restart();
		}
		else if(action.equals("stop"))
		{
			stop();
		}
		else if(action.equals("pause"))
		{
			pause();
		}

	}
	
	public void start()
	{
		if(storyGame == null)
			storyGame = new StoryGame(story);
		
		
		startButton.setVisible(false);
		stopButton.setVisible(true);
		resumeButton.setVisible(false);
		restartButton.setVisible(true);
	}
	
	public void restart()
	{
		storyGame.loadFirstStep();
		startButton.setVisible(false);
		stopButton.setVisible(true);
		resumeButton.setVisible(false);
		restartButton.setVisible(true);
	}
	
	public void stop()
	{
		if(storyGame != null)
		{
			storyGame.close();
			storyGame = null;
			story = null;
		}
		
		this.close();
		_Instance = null;
		
		new StoriesList();
	}
	
	public void resume()
	{
		storyGame.setVisible(true);
		startButton.setVisible(false);
		stopButton.setVisible(true);
		resumeButton.setVisible(false);
		restartButton.setVisible(true);
	}
	

	public void pause()
	{
		storyGame.setVisible(false);
		startButton.setVisible(false);
		stopButton.setVisible(false);
		resumeButton.setVisible(true);
		restartButton.setVisible(false);
	}
}
