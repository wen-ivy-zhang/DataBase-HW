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
<title>Rooms</title>
</head>
<body>
    <div class="container theme-showcase" role="main">
    
    <div class="jumbotron">
	<h1>${messages.title}</h1>
	</div>
        <table class="table table-striped">
            <thead><tr>
                <th>Room ID</th>
                <th>Room Type</th>
                <th>Share Bathroom</th>
                <th>Floor Type</th>
                <th>Description</th>
                <th>Open Nests</th>
            </tr></thead>
            <c:forEach items="${rooms}" var="room" >
                <tbody><tr>
                    <td><c:out value="${room.getRoomId()}" /></td>
                    <td><c:out value="${room.getRoomType()}" /></td>
                    <td><c:out value="${room.getShareBathroom()}" /></td>
                    <td><c:out value="${room.getFloorType()}" /></td>
                    <td><c:out value="${room.getDescription()}" /></td>
                    <td><a href="nests?roomid=<c:out value="${room.getRoomId()}"/>">Nests</a></td>
                </tr></tbody>
            </c:forEach>
       </table>
    </div>
       
    <!-- Bootstrap -->
    <!-- jQuery (necessary for Bootstrap's JavaScript plugins) -->
    <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.12.4/jquery.min.js"></script>
    <!-- Include all compiled plugins (below), or include individual files as needed -->
    <script src="js/bootstrap.min.js"></script>
</body>
</html>
