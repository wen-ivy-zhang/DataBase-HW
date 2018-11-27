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
import java.sql.Types;

import model.*;

public class NestDao {
	protected ConnectionManager connectionManager;

	private static NestDao instance = null;
	protected NestDao() {
		connectionManager = new ConnectionManager();
	}
	public static NestDao getInstance() {
		if(instance == null) {
			instance = new NestDao();
		}
		return instance;
	}
	/*
	private int nestId;
	private ApartmentListing apartmentListing;
	private Tenant tenant;
	private Date creationDateTime;
	private int  isDeleted;
	private int isAcceptedByLandLord;
	private Date lastModifiedDateTime;
	*/
	public Nest create(Nest nest) throws SQLException {
		String insertNest =
			"INSERT INTO Nest(ListingId,CreatedBy,CreationDateTime,IsDeleted,IsAcceptedByLandLord,LastModifiedDateTime) " +
			"VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertNest,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, nest.getApartmentListing().getListingId());
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setInt(2, nest.getTenant().getUserId());
			insertStmt.setTimestamp(3, new Timestamp(nest.getCreationDateTime().getTime()));
			insertStmt.setInt(4, nest.getIsDeleted());
			insertStmt.setInt(5, nest.getIsAcceptedByLandLord());
			insertStmt.setTimestamp(6, new Timestamp(nest.getLastModifiedDateTime().getTime()));
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int nestId = -1;
			if(resultKey.next()) {
				nestId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			nest.setNestId(nestId);
			return nest;
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
	
	
	public Nest create(int listingId, int userId) throws SQLException {
		String insertNest = "INSERT INTO Nest(ListingId,CreatedBy,CreationDateTime,IsDeleted,IsAcceptedByLandLord,LastModifiedDateTime) "
				+ "VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertNest, Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, listingId);
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setInt(2, userId);
			insertStmt.setNull(3, Types.TIMESTAMP);
			insertStmt.setInt(4, 0);
			insertStmt.setInt(5, 0);
			insertStmt.setNull(6, Types.TIMESTAMP);
			insertStmt.executeUpdate();
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int nestId = -1;
			if (resultKey.next()) {
				nestId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			TenantDao tenantDao = TenantDao.getInstance();
			Tenant tenant = tenantDao.getTenantByUserId(userId);
			ApartmentListingDao listingDao = ApartmentListingDao.getInstance();
			ApartmentListing listing = listingDao.getApartmentListingFromApartmentListingId(listingId);
			Nest nest = new Nest(nestId, listing, tenant);
			nest.setNestId(nestId);
			return nest;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if (connection != null) {
				connection.close();
			}
			if (insertStmt != null) {
				insertStmt.close();
			}
			if (resultKey != null) {
				resultKey.close();
			}
		}
	}


	
	
	
	
	public Nest getNestByNestId(int nestId) throws SQLException{
		String selectNest =
				"SELECT NestId, ListingId, CreatedBy, CreationDateTime, IsDeleted, IsAcceptedByLandLord, LastModifiedDateTime " +
				"FROM Nest "+
				"WHERE NestId = ? ; ";;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNest);
			selectStmt.setInt(1, nestId);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			ApartmentListingDao apartmentListingDao = ApartmentListingDao.getInstance();
			TenantDao tenantDao = TenantDao.getInstance();
			
			// You can iterate the result set (although the example below only retrieves 
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if(results.next()) {
				int id = results.getInt("NestId");
				int apartmentListingId = results.getInt("ListingId");
				int tenantId = results.getInt("CreatedBy");
				Date creationDateTime = new Date(results.getTimestamp("CreationDateTime").getTime());
				int isDeleted = results.getInt("IsDeleted");
				int isAccepted = results.getInt("IsAcceptedByLandLord");
				Date lastModifiedDate = new Date(results.getTimestamp("LastModifiedDateTime").getTime());
			
				Tenant user = tenantDao.getTenantByUserId(tenantId);
				ApartmentListing apartmentListing = apartmentListingDao.getApartmentListingFromApartmentListingId(apartmentListingId);
				Nest nest = new Nest(id,apartmentListing, user,creationDateTime,isDeleted, isAccepted, lastModifiedDate);
		        return nest;
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
	
	

	public List<Nest> getNestsByRoom(int roomId) throws SQLException{
		//UniversityDao universiytDao = UniversityDao.getInstance();
		//University university = universiytDao.getUniverisytFromUniversityName(universityName);
		List<Nest> nests = new ArrayList<>();
		RoomReservationDao reservationDao = RoomReservationDao.getInstance();
		List<RoomReservation> reservations = reservationDao.getReservationsFromRoomId(roomId);
		for(RoomReservation res:reservations) {
			Nest n = res.getNest();
			nests.add(n);
		}
		return nests;
	}
	
	
	/**
	 * Get a list of all nests by roomId.
	 */
	public List<Nest> getAllNestsByRoomId(int roomId) throws SQLException {
		List<Nest> nestsAll = new ArrayList<>();
		String selectNest =
				"SELECT NestId, RoomId " + 
				"FROM Room INNER JOIN Nest " + 
				"ON Nest.ListingId = Room.ListingId " + 
				"WHERE Nest.IsDeleted = 0 AND RoomId = ?;";
				
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNest);
			selectStmt.setInt(1, roomId);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				int nestId = results.getInt("NestId");
				
				Nest nest = NestDao.getInstance().getNestByNestId(nestId);
				
				nestsAll.add(nest);
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
		return nestsAll;
	}
	
	/**
	 * Get a list of nests with the room with roomId reserved.
	 */
	public List<Nest> getReservedNestsByRoomId(int roomId) throws SQLException {
		List<Nest> nestsReserved = new ArrayList<>();
		String selectNest =
				"SELECT RoomReservation.NestId AS NestId " + 
				"FROM Room INNER JOIN RoomReservation " + 
				"ON Room.RoomId = RoomReservation.RoomId " + 
				"WHERE Room.RoomId = ?;";
				
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectNest);
			selectStmt.setInt(1, roomId);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				int nestId = results.getInt("NestId");
				
				Nest nest = NestDao.getInstance().getNestByNestId(nestId);
				
				nestsReserved.add(nest);
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
		return nestsReserved;
	}
	
	/**
	 * Get a list of available nests with roomId.
	 */
	public List<Nest> getAvailableNestsByRoomId(int roomId) throws SQLException {
		
		List<Nest> nestsAvailable = new ArrayList<>();
		try {
			List<Nest> nestsAll = getAllNestsByRoomId(roomId);
			List<Nest> nestsReserved = getReservedNestsByRoomId(roomId);
			
			for (Nest nest : nestsAll) {
				
				if(!nestsReserved.contains(nest)) {
					
					nestsAvailable.add(nest);
				}
			}
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		}
		
		return nestsAvailable;
		
	}
	
	
	
}
