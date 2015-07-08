package com.projectx.server;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONObject;


/**
 * Servlet implementation class ProjectXServlet
 */
@WebServlet("/ProjectXServlet")
public class ProjectXServlet extends HttpServlet {
	
	private String requestBody;
	private String directory = "C:/Users/Aubtin/Documents/workspaceFahrzin/ProjectX/";
	
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ProjectXServlet() 
    {
        super();
        
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		//throw new ServletException("422 Unprocessable Entity");
		
		requestBody = readFile();
		System.out.println("GET: " + requestBody);
        request.setAttribute("message", requestBody); // This will be available as ${message}
        request.getRequestDispatcher("/index.jsp").forward(request, response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		PrintWriter out = response.getWriter();


		requestBody = extractPostRequestBody(request).substring(7);
		
//		 try{

//		        File file = new File("c:/example/filedata.txt");
//		        FileReader fileReader = new FileReader(file);
//		        BufferedReader bufferedReader = new BufferedReader(fileReader);
//		        String temp = bufferedReader.readLine();
   
		
			 	System.out.println(requestBody);
			 	writeFile(requestBody);
			 	
				doGet(request, response);

//			 	System.out.println("File Read As: " + readFile());
		        //Clear first.
//		        fileWriter.println("");
//		        fileWriter.println( requestBody );
//		        System.out.println("file saved");
//		        fileWriter.close(); 
//		        fileReader.close();
//			}
//		    catch(FileNotFoundException fnfe) {    
//
//		  }  
		/**
		//Create JSON object and add info.
		JSONObject userText = new JSONObject();
		userText.put("myText", requestBody);
		
	
		//Print JSON
		out.println(userText.toString());
		**/
	}

	//Capture the body from the POST, and return as a string.
	static String extractPostRequestBody(HttpServletRequest request) throws IOException 
	{
	    if ("POST".equalsIgnoreCase(request.getMethod())) {
	        Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
	        return s.hasNext() ? s.next() : "";
	    }
	    return "";
	}

	//Read the file
	public String readFile() throws IOException 
	{
		File writerFile = new File(directory + "textdata.txt");
		if(!writerFile.exists())
			  writerFile.createNewFile();
		
	    BufferedReader br = new BufferedReader(new FileReader(writerFile));
	    try {
	        StringBuilder sb = new StringBuilder();
	        String line = br.readLine();

	        while (line != null) {
	            sb.append(line);
	            sb.append("\n");
	            line = br.readLine();
	        }
	        return sb.toString();
	    } finally {
	        br.close();
	    }
	}
	
	//Write to file
	public void writeFile(String text) throws IOException
	{
		File writerFile = new File(directory + "textdata.txt");
		if(!writerFile.exists())
			  writerFile.createNewFile();
		
	 	PrintWriter writer = new PrintWriter(writerFile, "UTF-8");
	 	writer.println(text);
	 	writer.close();
	 	System.out.println("File Saved.");
	}
}
