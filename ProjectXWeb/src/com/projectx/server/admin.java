package com.projectx.server;

import java.io.IOException;
import java.io.PrintWriter;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.Key;
import java.security.NoSuchAlgorithmException;
import java.security.SecureRandom;
import java.util.Calendar;
import java.util.Date;
import java.util.Scanner;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import javax.crypto.spec.IvParameterSpec;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.simplemysql.SimpleMySQL;
import com.util.simplemysql.SimpleMySQLResult;

import static spark.Spark.*;

/**
 * Servlet implementation class admin
 * MySQL on AWS Oregon	
 * DB Instance Identifier: InternProject
 * DB Name: ebdb
 * Master Username: dbadmin
 * Master Password: dblogin123
 */
@WebServlet("/admin")
public class admin extends HttpServlet 
{
    //JDBC driver name and database URL
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver"; 
    static final String DB_URL="internproject.cotpco4fkejc.us-west-2.rds.amazonaws.com:3306/";
    
    //Database credentials
    static final String USER = "dbadmin";
    static final String PASS = "dblogin123";
    
    //Connect to DB
    private static SimpleMySQL mysql;
    
    private static final long serialVersionUID = 1L;
    
    //User login data
    private String username;
    private String password;
    
    
    //login check
    boolean loginError;
    boolean userCheck = false;
    
    /**
    * @see HttpServlet#HttpServlet()
    */
    public admin() 
    {
        super();
        
        mysql = new SimpleMySQL();
        mysql.connect(DB_URL, USER, PASS, "ebdb");
        
        username = "";
        password = "";
        
        loginError = false;
    }
    
    /**
    * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
    */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {     
        if(GlobalVar.myCookie.getValue().equals("Logout"))
        {
            SimpleMySQLResult result = mysql.Query("SELECT id, date FROM posts");
            
            int postCount = 0;
            String recentDate = "";
            
            while (result.next())
            {
                postCount++;
            }
            
            for(int x = 0; x != postCount - 1; x++)
                recentDate = result.getString("date");
            
            //Format for JSP
            request.setAttribute("postCount", postCount); // This will be available as ${message}
            request.setAttribute("instatus", GlobalVar.myCookie.getValue());
            request.setAttribute("recentPost", recentDate);
            request.getRequestDispatcher("admin.jsp").forward(request, response);
            
            result.close();
        }
        else
        {               
            SimpleMySQLResult resultUsers = mysql.Query("SELECT username, password FROM users");

            if(!loginError)
            {
                while (resultUsers.next())
                {
                    if(resultUsers.getString("username").equals(username) && resultUsers.getString("password").equals(password))
                    {
                        GlobalVar.myCookie.setValue("Logout");

                        doGet(request, response);
                        
                        username = "";
                        password = "";
                        return;
                    }
                    if(!username.equals("") && !password.equals(""))
                        loginError = true;
                }
            }
            
            if(loginError)
            {
                request.setAttribute("errors", "<span style=\"color: red;\">Sorry, but the username or password you have entered is incorrect.</span>");
                loginError = false;
            }  
                         
            GlobalVar.lastURL = request.getRequestURL();
            request.setAttribute("instatus", GlobalVar.myCookie.getValue());
            request.getRequestDispatcher("loginpage.jsp").forward(request, response);
        }
    }

    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {	    
        if(GlobalVar.myCookie.getValue().equals("Logout"))
        {
            String requestBody = extractPostRequestBody(request).substring(7);
            
            mysql.Query ("INSERT posts SET note='" + requestBody + "', date='" + new Date() + "'");
        }
        else
        {
            String requestUserData = extractPostRequestBody(request);
            
            username = requestUserData.substring(9, requestUserData.indexOf("&password="));
            password = requestUserData.substring(10 + requestUserData.indexOf("&password="));
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
            	return java.net.URLDecoder.decode(s.hasNext() ? s.next() : "", "UTF-8");
        }
        return ""; 
    }
}