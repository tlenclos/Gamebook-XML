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
		
		setSize(250, 250);
		setLocation(125,25);		
		
		list = new JList();
		reload();
        list.setVisibleRowCount(25);
        JScrollPane listScrollPane = new JScrollPane(list);
        
        listScrollPane.setPreferredSize(new Dimension(200,200));
        super.putGBC(0, 0, 25, 25);
        add(listScrollPane,gbc);
	}
	
	public void reload()
	{
		DefaultListModel listModel = new DefaultListModel();
		for (User user : users)
		{
			listModel.addElement(user.Username + " [ Score : "+user.Score+"]");
		}		
		list.setModel(listModel);
	}
}
