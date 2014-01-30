package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;

import Controllers.StoryGame;
import Model.Game;
import Model.Story;

public class GameManagementView extends MainView  implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	JLabel timeLabel;
	JButton startButton;
	JButton restartButton;
	JButton stopButton;
	JButton resumeButton;
	JButton pauseButton;
	
	Story story;
	StoryGame storyGame;
	
	Timer timer = new Timer();
	
	public GameManagementView(Story story)
	{
		this.story = story;
		
		super.setupView("Game Manager");
		
		timeLabel = new JLabel("");
		startButton = new JButton("Start Game");
		restartButton = new JButton("Restart Game");
		stopButton = new JButton("Stop Game");
		resumeButton = new JButton("Resume Game");
		pauseButton = new JButton("Pause");
		
		super.setDefaultCloseOperation(DO_NOTHING_ON_CLOSE);
		
		timer.scheduleAtFixedRate(new TimerTask()
		{
			  @Override
			  public void run()
			  {
				  timeLabel.setText(Game.getInstance().timeElapsed());
			  }
		}, 1000, 1000);
		
		super.putGBC(0, 0, 1, 1);
		add(timeLabel,gbc);
		super.putGBC(0, 1, 1, 1);
		add(startButton,gbc);
		super.putGBC(0, 2, 1, 1);
		add(stopButton,gbc);
		super.putGBC(0, 3, 1, 1);
		add(restartButton,gbc);
		super.putGBC(0, 4, 1, 1);
		add(resumeButton,gbc);
		super.putGBC(0, 5, 1, 1);
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
		else if(action.equals("save"))
		{
			save(-1,-1);
		}
	}
	
	public void start()
	{
		if(storyGame == null)
			storyGame = new StoryGame(story);
		
		Game.getInstance().start();
		startButton.setVisible(false);
		stopButton.setVisible(true);
		resumeButton.setVisible(false);
		pauseButton.setVisible(true);
		restartButton.setVisible(true);
	}
	
	public void restart()
	{
		Game.getInstance().restart();
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
		
		Game.getInstance().stop();
		startButton.setVisible(true);
		stopButton.setVisible(false);
		resumeButton.setVisible(false);
		pauseButton.setVisible(false);
		restartButton.setVisible(false);
	}
	
	public void resume()
	{
		storyGame.setVisible(true);
		Game.getInstance().resume();
		startButton.setVisible(false);
		stopButton.setVisible(true);
		resumeButton.setVisible(false);
		pauseButton.setVisible(true);
		restartButton.setVisible(true);
	}
	
	public void save(int storyId, int stepId)
	{
		Game.getInstance().save(storyId,stepId);
	}
	
	public void pause()
	{
		storyGame.setVisible(false);
		Game.getInstance().pause();
		startButton.setVisible(false);
		stopButton.setVisible(false);
		resumeButton.setVisible(true);
		pauseButton.setVisible(false);
		restartButton.setVisible(false);
	}
}
