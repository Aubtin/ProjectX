//POST 

import java.io.BufferedReader;
import java.io.DataInputStream;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class ProjectXPOST extends Thread 
{
	private String textInput;
	private ProjectXFrame myFrame;
	
	public ProjectXPOST(String input)
	{
		textInput = input;
		myFrame = new ProjectXFrame();
	}
	
	public void run()
	{		
		try
		{
			//Connect to server and perform POST action.
			//http://httpbin.org/post
			String httpURL = "http://localhost:8080/ProjectXWeb/ProjectXServlet";
			String query = textInput;

			URL myurl = new URL(httpURL);
			HttpURLConnection con = (HttpURLConnection)myurl.openConnection();
			con.setRequestMethod("POST");

			con.setRequestProperty("Content-length", String.valueOf(query.length())); 
			con.setRequestProperty("Content-Type","text/plain"); 
			con.setRequestProperty("User-Agent", "Intern project aubtins@gmail.com"); 
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
			String response = json.getString("myText");
			
			//Create dialog box once response received. 
			myFrame.createDialog(response);
		}
		catch(IOException e)
		{
			e.printStackTrace();
		}
	}
}
