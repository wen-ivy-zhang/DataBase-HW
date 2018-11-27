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


@WebServlet("/landlordupdate")
public class LandlordUpdate extends HttpServlet {
	
	protected LandlordDao landlordDao;
	
	@Override
	public void init() throws ServletException {
		landlordDao = LandlordDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String landlordID = req.getParameter("landlordID");
        if (landlordID == null || landlordID.trim().isEmpty()) {
            messages.put("success", "Please enter a valid landlordID.");
        } else {
        	try {
	        	int landlordId = Integer.valueOf(landlordID);
	        	Landlord landlord = landlordDao.getLandlordFromId(landlordId);
        		if(landlord == null) {
        			messages.put("success", "Landlord does not exist.");
        		}
        		req.setAttribute("landlord", landlord);
        	} catch (NumberFormatException e1) {
        		messages.put("success", "Please enter a valid LandlordID.");
	        } catch (SQLException e2) {
				e2.printStackTrace();
				throw new IOException(e2);
	        }
        }
        
        req.getRequestDispatcher("/LandlordUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String landlordID = req.getParameter("landlordID");
        if (landlordID == null || landlordID.trim().isEmpty()) {
            messages.put("success", "Please enter a valid landlordID.");
        } else {
        	try {
	        	int landlordId = Integer.valueOf(landlordID);
	        	Landlord landlord = landlordDao.getLandlordFromId(landlordId);
        		if(landlord == null) {
        			messages.put("success", "Landlord does not exist. No update to perform.");
        		} else {
        			String newEmail = req.getParameter("email");
        			if (newEmail == null || newEmail.trim().isEmpty()) {
        	            messages.put("success", "Please enter a valid Email.");
        	        } else {
        	        	landlord = (Landlord) landlordDao.updateEmail(landlord, newEmail);
        	        	messages.put("success", "Successfully updated email to " + newEmail + " for landlord " + landlordId);
        	        }
        		}
        		req.setAttribute("landlord", landlord);
        	} catch (NumberFormatException e1) {
        		messages.put("success", "Please enter a valid landlordID.");
	        } catch (SQLException e2) {
				e2.printStackTrace();
				throw new IOException(e2);
	        }
        }
        
        req.getRequestDispatcher("/LandlordUpdate.jsp").forward(req, resp);
    }
}
