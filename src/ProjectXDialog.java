//POST 

import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class ProjectXDialog extends Thread 
{
	private String textInput;
	private String response;
	
	public ProjectXDialog(String input)
	{
		textInput = input;
		response = "";
	}
	
	public void run()
	{
			try
			{
				//Connect to server and perform POST action.
				String httpURL = "http://httpbin.org/post";
				String query = textInput;
	
				URL myurl = new URL(httpURL);
				HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
				con.setRequestMethod("POST");
	
				con.setRequestProperty("Content-length", String.valueOf(query.length())); 
				con.setRequestProperty("Content-Type","text/plain"); 
				con.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0;Windows98;DigExt)"); 
				con.setDoOutput(true); 
				con.setDoInput(true); 
	
				DataOutputStream output = new DataOutputStream(con.getOutputStream());  
				output.writeBytes(query);
				output.close();
	
				DataInputStream input = new DataInputStream( con.getInputStream() ); 
	
				//Decode the bytes into text and insert into String.
				for( int c = input.read(); c != -1; c = input.read() ) 
					response += (char)c;
				
				input.close(); 
				
				//Retrieve data from returned POST data (JSON).
				JSONObject json = new JSONObject(response);
				response = json.getString("data");
				
				//Create dialog box once response received. 
				ProjectXFrame myFrame = new ProjectXFrame();
				myFrame.createDialog(response);
			}
			catch(IOException e)
			{
				e.printStackTrace();
			}
		}
}
