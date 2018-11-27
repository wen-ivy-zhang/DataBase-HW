package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.*;

public class UserDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static UserDao instance = null;
	protected UserDao() {
		connectionManager = new ConnectionManager();
	}
	public static UserDao getInstance() {
		if(instance == null) {
			instance = new UserDao();
		}
		return instance;
	}
	
	public User create(String firstName, String lastName, String email) throws SQLException {
		
		User user = new User(firstName, lastName, email);
		String insertUser = "INSERT INTO User(FirstName,LastName,Email) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUser, Statement.RETURN_GENERATED_KEYS);
			// PreparedStatement allows us to substitute specific types into the query template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			//insertStmt.setInt(1, user.getUserId());
			insertStmt.setString(1, user.getFirstName());
			insertStmt.setString(2, user.getLastName());
			insertStmt.setString(3, user.getEmail());
			
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int userId = -1;
			if(resultKey.next()) {
				userId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			user.setUserId(userId);
			
			return user;
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
	 * Update the LastName of the User instance.
	 * This runs a UPDATE statement.
	 */
	public User updateLastName(User user, String newLastName) throws SQLException {
		String updateUser = "UPDATE User SET LastName=? WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement updateStmt = null;
		try {
			connection = connectionManager.getConnection();
			updateStmt = connection.prepareStatement(updateUser);
			updateStmt.setString(1, newLastName);
			updateStmt.setInt(2, user.getUserId());
			updateStmt.executeUpdate();
			
			// Update the person param before returning to the caller.
			user.setLastName(newLastName);
			return user;
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
	
	/**
	 * Delete the User instance.
	 * This runs a DELETE statement.
	 */
	public User delete(User user) throws SQLException {
		String deleteUser = "DELETE FROM User WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteUser);
			deleteStmt.setInt(1, user.getUserId());
			deleteStmt.executeUpdate();

			// Return null so the caller can no longer operate on the Persons instance.
			return null;
		} catch (SQLException e) {
			e.printStackTrace();
			throw e;
		} finally {
			if(connection != null) {
				connection.close();
			}
			if(deleteStmt != null) {
				deleteStmt.close();
			}
		}
	}
	
	public User getUserByUserId(int userId) throws SQLException {
		String selectUser = "SELECT UserId,FirstName,LastName, Email FROM User WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setInt(1, userId);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves 
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if(results.next()) {
				int resultUserId = results.getInt("UserId");
				String firstName = results.getString("FirstName");
				String lastName = results.getString("LastName");
				String email = results.getString("Email");
				User user = new User(resultUserId, firstName, lastName, email);
				return user;
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
	  * Update the Email of the User instance.
	  * This runs a UPDATE statement.
	  */
	public User updateEmail(User user, String newEmail) throws SQLException {
		  String updateUser = "UPDATE User SET Email=? WHERE UserId=?;";
		  Connection connection = null;
		  PreparedStatement updateStmt = null;
		  try {
		   connection = connectionManager.getConnection();
		   updateStmt = connection.prepareStatement(updateUser);
		   updateStmt.setString(1, newEmail);
		   updateStmt.setInt(2, user.getUserId());
		   updateStmt.executeUpdate();
		   
		   // Update the person param before returning to the caller.
		   user.setEmail(newEmail);
		   return user;
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
