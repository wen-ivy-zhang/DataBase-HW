package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;


import model.*;

public class LandlordDao extends UserDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static LandlordDao instance = null;
	protected LandlordDao() {
		connectionManager = new ConnectionManager();
	}
	public static LandlordDao getInstance() {
		if(instance == null) {
			instance = new LandlordDao();
		}
		return instance;
	}
	
	public Landlord create(Landlord landlord) throws SQLException {
		// Insert into the superclass table first.
		User user = create(landlord.getFirstName(), landlord.getLastName(), landlord.getEmail());
		
		String insertLandlord = "INSERT INTO Landlord(UserId) VALUES(?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertLandlord);
			insertStmt.setInt(1, user.getUserId());
			
			insertStmt.executeUpdate();
			
			landlord.setUserId(user.getUserId());
			
			return landlord;
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
		}
	}
	
	public Landlord getLandlordFromId(int landlordId) throws SQLException {
		String selectLandlord = "SELECT* FROM Landlord INNER JOIN User ON Landlord.UserId = User.UserId WHERE Landlord.UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectLandlord);
			selectStmt.setInt(1, landlordId);
			
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int userId = results.getInt("UserId");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				
				Landlord landlord = new Landlord(userId, firstName, lastName, email);
				//Landlord landlord = new Landlord(userId);
				return landlord;
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

}
