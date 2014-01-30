package Views;

import java.awt.Dimension;
import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.JRadioButton;
import javax.swing.JScrollPane;
import javax.swing.JTextArea;

import Controllers.StoryGame;
import Model.Choice;
import Model.Step;

public class StoryGameView extends MainView implements ActionListener
{
	private static final long serialVersionUID = 1L;
	
	JTextArea description;
	JTextArea question;
	JLabel questionLabel;
	JLabel descriptionLabel;
	JLabel choicesLabel;
	JScrollPane radioButtonScrollPane;
	StoryGame storyGame;
	
	
	public StoryGameView(final StoryGame storyGame)
	{
		this.storyGame = storyGame;
		

		description = new JTextArea("");	
		question = new JTextArea("");
		description.setEditable(false);
		question.setEditable(false);
		
		super.setupView("Story : " + storyGame.story.title);		
	}
	
	public void loadStep(Step step)
	{
		//description
		description.setText(step.description);
		descriptionLabel = new JLabel("Description :");
		super.putGBC(0, 0, 250, 1);
		add(descriptionLabel,gbc);
		super.putGBC(0, 1, 250, 100);
		add(description,gbc);
		
		//question
		question.setText(step.question);
		questionLabel = new JLabel("Question : ");
		super.putGBC(0, 101, 250, 1);
		add(questionLabel,gbc);
		super.putGBC(0, 102, 250, 100);
		add(question,gbc);
		
		//choices
		choicesLabel = new JLabel("Choices : ");
		super.putGBC(0, 202, 250, 1);
		add(choicesLabel,gbc);
		
		JPanel radioPanel = new JPanel(new GridLayout(0, 1));
		for (Choice choice : step.choices)
		{
			JRadioButton radioButton = new JRadioButton(choice.description);
			radioButton.setActionCommand(choice.id);
			radioButton.addActionListener(this);
			radioPanel.add(radioButton);
		}        
        
		JScrollPane buttonScrollPane = new JScrollPane(radioPanel);
		buttonScrollPane.setPreferredSize(new Dimension(250, 100));
		super.putGBC(0, 203, 250, 100);
		add(buttonScrollPane,gbc);
	}

	@Override
	public void actionPerformed(ActionEvent e)
	{
		String id = e.getActionCommand();
		storyGame.loadStepWithId(id,true);
	}
}
