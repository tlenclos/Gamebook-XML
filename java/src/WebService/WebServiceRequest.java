package WebService;

import User.User;

public class WebServiceRequest
{
	public String URLString;
	public final static String API_URL = "http://localhost:8080/Gamebook-XML/web/api.php/";
	public final static String DATA_URL = "http://localhost:8080/Gamebook-XML/web/data/stories/";
	
	// UserAuthentication
	public static WebServiceRequest AuthenticationRequestForUser(User user)
	{
		WebServiceRequest request = new WebServiceRequest();
		request.URLString = API_URL+"user/login/"+user.username+"/"+user.password;
		return request;
	}
	
	public static WebServiceRequest StoriesRequest()
	{
		WebServiceRequest request = new WebServiceRequest();
		request.URLString = API_URL+"/stories/xml";
		return request;
	}
	
	public static WebServiceRequest StoryRequestXML(String xmlFileName)
	{
		WebServiceRequest request = new WebServiceRequest();
		request.URLString = DATA_URL+xmlFileName;
		return request;
	}
}
