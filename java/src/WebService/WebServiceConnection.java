package WebService;

import org.json.JSONObject;

import Lib.WebService;

public class WebServiceConnection
{
	Thread thread;
	
	//callback
	public interface WebServiceConnectionDelegate
	{ 
        void webServiceDidRetrieveJSON(JSONObject json);
    }
	
	public WebServiceConnection(final WebServiceRequest request, final WebServiceConnectionDelegate delegate)
	{
		if(thread == null)
		{
			thread = new Thread()
			{
				@Override
	            public void run()
	            {
					JSONObject json = WebService.getJSONObjectForUrl(request.URLString);

					if(delegate != null)
						delegate.webServiceDidRetrieveJSON(json);
					
					thread = null;
	            }
			};
			
			thread.start();
		}
	}
}
