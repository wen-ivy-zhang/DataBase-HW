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


@WebServlet("/userreviews")
public class GroupNestUserReviews extends HttpServlet {
	
	protected UserReviewDao userReviewDao;
	
	@Override
	public void init() throws ServletException {
		userReviewDao = userReviewDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        String userId = req.getParameter("userID");
        if (userId == null || userId.trim().isEmpty()) {
            messages.put("title", "Invalid User ID.");
        } else {
        	messages.put("title", "Reviews for user " + userId);
        }
        
        List<UserReview> reviews = new ArrayList<UserReview>();
        double averageRating;
        try {
        	reviews = userReviewDao.getUserReviewsByUserId(Integer.parseInt(userId));
        	averageRating = userReviewDao.getAveRatingByUser(Integer.parseInt(userId));
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
        messages.put("averagerating", String.format("%.1f", averageRating));
        req.setAttribute("userReviews", reviews);
        req.getRequestDispatcher("/GroupNestUserReviews.jsp").forward(req, resp);
	}
}
