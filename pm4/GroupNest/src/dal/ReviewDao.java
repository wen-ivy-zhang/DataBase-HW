package dal;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Date;
import model.*;


public class ReviewDao {
	
	protected ConnectionManager connectionManager;
	
	// Single pattern: instantiation is limited to one object.
	private static ReviewDao instance = null;
	protected ReviewDao() {
		connectionManager = new ConnectionManager();
	}
	public static ReviewDao getInstance() {
		if(instance == null) {
			instance = new ReviewDao();
		}
		return instance;
	}

	
	public Review create(User postedBy, Date postedDateTime, Date lastModifiedDateTime, String content,
		double rating, int isDeleted) throws SQLException {
		
		Review review = new Review(postedBy, postedDateTime, lastModifiedDateTime, content, rating, isDeleted);
		
		String insertReview = "INSERT INTO Review(PostedBy,PostedDateTime,LastModifiedDateTime,Content,Rating,IsDeleted) VALUES(?,?,?,?,?,?);";
		Connection connection = null;
		PreparedStatement insertStmt = null;
		ResultSet resultKey = null;
		try {
			connection = connectionManager.getConnection();
			insertStmt = connection.prepareStatement(insertReview, Statement.RETURN_GENERATED_KEYS);
			// PreparedStatement allows us to substitute specific types into the query template.
			// For an overview, see:
			// http://docs.oracle.com/javase/tutorial/jdbc/basics/prepared.html.
			// http://docs.oracle.com/javase/7/docs/api/java/sql/PreparedStatement.html
			// For nullable fields, you can check the property first and then call setNull()
			// as applicable.
			//insertStmt.setInt(1, review.getReviewId());
			insertStmt.setInt(1, review.getPostedBy().getUserId());
			insertStmt.setTimestamp(2, new Timestamp(review.getPostedDateTime().getTime()));
			insertStmt.setTimestamp(3, new Timestamp(review.getLastModifiedDateTime().getTime()));
			insertStmt.setString(4, review.getContent());
			insertStmt.setDouble(5, review.getRating());
			insertStmt.setInt(6, review.getIsDeleted());
			
			insertStmt.executeUpdate();
			
			// Retrieve the auto-generated key and set it, so it can be used by the caller.
			// For more details, see:
			// http://dev.mysql.com/doc/connector-j/en/connector-j-usagenotes-last-insert-id.html
			resultKey = insertStmt.getGeneratedKeys();
			int reviewId = -1;
			if(resultKey.next()) {
				reviewId = resultKey.getInt(1);
			} else {
				throw new SQLException("Unable to retrieve auto-generated key.");
			}
			review.setReviewId(reviewId);
			
			
			return review;
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

	
}
