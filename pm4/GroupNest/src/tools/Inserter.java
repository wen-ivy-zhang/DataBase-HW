package tools;

import model.*;
import dal.*;

import java.sql.SQLException;
import java.util.Date;
import java.util.List;

public class Inserter {
	
	public static void main(String[] args) throws SQLException {
		
		//Dao instances
		UserDao userDao = UserDao.getInstance(); 
		TenantDao tenantDao = TenantDao.getInstance();
		LandlordDao landlordDao = LandlordDao.getInstance();
		UniversityDao universityDao = UniversityDao.getInstance();
		ReviewDao reviewDao = ReviewDao.getInstance();
		UserReviewDao userReviewDao = UserReviewDao.getInstance();
		ApartmentReviewDao apartmentReviewDao = ApartmentReviewDao.getInstance();
		ApartmentDao apartmentDao = ApartmentDao.getInstance();
		FloorPlanDao floorPlanDao = FloorPlanDao.getInstance();
		ApartmentListingDao listingDao = ApartmentListingDao.getInstance();
		NestDao nestDao = NestDao.getInstance();
		RoomDao roomDao = RoomDao.getInstance();
		RoomReservationDao reservationDao = RoomReservationDao.getInstance();
		
		
		
		
		// INSERT objects from our model.
		User user1 = userDao.create("Nan", "Liu", "liu@husky.neu.edu");
		User user2 = userDao.create("Qian", "Wang", "wang@husky.neu.edu");
		
		University univ1 = new University("Northeastern University", "401 Terry Ave","Seattle","WA","98019");
		universityDao.create(univ1);
		
		Landlord landlord1 = new Landlord("Emma", "Stone", "stone@gmail.com");
		landlord1 = landlordDao.create(landlord1);
		Landlord landlord2 = new Landlord("Ben", "Jamin", "ben@gmail.com");
		landlord2 = landlordDao.create(landlord2);
		Landlord landlord3 = new Landlord("Taylor", "Swift", "taylor@gmail.com");
		landlord3 = landlordDao.create(landlord3);
		
		
		Tenant tenant1 = new Tenant("ShuFan", "Xing", "xing@husky.neu.edu", univ1, "Computer Science", Tenant.Gender.Female, "");
		tenant1 = tenantDao.create(tenant1);
		Tenant tenant2 = new Tenant("Wen", "Zhang", "zhang@husky.neu.edu", univ1, "Computer Science", Tenant.Gender.Female, "");
		tenant2 = tenantDao.create(tenant2);
		Tenant tenant3 = new Tenant("Nan", "Liu", "liu@husky.neu.edu", univ1, "Computer Science", Tenant.Gender.Female, "");
		tenant3 = tenantDao.create(tenant3);
		Tenant tenant4 = new Tenant("Qian", "Wang", "wang@husky.neu.edu", univ1, "Computer Science", Tenant.Gender.Female, "");
		tenant4 = tenantDao.create(tenant4);
		
		Date date = new Date();
		Review review1 = reviewDao.create(user1, date, date, "Good user", 4.5, 0);
	
		
		UserReview userReview1 = new UserReview(user2, date,date,"Good Tenant", 4.0, 0, tenant1,UserReview.Type.Tenant);
		userReview1 = userReviewDao.create(userReview1);
		UserReview userReview2 = new UserReview(user1, date,date,"Good Tenant", 4.8, 0, tenant1,UserReview.Type.Tenant);
		userReview2 = userReviewDao.create(userReview2);
		UserReview userReview3 = new UserReview(tenant1, date,date,"Good landlord", 4.0, 0, landlord1,UserReview.Type.Landlord);
		userReview3 = userReviewDao.create(userReview3);
		System.out.println("check1: type: " + userReview1.getType() + " name: " + userReview1.getType().name());
		System.out.println("check2: type: " + userReview2.getType() + " name: " + userReview2.getType().name());
		
		FloorPlan plan1 = new FloorPlan(2,2);
		floorPlanDao.create(plan1);
		FloorPlan plan2 = new FloorPlan(3,2);
		floorPlanDao.create(plan2);
		
		
		Apartment apt1 = new Apartment(plan1, "400 Boren Ave N", "Seattle", "WA", "98019", 548, "Radius Apartments", "About Radius Apartments", landlord1);
		apt1 = apartmentDao.create(apt1);	
		Apartment apt2 = new Apartment(plan2, "525 Boren Ave N", "Seattle", "WA", "98019", 2011, "Fox & Finch Apartments", "About Fox & Finch Apartments", landlord2);
		apt2 = apartmentDao.create(apt2);
		Apartment apt3 = new Apartment(plan1, "325 Westlake Ave N", "Seattle", "WA", "98019", 1400, "The Lofts at 325 Westlake", "About The Lofts at 325 Westlake", landlord3);
		apt3 = apartmentDao.create(apt3);		
		Apartment apt4 = new Apartment(plan1, "435 Dexter Ave N", "Seattle", "WA", "98019", 1220, "Modera South Lake Union", "About Modera South Lake Union", landlord1);
		apt4 = apartmentDao.create(apt4);	
		
		
		ApartmentReview  aptReview1 = new ApartmentReview(4, tenant1, date,date,"Great Apartment", 4.8, 0, apt1);
		ApartmentReview  aptReview2 = new ApartmentReview(4, tenant2, date,date,"Quiet Apartment", 4.0, 0, apt1);
		ApartmentReview  aptReview3 = new ApartmentReview(4, tenant3, date,date,"Awesome Apartment", 5.0, 0, apt1);
		ApartmentReview  aptReview4 = new ApartmentReview(4, tenant4, date,date,"Good Apartment", 3.0, 0, apt2);
		ApartmentReview  aptReview5 = new ApartmentReview(4, tenant1, date,date,"Great Apartment", 4.0, 0, apt2);
		ApartmentReview  aptReview6 = new ApartmentReview(4, tenant2, date,date,"Nice Apartment", 4.5, 0, apt2);
		ApartmentReview  aptReview7 = new ApartmentReview(4, tenant3, date,date,"Awesome Apartment", 5.0, 0, apt3);
		ApartmentReview  aptReview8 = new ApartmentReview(4, tenant4, date,date,"Just ok", 2.5, 0, apt3);
		ApartmentReview  aptReview9 = new ApartmentReview(4, tenant1, date,date,"Quiet Apartment", 3.8, 0, apt3);
		ApartmentReview  aptReview10 = new ApartmentReview(4, tenant2, date,date,"Great Apartment", 4.0, 0, apt4);
		ApartmentReview  aptReview11 = new ApartmentReview(4, tenant3, date,date,"Good Apartment", 3.5, 0, apt4);
		ApartmentReview  aptReview12 = new ApartmentReview(4, tenant4, date,date,"Nice Apartment", 4.5, 0, apt4);
		aptReview1 = apartmentReviewDao.create(aptReview1);
		aptReview2 = apartmentReviewDao.create(aptReview2);
		aptReview3 = apartmentReviewDao.create(aptReview3);
		aptReview4 = apartmentReviewDao.create(aptReview4);
		aptReview5 = apartmentReviewDao.create(aptReview5);
		aptReview6 = apartmentReviewDao.create(aptReview6);
		aptReview7 = apartmentReviewDao.create(aptReview7);
		aptReview8 = apartmentReviewDao.create(aptReview8);
		aptReview9 = apartmentReviewDao.create(aptReview9);
		aptReview10 = apartmentReviewDao.create(aptReview10);
		aptReview11 = apartmentReviewDao.create(aptReview11);
		aptReview12 = apartmentReviewDao.create(aptReview12);


		ApartmentListing listing1 = new ApartmentListing(apt1,"APT1",2000,date,365,"about APT1","li@gmai.com",false,landlord1,date, date);
		listingDao.create(listing1);
		ApartmentListing listing2 = new ApartmentListing(apt2,"APT2",1900,date,700,"about APT1","li@gmai.com",false,landlord2, date,date);
		listingDao.create(listing2);
		ApartmentListing listing3 = new ApartmentListing(apt3,"APT3",1800,date,150,"about APT1","li@gmai.com",false,landlord3,date, date);
		listingDao.create(listing3);
		ApartmentListing listing4 = new ApartmentListing(apt4,"APT4",1750,date,180,"about APT1","li@gmai.com",false,landlord1,date, date);
		listingDao.create(listing4);
		
		Nest nest1 = new Nest( listing1 , tenant1,date, 0, 0, date) ;
		Nest nest2 = new Nest( listing1 , tenant2,date, 0, 0, date) ;
		Nest nest3 = new Nest( listing1 , tenant3,date, 0, 0, date) ;
		Nest nest4 = new Nest( listing2 , tenant1,date, 0, 0, date) ;
		Nest nest5 = new Nest( listing2 , tenant2,date, 0, 0, date) ;
		Nest nest6 = new Nest( listing3 , tenant3,date, 0, 0, date) ;
		Nest nest7 = new Nest( listing4 , tenant4,date, 0, 0, date) ;
		nestDao.create(nest1);
		nestDao.create(nest2);
		nestDao.create(nest3);
		nestDao.create(nest4);
		nestDao.create(nest5);
		nestDao.create(nest6);
		nestDao.create(nest7);
		Date date1 = new Date();	
		
		
		
		Room room1_1 = new Room(listing1, 540, Room.RoomType.Master, true, Room.FloorType.Hardwood,  "About Room1_1");
		Room room1_2 = new Room(listing1, 325, Room.RoomType.Guest, true, Room.FloorType.Hardwood,  "About Room1_2");
		Room room2_1 = new Room(listing2, 400, Room.RoomType.Master, true, Room.FloorType.Laminate,  "About Room2_1");
		Room room2_2 = new Room(listing2, 450, Room.RoomType.Guest, true, Room.FloorType.Carpet,  "About Room2_2");
		Room room2_3 = new Room(listing2, 520, Room.RoomType.Master, true, Room.FloorType.Hardwood,  "About Room2_3");
		Room room3_1 = new Room(listing3, 360, Room.RoomType.Master, true, Room.FloorType.Laminate,  "About Room3_1");
		Room room3_2 = new Room(listing3, 405, Room.RoomType.Guest, true, Room.FloorType.Hardwood,  "About Room3_2");
		Room room4_1 = new Room(listing4, 604, Room.RoomType.Master, true, Room.FloorType.Carpet,  "About Room4_1");
		Room room4_2 = new Room(listing4, 462, Room.RoomType.Guest, true, Room.FloorType.Carpet,  "About Room4_2");
		
		roomDao.create(room1_1);
		roomDao.create(room1_2);
		roomDao.create(room2_1);
		roomDao.create(room2_2);
		roomDao.create(room2_3);
		roomDao.create(room3_1);
		roomDao.create(room3_2);
		roomDao.create(room4_1);
		roomDao.create(room4_2);
			
		
		Date date2 = new Date();
		RoomReservation reservation1 = new RoomReservation (room1_1, tenant1,date1, nest1, 700, "123456",0,date2);
		RoomReservation reservation2 = new RoomReservation (room1_2, tenant2,date1, nest2, 1300, "123456",0,date2);
		RoomReservation reservation3 = new RoomReservation (room1_1, tenant3,date1, nest3, 800, "123456",0,date2);
		RoomReservation reservation4 = new RoomReservation (room2_1, tenant1,date1, nest4, 1300, "123456",0,date2);
		RoomReservation reservation5 = new RoomReservation (room2_2, tenant2,date1, nest5, 800, "123456",0,date2);
		RoomReservation reservation6 = new RoomReservation (room3_1, tenant3,date1, nest6, 1300, "123456",0,date2);
		RoomReservation reservation7 = new RoomReservation (room4_1, tenant4,date1, nest7, 800, "123456",0,date2);
		reservationDao.create(reservation1);
		reservationDao.create(reservation2);
		reservationDao.create(reservation3);	
		reservationDao.create(reservation4);
		reservationDao.create(reservation5);	
		reservationDao.create(reservation6);
		reservationDao.create(reservation7);	
			
		
		// READ.
		user1 = userDao.updateLastName(user1, "Shi");
		System.out.format("Reading person: u:%s f:%s l:%s e:%s\n",
				user1.getUserId(), user1.getFirstName(), user1.getLastName(), user1.getEmail());
		
		Landlord landlord = landlordDao.getLandlordFromId(3);
		System.out.format("*****Reading landlord: u:%s f:%s l:%s e:%s\n",
				landlord.getUserId(), landlord.getFirstName(), landlord.getLastName(), landlord.getEmail());
		
		tenant1 = tenantDao.updateLastName(tenant1, "NewLastName");
		System.out.format("Reading person: u:%s f:%s l:%s e:%s\n",
				tenant1.getUserId(), tenant1.getFirstName(), tenant1.getLastName(), tenant1.getEmail());
		
		University univ = universityDao.getUniversityByName("Northeastern University");
		System.out.format("Reading university: Id:%s name:%s address:%s city:%s state:%s zip:%s \n",
				univ.getUniversityId(), univ.getName(), univ.getAddress(), univ.getCity(), univ.getState(), univ.getZip());
		
		List<Apartment> apts = apartmentDao.getApartmentsByUniversityName("Northeastern University");
		for (Apartment apt : apts) {
			System.out.format("Reading apartment: Id:%s floorplan:%s address:%s city:%s state:%s zip:%s name:%s description:%s owner:%s \n",
					apt.getApartmentId(), apt.getFloorPlan().getFloorPlanId(), apt.getAddress(), apt.getCity(), apt.getState(),
					apt.getZip(), apt.getName(), apt.getDescription(), apt.getOwner().getUserId());
		}
		
		double aveRatingForUser = userReviewDao.getAveRatingByUser(tenant1.getUserId());
		System.out.format("Average rating for tenant with tenant1: %s\n", aveRatingForUser);
		
		double aveRatingForApartment = apartmentReviewDao.getAveRatingByApartment(apt1.getApartmentId());
		System.out.format("Average rating for apartment with id 1: %s\n", aveRatingForApartment);
		
		List<ApartmentReview> aptReviews = apartmentReviewDao.getApartmentReviewsByApartment(apt1.getApartmentId());
		for(ApartmentReview aptRev:aptReviews) {
			System.out.format("Looping apartmentReviews: Id:%s postedBy:%s postedDateTime:%s lastModifiedDateTime:%s content:%s rating:%s isDeleted:%s apartmentId:%s \n",
					aptRev.getReviewId(), aptRev.getPostedBy().getUserId(), aptRev.getPostedDateTime(), 
					aptRev.getLastModifiedDateTime(), aptRev.getContent(), aptRev.getRating(), aptRev.getIsDeleted(),
					aptRev.getApartment().getApartmentId());;
		}
		
		List<ApartmentListing> allListings1 = listingDao.getApartmentListingsByApartmentId(apt1.getApartmentId());
		List<ApartmentListing> allListings2 = listingDao.getApartmentListingsByUniversityName("Northeastern University");
		for(ApartmentListing listing:allListings1) {
			System.out.format("Looping apartmentListing by apartment ID: Id:%s APTid:%s title:%s price:%s date:%s term:%s about:%s contact:%s isClosed:%s owner:%s  lastModidied:%s \n",
					listing.getListingId(),listing.getApartment().getApartmentId(),listing.getTitle(), listing.getAskingPrice(),listing.getMoveInDate(),listing.getLeaseTermInDays(),listing.getContent(),listing.getContact(),
					listing.isClosed(),listing.getOwner().getUserId(),listing.getLastModifiedDateTime());;
		}
		for(ApartmentListing listing:allListings2) {
			System.out.format("Looping apartmentListing by university name: Id:%s APTid:%s title:%s price:%s date:%s term:%s about:%s contact:%s isClosed:%s owner:%s  lastModidied:%s \n",
					listing.getListingId(),listing.getApartment().getApartmentId(),listing.getTitle(), listing.getAskingPrice(),listing.getMoveInDate(),listing.getLeaseTermInDays(),listing.getContent(),listing.getContact(),
					listing.isClosed(),listing.getOwner().getUserId(),listing.getLastModifiedDateTime());;
		}
		
		
		List<Nest> nests1 = nestDao.getNestsByRoom(1) ;
		for(Nest nest:nests1) {
			System.out.format("Looping nests: NestId:%s ListingId:%s TenantId:%s \n",
					nest.getNestId(), nest.getApartmentListing().getListingId(), nest.getTenant().getUserId());;
		}
		System.out.println("Get all nests by room1_2:");  // expect nest1, nest2, nest3
		List<Nest> nestAll = nestDao.getAllNestsByRoomId(room1_2.getRoomId());
		for(Nest nest:nestAll) {		
			System.out.format("Looping all nests with room1_2: nestId:%s \n", nest.getNestId());
		}
		
		System.out.println("Get nests whose room1_2 is reserved:");  // expect nest2
		List<Nest> nestReserved = nestDao.getReservedNestsByRoomId(room1_2.getRoomId());
		for(Nest nest:nestReserved) {
			
			System.out.format("Looping reserved nests with room1_2: nestId:%s \n", nest.getNestId());
		}
		
		System.out.println("Get nests whose room1_2 is available:");  // expect nest1, nest3
		List<Nest> nests = nestDao.getAvailableNestsByRoomId(room1_2.getRoomId());
		for(Nest nest:nests) {
			
			System.out.format("Looping available nests with room1_2: nestId:%s \n", nest.getNestId());
			//System.out.format("Looping nests: nestId:%s ApartmentListing:%s tenant:%s creationDateTime:%s isDeleted:%s isAcceptedByLandLord:%s lastModifiedDateTime:%s \n",
			//		nest.getNestId(), nest.getApartmentListing().getListingId(), nest.getCreationDateTime(), 
			//		nest.getIsDeleted(), nest.getIsAcceptedByLandLord(),nest.getLastModifiedDateTime());;
		}
		
		List<Room> rooms1 = roomDao.getRoomsByApartmentListing(1);
		for(Room room:rooms1) {
			System.out.format("Looping rooms: roomId:%s aptID:%s \n",
					room.getRoomId(), room.getListing().getApartment().getApartmentId());;
		}
		
		List<Tenant> tenants = tenantDao.getTenantByNest(1);
		for(Tenant tenant:tenants) {
			System.out.format("Looping tenants: tenantId:%s university:%s \n", tenant.getUserId(), tenant.getUniversity().getName());
		}

	}

}


