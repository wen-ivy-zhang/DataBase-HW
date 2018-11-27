<%@ taglib uri="http://java.sun.com/jsp/jstl/functions" prefix="fn" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ taglib uri="http://java.sun.com/jsp/jstl/fmt" prefix="fmt" %>

<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<!-- Bootstrap -->
<link href="css/bootstrap.min.css" rel="stylesheet">
<title>Create a Tenant</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
	
	<div class="jumbotron">
	<h1>Create Tenant</h1>
	</div>
	<form action="Tenantcreate" method="post">
		<p>
			<h2><label for="firstname">FirstName</label></h2>
			<input id="firstname" name="firstname" value="">
		</p>
		<p>
			<h2><label for="lastname">LastName</label></h2>
			<input id="lastname" name="lastname" value="">
		</p>
		<p>
			<h2><label for="email">Email</label></h2>
			<input id="email" name="email" value="">
		</p>
 		<p>
			<h2><label for="universityName">University</label></h2>
			<input id="universityName" name="universityName" value="">
		</p>
		<p>
			<h2><label for="major">Major</label></h2>
			<input id="major" name="major" value="">
		</p>
		<p>
			<h2><label for="gender">Gender (Male, Female)</label></h2>
			<input id="gender" name="gender" value="">
		</p>
		<p>
			<h2><label for="bio">Background Information</label></h2>
			<input id="bio" name="bio" value="">
		</p>
		
		<p>
			<input type="submit" class="btn btn-lg btn-primary">
		</p>
		<h3><div id="backHome"><a href="findapartments">Back to home page</a></div></h3>
	</form>
	<br/><br/>
	<p>
		<div class="alert alert-success" role="alert">
		<span id="successMessage"><b>${messages.success}</b></span>
		</div>
	</p>
	
	</div>
	
	<!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
    
</body>
</html>