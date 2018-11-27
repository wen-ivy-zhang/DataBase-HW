package model;

import java.util.Date;

public class RoomReservation {
	
	
	
	/*ReservationId INT UNSIGNED NOT NULL AUTO_INCREMENT,
	RoomId INT UNSIGNED NOT NULL,
	TenantId INT UNSIGNED NOT NULL,
	ReservationDateTime TIMESTAMP, # for first time reservation, later modification on the reservation not changing this value
	NestId INT UNSIGNED NOT NULL, 
	OfferedPrice DECIMAL(13,2), # a negotiable price that the tenant is willing to offer. should be lower than the apartment listing price
	Contact VARCHAR(255), # contact for negotiation
	IsCancelled TINYINT(1), # if cancelled, room under the nest can still be reserved by others, but at any time, only one active reservation for one room is allowed
	LastModifiedDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,*/
	
	private int reservationId;
	private Room room;
	private Tenant tenant;
	private Date reservationDateTime;
	private Nest nest;
	private double offeredPrice;
	private String contact;
	private int isCancelled;
	private Date lastModifiedDateTime; 
	
	public RoomReservation(int reservationId, Room room, Tenant tenant, Date reservationDateTime, Nest nest, double offeredPrice, String contact,int isCancled, Date lastModifiedDateTime) {
		this.reservationId = reservationId;
		this.room = room;
		this.tenant = tenant;
		this.nest = nest;
		this.offeredPrice = offeredPrice;
		this.isCancelled = isCancled;
		this.lastModifiedDateTime = lastModifiedDateTime;
		this.reservationDateTime = reservationDateTime;
		this.contact = contact;
	}
	public RoomReservation( Room room, Tenant tenant,Date reservationDateTime, Nest nest, double offeredPrice, String contact,int isCancled, Date lastModifiedDateTime) {
		
		this.room = room;
		this.tenant = tenant;
		this.nest = nest;
		this.offeredPrice = offeredPrice;
		this.isCancelled = isCancled;
		this.reservationDateTime = reservationDateTime;
		this.contact = contact;
		}
	public RoomReservation(int reservationId) {
		this.reservationId = reservationId;
	}
	public RoomReservation(Room room, Tenant tenant, Nest nest) {
		this.room = room;
		this.tenant = tenant;
		this.nest = nest;
	}
	
	public int getReservationId() {
		return reservationId;
	}
	public void setReservationId(int reservationId) {
		this.reservationId = reservationId;
	}
	public Room getRoom() {
		return room;
	}
	public void setRoomId(Room room) {
		this.room = room;
	}
	public Tenant getTenant() {
		return tenant;
	}
	public void setTenantId(Tenant tenant) {
		this.tenant = tenant;
	}
	public Nest getNest() {
		return nest;
	}
	public void setNestId(Nest nest) {
		this.nest = nest;
	}
	public double getOfferedPrice() {
		return offeredPrice;
	}
	public void setOfferedPrice(double offeredPrice) {
		this.offeredPrice = offeredPrice;
	}
	public int getIsCancelled() {
		return isCancelled;
	}
	public void setIsCancelled(int isCancled) {
		this.isCancelled = isCancled;
	}
	public Date getLastModifiedDateTime() {
		return lastModifiedDateTime;
	}
	public void setLastModifiedDateTime(Date lastModifiedDateTime) {
		this.lastModifiedDateTime = lastModifiedDateTime;
	}
	public Date getReservationDateTime() {
		return reservationDateTime;
	}
	public void setReservationDateTime(Date reservationDateTime) {
		this.reservationDateTime = reservationDateTime;
	}
	public String getContact() {
		return contact;
	}
	public void setContact(String contact) {
		this.contact = contact;
	}
	
	
		
  
}
