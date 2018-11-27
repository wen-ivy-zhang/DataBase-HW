package dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.*;


public class ApartmentListingDao {
	protected ConnectionManager connectionManager;

	private static ApartmentListingDao instance = null;
	protected ApartmentListingDao() {
		connectionManager = new ConnectionManager();
	}
	public static ApartmentListingDao getInstance() {
		if(instance == null) {
			instance = new ApartmentListingDao();
		}
		return instance;
	}
	
	/*
	 * 
	 * private int listingId;
	private Apartment apartment;
	private String title;
	private double askingPrice;
	private Date moveInDate;
	private int leaseTermInDays;
	private String content;
	private String contact;
	private int isClosed;
	private LandLord owner;
	private Date postedDateTime;
	private Date lastModifiedDateTime;
	 */
	public ApartmentListing create(ApartmentListing listing) throws SQLException {
		String insertListing =
			"INSERT INTO ApartmentListing(ApartmentId,Title,AskingPrice,MoveInDate,LeaseTermInDays,Content,Contact,IsClosed,PostedBy,LastModifiedDateTime) " +
			"VALUES(?,?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertListing,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, listing.getApartment().getApartmentId());
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setString(2, listing.getTitle());
			insertStmt.setDouble(3, listing.getAskingPrice());
			insertStmt.setTimestamp(4, new Timestamp(listing.getMoveInDate().getTime()));
			insertStmt.setInt(5, listing.getLeaseTermInDays());
			insertStmt.setString(6, listing.getContent());
			insertStmt.setString(7, listing.getContact());
			insertStmt.setBoolean(8, listing.isClosed());
			insertStmt.setInt(9, listing.getOwner().getUserId());
			//insertStmt.setTimestamp(10, new Timestamp(listing.getPostedDateTime().getTime()));
			insertStmt.setTimestamp(10, new Timestamp(listing.getLastModifiedDateTime().getTime()));
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int listingId = -1;
			if(resultKey.next()) {
				listingId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			listing.setListingId(listingId);
			return listing;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(insertStmt != null) {
				insertStmt.close();
			}
			if(resultKey != null) {
				resultKey.close();
			}
		}
	}
	
	public ApartmentListing getApartmentListingFromApartmentListingId(int listingId) throws SQLException{
		String selectApartmentListing =
				"SELECT ListingId, ApartmentId,Title,AskingPrice,MoveInDate,LeaseTermInDays,Content,Contact,IsClosed,PostedBy,PostedDateTime,LastModifiedDateTime " +
				"FROM ApartmentListing " +
				"WHERE ListingId=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectApartmentListing);
				selectStmt.setInt(1, listingId);
				results = selectStmt.executeQuery();
				
				ApartmentDao apartmentDao = ApartmentDao.getInstance();
				LandlordDao landLordDao = LandlordDao.getInstance();
				while(results.next()) {
					int listingIdRe = results.getInt("ListingId");
					int apartmentIdRe = results.getInt("ApartmentId");
					String title = results.getString("Title");
					double price = results.getDouble("AskingPrice");
					Date moveInTime = results.getDate("MoveInDate");
					int leaseTermInDays = results.getInt("LeaseTermInDays");
					String content = results.getString("Content");
					String contact = results.getString("Contact");
					boolean isClosed = results.getBoolean("IsClosed");
					int userId = results.getInt("PostedBy");
					Date postedDate = new Date(results.getTimestamp("PostedDateTime").getTime()); 
					Date lastModifiedDate = new Date(results.getTimestamp("LastModifiedDateTime").getTime()); 
					Landlord user = landLordDao.getLandlordFromId(userId);
					Apartment apartment = apartmentDao.getApartmentByApartmentId(apartmentIdRe);
					ApartmentListing listing = new ApartmentListing(listingIdRe,apartment, title, price,moveInTime,leaseTermInDays,content,contact,isClosed,user,postedDate,lastModifiedDate);
					return listing;
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(selectStmt != null) {
					selectStmt.close();
				}
				if(results != null) {
					results.close();
				}
			}
			return null;
	}
	
    public List<ApartmentListing> getApartmentListingsByApartmentId(int apartmentId) throws SQLException{
    	List<ApartmentListing> listings = new ArrayList<>();
    	
    	String selectApartmentListing =
				"SELECT ListingId, ApartmentId,Title,AskingPrice,MoveInDate,LeaseTermInDays,Content,Contact,IsClosed,PostedBy,PostedDateTime,LastModifiedDateTime " +
				"FROM ApartmentListing " +
				"WHERE ApartmentId=?;";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectApartmentListing);
				selectStmt.setInt(1, apartmentId);
				results = selectStmt.executeQuery();
				
