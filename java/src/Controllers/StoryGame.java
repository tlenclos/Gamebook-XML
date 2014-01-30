package Controllers;

import Model.GameHistory;
import Model.History;
import Model.Step;
import Model.Story;
import User.User;
import Views.StoryGameView;

public class StoryGame
{
	public Story story;
	StoryGameView storyGameView;
	GameHistory gameHistory;
	
	public StoryGame(Story story)
	{
		this.story = story;
		
		gameHistory = User.getCurrentUser().UserHistory.FindGameByStoryId(story.id);
		
		
		storyGameView = new StoryGameView(this);
		
		if (gameHistory == null)
		{
			loadFirstStep();
		}
		else
		{
			String lastStepId = gameHistory.Steps.get(gameHistory.Steps.size() - 1);
			loadStepWithId(lastStepId,false);
		}
		
		
		storyGameView.setVisible(true);
	}
	
	public void loadFirstStep()
	{
		gameHistory = new GameHistory();
		gameHistory.StoryId = story.id;
		User.getCurrentUser().UserHistory.AddGame(gameHistory);
		Step firstStep = story.steps.get(0);
		loadStepWithId(firstStep.id + "",true);
	}
	
	public void loadStepWithId(String id,boolean newStep)
	{
		Step step = story.getStepWithId(Integer.parseInt(id));
		if( step != null )
		{
			if (newStep != false)
			{
				gameHistory.AddStep(step.id + "");
				History.saveHistory(User.getCurrentUser().UserHistory);
			}
			//need to find a way to refresh view only
			if (storyGameView != null)
				storyGameView.close();
			storyGameView = new StoryGameView(this);
			storyGameView.loadStep(step);
			storyGameView.setVisible(true);
		}
		else
		{
			storyGameView.showMessageDialog("Step not found! Please check the Gamebook's database");
		}
	}
	
	public void close()
	{
		storyGameView.close();
	}
	
	public void setVisible(boolean visible)
	{
		storyGameView.setVisible(visible);
	}
}
