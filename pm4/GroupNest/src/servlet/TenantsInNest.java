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


@WebServlet("/tenantsinnest")
public class TenantsInNest extends HttpServlet {
	
	protected TenantDao tenantDao;
	
	@Override
	public void init() throws ServletException {
		tenantDao = TenantDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
		
        String nestId = req.getParameter("nestid");
        if (nestId == null || nestId.trim().isEmpty()) {
            messages.put("title", "Invalid nest ID.");
        } else {
        	messages.put("title", "Users in nest " + nestId);
        	
            List<Tenant> tenants = new ArrayList<Tenant>();
            try {
            	tenants = tenantDao.getTenantByNest(Integer.parseInt(nestId));
            } catch (SQLException e) {
    			e.printStackTrace();
    			throw new IOException(e);
            }
            req.setAttribute("tenants", tenants);
        }
        
        req.getRequestDispatcher("/TenantsInNest.jsp").forward(req, resp);
	}
}
