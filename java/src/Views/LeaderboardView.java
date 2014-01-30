package Views;

import java.awt.Dimension;
import java.util.ArrayList;

import javax.swing.DefaultListModel;
import javax.swing.JList;
import javax.swing.JScrollPane;

import User.User;

public class LeaderboardView extends MainView
{	
	private static final long serialVersionUID = 1L;
	public ArrayList<User> users;
	
	private JList list;
	
	public LeaderboardView(ArrayList<User> users)
	{
		this.users = users;
		
		super.setupView("Leaderboard");
		
		DefaultListModel listModel = new DefaultListModel();
		for (User user : users)
		{
			listModel.addElement(user.username);
		}
		
		list = new JList(listModel);
        list.setVisibleRowCount(25);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        listScrollPane.setPreferredSize(new Dimension(250, 250));
        super.putGBC(0, 0, 25, 25);
        add(listScrollPane,gbc);
	}
}
