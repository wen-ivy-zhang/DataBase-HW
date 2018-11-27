package model;

import java.util.Date;

/*
NestId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ListingId INT UNSIGNED NOT NULL,
CreatedBy INT UNSIGNED,
CreationDateTime TIMESTAMP,# NOT NULL, # for first time creation, later modification not changing this
value
IsDeleted TINYINT(1) NOT NULL, # if deleted, all room reservations are deleted. if no nest is created under a
listing, a new nest has to be created in order to put reservations.
IsAcceptedByLandlord TINYINT(1) NOT NULL, # multiple nests under one listing is possible, the full nest
has the highest possibility to be accepted by the landlord
LastModifiedDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
*/

public class Nest {
	private int nestId;
	private ApartmentListing apartmentListing;
	private Tenant tenant;
	private Date creationDateTime;
	private int  isDeleted;
	private int isAcceptedByLandLord;
	private Date lastModifiedDateTime;
	
	public Nest(int nestId, ApartmentListing apartmentListing, Tenant tenant,Date creationDateTime, int isDeleted, int isAcceptedByLandLord, Date lastModifiedDateTime) {
		this.apartmentListing = apartmentListing;
		this.creationDateTime = creationDateTime;
		this.isAcceptedByLandLord = isAcceptedByLandLord;
		this.isDeleted = isDeleted;
		this.lastModifiedDateTime= lastModifiedDateTime;
		this.nestId = nestId;
		this.tenant = tenant;
	}
	public Nest( ApartmentListing apartmentListing, Tenant tenant,Date creationDateTime, int isDeleted, int isAcceptedByLandLord, Date lastModifiedDateTime) {
		this.apartmentListing = apartmentListing;
		this.creationDateTime = creationDateTime;
		this.isAcceptedByLandLord = isAcceptedByLandLord;
		this.isDeleted = isDeleted;
		this.lastModifiedDateTime= lastModifiedDateTime;
		this.tenant = tenant;
	}
	
	public Nest(int nestId, ApartmentListing apartmentListing, Tenant tenant ) {
		this.nestId = nestId;
		this.apartmentListing = apartmentListing;
		this.tenant = tenant;
	}
	
	public Nest(int nestId) {
		this.nestId = nestId;
	}
	public int getNestId() {
		return nestId;
	}
	public void setNestId(int nestId) {
		this.nestId = nestId;
	}
	public ApartmentListing getApartmentListing() {
		return apartmentListing;
	}
	public void setApartmentListing(ApartmentListing apartmentListing) {
		this.apartmentListing = apartmentListing;
	}
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenant(Tenant tenant) {
		this.tenant = tenant;
	}
	public Date getCreationDateTime() {
		return creationDateTime;
	}
	public void setCreationDateTime(Date creationDateTime) {
		this.creationDateTime = creationDateTime;
	}
	public int getIsDeleted() {
		return isDeleted;
	}
	public void setIsDeleted(int isDeleted) {
		this.isDeleted = isDeleted;
	}
	public int getIsAcceptedByLandLord() {
		return isAcceptedByLandLord;
	}
	public void setIsAcceptedByLandLord(int isAcceptedByLandLord) {
		this.isAcceptedByLandLord = isAcceptedByLandLord;
	}
	public Date getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}
	public void setLastModifiedDateTime(Date lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((apartmentListing == null) ? 0 : apartmentListing.hashCode());
		result = prime * result + ((creationDateTime == null) ? 0 : creationDateTime.hashCode());
		result = prime * result + isAcceptedByLandLord;
		result = prime * result + isDeleted;
		result = prime * result + ((lastModifiedDateTime == null) ? 0 : lastModifiedDateTime.hashCode());
		result = prime * result + nestId;
		result = prime * result + ((tenant == null) ? 0 : tenant.hashCode());
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
		Nest other = (Nest) obj;
		if (apartmentListing == null) {
			if (other.apartmentListing != null)
				return false;
		} else if (!apartmentListing.equals(other.apartmentListing))
			return false;
		if (creationDateTime == null) {
			if (other.creationDateTime != null)
				return false;
		} else if (!creationDateTime.equals(other.creationDateTime))
			return false;
		if (isAcceptedByLandLord != other.isAcceptedByLandLord)
			return false;
		if (isDeleted != other.isDeleted)
			return false;
		if (lastModifiedDateTime == null) {
			if (other.lastModifiedDateTime != null)
				return false;
		} else if (!lastModifiedDateTime.equals(other.lastModifiedDateTime))
			return false;
		if (nestId != other.nestId)
			return false;
		if (tenant == null) {
			if (other.tenant != null)
				return false;
		} else if (!tenant.equals(other.tenant))
			return false;
		return true;
	}
	
	

}
