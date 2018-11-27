package model;
/*RoomId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ApartmentId INT UNSIGNED NOT NULL,
Sqrt INT UNSIGNED,
RoomType ENUM('Master Bedroom', 'Guest Bedroom', 'Other') NOT NULL,
ShareBathroom BOOLEAN,
FloorType ENUM('Hardwood', 'Laminate', 'Carpet', 'Other'),
Description TEXT,
*/

public class Room {
	private int roomId;
	private ApartmentListing listing;
	private int sqrt;
	private RoomType roomType;
	private boolean shareBathroom;
	private FloorType floorType;
	private String description;

	public enum RoomType {
		Master, Guest, Other;
	}

	public enum FloorType {
		Hardwood, Laminate, Carpet, Other;
	}

	public Room(int roomId, ApartmentListing listing, int sqrt, RoomType roomType, boolean shareBathroom,
			FloorType floorType, String description) {
		this.listing = listing;
		this.description = description;
		this.floorType = floorType;
		this.roomId = roomId;
		this.roomType = roomType;
		this.shareBathroom = shareBathroom;
		this.sqrt = sqrt;
	}

	public Room(ApartmentListing listing, int sqrt, RoomType roomType, boolean shareBathroom, FloorType floorType,
			String description) {
		this.listing = listing;
		this.description = description;
		this.floorType = floorType;
		this.roomType = roomType;
		this.shareBathroom = shareBathroom;
		this.sqrt = sqrt;
	}

	public Room(int roomId) {
		this.roomId = roomId;
	}

	public int getRoomId() {
		return roomId;
	}

	public void setRoomId(int roomId) {
		this.roomId = roomId;
	}

	public ApartmentListing getListing() {
		return listing;
	}

	public void setListing(ApartmentListing listing) {
		this.listing = listing;
	}

	public int getSqrt() {
		return sqrt;
	}

	public void setSqrt(int sqrt) {
		this.sqrt = sqrt;
	}

	public RoomType getRoomType() {
		return roomType;
	}

	public void setRoomType(RoomType roomType) {
		this.roomType = roomType;
	}

	public String getShareBathroom() {
		if (shareBathroom) {
			return "Yes";
		}
		return "No";
	}

	public boolean isShareBathroom() {
		return shareBathroom;
	}

	public void setShareBathroom(boolean shareBathroom) {
		this.shareBathroom = shareBathroom;
	}

	public FloorType getFloorType() {
		return floorType;
	}

	public void setFloorType(FloorType floorType) {
		this.floorType = floorType;
	}

	public String getDescription() {
		return description;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((description == null) ? 0 : description.hashCode());
		result = prime * result + ((floorType == null) ? 0 : floorType.hashCode());
		result = prime * result + ((listing == null) ? 0 : listing.hashCode());
		result = prime * result + roomId;
		result = prime * result + ((roomType == null) ? 0 : roomType.hashCode());
		result = prime * result + (shareBathroom ? 1231 : 1237);
		result = prime * result + sqrt;
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
		Room other = (Room) obj;
		if (description == null) {
			if (other.description != null)
				return false;
		} else if (!description.equals(other.description))
			return false;
		if (floorType != other.floorType)
			return false;
		if (listing == null) {
			if (other.listing != null)
				return false;
		} else if (!listing.equals(other.listing))
			return false;
		if (roomId != other.roomId)
			return false;
		if (roomType != other.roomType)
			return false;
		if (shareBathroom != other.shareBathroom)
			return false;
		if (sqrt != other.sqrt)
			return false;
		return true;
	}

	
}
