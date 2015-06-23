//POST 

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class ProjectXDialog extends Thread 
{
	private String textInput;
	
	public ProjectXDialog(String input)
	{
		textInput = input;
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

			DataInputStream input = new DataInputStream(con.getInputStream()); 
			BufferedReader reader = new BufferedReader(new InputStreamReader(input));
			
			//Creating StringBuilder
			StringBuilder rawResponse = new StringBuilder();
			
			//Decode the bytes into text and insert into String.
			while (reader.ready())
			{
				rawResponse.append(reader.readLine());
			}
			
			input.close(); 
			
			//Retrieve data from returned POST data (JSON).
			JSONObject json = new JSONObject(rawResponse.toString());
			String response = json.getString("data");
			
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
