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
<title>Find apartments</title>
</head>
<body>
    <div class="container theme-showcase" role="main">
    
	<form action="findapartments" method="post">
	    <div class="jumbotron">
		<h1>Search for apartments</h1>
		</div>
		<p>
			<h2><label for="universityname">UniversityName</label></h2>
			<input id="universityname" name="universityname" value="${fn:escapeXml(param.universityname)}">
		</p>
		<p>
			<input type="submit" class="btn btn-lg btn-primary">
			<br/><br/>
		</p>
	</form>
	<h3><div id="userCreate"><a href="usercreate">Create User</a></div></h3>
	<br/>
	<div class="alert alert-info" role="alert">
	<h2><span id="successMessage"><b>${messages.success}</b></span></h2>
	</div>
	<br/>
	<h1>Available apartments:</h1>
        <table class="table table-striped">
            <thead><tr>
                <th>Listing ID</th>
                <th>Apartment Name</th>
                <th>Owner</th>
                <th>Bedrooms</th>
                <th>Bathrooms</th>
                <th>Sqft</th>
                <th>Reviews</th>
                <th>Rooms</th>
                <th>Address</th>
                <th>City</th>
                <th>State</th>
                <th>Zip code</th>
            </tr></thead>
            <c:forEach items="${listings}" var="listing" >
                <tbody><tr>
                    <td><c:out value="${listing.getListingId()}" /></td>
                    <td><c:out value="${listing.getApartment().getName()}" /></td>
                    
                    
                    <td><a href="landlordofapartment?apartmentID=<c:out value="${listing.getApartment().getApartmentId()}"/>">Owner</a></td>
                    
                    
<%--                     <td><a href="landlord?userid=<c:out value="${listing.getLandlord().getUserId()}"/>">Owner</a></td> --%>
                    <td><c:out value="${listing.getApartment().getFloorPlan().getNumberOfBedrooms()}" /></td>
                    <td><c:out value="${listing.getApartment().getFloorPlan().getNumberOfBathrooms()}" /></td>
                    <td><c:out value="${listing.getApartment().getSqft()}" /></td>
                    <td><a href="apartmentreviews?apartmentid=<c:out value="${listing.getApartment().getApartmentId()}"/>">Reviews</a></td>
                    <td><a href="rooms?apartmentlistingid=<c:out value="${listing.getListingId()}"/>">Rooms</a></td>
                    <td><c:out value="${listing.getApartment().getAddress()}" /></td>
                    <td><c:out value="${listing.getApartment().getCity()}" /></td>
                    <td><c:out value="${listing.getApartment().getState()}" /></td>
                    <td><c:out value="${listing.getApartment().getZip()}" /></td>
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
