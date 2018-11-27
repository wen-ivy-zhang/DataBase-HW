package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class TenantDao extends UserDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static TenantDao instance = null;
	protected TenantDao() {
		connectionManager = new ConnectionManager();
	}
	public static TenantDao getInstance() {
		if(instance == null) {
			instance = new TenantDao();
		}
		return instance;
	}
	
	
	public Tenant create(Tenant tenant) throws SQLException {
		// Insert into the superclass table first.
		User user = create(tenant.getFirstName(), tenant.getLastName(),tenant.getEmail());

		String insertTenant = "INSERT INTO Tenant(UserId,UniversityId,Major,Gender,Bio) VALUES(?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertTenant);
			insertStmt.setInt(1, user.getUserId());
			insertStmt.setInt(2, tenant.getUniversity().getUniversityId());
			insertStmt.setString(3, tenant.getMajor());
			insertStmt.setString(4, tenant.getGender().name());
			insertStmt.setString(5, tenant.getBio());
			
			insertStmt.executeUpdate();
			
			tenant.setUserId(user.getUserId());
			
			return tenant;
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
	 * Update the LastName of the Tenant instance.
	 * This runs a UPDATE statement.
	 */
	public Tenant updateLastName(Tenant tenant, String newLastName) throws SQLException {
		// The field to update only exists in the superclass table, so we can
		// just call the superclass method.
		super.updateLastName(tenant, newLastName);
		return tenant;
	}
	
	/**
	 * Delete the Tenant instance.
	 * This runs a DELETE statement.
	 */
	public Tenant delete(Tenant tenant) throws SQLException {
		String deleteTenant = "DELETE FROM Tenant WHERE UserId=?;";
		Connection connection = null;
		PreparedStatement deleteStmt = null;
		try {
			connection = connectionManager.getConnection();
			deleteStmt = connection.prepareStatement(deleteTenant);
			deleteStmt.setInt(1, tenant.getUserId());
			int affectedRows = deleteStmt.executeUpdate();
			if (affectedRows == 0) {
				throw new SQLException("No records available to delete for UserId=" + tenant.getUserId());
			}

			// Then also delete from the superclass.
			// Notes:
			// 1. Due to the fk constraint (ON DELETE CASCADE), we could simply call
			//    super.delete() without even needing to delete from Administrators first.
			// 2. BlogPosts has a fk constraint on BlogUsers with the reference option
			//    ON DELETE SET NULL. If the BlogPosts fk reference option was instead
			//    ON DELETE RESTRICT, then the caller would need to delete the referencing
			//    BlogPosts before this BlogUser can be deleted.
			//    Example to delete the referencing BlogPosts:
			//    List<BlogPosts> posts = BlogPostsDao.getBlogPostsForUser(blogUser.getUserName());
			//    for(BlogPosts p : posts) BlogPostsDao.delete(p);
			super.delete(tenant);

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
	
	public Tenant getTenantByUserId(int userId) throws SQLException {
		String selectTenant = 
				"SELECT Tenant.UserId AS UserId,FirstName,LastName, Email, UniversityId, Major, Gender, Bio " +
				"FROM Tenant INNER JOIN User " +
				"  ON Tenant.UserId = User.UserId " +		
				"WHERE Tenant.UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectTenant);
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
				int universityId = results.getInt("UniversityId");
				String major = results.getString("Major");
				Tenant.Gender gender = Tenant.Gender.valueOf(results.getString("Gender"));
				String bio = results.getString("Bio");
				
				University university = UniversityDao.getInstance().getUniversityByUniversityId(universityId);
				
				Tenant tenant = new Tenant(resultUserId, firstName, lastName, email, university, major, gender, bio);
				return tenant;
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
	
	public List<Tenant> getTenantByNest(int nestId) throws SQLException{
		List<Tenant> list = new ArrayList<>();
		RoomReservationDao reservationDao = RoomReservationDao.getInstance();
		List<RoomReservation> reservations = reservationDao.getReservationsFromNestId(nestId);
		for(RoomReservation res:reservations) {
			list.add(res.getTenant());
		}
		return list;
	}
	
	

}
