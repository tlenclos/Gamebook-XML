package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JButton;


import Controllers.StoryGame;
import Model.Story;

public class GameManagementView extends MainView  implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	JButton startButton;
	JButton restartButton;
	JButton stopButton;
	JButton resumeButton;
	JButton pauseButton;
	
	Story story;
	StoryGame storyGame;
	
	boolean isPaused = false;
	
	public GameManagementView(Story story)
	{
		this.story = story;
		
		super.setupView("Game Manager");
		

		startButton = new JButton("Start Game");
		restartButton = new JButton("Restart Game");
		stopButton = new JButton("Stop Game");
		resumeButton = new JButton("Resume Game");
		pauseButton = new JButton("Pause");
		
		super.setDefaultCloseOperation(EXIT_ON_CLOSE);

		
		super.putGBC(0, 0, 1, 1);
		add(startButton,gbc);
		super.putGBC(0, 1, 1, 1);
		add(stopButton,gbc);
		super.putGBC(0, 2, 1, 1);
		add(restartButton,gbc);
		super.putGBC(0, 3, 1, 1);
		add(resumeButton,gbc);
		super.putGBC(0, 4, 1, 1);
		add(pauseButton,gbc);
		
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
		pauseButton.addActionListener(this);
		pauseButton.setActionCommand("pause");
		
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
		
		isPaused = false;
		
		startButton.setVisible(false);
		stopButton.setVisible(true);
		resumeButton.setVisible(false);
		pauseButton.setVisible(true);
		restartButton.setVisible(true);
	}
	
	public void restart()
	{
		storyGame.loadFirstStep();
		isPaused = false;
		startButton.setVisible(false);
		stopButton.setVisible(true);
		resumeButton.setVisible(false);
		pauseButton.setVisible(true);
		restartButton.setVisible(true);
	}
	
	public void stop()
	{
		if(storyGame != null)
		{
			storyGame.close();
			storyGame = null;
		}
		
		startButton.setVisible(true);
		stopButton.setVisible(false);
		resumeButton.setVisible(false);
		pauseButton.setVisible(false);
		restartButton.setVisible(false);
	}
	
	public void resume()
	{
		isPaused = false;
		storyGame.setVisible(true);
		startButton.setVisible(false);
		stopButton.setVisible(true);
		resumeButton.setVisible(false);
		pauseButton.setVisible(true);
		restartButton.setVisible(true);
	}
	

	public void pause()
	{
		isPaused = true;
		storyGame.setVisible(false);
		startButton.setVisible(false);
		stopButton.setVisible(false);
		resumeButton.setVisible(true);
		pauseButton.setVisible(false);
		restartButton.setVisible(false);
	}
}
