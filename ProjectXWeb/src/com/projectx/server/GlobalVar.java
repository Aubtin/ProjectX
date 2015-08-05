package com.projectx.server;

import javax.servlet.http.Cookie;


public class GlobalVar
{
    //JDBC driver name and database URL
    static final String JDBC_DRIVER="com.mysql.jdbc.Driver"; 
    static final String DB_URL="internproject.cotpco4fkejc.us-west-2.rds.amazonaws.com:3306/";
    
    //Database credentials
    static final String USER = "dbadmin";
    static final String PASS = "dblogin123";
    
    //Login status
    static Cookie myCookie = new Cookie("logStatus", "Login");
    
    //Last URl to go back to after login.
    static StringBuffer lastURL;
}
