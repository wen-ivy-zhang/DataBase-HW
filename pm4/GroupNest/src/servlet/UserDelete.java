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


@WebServlet("/userdelete")
public class UserDelete extends HttpServlet {
	
	protected UserDao userDao;
	
	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        // Provide a title and render the JSP.
        messages.put("title", "Delete User");        
        req.getRequestDispatcher("/UserDelete.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String userID = req.getParameter("userID");
        if (userID == null || userID.trim().isEmpty()) {
            messages.put("title", "Invalid UserID");
            messages.put("disableSubmit", "true");
        } else {
        	// Delete the User.
	        try {
	        	int userId = Integer.valueOf(userID);
	        	User user = userDao.getUserByUserId(userId);
	        	if (user != null) {
	        		System.out.println("*****Check 123: FirstName: " + user.getFirstName());
	        	}
	        	else {
	        		System.out.println("*****Check 234: delete user null");
	        	}
	        	user = userDao.delete(user);
	        	// Update the message.
		        if (user == null) {
		            messages.put("title", "Successfully deleted " + userId);
		            messages.put("disableSubmit", "true");
		        } else {
		        	messages.put("title", "Failed to delete " + userId);
		        	messages.put("disableSubmit", "false");
		        }
	        } catch (NumberFormatException e1) {
	        	messages.put("title", "Invalid UserID");
	            messages.put("disableSubmit", "true");
	        } catch (SQLException e2) {
				e2.printStackTrace();
				throw new IOException(e2);
	        }
        }
        
        req.getRequestDispatcher("/UserDelete.jsp").forward(req, resp);
    }
}
