package User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import Lib.FileManager;
import Model.History;

import com.thoughtworks.xstream.XStream;

public class User
{
	private static User _CurrentUser = null;
	public static User getCurrentUser() { return _CurrentUser; };
	
	public String Username;
	public String Password;
	public int Score;
	
	public History UserHistory = null;
	
	static XStream xstream = new XStream();
	
	public final static String kUserXMLFilename = "userData.xml";
	
	public User(String username,String password)
	{
		this.Username = username;
		this.Password = password;
		UserHistory = History.loadHistoryForUser(Username);
		if (UserHistory == null)
		{
			UserHistory = new History();
			UserHistory.UserName = Username;
		}
	}
	
	public User(Node xmlNode)
	{
		NodeList userChildren = xmlNode.getChildNodes();
		for(int i=0;i<userChildren.getLength();i++)
		{
			Node n = userChildren.item(i);
			if(n.getNodeName().equals("score") && n.getFirstChild() != null)
			{
				Score = Integer.parseInt(n.getFirstChild().getNodeValue());
			}
			else if(n.getNodeName().equals("login"))
			{
				Username = n.getFirstChild().getNodeValue();
			}
			else if(n.getNodeName().equals("password"))
			{
				Password = n.getFirstChild().getNodeValue();
			}		
		}
		
		UserHistory = History.loadHistoryForUser(Username);
		if (UserHistory == null)
		{
			UserHistory = new History();
			UserHistory.UserName = Username;
		}
	}
	
	public void setAsDefault()
	{
		_CurrentUser = this;
		
		File file = new File(kUserXMLFilename);
		if(this.Username.length() > 0 && this.Password.length() > 0)
		{
			String xml = xstream.toXML(this);
			try {
				if(file.exists() == false)
					file.createNewFile();
				FileManager.setContents(file, xml);
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	public static User defaultUser()
	{
		File f = new File(kUserXMLFilename);
		if(f.exists())
		{
			String xml = FileManager.getContents(f);
			User user = (User)xstream.fromXML(xml);

			return user;
		}
		return null;
	}
}
