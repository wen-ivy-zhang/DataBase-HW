# Created on OCT 20, Lastmodified: OCT25
USE GruopNest;

SELECT* FROM User;
SELECT* FROM CreditCard;
SELECT* FROM University;
SELECT* FROM Tenant;
SELECT* FROM Landlord;
SELECT* FROM FloorPlan;
SELECT* FROM Apartment;
SELECT* FROM Room;
SELECT* FROM ApartmentListing;
SELECT* FROM Nest;
SELECT* FROM RoomReservation;
SELECT* FROM Review;
SELECT* FROM UserReview;
SELECT* FROM ApartmentReview;

#1. How many apartments are owned by a given landlord?

SELECT Apartment.OwnerId, COUNT(*)AS APARTMENTS_CNT
FROM Apartment
GROUP BY Apartment.OwnerId;

#2. How many reservations are made on a given apartment?
#DATA NEEDED: ApartmentId, RoomId, RoomReservationId

SET @@sql_mode = ' ';
SELECT ApartmentId, 
               SUM(IF(RoomReservation.ReservationId IS NULL, 0, 1)) AS RESERVATIONS_CNT
FROM Room LEFT OUTER JOIN RoomReservation
           ON Room.RoomId = RoomReservation.RoomId           
GROUP BY Room.ApartmentId;



#3. What are the available apartments near given University (University Name = 'University of Alabama at Birmingham')?  
#Search by University Name and return apartments who has same zip code. 
#List out apartment ID and address.
#For example seach all apartments aroud 

#SET @@sql_mode = ' ';
SELECT University.UniversityId, University.Name, Apartment.ApartmentId,Apartment.Address, 
               Apartment.City,Apartment.State,Apartment.Zip
FROM University LEFT OUTER JOIN  Apartment
            ON University.Zip = Apartment.Zip
 WHERE  University.Name = 'University of Alabama at Birmingham';           
            


#4. What are the average review rating for each available apartment around one certain University(UniversityName =  'University of Alabama at Birmingham')?
SELECT APT_U.UniversityId, APT_U.Name AS UniversityName, APT_U.APT_ID,
                IF(APT_RATING.APT_ID_1 IS NULL, 0, APT_RATING.AVG_RATING) AS APARTMENT_AVG_RATING
FROM (
  SELECT University.UniversityId, University.Name, Apartment.ApartmentId AS APT_ID, Apartment.Address, 
  Apartment.City,Apartment.State,Apartment.Zip
  FROM University LEFT OUTER JOIN  Apartment
  ON University.Zip = Apartment.Zip
  Where University.Name = 'University of Alabama at Birmingham') AS APT_U
  LEFT OUTER JOIN (
    SELECT ApartmentReview.ApartmentId AS APT_ID_1, AVG(Review.Rating) AS AVG_RATING
    FROM Review INNER JOIN ApartmentReview
    ON Review.ReviewId = ApartmentReview.ReviewId
    GROUP BY ApartmentReview.ApartmentId) AS APT_RATING
  ON APT_U.APT_ID = APT_RATING.APT_ID_1;
 

 
 
 
 #5. What are the available rooms  that satisfied user’s specific requirement?
 #Given a university name(University of Alabama at Birmingham) find all avaible aparments nearby and then
 #Filter available rooms by room number = 2,  room type = GuestBedroom, ShareBathroom = false 
SELECT UNIVERSITY_APT.UniversityId, UNIVERSITY_APT. Name, Room.RoomId, COUNT(Room.RoomId) AS ROOM_NUM, Room.RoomType,
               IF(Room.ShareBathroom, 'YES', 'NO') AS ShareBathroom, Room.FloorType
FROM(
  SELECT University.UniversityId, University.Name, Apartment.ApartmentId AS APT_ID,Apartment.Address, 
            Apartment.City,Apartment.State,Apartment.Zip
  FROM University LEFT OUTER JOIN  Apartment
  ON University.Zip = Apartment.Zip
  WHERE  University.Name = 'University of Alabama at Birmingham') AS UNIVERSITY_APT
  INNER JOIN Room
  ON UNIVERSITY_APT.APT_ID = Room.ApartmentId 
GROUP BY Room.ApartmentId
HAVING  ROOM_NUM = 2 AND Room.RoomType = 'Guest Bedroom' AND ShareBathroom = FALSE;


 #6. How many nests are associated with a given room（room id = 5）? List all the nest ID.
 
 #step1: find out all nests associated with a certain apartment
 SELECT ApartmentListing.ApartmentId, Nest.NestId 
			
