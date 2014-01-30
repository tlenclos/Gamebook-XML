package XMLParser;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

import Lib.WebService;
import User.User;
import WebService.WebServiceRequest;

public class UsersXMLParser
{
	public static ArrayList<User> parseWithFilename(String filename)
	{
		ArrayList<User> users = new ArrayList<User>();
		
		try
		{
			DOMImplementationRegistry dir = DOMImplementationRegistry.newInstance();
			DOMImplementationLS dils = (DOMImplementationLS) dir.getDOMImplementation("LS 3.0");
			LSParser lsp = dils.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
			
			String url = WebServiceRequest.UserRequestXML(filename).URLString;
			String xml = WebService.getStringForUrl(url);
			
			LSInput input = dils.createLSInput();
			input.setStringData(xml);
			Document doc = lsp.parse(input);
			
			NodeList userNodes = doc.getElementsByTagName("user");
			for(int i=0; i< userNodes.getLength(); i++)
			{
				User u = new User(userNodes.item(i));
				users.add(u);
			}			
			
			Collections.sort(users, new Comparator<User>(){
			    public int compare(User u1, User u2) {
			        return u2.Score - u1.Score;
			    }
			});
		}
		catch (ClassCastException e) {
			e.printStackTrace();
		} catch (ClassNotFoundException e) {
			e.printStackTrace();
		} catch (InstantiationException e) {
			e.printStackTrace();
		} catch (IllegalAccessException e) {
			e.printStackTrace();
		}
		
		return users;
	}
}
