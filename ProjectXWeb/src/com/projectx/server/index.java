package com.projectx.server;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.util.simplemysql.SimpleMySQL;
import com.util.simplemysql.SimpleMySQLResult;

/**
 * Servlet implementation class index
 */
@WebServlet("/index")
public class index extends HttpServlet 
{

    //Connect to DB
    private static SimpleMySQL mysql;
    private GlobalVar vars;
    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public index() 
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
        GlobalVar.lastURL = request.getRequestURL();
        
    	SimpleMySQLResult result = mysql.Query("SELECT id, note, date FROM posts");
        
        String note = "";
        
        while (result.next())
        { 
            note = note + "<html><table style=\"text-align: left;  width: 1144px; height: 32px; border: 10px solid transparent\" border=\"1\" cellpadding=\"2\" cellspacing=\"2\">"
                        + "<tbody>"
                        + "<tr>"
                        + "<td style=\"vertical-align: top; width: 270px; WORD-BREAK: BREAK-ALL; border: 10px solid transparent\">" + result.getString("date") + "</td>"
                        + "<td style=\"vertical-align: top; WORD-BREAK: BREAK-ALL; border: 10px solid transparent\">" + result.getString("note") + "<br>"
                        + "</td>"
                        + "</tr>"
                        + "</tbody>"
                        + "</table>"
                        + "<hr>"
                        + "</html>";
        }
        
        //Format for JSP
        request.setAttribute("message", note); // This will be available as ${message}
        request.setAttribute("instatus", GlobalVar.myCookie.getValue());
        request.getRequestDispatcher("index.jsp").forward(request, response);
        
        note = "";
        result.close();
    }
    
    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    	{
    	}
}