FROM ApartmentListing LEFT OUTER JOIN Nest
ON ApartmentListing.ListingId = Nest.ListingId
WHERE ApartmentListing.ApartmentId = 2 AND ApartmentListing.IsClosed = FALSE AND Nest.IsDeleted = False;


 
 #step2: find out all nests associated with a given room(for example: roomid = 5)
 SELECT Room.RoomId, Room.ApartmentId, APT_NEST.NestId
 FROM Room INNER JOIN (
  SELECT ApartmentListing.ApartmentId, Nest.NestId 

  FROM ApartmentListing LEFT OUTER JOIN Nest
  ON ApartmentListing.ListingId = Nest.ListingId
  WHERE ApartmentListing.IsClosed = FALSE AND Nest.IsDeleted = False)AS APT_NEST
  
  ON Room.ApartmentId = APT_NEST.ApartmentId
  WHERE Room.RoomId = 5;               
               

               
             
 

 #7. List information of all the Tenants in a certain nest(eg: nestId = 20), like first name, last name, university name, 
 #major, gender, Bio, average peer tenant review rating.
 
 #step1: select related tenent infromation from User, tenant, Unversity
 
 SELECT User.UserId AS TENANTID, User.FirstName, User.LastName, User.Email,
			University.Name, Tenant.Major, Tenant.Gender, Tenant.Bio
FROM User INNER JOIN Tenant
ON User.UserId = Tenant.UserId
  INNER JOIN University 
  ON Tenant.UniversityId = University.UniversityId
GROUP BY User.UserId;

#step2:  calculate average peer tenant rating for each User:
SELECT UserReview.UserId AS USER_ID, AVG(Review.Rating) AS AVG_RATING
FROM Review INNER JOIN UserReview 
ON Review.ReviewId = UserReview.ReviewId		
GROUP BY UserId;

#step3: combine step1 and step2:  all tenent info
SELECT TENANT_INFO.TENANT_ID, TENANT_INFO.FirstName, TENANT_INFO.LastName,
               TENANT_INFO.Email, TENANT_INFO.University, TENANT_INFO.Major, TENANT_INFO.Gender,
               TENANT_INFO.Description, IF(AVG_REVIEW.USER_ID IS NULL, 0, AVG_REVIEW.AVG_RATING) AS User_AVG_RATING
FROM
           (SELECT User.UserId AS TENANT_ID, User.FirstName AS FirstName, User.LastName AS LastName,
            User.Email AS Email,University.Name AS University, Tenant.Major AS Major, Tenant.Gender AS Gender, 
            Tenant.Bio AS Description
             FROM User 
             INNER JOIN Tenant
             ON User.UserId = Tenant.UserId
             INNER JOIN University 
			 ON Tenant.UniversityId = University.UniversityId
             GROUP BY User.UserId) AS TENANT_INFO
             
              LEFT OUTER JOIN
               
             (SELECT UserReview.UserId AS USER_ID, AVG(Review.Rating) AS AVG_RATING
               FROM Review INNER JOIN UserReview 
               ON Review.ReviewId = UserReview.ReviewId
			
               GROUP BY UserId) AS AVG_REVIEW
               
               ON TENANT_INFO.TENANT_ID = AVG_REVIEW.USER_ID;



#step4: Tenents in nest (nestId = 20)
SELECT Nest.NestId, RoomReservation.TenantId 
FROM Nest INNER JOIN RoomReservation
ON Nest.NestId = RoomReservation.NestId
WHERE Nest.NestId = 20;



#step 5: combine #3 and #4

SELECT Nest.NestId, TENANT_INFO.ID AS TENANT_ID, TENANT_INFO.FirstName, TENANT_INFO.LastName,
              TENANT_INFO.Email, TENANT_INFO.University, TENANT_INFO.Major, 
              TENANT_INFO.Gender, 
              IF(AVG_REVIEW.ID_TWO IS NULL, 0, AVG_REVIEW.AVG_RATING) AS RATING
              
FROM Nest INNER JOIN RoomReservation
ON Nest.NestId = RoomReservation.NestId
  INNER JOIN
            
            (SELECT User.UserId AS ID, User.FirstName AS FirstName, User.LastName AS LastName,
                            User.Email AS Email,University.Name AS University, Tenant.Major AS Major, Tenant.Gender AS Gender, 
							Tenant.Bio AS Description
			  FROM User 
                         INNER JOIN Tenant
                         ON User.UserId = Tenant.UserId
                         INNER JOIN University 
                         ON Tenant.UniversityId = University.UniversityId
			GROUP BY User.UserId) AS TENANT_INFO
     
            ON RoomReservation.TenantId = TENANT_INFO.ID
  
             LEFT OUTER JOIN
  
             (SELECT UserReview.UserId AS ID_TWO, AVG(Review.Rating) AS AVG_RATING
               FROM Review INNER JOIN UserReview 
               ON Review.ReviewId = UserReview.ReviewId
               #WHERE UserReview.Type = 'Tenant'
               GROUP BY UserId) AS AVG_REVIEW
     
               ON TENANT_INFO.ID = AVG_REVIEW.ID_TWO
  
WHERE Nest.NestId = 20;
 
 
 
 
 
 #8. How many nests have reached the Apartment capacity (for Aparment Id = 26)?
 # for certain apartment(current apartmentlist), lists all nests that reach the apartment capactiy
 
