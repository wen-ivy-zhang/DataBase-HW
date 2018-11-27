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


@WebServlet("/tenantupdate")
public class TenantUpdate extends HttpServlet {
	
	protected TenantDao tenantDao;
	protected UniversityDao universityDao;
	
	@Override
	public void init() throws ServletException {
		tenantDao = TenantDao.getInstance();
		universityDao = UniversityDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String tenantID = req.getParameter("tenantID");
        if (tenantID == null || tenantID.trim().isEmpty()) {
            messages.put("success", "Please enter a valid tenantID.");
        } else {
        	try {
	        	int tenantId = Integer.valueOf(tenantID);
	        	Tenant tenant = tenantDao.getTenantByUserId(tenantId);
        		if(tenant == null) {
        			messages.put("success", "Tenant does not exist.");
        		}
        		req.setAttribute("tenant", tenant);
        	} catch (NumberFormatException e1) {
        		messages.put("success", "Please enter a valid tenantID.");
	        } catch (SQLException e2) {
				e2.printStackTrace();
				throw new IOException(e2);
	        }
        }
        
        req.getRequestDispatcher("/TenantUpdate.jsp").forward(req, resp);
	}
	
	@Override
    public void doPost(HttpServletRequest req, HttpServletResponse resp)
    		throws ServletException, IOException {
        // Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);

        // Retrieve user and validate.
        String tenantID = req.getParameter("tenantID");
        if (tenantID == null || tenantID.trim().isEmpty()) {
            messages.put("success", "Please enter a valid tenantID.");
        } else {
        	try {
	        	int tenantId = Integer.valueOf(tenantID);
	        	Tenant tenant = tenantDao.getTenantByUserId(tenantId);
        		if(tenant == null) {
        			messages.put("success", "Tenant does not exist. No update to perform.");
        		} else {
        			String newEmail = req.getParameter("email");
        			String newUniversityName = req.getParameter("university");
        			//System.out.println("CheckA: " + newUniversityName);
        			
        			if (newEmail == null || newEmail.trim().isEmpty()) {
        	            messages.put("success", "Not valid Email. Email won't be updated");
        	        } else {
        	        	tenant = (Tenant) tenantDao.updateEmail(tenant, newEmail);
        	        	messages.put("success", "Successfully updated email to " + newEmail + " for Tenant " + tenantId);
        	        }
        			
        			if (newUniversityName == null || newUniversityName.trim().isEmpty()) {
        	            messages.put("success2", "Not valid Univeristy. Univeristy won't be updated");
        	        } else {
        	        	University university = tenant.getUniversity();
        	        	university = universityDao.updateUniverstyName(university, newUniversityName);
        	        	messages.put("success2", "Successfully updated university to " + newUniversityName + " for Tenant " + tenantId);
        	        }
        		}
        		req.setAttribute("tenant", tenant);
        	} catch (NumberFormatException e1) {
        		messages.put("success", "Please enter a valid tenantID.");
	        } catch (SQLException e2) {
				e2.printStackTrace();
				throw new IOException(e2);
	        }
        }
        
        req.getRequestDispatcher("/TenantUpdate.jsp").forward(req, resp);
    }
}
