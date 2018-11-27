package model;

public class Apartment {
	
	protected int ApartmentId;
	protected FloorPlan FloorPlan;
	protected String Address;
	protected String City;
	protected String State;
	protected String Zip;
	protected int Sqft;
	protected String Name;
	protected String Description;
	protected Landlord Owner;
	
	

	public Apartment(int apartmentId, model.FloorPlan floorPlan, String address, String city, String state, String zip,
			int sqft, String name, String description, Landlord owner) {
		super();
		ApartmentId = apartmentId;
		FloorPlan = floorPlan;
		Address = address;
		City = city;
		State = state;
		Zip = zip;
		Sqft = sqft;
		Name = name;
		Description = description;
		Owner = owner;
	}



	public Apartment(int apartmentId) {
		super();
		ApartmentId = apartmentId;
	}
	
	

	

	public Apartment(model.FloorPlan floorPlan, String address, String city, String state, String zip, int sqft,
			String name, String description, Landlord owner) {
		super();
		FloorPlan = floorPlan;
		Address = address;
		City = city;
		State = state;
		Zip = zip;
		Sqft = sqft;
		Name = name;
		Description = description;
		Owner = owner;
	}



	public int getApartmentId() {
		return ApartmentId;
	}



	public void setApartmentId(int apartmentId) {
		ApartmentId = apartmentId;
	}



	public FloorPlan getFloorPlan() {
		return FloorPlan;
	}



	public void setFloorPlan(FloorPlan floorPlan) {
		FloorPlan = floorPlan;
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



	public int getSqft() {
		return Sqft;
	}



	public void setSqft(int sqft) {
		Sqft = sqft;
	}



	public String getName() {
		return Name;
	}



	public void setName(String name) {
		Name = name;
	}



	public String getDescription() {
		return Description;
	}



	public void setDescription(String description) {
		Description = description;
	}



	public Landlord getOwner() {
		return Owner;
	}



	public void setOwner(Landlord owner) {
		Owner = owner;
	}



	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((Address == null) ? 0 : Address.hashCode());
		result = prime * result + ApartmentId;
		result = prime * result + ((City == null) ? 0 : City.hashCode());
		result = prime * result + ((Description == null) ? 0 : Description.hashCode());
		result = prime * result + ((FloorPlan == null) ? 0 : FloorPlan.hashCode());
		result = prime * result + ((Name == null) ? 0 : Name.hashCode());
		result = prime * result + ((Owner == null) ? 0 : Owner.hashCode());
		result = prime * result + Sqft;
		result = prime * result + ((State == null) ? 0 : State.hashCode());
		result = prime * result + ((Zip == null) ? 0 : Zip.hashCode());
		return result;
	}



	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		Apartment other = (Apartment) obj;
		if (Address == null) {
			if (other.Address != null)
				return false;
		} else if (!Address.equals(other.Address))
			return false;
		if (ApartmentId != other.ApartmentId)
			return false;
		if (City == null) {
			if (other.City != null)
				return false;
		} else if (!City.equals(other.City))
			return false;
		if (Description == null) {
			if (other.Description != null)
				return false;
		} else if (!Description.equals(other.Description))
			return false;
		if (FloorPlan == null) {
			if (other.FloorPlan != null)
				return false;
		} else if (!FloorPlan.equals(other.FloorPlan))
			return false;
		if (Name == null) {
			if (other.Name != null)
				return false;
		} else if (!Name.equals(other.Name))
			return false;
		if (Owner == null) {
			if (other.Owner != null)
				return false;
		} else if (!Owner.equals(other.Owner))
			return false;
		if (Sqft != other.Sqft)
			return false;
		if (State == null) {
			if (other.State != null)
				return false;
		} else if (!State.equals(other.State))
			return false;
		if (Zip == null) {
			if (other.Zip != null)
				return false;
		} else if (!Zip.equals(other.Zip))
			return false;
		return true;
	}


	

	
}
