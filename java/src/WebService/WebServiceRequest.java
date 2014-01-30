package WebService;

import User.User;

public class WebServiceRequest
{
	public String URLString;
	public final static String SERVER_URL = "http://localhost:8080/Gamebook-XML/web/";
	public final static String API_URL = SERVER_URL+"api.php/";
	public final static String DATA_URL = SERVER_URL+"data/stories/";
	public final static String USERS_URL = SERVER_URL+"data/users/";
	
	// UserAuthentication
	public static WebServiceRequest AuthenticationRequestForUser(User user)
	{
		WebServiceRequest request = new WebServiceRequest();
		request.URLString = API_URL+"user/login/"+user.Username+"/"+user.Password;
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
	
	public static WebServiceRequest UserRequestXML(String xmlFileName)
	{
		WebServiceRequest request = new WebServiceRequest();
		request.URLString = USERS_URL+xmlFileName;
		return request;
	}
	
	public static WebServiceRequest UsersRequestXML()
	{
		WebServiceRequest request = new WebServiceRequest();
		request.URLString = API_URL+"/users";
		return request;
	}
	
	public static WebServiceRequest ScoreAddRequest(int score)
	{
		WebServiceRequest request = new WebServiceRequest();
		request.URLString = API_URL+"user/addScore/"+score;
		return request;
	}
}