#step 1: calculate reservations per nest
 SELECT Nest.NestId , RoomReservation.ReservationId,COUNT(RoomReservation.ReservationId) AS RESERVATION_NUM_PER_NEST
 FROM Nest INNER JOIN RoomReservation
 ON RoomReservation.NestId = Nest.NestId
 GROUP BY Nest.NestId;
 
# step2: find out nests for Apartment26 current listing
SELECT ApartmentListing.ListingId AS LIST_ID, ApartmentListing.ApartmentId AS APT_ID_1, 
			   NEST_INFO.NestId, NEST_INFO.NEST_VOL
FROM ApartmentListing 

            LEFT OUTER JOIN 
            
            (SELECT Nest.NestId, Nest.ListingId AS LIST_ID_1, COUNT(RoomReservation.ReservationId) AS NEST_VOL
              FROM Nest INNER JOIN RoomReservation
              ON RoomReservation.NestId = Nest.NestId
              WHERE  Nest.IsDeleted = FALSE
              GROUP BY Nest.NestId) AS NEST_INFO
              
              ON ApartmentListing.ListingId = NEST_INFO.LIST_ID_1
              
WHERE ApartmentListing.IsClosed = FALSE AND ApartmentListing.ApartmentId = 32;



#step3: find out apartment capacity
SELECT Apartment.ApartmentId AS APT_ID, Floorplan.NumberOfBedrooms AS CAPACITY

FROM  Apartment INNER JOIN Floorplan
            ON Apartment.FloorPlanId = Floorplan.FloorPlanId
            
GROUP BY ApartmentId;
  
#step4:  for each apartment(current apartmentlist), lists all nests that reach the apartment capactiy


SELECT APT_CAP.APT_ID, APT_CAP.CAPACITY, LIST_NEST.NestId, LIST_NEST.NEST_VOL

FROM (SELECT Apartment.ApartmentId AS APT_ID, Floorplan.NumberOfBedrooms AS CAPACITY
             FROM  Apartment INNER JOIN Floorplan
                          ON Apartment.FloorPlanId = Floorplan.FloorPlanId
             GROUP BY ApartmentId) AS APT_CAP
   
             INNER JOIN 
   
             (SELECT ApartmentListing.ListingId AS LIST_ID, ApartmentListing.ApartmentId AS APT_ID_1, 
			                 NEST_INFO.NestId, NEST_INFO.NEST_VOL
               FROM  ApartmentListing 
                            LEFT OUTER JOIN 
                            (SELECT Nest.NestId, Nest.ListingId AS LIST_ID_1, COUNT(RoomReservation.ReservationId) AS NEST_VOL
	                         FROM Nest INNER JOIN RoomReservation
		                     ON RoomReservation.NestId = Nest.NestId
                             WHERE  Nest.IsDeleted = FALSE
							 GROUP BY Nest.NestId) AS NEST_INFO
	                        ON ApartmentListing.ListingId = NEST_INFO.LIST_ID_1
	         WHERE ApartmentListing.IsClosed = FALSE AND ApartmentListing.ApartmentId = 32 ) AS LIST_NEST
             
    
             ON APT_CAP.APT_ID = LIST_NEST.APT_ID_1

HAVING LIST_NEST.NEST_VOL = APT_CAP.CAPACITY;





#9. Who are the top 10 highest rating land lord this year?

SELECT LAND_LORD.UserId, LAND_LORD.FirstName, LAND_LORD.LastName, 
               IF(LANDLORD_REVIEW.UserId IS NULL, 0, AVG_RATING) AS LANDLORD_RATING
               
FROM (SELECT User.UserId, User.FirstName, User.LastName 
             FROM User INNER JOIN Landlord
                         ON User.UserId = Landlord.UserId) AS LAND_LORD

            LEFT OUTER JOIN

            (SELECT UserReview.UserId, AVG(Review.Rating) AS AVG_RATING
              FROM Review INNER JOIN UserReview
                         ON Review.ReviewId = UserReview.ReviewId
	          #WHERE UserReview.Type = 'Landlord'
              GROUP BY UserReview.UserId) AS LANDLORD_REVIEW

            ON LAND_LORD.UserId = LANDLORD_REVIEW.UserId

GROUP BY LAND_LORD.UserId

ORDER BY LANDLORD_RATING DESC, LAND_LORD.FirstName ASC, LAND_LORD.LastName ASC

LIMIT 10;





#10. What are the top 10 universities that has maximum housing demand this year?
SELECT University.UniversityId,University.Name, TENANT_RESERVATION.Tenant_CNT

FROM University 
  
			INNER JOIN 
  
            (SELECT Tenant.UniversityId, COUNT(Tenant.UserId) AS Tenant_CNT
			FROM Tenant INNER JOIN RoomReservation
			ON Tenant.UserId  = RoomReservation.TenantId
			GROUP BY UniversityId) AS TENANT_RESERVATION
   
            ON University.UniversityId = TENANT_RESERVATION.UniversityId
   
GROUP BY University.UniversityId

ORDER BY TENANT_RESERVATION.Tenant_CNT DESC

LIMIT 10;


   




 

 
   
    













