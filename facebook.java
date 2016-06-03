package translategame;



import com.gargoylesoftware.htmlunit.BrowserVersion;
import com.gargoylesoftware.htmlunit.FailingHttpStatusCodeException;
import com.gargoylesoftware.htmlunit.WebClient;
import com.gargoylesoftware.htmlunit.html.HtmlForm;
import com.gargoylesoftware.htmlunit.html.HtmlInput;
import com.gargoylesoftware.htmlunit.html.HtmlPage;
import com.gargoylesoftware.htmlunit.html.HtmlPasswordInput;
import com.gargoylesoftware.htmlunit.html.HtmlSubmitInput;
import com.gargoylesoftware.htmlunit.html.HtmlTextInput;

import java.io.IOException;

import javax.net.ssl.HttpsURLConnection;



public class facebook {
	private static int state_login;
	private static String user = "";
	private static String pass = ""; 
	public static void main (String[] args) throws FailingHttpStatusCodeException, IOException{

		 try (WebClient webClient = new WebClient(BrowserVersion.CHROME)) {
			    HtmlPage page = (HtmlPage) webClient.getPage("https://www.facebook.com"); 
			    HtmlTextInput textInput = (HtmlTextInput)page.getElementById("email");
			    textInput.setValueAttribute(user);
			    HtmlPasswordInput passInput = (HtmlPasswordInput)page.getElementById("pass");
			    passInput.setValueAttribute(pass);
			    HtmlSubmitInput submitBtn = (HtmlSubmitInput)page.getElementById("loginbutton").getFirstChild();
			    page = submitBtn.click();
			    
			    System.out.println("running7");
			    if(page.asText().length() >= 0)
			    {
			    	String s = page.asText();
			    	if(s.charAt(4) == 'b'){
			    		System.out.println("login seccuss");
			    		state_login = 1;
			    	}
			    	else if(s.charAt(4) == 'a'){
			    		System.out.println("login fail");
			    		state_login = 0;
			    	}
			    	else{
			    		System.out.println(s.charAt(4));
			    		state_login = -1;
			    	}
			    }
			    //System.out.println(page.asText());
		 }
	}
	
	int get_login_state()
	{
		return state_login;
	}
	
	void input_email(String e)
	{
		user = e;
	}
	
	void input_pass(String p)
	{
		pass = p;
	}
}
