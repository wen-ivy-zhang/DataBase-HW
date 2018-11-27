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
<title>Create New Nest</title>
</head>
<body>
	<div class="container theme-showcase" role="main">
	
	<div class="jumbotron">
	<h1>Create a new nest for room ${messages.roomid}</h1>
	</div>
	<form action="createnest" method="post">
		<p>
			<h2><label for="userid">User ID</label></h2>
			<input id="userid" name="userid" value="">
			<input type="hidden" id="roomid" name="roomid" value=${messages.roomid}>
		</p>
		<p>
			<input type="submit" class="btn btn-lg btn-primary">
		</p>
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