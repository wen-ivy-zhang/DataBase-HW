package model;

import java.util.Date;

public class ApartmentReview extends Review {
	
	protected Apartment apartment;

	
	public ApartmentReview(int reviewId, User postedBy, Date postedDateTime, Date lastModifiedDateTime, String content,
			double rating, int isDeleted, Apartment apartment) {
		super(reviewId, postedBy, postedDateTime, lastModifiedDateTime, content, rating, isDeleted);
		this.apartment = apartment;
	}

	public ApartmentReview(int reviewId) {
		super(reviewId);
	}

	public Apartment getApartment() {
		return apartment;
	}

	public void setApartment(Apartment apartment) {
		this.apartment = apartment;
	}

	

}
