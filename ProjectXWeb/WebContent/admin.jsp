<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Admin - Blog</title>

</head>

<body>
	<a href="index">Home</a>&nbsp;&nbsp;&nbsp; 
	<a href="admin">Admin</a>&nbsp;&nbsp;&nbsp;
	<a style="text-align: right;" href="toggleinout">${instatus}</a><br>
	
	<hr1>Admin Page</hr1>
	<form action = "admin" method="POST"></form>
	<form action = "admin" method="POST">
	<textarea rows="6" cols="70" name="myText" autofocus="true"></textarea>
	<br>
	<br>
	<input type="Submit" value="Send"/>
	</form>
	<p>Post Count: ${postCount}		
	<br>Last Post: ${recentPost}</p>
</body>
</html>