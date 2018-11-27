package model;

public class Tenant extends User {
	
	protected University university;
	protected String Major;
	protected Gender gender;
	
	public enum Gender {Male, Female}
	protected String Bio;
	
	
	


	public Tenant(int userId, String firstName, String lastName, String email, University university, String major,
			Gender gender, String bio) {
		super(userId, firstName, lastName, email);
		this.university = university;
		Major = major;
		this.gender = gender;
		Bio = bio;
	}


	public Tenant(int userId) {
		super(userId);
	}




	public Tenant(String firstName, String lastName, String email, University university, String major, Gender gender,
			String bio) {
		super(firstName, lastName, email);
		this.university = university;
		Major = major;
		this.gender = gender;
		Bio = bio;
	}


	public University getUniversity() {
		return university;
	}


	public void setUniversity(University university) {
		this.university = university;
	}


	public String getMajor() {
		return Major;
	}


	public void setMajor(String major) {
		Major = major;
	}


	public Gender getGender() {
		return gender;
	}


	public void setGender(Gender gender) {
		this.gender = gender;
	}


	public String getBio() {
		return Bio;
	}


	public void setBio(String bio) {
		Bio = bio;
	}


	
	

}
