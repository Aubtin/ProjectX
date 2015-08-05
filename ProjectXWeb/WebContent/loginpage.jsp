<html>
<head>
<meta content="text/html; charset=ISO-8859-1"
http-equiv="content-type">
<title></title>
</head>
	<body>
	<a href="index">Home</a>&nbsp;&nbsp;&nbsp; <a href="admin">Admin</a>&nbsp;&nbsp;&nbsp;
	<a style="text-align: right;" href="toggleinout">${instatus}</a><br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<div style="text-align: center;">
	<h2>Login:</h2>
	</div>
	<div style="text-align: center;">
	${errors}
	<form method="post" action="admin">
	<div style="text-align: left;"> </div>
	<h4 style="text-align: center;">Username: <input name="username"></h4>
	<div> </div>
	<h4 style="text-align: center;">Password: <input name="password"
	type="password"></h4>
	<div style="text-align: center;"> <input value="Send" type="submit"/>
	</div>
	</form>
	</div>
	<br>
	<br>
	</body>
</html>
