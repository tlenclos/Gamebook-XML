package User;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;

import Lib.FileManager;

import com.thoughtworks.xstream.XStream;

public class User
{
	public String username;
	public String password;
	static XStream xstream = new XStream();
	
	public final static String kUserXMLFilename = "userData.xml";
	
	public User(String username,String password)
	{
		this.username = username;
		this.password = password;
	}
	
	public void setAsDefault()
	{
		File file = new File(kUserXMLFilename);
		if(this.username.length() > 0 && this.password.length() > 0)
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
