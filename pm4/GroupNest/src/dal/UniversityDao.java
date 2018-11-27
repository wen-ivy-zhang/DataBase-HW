package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.*;

public class UniversityDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static UniversityDao instance = null;
	protected UniversityDao() {
		connectionManager = new ConnectionManager();
	}
	public static UniversityDao getInstance() {
		if(instance == null) {
			instance = new UniversityDao();
		}
		return instance;
	}
	
	public University create(University university) throws SQLException {
		
		
		String insertUniversity = "INSERT INTO University(Name,Address,City,State,Zip) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUniversity, Statement.RETURN_GENERATED_KEYS);
			// PreparedStatement allows us to substitute specific types into the query template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			//insertStmt.setInt(1, university.getUniversityId());
			insertStmt.setString(1, university.getName());
			insertStmt.setString(2, university.getAddress());
			insertStmt.setString(3, university.getCity());
			insertStmt.setString(4, university.getState());
			insertStmt.setString(5, university.getZip());

			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int universityId = -1;
			if(resultKey.next()) {
				universityId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			university.setUniversityId(universityId);
			
			
			return university;
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
	
	/**
	 * Get the University by name
	 * UniversityId INT UNSIGNED NOT NULL AUTO_INCREMENT,
Name VARCHAR(255) NOT NULL,
Address VARCHAR(255),
City VARCHAR(255),
State VARCHAR(255),
Zip
	 */
	public University getUniversityByName(String universityName) throws SQLException {
		String selectUniversity = "SELECT UniversityId,Name,Address,City,State,Zip FROM University WHERE Name=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUniversity);
			selectStmt.setString(1, universityName);
			
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				int universityId = results.getInt("UniversityId");
				String resultName = results.getString("Name");
				String address = results.getString("Address");
				String city = results.getString("City");
				String state = results.getString("State");
				String zip = results.getString("Zip");
				
				University university = new University(universityId, resultName, address,city,state,zip);
				return university;
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
	
	public University getUniversityByUniversityId(int universityId) throws SQLException {
		String selectUniversity = "SELECT UniversityId,Name,Address,City,State,Zip FROM University WHERE UniversityId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUniversity);
			selectStmt.setInt(1, universityId);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves 
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if(results.next()) {
				int resultUniversityId = results.getInt("UniversityId");
				String name = results.getString("Name");
				String address = results.getString("Address");
				String city = results.getString("City");
				String state = results.getString("State");
				String zip = results.getString("Zip");
				University university = new University(resultUniversityId, name, address, city, state, zip);
				return university;
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

	/**
	  * Update the Name of the University instance.
	  * This runs a UPDATE statement.
	  */
	 public University updateUniverstyName(University university, String newName) throws SQLException {
		  String updateUser = "UPDATE University SET Name=? WHERE UniversityId=?;";
		  Connection connection = null;
		  PreparedStatement updateStmt = null;
		  try {
		   connection = connectionManager.getConnection();
		   updateStmt = connection.prepareStatement(updateUser);
		   updateStmt.setString(1, newName);
		   updateStmt.setInt(2, university.getUniversityId());
		   updateStmt.executeUpdate();
		   
		// Update the person param before returning to the caller.
		   university.setName(newName);
		   return university;
		  } catch (SQLException e) {
		   e.printStackTrace();
		   throw e;
		  } finally {
		   if(connection != null) {
		    connection.close();
		   }
		   if(updateStmt != null) {
		    updateStmt.close();
		   }
		  }
		 }



}
