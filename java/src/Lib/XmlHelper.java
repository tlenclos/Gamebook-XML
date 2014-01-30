package Lib;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.xml.sax.InputSource;

public class XmlHelper {
	
	public static Document loadFromString(String xmlContent)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			return builder.parse(new InputSource(new StringReader(xmlContent)));
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static Document loadFromFile(String filepath)
	{
		try
		{
			DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
			DocumentBuilder builder = factory.newDocumentBuilder();
			
			return builder.parse(new InputSource(new FileReader(filepath)));
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static boolean saveDocument(Document xml,String filepath)
	{
		try
		{
			 TransformerFactory transformerFactory = TransformerFactory.newInstance();
	         Transformer transformer = transformerFactory.newTransformer();

	         transformer.setOutputProperty(OutputKeys.INDENT, "yes");
	         DOMSource source = new DOMSource(xml);
	         
	         StreamResult file = new StreamResult(new File(filepath));
	         
	         transformer.transform(source, file);

         	return true;
		}
		catch(Exception e)
		{
			return false;
		}
	}
}
