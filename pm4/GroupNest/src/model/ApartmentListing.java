package model;

import java.util.Date;

/*
 * CREATE TABLE ApartmentListing (
ListingId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ApartmentId INT UNSIGNED NOT NULL,
Title VARCHAR(255), #NOT NULL,
AskingPrice DECIMAL(13,2) NOT NULL,
MoveInDate DATE NOT NULL,
LeaseTermInDays INT,# UNSIGNED NOT NULL,
Content TEXT,# NOT NULL,
Contact VARCHAR(255),# NOT NULL, # required, either phone or email
IsClosed BOOLEAN NOT NULL, # if closed, not shown to public, not available for lease
PostedBy INT UNSIGNED, #NOT NULL,
PostedDateTime TIMESTAMP,# NOT NULL, # for first time listing, later modification on the listing not
changing this value
LastModifiedDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT pk_Listing_ListingId
PRIMARY KEY(ListingId),
CONSTRAINT fk_Listing_Apartment_ApartmentId
FOREIGN KEY (ApartmentId)
REFERENCES Apartment (ApartmentId)
ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_Listing_Landlord_PostedBy
FOREIGN KEY (PostedBy)
REFERENCES Landlord (UserId)
ON UPDATE CASCADE ON DELETE CASCADE # if user is deleted, listings are deleted
);
 */

public class ApartmentListing {
	private int listingId;
	private Apartment apartment;
	private String title;
	private double askingPrice;
	private Date moveInDate;
	private int leaseTermInDays;
	private String content;
	private String contact;
	private boolean isClosed;
	private Landlord owner;
	private Date postedDateTime;
	private Date lastModifiedDateTime;
	public ApartmentListing(int listingId, Apartment apartment, String title, double askingPrice,Date moveInDate,int leaseTermInDays,String content, String contact,boolean isClosed, Landlord owner,Date postedDateTime, Date lastModifiedDateTime ) {
		this.apartment = apartment;
		this.askingPrice = askingPrice;
		this.contact = contact;
		this.content = content;
		this.isClosed = isClosed;
		this.lastModifiedDateTime = lastModifiedDateTime;
		this.leaseTermInDays = leaseTermInDays;
		this.listingId = listingId;
		this.moveInDate = moveInDate;
		this.owner = owner;
	}
	public ApartmentListing(Apartment apartment, String title, double askingPrice,Date moveInDate,int leaseTermInDays,String content, String contact,boolean isClosed, Landlord owner,Date postedDateTime, Date lastModifiedDateTime ) {
		this.apartment = apartment;
		this.askingPrice = askingPrice;
		this.contact = contact;
		this.content = content;
		this.isClosed = isClosed;
		this.lastModifiedDateTime = lastModifiedDateTime;
		this.leaseTermInDays = leaseTermInDays;
		this.moveInDate = moveInDate;
		this.owner = owner;
	}
	
	public ApartmentListing(int listingId) {
		this.listingId = listingId;
	}
	public int getListingId() {
		return listingId;
	}
	public void setListingId(int listingId) {
		this.listingId = listingId;
	}
	public Apartment getApartment() {
		return apartment;
	}
	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}
	public String getTitle() {
		return title;
	}
	public void setTitle(String title) {
		this.title = title;
	}
	public double getAskingPrice() {
		return askingPrice;
	}
	public void setAskingPrice(double askingPrice) {
		this.askingPrice = askingPrice;
	}
	public Date getMoveInDate() {
		return moveInDate;
	}
	public void setMoveInDate(Date moveInDate) {
		this.moveInDate = moveInDate;
	}
	public int getLeaseTermInDays() {
		return leaseTermInDays;
	}
	public void setLeaseTermInDays(int leaseTermInDays) {
		this.leaseTermInDays = leaseTermInDays;
	}
	public String getContent() {
		return content;
	}
	public void setContent(String content) {
		this.content = content;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	public boolean isClosed() {
		return isClosed;
	}
	public void setClosed(boolean isClosed) {
		this.isClosed = isClosed;
	}
	public Landlord getOwner() {
		return owner;
	}
	public void setOwner(Landlord owner) {
		this.owner = owner;
	}
	public Date getPostedDateTime() {
		return postedDateTime;
	}
	public void setPostedDateTime(Date postedDateTime) {
		this.postedDateTime = postedDateTime;
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
		result = prime * result + ((apartment == null) ? 0 : apartment.hashCode());
		long temp;
		temp = Double.doubleToLongBits(askingPrice);
		result = prime * result + (int) (temp ^ (temp >>> 32));
		result = prime * result + ((contact == null) ? 0 : contact.hashCode());
		result = prime * result + ((content == null) ? 0 : content.hashCode());
		result = prime * result + (isClosed ? 1231 : 1237);
		result = prime * result + ((lastModifiedDateTime == null) ? 0 : lastModifiedDateTime.hashCode());
		result = prime * result + leaseTermInDays;
		result = prime * result + listingId;
		result = prime * result + ((moveInDate == null) ? 0 : moveInDate.hashCode());
		result = prime * result + ((owner == null) ? 0 : owner.hashCode());
		result = prime * result + ((postedDateTime == null) ? 0 : postedDateTime.hashCode());
		result = prime * result + ((title == null) ? 0 : title.hashCode());
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
		ApartmentListing other = (ApartmentListing) obj;
		if (apartment == null) {
			if (other.apartment != null)
				return false;
		} else if (!apartment.equals(other.apartment))
			return false;
		if (Double.doubleToLongBits(askingPrice) != Double.doubleToLongBits(other.askingPrice))
			return false;
		if (contact == null) {
			if (other.contact != null)
				return false;
		} else if (!contact.equals(other.contact))
			return false;
		if (content == null) {
			if (other.content != null)
				return false;
		} else if (!content.equals(other.content))
			return false;
		if (isClosed != other.isClosed)
			return false;
		if (lastModifiedDateTime == null) {
			if (other.lastModifiedDateTime != null)
				return false;
		} else if (!lastModifiedDateTime.equals(other.lastModifiedDateTime))
			return false;
		if (leaseTermInDays != other.leaseTermInDays)
			return false;
		if (listingId != other.listingId)
			return false;
		if (moveInDate == null) {
			if (other.moveInDate != null)
				return false;
		} else if (!moveInDate.equals(other.moveInDate))
			return false;
		if (owner == null) {
			if (other.owner != null)
				return false;
		} else if (!owner.equals(other.owner))
			return false;
		if (postedDateTime == null) {
			if (other.postedDateTime != null)
				return false;
		} else if (!postedDateTime.equals(other.postedDateTime))
			return false;
		if (title == null) {
			if (other.title != null)
				return false;
		} else if (!title.equals(other.title))
			return false;
		return true;
	}
	
	

	
}
