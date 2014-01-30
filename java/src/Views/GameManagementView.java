package Views;

import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.Timer;
import java.util.TimerTask;

import javax.swing.JButton;
import javax.swing.JLabel;

import Model.Game;

public class GameManagementView extends MainView  implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	JLabel timeLabel;
	JButton startButton;
	JButton restartButton;
	JButton stopButton;
	JButton resumeButton;
	
	Timer timer = new Timer();
	
	public GameManagementView()
	{
		super.setupView("Game Manager");
		
		timeLabel = new JLabel("");
		startButton = new JButton("Start Game");
		restartButton = new JButton("Restart Game");
		stopButton = new JButton("Stop Game");
		resumeButton = new JButton("Resume Game");
		
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
		
		setSize(150, 150);
		
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
			Game.getInstance().start();
		}
		else if(action.equals("resume"))
		{
			Game.getInstance().resume();
		}
		else if(action.equals("restart"))
		{
			Game.getInstance().restart();
		}
		else if(action.equals("stop"))
		{
			Game.getInstance().stop();
		}
		
	}
}
