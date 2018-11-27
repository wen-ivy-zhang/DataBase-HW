package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

import model.*;

public class ApartmentDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ApartmentDao instance = null;
	protected ApartmentDao() {
		connectionManager = new ConnectionManager();
	}
	public static ApartmentDao getInstance() {
		if(instance == null) {
			instance = new ApartmentDao();
		}
		return instance;
	}
	
	public Apartment create(Apartment apartment) throws SQLException {
		

		String insertApartment = "INSERT INTO Apartment(FloorPlanId,Address,City,State,Zip,Sqft,Name,Description,OwnerId) VALUES(?,?,?,?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertApartment, Statement.RETURN_GENERATED_KEYS);
			//insertStmt.setInt(1, apartment.getApartmentId());
			insertStmt.setInt(1, apartment.getFloorPlan().getFloorPlanId());
			insertStmt.setString(2, apartment.getAddress());
			insertStmt.setString(3, apartment.getCity());
			insertStmt.setString(4, apartment.getState());
			insertStmt.setString(5, apartment.getZip());
			insertStmt.setInt(6, apartment.getSqft());
			insertStmt.setString(7, apartment.getName());
			insertStmt.setString(8, apartment.getDescription());
			insertStmt.setInt(9, apartment.getOwner().getUserId());
			
			
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int aptId = -1;
			if(resultKey.next()) {
				aptId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			apartment.setApartmentId(aptId);
			
			return apartment;
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
	 * Get the matching Apartments by university name
	 */
	public List<Apartment> getApartmentsByUniversityName(String universityName) throws SQLException {
		List<Apartment> apartments = new ArrayList<Apartment>();
		String selectApartment =
				"SELECT * " +
				"FROM Apartment INNER JOIN University " +
				" ON Apartment.Zip = University.Zip " + 
				"WHERE University.Name=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectApartment);
			selectStmt.setString(1, universityName);
			results = selectStmt.executeQuery();
			while(results.next()) {
				int id = results.getInt("Apartment.ApartmentId");
				int floorPlanId = results.getInt("Apartment.FloorPlanId");
				String address = results.getString("Apartment.Address");
				String city = results.getString("Apartment.City");
				String state = results.getString("Apartment.State");
				String zip = results.getString("Apartment.Zip");
				int sqft = results.getInt("Apartment.Sqft");
				String name = results.getString("Apartment.Name");
				String description = results.getString("Apartment.Description");
				int ownerId = results.getInt("Apartment.OwnerId");
				Landlord landlord = LandlordDao.getInstance().getLandlordFromId(ownerId);
				FloorPlan floorPlan = FloorPlanDao.getInstance().getFloorPlanFromFloorPlanId(floorPlanId);
				
				Apartment apt = new Apartment(id, floorPlan,address, city, state, zip, sqft, name, description, landlord);
				apartments.add(apt);
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
		return apartments;
	}
	
	public Apartment getApartmentByApartmentId(int apartmentId) throws SQLException {
		String selectApartment = "SELECT ApartmentId,FloorPlanId,Address,City,State,Zip,Sqft,Sqft,Name,Description,OwnerId FROM Apartment WHERE ApartmentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectApartment);
			selectStmt.setInt(1, apartmentId);
			// Note that we call executeQuery(). This is used for a SELECT statement
			// because it returns a result set. For more information, see:
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// http://docs.oracle.com/javase/7/docs/api/java/sql/ResultSet.html
			results = selectStmt.executeQuery();
			// You can iterate the result set (although the example below only retrieves 
			// the first record). The cursor is initially positioned before the row.
			// Furthermore, you can retrieve fields by name and by type.
			if(results.next()) {
				int resultApartmentId = results.getInt("ApartmentId");
				int floorPlanId = results.getInt("FloorPlanId");
				String address = results.getString("Address");
				String city = results.getString("City");
				String state = results.getString("State");
				String zip = results.getString("Zip");
				int sqft = results.getInt("Sqft");
				String name = results.getString("Name");
				String description = results.getString("Description");
				int ownerId = results.getInt("OwnerId");
				Landlord landlord = LandlordDao.getInstance().getLandlordFromId(ownerId);
				
				FloorPlan floorPlan = FloorPlanDao.getInstance().getFloorPlanFromFloorPlanId(floorPlanId);
				
				Apartment apt = new Apartment(resultApartmentId, floorPlan,address, city, state, zip, sqft, name, description, landlord);
				return apt;
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
