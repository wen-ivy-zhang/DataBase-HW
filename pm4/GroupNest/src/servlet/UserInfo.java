package servlet;

import dal.*;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.annotation.*;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;


@WebServlet("/users")
public class UserInfo extends HttpServlet {
	
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
		
        String userId = req.getParameter("userid");
        if (userId == null || userId.trim().isEmpty()) {
            messages.put("title", "Invalid user ID.");
        } else {
        	messages.put("title", "User " + userId);
        	
        	User user = null;
            try {
            	user = userDao.getUserByUserId(Integer.valueOf(userId));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
            req.setAttribute("user", user);
        }
        
        req.getRequestDispatcher("/UserInfo.jsp").forward(req, resp);
	}
}
