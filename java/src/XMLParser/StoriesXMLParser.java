package XMLParser;

import java.util.ArrayList;

import org.w3c.dom.Document;
import org.w3c.dom.NodeList;
import org.w3c.dom.bootstrap.DOMImplementationRegistry;
import org.w3c.dom.ls.DOMImplementationLS;
import org.w3c.dom.ls.LSInput;
import org.w3c.dom.ls.LSParser;

import Lib.WebService;
import Model.Story;
import WebService.WebServiceRequest;

public class StoriesXMLParser
{	
	public static ArrayList<Story> parseWithFiles(ArrayList<String> files)
	{
		ArrayList<Story> stories = new ArrayList<Story>();
		
		try
		{
			DOMImplementationRegistry dir = DOMImplementationRegistry.newInstance();
			DOMImplementationLS dils = (DOMImplementationLS) dir.getDOMImplementation("LS 3.0");
			LSParser lsp = dils.createLSParser(DOMImplementationLS.MODE_SYNCHRONOUS, null);
			
			for(String file : files)
			{
				String url = WebServiceRequest.StoryRequestXML(file).URLString;
				String xml = WebService.getStringForUrl(url);				
				
				LSInput input = dils.createLSInput();
				input.setStringData(xml);
				Document doc = lsp.parse(input);
				
				NodeList gameNodes = doc.getElementsByTagName("game");
				for(int i=0; i< gameNodes.getLength(); i++)
				{
					Story story = new Story(gameNodes.item(i));
					stories.add(story);
				}
			}
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
		
		return stories;
	}

}
