package servlet;


import dal.*;
import model.*;

import java.io.IOException;
import java.sql.SQLException;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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


@WebServlet("/Landlordcreate")
public class LandlordCreate extends HttpServlet {
	
	protected UserDao userDao;
	protected LandlordDao landlordDao;
	
	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
		landlordDao = LandlordDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/LandlordCreate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve and validate name.
        String firstName = req.getParameter("firstname");
        String lastName = req.getParameter("lastname");
        String email = req.getParameter("email");
        String userType = req.getParameter("userType");

        if (firstName == null || firstName.trim().isEmpty()) {
        	messages.put("success", "Invalid First Name");
        }
        else if (lastName == null || lastName.trim().isEmpty()) {
        	messages.put("success", "Invalid Last Name");
        }
        else if (email == null || email.trim().isEmpty()) {
            messages.put("success", "Invalid Email");
        }
        else {
	        // create landlord
			try {
			    Landlord landlord = new Landlord(firstName, lastName, email);
			   	landlord = landlordDao.create(landlord);
			   	int UserID = landlord.getUserId();
			    messages.put("success", "Successfully created landlord: " + firstName 
			    		+  " " + lastName + " UserID is: " + UserID + ". Please remember UserID for use of deletion.");
			}
	  	    catch (SQLException e) {
	  	    	e.printStackTrace();
	  	    	throw new IOException(e);
			}

        }
        
        req.getRequestDispatcher("/LandlordCreate.jsp").forward(req, resp);
    }
}


