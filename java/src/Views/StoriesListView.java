package Views;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JLabel;
import javax.swing.JList;
import javax.swing.JScrollPane;
import javax.swing.ListSelectionModel;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;

import Controllers.StoriesList;
import Model.Story;

public class StoriesListView extends MainView implements ListSelectionListener
{
	private static final long serialVersionUID = 1L;
	public ArrayList<Story> stories;
	private JList list;
	StoriesList storyController;
	
	public StoriesListView(ArrayList<Story> stories,StoriesList storyController)
	{
		this.stories = stories;
		this.storyController = storyController;
		
		super.setupView("Stories");
		
		DefaultListModel listModel = new DefaultListModel();
		for (Story story : stories)
		{
			listModel.addElement(story.title);
		}
		
		list = new JList(listModel);
        list.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
        list.addListSelectionListener(this);
        list.setVisibleRowCount(25);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        listScrollPane.setPreferredSize(new Dimension(250, 250));
        JLabel label = new JLabel("Please choose a story");
        super.putGBC(0, 0, 25, 1);
        add(label,gbc);
        super.putGBC(0, 1, 25, 25);
        add(listScrollPane,gbc);
        
	}

	@Override
	public void valueChanged(ListSelectionEvent arg0) {
		if(list.getSelectedIndex() != -1)
		{
			Story story = stories.get(list.getSelectedIndex());
			if(storyController != null)
				storyController.onSelectStory(story);
		}
		else
		{
			super.showMessageDialog("Please choose a story to proceed");
		}
		
	}
}