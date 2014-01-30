package Lib;

import java.io.File;
import java.io.FileReader;
import java.io.StringReader;
import java.text.DateFormat;
import java.util.Date;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.transform.OutputKeys;
import javax.xml.transform.Transformer;
import javax.xml.transform.TransformerFactory;
import javax.xml.transform.dom.DOMSource;
import javax.xml.transform.stream.StreamResult;

import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;
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
	
	public static Node getNodeChild(Element parentNode, String nodeName)
	{
		try
		{
			NodeList Nodes = parentNode.getElementsByTagName(nodeName);
			if ( Nodes.getLength() < 1)
				throw new Exception("Nodes.getLenght < 1");
			
			return Nodes.item(0).getFirstChild();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public static Element getElementChild(Element parentNode, String nodeName)
	{
		try
		{
			NodeList Nodes = parentNode.getElementsByTagName(nodeName);
			if ( Nodes.getLength() < 1)
				throw new Exception("Nodes.getLenght < 1");
			
			return (Element)Nodes.item(0).getFirstChild();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	
	public static String getNodeValue(Element parentNode, String nodeName)
	{
		try
		{
			Node childNode = getNodeChild(parentNode,nodeName);
			if ( childNode == null )
				throw new Exception();
			
			return childNode.getNodeValue();
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public static int getNodeValueAsInt(Element parentNode, String nodeName)
	{
		try
		{
			String nodeValue = getNodeValue(parentNode,nodeName);
			return Integer.parseInt(nodeValue);
		}
		catch(Exception e)
		{
			return -1;
		}
	}
	public static Date getNodeValueAsDate(Element parentNode, String nodeName)
	{
		try
		{
			String nodeValue = getNodeValue(parentNode,nodeName);
			return DateFormat.getDateInstance().parse(nodeValue);
		}
		catch(Exception e)
		{
			return null;
		}
	}
	public static boolean getNodeValueAsBoolean(Element parentNode, String nodeName)
	{
		try
		{
			String nodeValue = getNodeValue(parentNode,nodeName);
			return Boolean.parseBoolean(nodeValue);
		}
		catch(Exception e)
		{
			return false;
		}
	}
	
	public static void setNodeValue(Element parentNode, String nodeName, String value)
	{
		try
		{
			NodeList Nodes = parentNode.getElementsByTagName(nodeName);
			if ( Nodes.getLength() < 1)
				throw new Exception("Nodes.getLenght < 1");
			
			Node ActNode = Nodes.item(0);
			if (ActNode.hasChildNodes())
				ActNode.getFirstChild().setNodeValue(value);
			else
				ActNode.appendChild(parentNode.getOwnerDocument().createTextNode(value));
		}
		catch(Exception e){}
	}
}