				ApartmentDao apartmentDao = ApartmentDao.getInstance();
				LandlordDao landLordDao = LandlordDao.getInstance();
				while(results.next()) {
					int listingId = results.getInt("ListingId");
					int apartmentIdRe = results.getInt("ApartmentId");
					String title = results.getString("Title");
					double price = results.getDouble("AskingPrice");
					Date moveInTime = results.getDate("MoveInDate");
					int leaseTermInDays = results.getInt("LeaseTermInDays");
					String content = results.getString("Content");
					String contact = results.getString("Contact");
					boolean isClosed = results.getBoolean("IsClosed");
					int userId = results.getInt("PostedBy");
					Date postedDate = new Date(results.getTimestamp("PostedDateTime").getTime()); 
					Date lastModifiedDate = new Date(results.getTimestamp("LastModifiedDateTime").getTime()); 
					Landlord user = landLordDao.getLandlordFromId(userId);
					Apartment apartment = apartmentDao.getApartmentByApartmentId(apartmentIdRe);
					ApartmentListing listing = new ApartmentListing(listingId,apartment, title, price,moveInTime,leaseTermInDays,content,contact,isClosed,user,postedDate,lastModifiedDate);
					listings.add(listing);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(selectStmt != null) {
					selectStmt.close();
				}
				if(results != null) {
					results.close();
				}
			}
			return listings;
		}
    	
    public List<ApartmentListing> getApartmentListingsByUniversityName(String universityName) throws SQLException{
    	List<ApartmentListing> listings = new ArrayList<>();
    	List<Apartment> apartmentList = new ArrayList<>();
    	ApartmentDao apartmentDao = ApartmentDao.getInstance();
    	apartmentList = apartmentDao.getApartmentsByUniversityName(universityName);
    	for(Apartment apartment:apartmentList) {
    		List<ApartmentListing> list = getApartmentListingsByApartmentId(apartment.getApartmentId());
    		listings.addAll(list);
    	}
    	return listings;
    }
    
    public List<ApartmentListing> getApartmentListingsByUniversityName1(String universityName) throws SQLException{
    	UniversityDao universityDao = UniversityDao.getInstance();
    	University university = universityDao.getUniversityByName(universityName);
    	List<ApartmentListing> listings = new ArrayList<>();
    	String selectApartmentListing =
				"SELECT ListingId, ApartmentListing.ApartmentId AS ApartmentId,Title,AskingPrice,MoveInDate,LeaseTermInDays,Content,Contact,IsClosed,PostedBy,PostedDateTime,LastModifiedDateTime " +
				"FROM ApartmentListing INNER JOIN "
				+ "(SELECT ApartmentId FROM Apartment "
				+ " WHERE Apartment.Zip = ? ) AS APART" +
				"ON ApartmentListing.ApartmentId = APART.ApartmentId";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectApartmentListing);
				selectStmt.setString(1, university.getZip());
				results = selectStmt.executeQuery();
				
				ApartmentDao apartmentDao = ApartmentDao.getInstance();
				LandlordDao landLordDao = LandlordDao.getInstance();
				while(results.next()) {
					int listingId = results.getInt("ListingId");
					int apartmentIdRe = results.getInt("ApartmentId");
					String title = results.getString("Title");
					double price = results.getDouble("AskingPrice");
					Date moveInTime = results.getDate("MoveInDate");
					int leaseTermInDays = results.getInt("LeaseTermInDays");
					String content = results.getString("Content");
					String contact = results.getString("Contact");
					boolean isClosed = results.getBoolean("IsClosed");
					int userId = results.getInt("PostedBy");
					Date postedDate = new Date(results.getTimestamp("PostedDateTime").getTime()); 
					Date lastModifiedDate = new Date(results.getTimestamp("LastModifiedDateTime").getTime()); 
					Landlord user = landLordDao.getLandlordFromId(userId);
					Apartment apartment = apartmentDao.getApartmentByApartmentId(apartmentIdRe);
					ApartmentListing listing = new ApartmentListing(listingId,apartment, title, price,moveInTime,leaseTermInDays,content,contact,isClosed,user,postedDate,lastModifiedDate);
					listings.add(listing);
				}
			} catch (SQLException e) {
				e.printStackTrace();
				throw e;
			} finally {
				if(connection != null) {
					connection.close();
				}
				if(selectStmt != null) {
					selectStmt.close();
				}
				if(results != null) {
					results.close();
				}
			}
			return listings;
    }
    
    public ApartmentListing getApartmentListingByRoom(int roomId) throws SQLException{
    	RoomDao roomDao = RoomDao.getInstance(); 	
		Room room = roomDao.getRoomByRoomId(roomId);
		return room.getListing();
    }
}
