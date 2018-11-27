package model;

import java.util.Date;

public class Review {
	
	protected int ReviewId; 
	protected User PostedBy;
	protected Date PostedDateTime;
	protected Date LastModifiedDateTime;
	protected String Content;
	protected double Rating;
	protected int IsDeleted;
	
	public Review(int reviewId, User postedBy, Date postedDateTime, Date lastModifiedDateTime, String content,
			double rating, int isDeleted) {
		super();
		ReviewId = reviewId;
		PostedBy = postedBy;
		PostedDateTime = postedDateTime;
		LastModifiedDateTime = lastModifiedDateTime;
		Content = content;
		Rating = rating;
		IsDeleted = isDeleted;
	}
	
	public Review(int reviewId) {
		super();
		ReviewId = reviewId;
	}
	
	

	public Review(User postedBy, Date postedDateTime, Date lastModifiedDateTime, String content, double rating,
			int isDeleted) {
		super();
		PostedBy = postedBy;
		PostedDateTime = postedDateTime;
		LastModifiedDateTime = lastModifiedDateTime;
		Content = content;
		Rating = rating;
		IsDeleted = isDeleted;
	}

	public int getReviewId() {
		return ReviewId;
	}

	public void setReviewId(int reviewId) {
		ReviewId = reviewId;
	}

	public User getPostedBy() {
		return PostedBy;
	}

	public void setPostedBy(User postedBy) {
		PostedBy = postedBy;
	}

	public Date getPostedDateTime() {
		return PostedDateTime;
	}

	public void setPostedDateTime(Date postedDateTime) {
		PostedDateTime = postedDateTime;
	}

	public Date getLastModifiedDateTime() {
		return LastModifiedDateTime;
	}

	public void setLastModifiedDateTime(Date lastModifiedDateTime) {
		LastModifiedDateTime = lastModifiedDateTime;
	}

	public String getContent() {
		return Content;
	}

	public void setContent(String content) {
		Content = content;
	}

	public double getRating() {
		return Rating;
	}

	public void setRating(double rating) {
		Rating = rating;
	}

	public int getIsDeleted() {
		return IsDeleted;
	}

	public void setIsDeleted(int isDeleted) {
		IsDeleted = isDeleted;
	}
	
	

}
