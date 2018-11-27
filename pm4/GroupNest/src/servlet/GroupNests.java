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


@WebServlet("/nests")
public class GroupNests extends HttpServlet {
	
	protected NestDao nestDao;
	
	@Override
	public void init() throws ServletException {
		nestDao = NestDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        String roomId = req.getParameter("roomid");
        if (roomId == null || roomId.trim().isEmpty()) {
            messages.put("title", "Invalid room ID.");
        } else {
        	messages.put("title", "Open nests for room " + roomId);
        	messages.put("roomid", roomId);
        }
        
        List<Nest> nests = new ArrayList<Nest>();
        try {
        	nests = nestDao.getAvailableNestsByRoomId(Integer.parseInt(roomId));
        } catch (SQLException e) {
			e.printStackTrace();
			throw new IOException(e);
        }
        req.setAttribute("nests", nests);
        req.getRequestDispatcher("/GroupNests.jsp").forward(req, resp);
	}
}
