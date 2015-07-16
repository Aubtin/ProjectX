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

import com.util.simplemysql.SimpleMySQL;
import com.util.simplemysql.SimpleMySQLResult;

/**
 * Servlet implementation class ProjectXServlet
 * MySQL on AWS Oregon	
 * DB Instance Identifier: InternProject
 * DB Name: ebdb
 * Master Username: dbadmin
 * Master Password: dblogin123
 */
@WebServlet("/ProjectXServlet")
public class ProjectXServlet extends HttpServlet 
{
    //JDBC driver name and database URL
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver"; 
    static final String DB_URL="internproject.cotpco4fkejc.us-west-2.rds.amazonaws.com:3306/";
    
    //Database credentials
    static final String USER = "dbadmin";
    static final String PASS = "dblogin123";
    
    //Connect to DB
    private SimpleMySQL mysql;
    
    	
    private static final long serialVersionUID = 1L;
    
    /**
    * @see HttpServlet#HttpServlet()
    */
    public ProjectXServlet() 
    {
        super();
        
        mysql = new SimpleMySQL();
        mysql.connect(DB_URL, USER, PASS, "ebdb");
    }
    
    /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {	    
        SimpleMySQLResult result = mysql.Query("SELECT id, note FROM posts");
        
        String note = "";
        
        while (result.next())
        {
            note = note + "<html><br></html>" + result.getString("note");
        }
        
        //Format for JSP
        request.setAttribute("message", note); // This will be available as ${message}
        request.getRequestDispatcher("/index.jsp").forward(request, response);
        
        note = "";
        result.close();
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {	    
        String requestBody = extractPostRequestBody(request).substring(7);
        
        mysql.Query ("INSERT posts SET note='" + requestBody + "'");
    
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