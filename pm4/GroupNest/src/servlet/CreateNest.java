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


@WebServlet("/createnest")
public class CreateNest extends HttpServlet {
	
	protected RoomReservationDao reservationDao;
	protected NestDao nestDao;
	protected ApartmentListingDao apartmentListingDao;
	
	@Override
	public void init() throws ServletException {
		reservationDao = RoomReservationDao.getInstance();
		nestDao = NestDao.getInstance();
		apartmentListingDao = ApartmentListingDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        String roomId = req.getParameter("roomid");

        messages.put("roomid", roomId);
        //Just render the JSP.   
        req.getRequestDispatcher("/CreateNest.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);   
        int roomId = Integer.parseInt(req.getParameter("roomid"));        
    	int userId = Integer.parseInt(req.getParameter("userid"));
  
        try {
        	int listingId = apartmentListingDao.getApartmentListingByRoom(roomId).getListingId();
        	System.out.println("\n\n\n\n listing id: " + listingId  + " roomId: " + roomId + " user id: " + userId + "\n\n\n\n\n");
        	Nest nest = nestDao.create(listingId, userId);    
        	
        	System.out.println("\n\n\n\n nest id: " + nest.getNestId() + "\n\n\n\n\n");
        	RoomReservation reservation = reservationDao.create(userId, nest.getNestId(), roomId);
        	
        	System.out.println("\n\n\n\n reservation id: " + reservation.getReservationId() + "\n\n\n\n\n");
        	messages.put("success", "Successfully created a nest " + nest.getNestId() + ", your reservation number is:  " + reservation.getReservationId());
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }

        req.getRequestDispatcher("/CreateNest.jsp").forward(req, resp);
    }
}
