package Controllers;

import Model.Step;
import Model.Story;
import Views.StoryGameView;

public class StoryGame
{
	public Story story;
	StoryGameView storyGameView;
	
	public StoryGame(Story story)
	{
		this.story = story;
		
		storyGameView = new StoryGameView(this);
		storyGameView.loadStep(story.steps.get(0));
		storyGameView.setVisible(true);
	}
	
	public void loadStepWithId(String id)
	{
		Step step = story.getStepWithId(Integer.parseInt(id));
		if( step != null )
		{
			//need to find a way to refresh view only
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
}
