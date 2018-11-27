package servlet;

import dal.*;
import model.*;


import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/apartmentreviews")
public class GroupNestApartmentReviews extends HttpServlet {
	
	protected ApartmentReviewDao apartmentReviewDao;
	
	@Override
	public void init() throws ServletException {
		apartmentReviewDao = ApartmentReviewDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        String apartmentId = req.getParameter("apartmentid");
        if (apartmentId == null || apartmentId.trim().isEmpty()) {
            messages.put("title", "Invalid apartment ID.");
        } else {
        	messages.put("title", "Reviews for apartment " + apartmentId);
        }
        
        List<ApartmentReview> reviews = new ArrayList<ApartmentReview>();
        double averageRating;
        try {
        	reviews = apartmentReviewDao.getApartmentReviewsByApartment(Integer.parseInt(apartmentId));
        	averageRating = apartmentReviewDao.getAveRatingByApartment(Integer.parseInt(apartmentId));
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        messages.put("averagerating", String.format("%.1f", averageRating));
        req.setAttribute("apartmentReviews", reviews);
        req.getRequestDispatcher("/GroupNestApartmentReviews.jsp").forward(req, resp);
	}
}
