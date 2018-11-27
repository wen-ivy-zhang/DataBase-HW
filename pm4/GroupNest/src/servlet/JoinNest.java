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


@WebServlet("/joinnest")
public class JoinNest extends HttpServlet {
	
	protected RoomReservationDao reservationDao;
	protected NestDao nestDao;
//	protected TenantDao tenantDao;
//	protected RoomDao roomDao;
	
	@Override
	public void init() throws ServletException {
		reservationDao = RoomReservationDao.getInstance();
		nestDao = NestDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        String nestId = req.getParameter("nestid");
        String roomId = req.getParameter("roomid");
        messages.put("nestid", nestId);
        messages.put("roomid", roomId);
        //Just render the JSP.   
        req.getRequestDispatcher("/JoinNest.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);   
        String nestId = req.getParameter("nestid");
        String roomId = req.getParameter("roomid");       
    	String userId = req.getParameter("userid");
        
        try {
        	Nest nest = nestDao.getNestByNestId(Integer.parseInt(nestId));        
        	RoomReservation reservation = reservationDao.create(Integer.parseInt(userId), nest.getNestId(), Integer.parseInt(roomId));
        	messages.put("success", "Successfully created reservation, your reservation number is:  " + reservation.getReservationId());
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        
//        try {
//            if (nestId == null || nestId.trim().isEmpty()) {
//        	    nest = nestDao.create(Integer.parseInt(roomId), Integer.parseInt(userId));
//        	    messages.put("success", "Create a new nest " + nest.getNestId());
//            } else {
//        	    messages.put("success", "Join nest " + nestId);
//        	    nest = nestDao.getNestByNestId(Integer.parseInt(nestId));
//            }
//    	    // Create the Reservation.
//    	
//        
//        	RoomReservation reservation = reservationDao.create(Integer.parseInt(userId), nest.getNestId(), Integer.parseInt(roomId));
//        	messages.put("success", "Successfully created reservation " + reservation.getReservationId());
//        } catch (SQLException e) {
//			e.printStackTrace();
//			throw new IOException(e);
//        }

        req.getRequestDispatcher("/JoinNest.jsp").forward(req, resp);
    }
}
