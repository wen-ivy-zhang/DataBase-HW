package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import model.*;

public class ApartmentReviewDao extends ReviewDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ApartmentReviewDao instance = null;
	protected ApartmentReviewDao() {
		connectionManager = new ConnectionManager();
	}
	public static ApartmentReviewDao getInstance() {
		if(instance == null) {
			instance = new ApartmentReviewDao();
		}
		return instance;
	}
	
	public ApartmentReview create(ApartmentReview apartmentReview) throws SQLException {
		// Insert into the superclass table first.
		Review rev = create(apartmentReview.getPostedBy(),apartmentReview.getPostedDateTime(),
				apartmentReview.getLastModifiedDateTime(),apartmentReview.getContent(),apartmentReview.getRating(),
				apartmentReview.getIsDeleted());

		String insertApartmentReview = "INSERT INTO ApartmentReview(ReviewId,ApartmentId) VALUES(?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertApartmentReview);
			insertStmt.setInt(1, rev.getReviewId());
			insertStmt.setInt(2, apartmentReview.getApartment().getApartmentId());
			
			insertStmt.executeUpdate();
			
			apartmentReview.setReviewId(rev.getReviewId());
			
			return apartmentReview;
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
	 * Get the average rating for an apartment.
	 */
	public double getAveRatingByApartment(int apartmentId) throws SQLException {
		double ave_rating = 0;
		
		String selectRating =
			"SELECT ApartmentReview.ApartmentId, AVG(Review.Rating) AS Ave_Rating " +
			"FROM Review INNER JOIN ApartmentReview " +
			" ON Review.ReviewId = ApartmentReview.ReviewId " + 
			"GROUP BY ApartmentReview.ApartmentId " +
			"HAVING ApartmentReview.ApartmentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRating);
			selectStmt.setInt(1, apartmentId);
			results = selectStmt.executeQuery();
			
			if(results.next()) {
				ave_rating = results.getDouble("Ave_Rating");
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
		return ave_rating;
	}
	
	
	/**
	 * Get a list of apartment reviews by apartmentId.
	 */
	public List<ApartmentReview> getApartmentReviewsByApartment(int apartmentId) throws SQLException {
		List<ApartmentReview> apartmentReviews = new ArrayList<>();
		
		String selectApartmentReview =
			"SELECT ApartmentReview.ReviewId AS ReviewId, PostedBy, PostedDateTime, LastModifiedDateTime, Content, Rating, IsDeleted, ApartmentId " +
			"FROM ApartmentReview INNER JOIN Review " +
			"  ON ApartmentReview.ReviewId = Review.ReviewId " +
			"WHERE ApartmentReview.ApartmentId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectApartmentReview);
			selectStmt.setInt(1, apartmentId);
			results = selectStmt.executeQuery();
			
			while(results.next()) {
				int reviewId = results.getInt("ReviewId");
				int userId = results.getInt("Postedby");
				Date postedDateTime = new Date(results.getTimestamp("PostedDateTime").getTime());
				Date lastModifiedDateTime = new Date(results.getTimestamp("LastModifiedDateTime").getTime());
				String content = results.getString("Content");
				double rating = results.getDouble("Rating");
				int isDeleted = results.getInt("IsDeleted");
				int resultApartmentId = results.getInt("ApartmentId");
				
				User user = UserDao.getInstance().getUserByUserId(userId);
				Apartment apartment = ApartmentDao.getInstance().getApartmentByApartmentId(resultApartmentId);
				
				ApartmentReview apartmentReview = new ApartmentReview(reviewId,user,postedDateTime, lastModifiedDateTime,content, rating, isDeleted, apartment);
				apartmentReviews.add(apartmentReview);
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
		return apartmentReviews;
	}

}
