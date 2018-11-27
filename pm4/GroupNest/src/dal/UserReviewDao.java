package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.*;

import model.*;


public class UserReviewDao extends ReviewDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static UserReviewDao instance = null;
	protected UserReviewDao() {
		connectionManager = new ConnectionManager();
	}
	public static UserReviewDao getInstance() {
		if(instance == null) {
			instance = new UserReviewDao();
		}
		return instance;
	}
	
	
	public UserReview create(UserReview userReview) throws SQLException {
		// Insert into the superclass table first.
		Review rev = create(userReview.getPostedBy(),userReview.getPostedDateTime(),userReview.getLastModifiedDateTime(),userReview.getContent(),userReview.getRating(),userReview.getIsDeleted());

		String insertUserReview = "INSERT INTO UserReview(ReviewId,UserId,Type) VALUES(?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertUserReview);
			insertStmt.setInt(1, rev.getReviewId());
			insertStmt.setInt(2, userReview.getUser().getUserId());
			insertStmt.setString(3, userReview.getType().name());
			System.out.println("check3: name: " + userReview.getType().name());
			
			insertStmt.executeUpdate();
			
			System.out.println("check4: name: " + userReview.getType().name());
			
			userReview.setReviewId(rev.getReviewId());
			return userReview;
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
	 * Get the average rating for a user.
	 */
	public double getAveRatingByUser(int userId) throws SQLException {
		double ave_rating = 0;
		
		String selectRating =
			"SELECT UserReview.UserId, AVG(Review.Rating) AS Ave_Rating " +
			"FROM Review INNER JOIN UserReview " +
			" ON Review.ReviewId = UserReview.ReviewId " + 
			"GROUP BY UserReview.UserId " +
			"HAVING UserReview.UserId=?;";
		Connection connection = null;
		PreparedStatement selectStmt = null;
		ResultSet results = null;
		try {
			connection = connectionManager.getConnection();
			selectStmt = connection.prepareStatement(selectRating);
			selectStmt.setInt(1, userId);
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
	  * Get a list of user reviews by userid.
	  */
	 public List<UserReview> getUserReviewsByUserId(int userId) throws SQLException {
	  List<UserReview> userReviews = new ArrayList<>();
	  
	  String selectUserReview =
	   "SELECT UserReview.ReviewId AS ReviewId, PostedBy, PostedDateTime, LastModifiedDateTime, Content, Rating, IsDeleted,UserId,Type " +
	   "FROM UserReview INNER JOIN Review " +
	   "  ON UserReview.ReviewId = Review.ReviewId " +
	   "WHERE UserReview.UserId=?;";
	  Connection connection = null;
	  PreparedStatement selectStmt = null;
	  ResultSet results = null;
	  try {
	   connection = connectionManager.getConnection();
	   selectStmt = connection.prepareStatement(selectUserReview);
	   selectStmt.setInt(1, userId);
	   results = selectStmt.executeQuery();
	   
	   while(results.next()) {
	    int reviewId = results.getInt("ReviewId");
	    int postedBy = results.getInt("Postedby");
	    Date postedDateTime = new Date(results.getTimestamp("PostedDateTime").getTime());
	    Date lastModifiedDateTime = new Date(results.getTimestamp("LastModifiedDateTime").getTime());
	    String content = results.getString("Content");
	    double rating = results.getDouble("Rating");
	    int isDeleted = results.getInt("IsDeleted");
	    int resultUserId = results.getInt("UserId");
	    System.out.println("check10: Type: " + results.getString("Type"));
	    UserReview.Type type = UserReview.Type.valueOf(results.getString("Type"));
	    System.out.println("check11: Type: " + results.getString("Type"));
	    
	    User postedUser = UserDao.getInstance().getUserByUserId(postedBy);
	    User resultUser = UserDao.getInstance().getUserByUserId(resultUserId);
	    
	    UserReview userReview = new UserReview(reviewId,postedUser,postedDateTime, lastModifiedDateTime,content, rating, isDeleted, resultUser,type);
	    userReviews.add(userReview);
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
	  return userReviews;
	 }
	
	

}
