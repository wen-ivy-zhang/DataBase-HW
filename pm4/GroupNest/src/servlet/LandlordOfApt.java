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


@WebServlet("/landlordofapartment")
public class LandlordOfApt extends HttpServlet {
	
	protected ApartmentDao apartmentDao;
	protected LandlordDao landlordDao;
	
	@Override
	public void init() throws ServletException {
		apartmentDao = ApartmentDao.getInstance();
		landlordDao = LandlordDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        Landlord landlord = null;
        String aptID = req.getParameter("apartmentID");
        if (aptID == null || aptID.trim().isEmpty()) {
            messages.put("title", "Invalid Apartment ID.");
        } else {
        	messages.put("title", "Landlord of the Apartment " + aptID);
            try {
            	int apartmentId = Integer.valueOf(aptID);
            	Apartment apartment = apartmentDao.getApartmentByApartmentId(apartmentId);
            	int landlordId = apartment.getOwner().getUserId();
            	System.out.println("CheckB: landlordId: " + landlordId);
            	landlord = landlordDao.getLandlordFromId(landlordId);
            	if (landlord == null) {
            		System.out.println("CheckC: landlord null ");
            	}
            	else {
            		System.out.println("CheckD First name: " + landlord.getFirstName());
            	}
                
            	
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
            req.setAttribute("landlord", landlord);
            
        }

        req.getRequestDispatcher("/LandlordOfApt.jsp").forward(req, resp);
	}
}

