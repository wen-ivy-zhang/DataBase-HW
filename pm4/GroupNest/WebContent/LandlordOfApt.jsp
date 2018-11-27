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
<title>Landlord of Apartment</title>
</head>
<body>
    <div class="container theme-showcase" role="main">
    
	<h1>Landlord of Apartment</h1>
        <table class="table table-striped">
            <thead><tr>
                <th>UserId</th>
                <th>FirstName</th>
                <th>LastName</th>
                <th>Email</th>
                <th>Review</th>
                <th>Update Landlord</th>
                <!-- <th>Delete Landlord</th> -->
                
            </tr></thead>
            <%-- <c:forEach items="${landlord}" var="landlord" > --%>
            <c:set var = "landlord" scope = "session" value = "${landlord}"/>
                <tbody><tr>
                    <td><c:out value="${landlord.getUserId()}" /></td>
                    <td><c:out value="${landlord.getFirstName()}" /></td>
                    <td><c:out value="${landlord.getLastName()}" /></td>
                    <td><c:out value="${landlord.getEmail()}" /></td>
                    <td><a href="userreviews?userID=<c:out value="${landlord.getUserId()}"/>">Reviews</a></td>
                    <td><a href="landlordupdate?landlordID=<c:out value="${landlord.getUserId()}"/>">Update</a></td>
                    <%-- <td><a href="userdelete?userID=<c:out value="${landlord.getUserId()}"/>">Delete</a></td>  --%>               
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
