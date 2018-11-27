package model;

import java.util.Date;

public class UserReview extends Review {
	
	protected User user;
	protected Type type;
	
	public enum Type {Tenant, Landlord}

	

	public UserReview(int reviewId, User postedBy, Date postedDateTime, Date lastModifiedDateTime, String content,
			double rating, int isDeleted, User user, Type type) {
		super(reviewId, postedBy, postedDateTime, lastModifiedDateTime, content, rating, isDeleted);
		this.user = user;
		this.type = type;
	}


	public UserReview(int reviewId) {
		super(reviewId);
	}
	
	


	public UserReview(User postedBy, Date postedDateTime, Date lastModifiedDateTime, String content, double rating,
			int isDeleted, User user, Type type) {
		super(postedBy, postedDateTime, lastModifiedDateTime, content, rating, isDeleted);
		this.user = user;
		this.type = type;
	}


	public User getUser() {
		return user;
	}


	public void setUser(User user) {
		this.user = user;
	}


	public Type getType() {
		return type;
	}


	public void setType(Type type) {
		this.type = type;
	}


	

}
