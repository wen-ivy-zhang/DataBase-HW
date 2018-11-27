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
import model.Nest;
import model.Room;
import model.RoomReservation;
import model.Tenant;

public class RoomReservationDao {
	protected ConnectionManager connectionManager;

	private static RoomReservationDao instance = null;
	protected RoomReservationDao() {
		connectionManager = new ConnectionManager();
	}
	public static RoomReservationDao getInstance() {
		if(instance == null) {
			instance = new RoomReservationDao();
		}
		return instance;
	}
	
	public RoomReservation create(RoomReservation reservation) throws SQLException {
		String insertReservation=
			"INSERT INTO RoomReservation(RoomId,TenantId,ReservationDateTime,NestId,OfferedPrice,Contact,IsCancelled) " +
			"VALUES(?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertReservation,
				Statement.RETURN_GENERATED_KEYS);
			
			insertStmt.setInt(1, reservation.getRoom().getRoomId());
			insertStmt.setInt(2, reservation.getTenant().getUserId());
			insertStmt.setTimestamp(3, new Timestamp(reservation.getReservationDateTime().getTime()));
			insertStmt.setInt(4, reservation.getNest().getNestId());
			insertStmt.setDouble(5, reservation.getOfferedPrice());
			insertStmt.setString(6, reservation.getContact());
			insertStmt.setInt(7, reservation.getIsCancelled());
			//insertStmt.setTimestamp(8, new Timestamp(reservation.getLastModifiedDateTime().getTime()));
			
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int reservationId  = -1;
			if(resultKey.next()) {
				reservationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			reservation.setReservationId(reservationId);
			return reservation;
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
	public RoomReservation create(int tenantId, int nestId, int roomId) throws SQLException {
		String insertReservation=
			"INSERT INTO RoomReservation(RoomId,TenantId,ReservationDateTime,NestId,OfferedPrice,Contact,IsCancelled,LastModifiedDateTime) " +
			"VALUES(?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		RoomDao roomDao = RoomDao.getInstance();
		NestDao nestDao = NestDao.getInstance();
		TenantDao tenantDao = TenantDao.getInstance();
	try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertReservation,
				Statement.RETURN_GENERATED_KEYS);
			
			insertStmt.setInt(1, roomId);
			insertStmt.setInt(2, tenantId);
			insertStmt.setTimestamp(3, null);
			insertStmt.setInt(4, nestId);
			insertStmt.setDouble(5, 0);
			insertStmt.setString(6, null);
			insertStmt.setInt(7, 1);
			insertStmt.setTimestamp(8, null);
			
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int reservationId  = -1;
			if(resultKey.next()) {
				reservationId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			Room room = roomDao.getRoomByRoomId(roomId);
			Tenant tenant = tenantDao.getTenantByUserId(tenantId);
			Nest nest = nestDao.getNestByNestId(nestId);
			RoomReservation reservation = new RoomReservation(room,tenant,nest);
			reservation.setReservationId(reservationId);
			return reservation;
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
	public List<RoomReservation> getReservationsFromRoomId(int roomId) throws SQLException {
		List<RoomReservation> reservations = new ArrayList<>();
		String seletctReservation=
			"SElECT ReservationId, RoomId,TenantId,ReservationDateTime,NestId,OfferedPrice,Contact,IsCancelled,LastModifiedDateTime From RoomReservation  " +
			"WHERE RoomId = ? ;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
	
				try {
					connection = connectionManager.getConnection();
					selectStmt = connection.prepareStatement(seletctReservation);
					selectStmt.setInt(1, roomId);
					results = selectStmt.executeQuery();
					
					RoomDao roomDao = RoomDao.getInstance();
					TenantDao tenantDao = TenantDao.getInstance();
					NestDao nestDao = NestDao.getInstance()
;					while(results.next()) {
						int reservationId = results.getInt("ReservationId");
						int roomIdRe = results.getInt("RoomId");
						int tenantId = results.getInt("TenantId");
						Date reservationDateTime = new Date(results.getTimestamp("ReservationDateTime").getTime());
						int nestId = results.getInt("NestId");
						double price = results.getDouble("OfferedPrice");
						String contact = results.getString("Contact");
						int isCancled = results.getInt("IsCancelled");
						Date lastModifiedDate = new Date(results.getTimestamp("LastModifiedDateTime").getTime()); 
						
						Room room = roomDao.getRoomByRoomId(roomIdRe);
						Tenant tenant = tenantDao.getTenantByUserId(tenantId);
						Nest nest = nestDao.getNestByNestId(nestId);
						
						RoomReservation reservation = new RoomReservation(reservationId,room, tenant, reservationDateTime,nest,price,contact,isCancled,lastModifiedDate);
						reservations.add(reservation);
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
				return reservations;
			}
	
	public List<RoomReservation> getReservationsFromNestId(int nestId) throws SQLException {
		List<RoomReservation> reservations = new ArrayList<>();
		String seletctReservation=
			"SElECT ReservationId, RoomId,TenantId,ReservationDateTime,NestId,OfferedPrice,Contact,IsCancelled,LastModifiedDateTime From RoomReservation  " +
			"WHERE nestId = ? ;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
	
				try {
					connection = connectionManager.getConnection();
					selectStmt = connection.prepareStatement(seletctReservation);
					selectStmt.setInt(1, nestId);
					results = selectStmt.executeQuery();
					
					RoomDao roomDao = RoomDao.getInstance();
					TenantDao tenantDao = TenantDao.getInstance();
					NestDao nestDao = NestDao.getInstance()
;					while(results.next()) {
						int reservationId = results.getInt("ReservationId");
						int roomId = results.getInt("RoomId");
						int tenantId = results.getInt("TenantId");
						Date reservationDateTime = new Date(results.getTimestamp("ReservationDateTime").getTime());
						int nestIdRe = results.getInt("NestId");
						double price = results.getDouble("OfferedPrice");
						String contact = results.getString("Contact");
						int isCancled = results.getInt("IsCancelled");
						Date lastModifiedDate = new Date(results.getTimestamp("LastModifiedDateTime").getTime()); 
						
						Room room = roomDao.getRoomByRoomId(roomId);
						Tenant tenant = tenantDao.getTenantByUserId(tenantId);
						Nest nest = nestDao.getNestByNestId(nestIdRe);
						
						RoomReservation reservation = new RoomReservation(reservationId,room, tenant, reservationDateTime,nest,price,contact,isCancled,lastModifiedDate);
						reservations.add(reservation);
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
				return reservations;
			}



}
