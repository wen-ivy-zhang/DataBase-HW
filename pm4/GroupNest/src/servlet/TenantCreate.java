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


@WebServlet("/Tenantcreate")
public class TenantCreate extends HttpServlet {
	
	protected UserDao userDao;
	protected UniversityDao universityDao;
	protected TenantDao tenantDao;
	
	@Override
	public void init() throws ServletException {
		userDao = UserDao.getInstance();
		universityDao = UniversityDao.getInstance();
		tenantDao = TenantDao.getInstance();
	}
	
	@Override
	public void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		// Map for storing messages.
        Map<String, String> messages = new HashMap<String, String>();
        req.setAttribute("messages", messages);
        //Just render the JSP.   
        req.getRequestDispatcher("/TenantCreate.jsp").forward(req, resp);
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
        	String universityName = req.getParameter("universityName");
	        String major = req.getParameter("major");
	        String gender = req.getParameter("gender");
	        String bio = req.getParameter("bio");
	        	
	        University university = null;
			try {
				//System.out.println("checkpoint1: " + universityName);
				university = universityDao.getUniversityByName(universityName);
//				if (university == null) {
//					System.out.println("checkpoint2: null");
//				}
			} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
	        if (university == null) {
	        	messages.put("success", "Please provide a valid univeristy name.");
	        	req.getRequestDispatcher("/TenantCreate.jsp").forward(req, resp);
	        	return;
	        }
	        	
	        if (!gender.equals("Male") && !gender.equals("Female")) {
	        	messages.put("success", "Please provide a valid gender.");
	        	req.getRequestDispatcher("/TenantCreate.jsp").forward(req, resp);
	        	return;
	        }
	        Tenant.Gender genderEnum = Tenant.Gender.valueOf(gender);
	        	
	        // create tenant
			try {
			    Tenant tenant = new Tenant(firstName, lastName, email, university, major, genderEnum, bio);
			    tenant = tenantDao.create(tenant);
			    int UserID = tenant.getUserId();
		        messages.put("success", "Successfully created tenant: " + firstName +  
		        		" " + lastName + " UserID is: " + UserID + ". Please remember UserID for use of deletion.");
			}
			catch (SQLException e) {
				e.printStackTrace();
				throw new IOException(e);
		    }
        }
        
        req.getRequestDispatcher("/TenantCreate.jsp").forward(req, resp);
    }
}


