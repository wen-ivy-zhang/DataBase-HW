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


@WebServlet("/rooms")
public class GroupNestRooms extends HttpServlet {
	
	protected RoomDao roomDao;
	
	@Override
	public void init() throws ServletException {
		roomDao = RoomDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        String apartmentListingId = req.getParameter("apartmentlistingid");
        if (apartmentListingId == null || apartmentListingId.trim().isEmpty()) {
            messages.put("title", "Invalid apartment listing ID.");
        } else {
        	messages.put("title", "Rooms in apartment lising " + apartmentListingId);
        }
        
        List<Room> rooms = new ArrayList<Room>();
        try {
        	rooms = roomDao.getRoomsByApartmentListing(Integer.parseInt(apartmentListingId));
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("rooms", rooms);
        req.getRequestDispatcher("/GroupNestRooms.jsp").forward(req, resp);
	}
}
