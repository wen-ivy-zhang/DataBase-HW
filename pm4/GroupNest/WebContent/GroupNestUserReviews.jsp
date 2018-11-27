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
<title>User Reviews</title>
</head>
<body>
    <div class="container theme-showcase" role="main">
    
    <div class="jumbotron">
	<h1>${messages.title}</h1>
	<h2>Average rating: ${messages.averagerating}</h2>
	</div>
        <table class="table table-striped">
            <thead><tr>
                <th>Review ID</th>
                <th>Posted By</th>
                <th>Posted Time</th>
                <th>Content</th>
                <th>Rating</th>
            </tr></thead>
            <c:forEach items="${userReviews}" var="review" >
                <tbody><tr>
                    <td><c:out value="${review.getReviewId()}" /></td>
                    <td><a href="users?userid=<c:out value="${review.getPostedBy().getUserId()}"/>">${review.getPostedBy().getFirstName()} ${review.getPostedBy().getLastName()}</a></td>
                    <td><fmt:formatDate value="${review.getPostedDateTime()}" pattern="MM-dd-yyyy hh:mm:sa"/></td>   
                    <td><c:out value="${review.getContent()}" /></td>
                    <td><c:out value="${review.getRating()}" /></td>
                </tr></tbody>
            </c:forEach>
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
