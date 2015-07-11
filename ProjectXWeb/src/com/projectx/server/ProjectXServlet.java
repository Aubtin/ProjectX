package com.projectx.server;

import java.io.IOException;
import java.util.Scanner;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;


/**
 * Servlet implementation class ProjectXServlet
 * MySQL on AWS Oregon	
 * DB Instance Identifier: InternProject
 * DB Name: BlogDB
 * Master Username: dbaccess
 * Master Password: InternProject123
 */
@WebServlet("/ProjectXServlet")
public class ProjectXServlet extends HttpServlet 
{

	// JDBC driver name and database URL
    	static final String JDBC_DRIVER="com.mysql.jdbc.Driver"; 
    	static final String DB_URL="jdbc:mysql://internproject.cotpco4fkejc.us-west-2.rds.amazonaws.com:3306/BlogDB";

    	//  Database credentials
    	static final String USER = "dbaccess";
    	static final String PASS = "InternProject123";
	
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
		Connection conn = null;  
		Statement stmt = null;
		ResultSet rs = null;

		try
    		{
			Class.forName(JDBC_DRIVER); 

			// Open a connection
	    		conn = DriverManager.getConnection(DB_URL,USER,PASS);

	    		// Execute SQL query
    			stmt = conn.createStatement();

    			String sql = "SELECT id, note FROM posts WHERE id=1";
	    		rs = stmt.executeQuery(sql);

	    		// Extract data from result set
    			while(rs.next())
    			{
               			//Retrieve by column name
                		int id  = rs.getInt("id");
             			String note = rs.getString("note");

                		//Format for JSP
                		request.setAttribute("message", note); // This will be available as ${message}
                		request.getRequestDispatcher("/index.jsp").forward(request, response);
    			}
             		// Clean-up environment
             		rs.close();
             		stmt.close();
             		conn.close();
    		}
    		catch(SQLException se)
    		{
    			//Handle errors for JDBC
    			se.printStackTrace();
    		}
		catch(Exception e)
    		{
    			//Handle errors for Class.forName
    			e.printStackTrace();
    		}
    		finally
    		{
             		//finally block used to close resources
             		try
             		{
                		if(stmt!=null)
                  			stmt.close();
             		}
             		catch(SQLException se2)
             		{
             		}
             		try
             		{
            	 		if(conn!=null)
            		 		conn.close();
             		}
             		catch(SQLException se)
            		{
            	 		se.printStackTrace();
            		}
    		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		Connection conn = null;  
		Statement stmt = null;

		String requestBody;

		try
    		{
			Class.forName(JDBC_DRIVER);

    			// Open a connection
    			conn = DriverManager.getConnection(DB_URL,USER,PASS);

	    		// Execute SQL query
    			stmt = conn.createStatement();
    		
    			//Get text from textarea.
			requestBody = extractPostRequestBody(request).substring(7);
			
			try 
			{
				stmt.executeUpdate("UPDATE posts SET note='" + requestBody + "' WHERE id=1");
			}
			catch (SQLException e) 
			{
				e.printStackTrace();
			}

			// Clean-up environment
             		stmt.close();
             		conn.close();
    		}
    		catch(SQLException se)
    		{
    			//Handle errors for JDBC
    			se.printStackTrace();
    		}
		catch(Exception e)
    		{
    			//Handle errors for Class.forName
    			e.printStackTrace();
    		}
    		finally
    		{	
             		//finally block used to close resources
             		try
             		{
               			if(stmt!=null)
                   			stmt.close();
             		}
             		catch(SQLException se2)
             		{
             		}

             		try
            		{
            	 		if(conn!=null)
            		 		conn.close();
             		}
             		catch(SQLException se)
             		{
            	 		se.printStackTrace();
             		}
    		}

		//Retrieve info again.
		doGet(request, response);
	}

	//Capture the body from the POST, and return as a string.
	static String extractPostRequestBody(HttpServletRequest request) throws IOException 
	{
		if ("POST".equalsIgnoreCase(request.getMethod())) 
		{
	        	Scanner s = new Scanner(request.getInputStream(), "UTF-8").useDelimiter("\\A");
	        	return s.hasNext() ? s.next() : "";
	    	}
	    	return ""; 
	}
}
