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
<title>User Information</title>
</head>
<body>
    <div class="container theme-showcase" role="main">
    
	<h1>User Information</h1>
        <table class="table table-striped">
            <thead><tr>
                <th>UserId</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
                <th>Review</th>
                
            </tr></thead>
            <%-- <c:forEach items="${tenants}" var="tenant" > --%>
            <c:set var = "user" scope = "session" value = "${user}"/>
                <tbody><tr>
                    <td><c:out value="${user.getUserId()}" /></td>
                    <td><c:out value="${user.getFirstName()}" /></td>
                    <td><c:out value="${user.getLastName()}" /></td>
                    <td><c:out value="${user.getEmail()}" /></td>          
                    <td><a href="userreviews?userID=<c:out value="${user.getUserId()}"/>">Reviews</a></td>              
                </tr></tbody>
            <%-- </c:forEach> --%>
       </table>
       
       <h3><div id="backHome"><a href="findapartments">Back to home page</a></div></h3>
       
    </div>
     
    <!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
       
</body>
</html>
