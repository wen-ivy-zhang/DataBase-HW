package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import model.*;

public class FloorPlanDao {
	protected ConnectionManager connectionManager;

	private static FloorPlanDao instance = null;
	protected FloorPlanDao() {
		connectionManager = new ConnectionManager();
	}
	public static FloorPlanDao getInstance() {
		if(instance == null) {
			instance = new FloorPlanDao();
		}
		return instance;
	}
	
	public FloorPlan create(FloorPlan floorPlan) throws SQLException {
		String insertFloorPlan =
			"INSERT INTO FloorPlan(NumberOfBedrooms, NumberOfBathrooms) " +
			"VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			// BlogPosts has an auto-generated key. So we want to retrieve that key.
			insertStmt = connection.prepareStatement(insertFloorPlan,
				Statement.RETURN_GENERATED_KEYS);
			insertStmt.setInt(1, floorPlan.getNumberOfBedrooms());
			// Note: for the sake of simplicity, just set Picture to null for now.
			insertStmt.setInt(2, floorPlan.getNumberOfBathrooms());
			
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int planId = -1;
			if(resultKey.next()) {
				planId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			floorPlan.setFloorPlanId(planId);;
			return floorPlan;
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
	
	
	
	public FloorPlan getFloorPlanFromFloorPlanId(int floorPlanId) throws SQLException {
		String selectUser = "SELECT FloorPlanId, NumberOfBedrooms, NumberOfBathrooms FROM FloorPlan WHERE FloorPlanId =? ;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectUser);
			selectStmt.setInt(1, floorPlanId);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves 
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if(results.next()) {
				int id = results.getInt("FloorPlanId");
				int numBedrooms = results.getInt("NumberOfBedrooms");
				int numBathrooms = results.getInt("NumberOfBathrooms");
				
				FloorPlan floorPlan = new FloorPlan(id,numBedrooms,numBathrooms);
				return floorPlan;
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
