package model;

public class Landlord extends User{
	
	public Landlord (int userId) {
		
		super(userId);
	}
	
	public Landlord(int userId, String firstName, String lastName, String email) {
		
		super(userId, firstName, lastName, email);
	}
	
	public Landlord(String firstName, String lastName, String email) {
		
		super(firstName, lastName, email);
	}
	
	

}
