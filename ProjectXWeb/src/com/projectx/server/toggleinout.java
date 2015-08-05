package com.projectx.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.simplemysql.SimpleMySQL;

/**
 * Servlet implementation class toggleinout
 */
@WebServlet("/toggleinout")
public class toggleinout extends HttpServlet 
{
       
    //Connect to DB
    private static SimpleMySQL mysql;
    private GlobalVar vars;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public toggleinout() 
    {
        super();

        mysql = new SimpleMySQL();
        mysql.connect(GlobalVar.DB_URL, GlobalVar.USER, GlobalVar.PASS, "ebdb");
        vars = new GlobalVar();
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
    {	    
        if(GlobalVar.myCookie.getValue().equals("Logout"))
        {
            GlobalVar.myCookie.setValue("Login");
        }
        
        if(GlobalVar.myCookie.getValue().equals("Login"))
        {    
            request.getRequestDispatcher("admin").forward(request, response);
            return;
        }
        
        request.setAttribute("instatus", GlobalVar.myCookie.getValue());
        request.setAttribute("redirect", GlobalVar.lastURL);
        request.getRequestDispatcher("toggleinout.jsp").forward(request, response);
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    	{
    	}
}