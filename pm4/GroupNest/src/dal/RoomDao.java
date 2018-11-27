package dal;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ArrayList;

import java.util.List;
import model.*;



public class RoomDao {
	protected ConnectionManager connectionManager;

	private static RoomDao instance = null;
	protected RoomDao() {
		connectionManager = new ConnectionManager();
	}
	public static RoomDao getInstance() {
		if(instance == null) {
			instance = new RoomDao();
		}
		return instance;
	}
	
	/*private int roomId;
	private Apartment apartment;
	private int sqrt;
	private RoomType roomType;
	private boolean shareBathroom;
	private FloorType floorType;
	private String description;
	*/
	public Room create(Room room) throws SQLException {
		String insertRoom =
			"INSERT INTO Room(ListingId,Sqrt,RoomType,ShareBathroom,FloorType, Description) " +
			"VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertRoom,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, room.getListing().getListingId());
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setInt(2, room.getSqrt());
			insertStmt.setString(3, room.getRoomType().name());
			insertStmt.setBoolean(4, room.isShareBathroom());
			insertStmt.setString(5, room.getFloorType().name());
			insertStmt.setString(6, room.getDescription());
		
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int roomId = -1;
			if(resultKey.next()) {
				roomId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			room.setRoomId(roomId);
			return room;
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
	
	
	public Room getRoomByRoomId(int roomId) throws SQLException{
		String selectRoom =
				"SELECT RoomId, ListingId,Sqrt,RoomType,ShareBathroom,FloorType, Description " +
						"FROM Room " +
						"WHERE RoomId=?;";;
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRoom);
			selectStmt.setInt(1, roomId);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			ApartmentListingDao listingDao = ApartmentListingDao.getInstance();
			
			while(results.next()) {
				int roomIdRe = results.getInt("RoomId");
				int listingId = results.getInt("ListingId");
				int sqrt = results.getInt("Sqrt");
				Room.RoomType roomType = Room.RoomType.valueOf(results.getString("RoomType"));
				Room.FloorType floorType = Room.FloorType.valueOf(results.getString("FloorType"));
				boolean shareBathroom = results.getBoolean("ShareBathroom");
				String description = results.getString("Description");
				ApartmentListing listing = listingDao.getApartmentListingFromApartmentListingId(listingId);
				Room room = new Room(roomIdRe,listing, sqrt, roomType,shareBathroom ,floorType,description);
				return room;
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
	public List<Room> getRoomsByApartmentListing(int listingId) throws SQLException{
		List<Room> rooms = new ArrayList<>();
		String selectRooms =
				"SELECT RoomId, ListingId,Sqrt,RoomType,ShareBathroom,FloorType, Description " +
				"FROM Room " +
				"WHERE ListingId =? ; ";
			Connection connection = null;
			PreparedStatement selectStmt = null;
			ResultSet results = null;
			try {
				connection = connectionManager.getConnection();
				selectStmt = connection.prepareStatement(selectRooms);
				selectStmt.setInt(1, listingId);
				results = selectStmt.executeQuery();
				
				ApartmentListingDao listingDao = ApartmentListingDao.getInstance();
				
				while(results.next()) {
					int roomId = results.getInt("RoomId");
					int listingIdRe = results.getInt("ListingId");
					int sqrt = results.getInt("Sqrt");
					Room.RoomType roomType = Room.RoomType.valueOf(results.getString("RoomType"));
					Room.FloorType floorType = Room.FloorType.valueOf(results.getString("FloorType"));
					boolean shareBathroom = results.getBoolean("ShareBathroom");
					String description = results.getString("Description");
					ApartmentListing listing = listingDao.getApartmentListingFromApartmentListingId(listingIdRe);
					Room room = new Room(roomId,listing, sqrt, roomType,shareBathroom ,floorType,description);
					rooms.add(room);
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
			return rooms;
	}

}
