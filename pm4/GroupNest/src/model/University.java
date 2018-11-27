package model;

public class University {
	
	protected int UniversityId;
	protected String Name;
	protected String Address;
	protected String City;
	protected String State;
	protected String Zip;
	
	public University(int universityId, String name, String address, String city, String state, String zip) {
		super();
		UniversityId = universityId;
		Name = name;
		Address = address;
		City = city;
		State = state;
		Zip = zip;
	}

	public University(int universityId) {
		super();
		UniversityId = universityId;
	}
	
	

	public University(String name, String address, String city, String state, String zip) {
		super();
		Name = name;
		Address = address;
		City = city;
		State = state;
		Zip = zip;
	}

	public int getUniversityId() {
		return UniversityId;
	}

	public void setUniversityId(int universityId) {
		UniversityId = universityId;
	}

	public String getName() {
		return Name;
	}

	public void setName(String name) {
		Name = name;
	}

	public String getAddress() {
		return Address;
	}

	public void setAddress(String address) {
		Address = address;
	}

	public String getCity() {
		return City;
	}

	public void setCity(String city) {
		City = city;
	}

	public String getState() {
		return State;
	}

	public void setState(String state) {
		State = state;
	}

	public String getZip() {
		return Zip;
	}

	public void setZip(String zip) {
		Zip = zip;
	}
	
	
	

}
