USE GruopNest;

LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/University.csv' INTO TABLE University
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
(@col1,@col2,@col3,@col4,@col5) SET Name=@col1, Address=@col2,City=@col3, State=@col4,Zip = @col5;

  
LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/User.csv' INTO TABLE User
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES 
  (@col1,@col2,@col3) SET FirstName = @col1, LastName = @col2, Email = @col3;
  
LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data//Tenant.csv' INTO TABLE Tenant
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4) SET UserId = @col2, UniversityId = @col1,  Major = @col3, Gender =@col4;
  
LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/LandLord.csv' INTO TABLE Landlord
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1) SET UserId = @col1; 
  
LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/Floorplan.csv' INTO TABLE FloorPlan
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2) SET NumberOfBedrooms = @col1, NumberOfBathrooms = @col2;
  
LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/Apartment.csv' INTO TABLE Apartment
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4,@col5,@col6,@col7,@col8) 
  SET FloorPlanId = @col1, Address = @col2, City = @col3, State = @col4, Zip = @col5,
  	  Sqft = @col6, Description = @col7, OwnerId = @col8; 

LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/Rooms.csv' INTO TABLE Room
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3,@col4) 
  SET ApartmentId = @col1, RoomType = @col2, ShareBathroom = @col3, FloorType = @col4; 

LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/ApartmentListing.csv' INTO TABLE ApartmentListing
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2, @col3) 
  SET ApartmentId = @col1, IsClosed = (@col2 = 'TRUE') , PostedBy = @col3; 

LOAD DATA LOCAL INFILE '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/Nest.csv' INTO TABLE Nest
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3) 
  SET ListingId = @col1, IsDeleted = (@col2 = 'TRUE'), IsAcceptedByLandLord = (@col3 = 'TRUE'); 
  
LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/RoomReservation.csv' INTO TABLE RoomReservation
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2,@col3) 
  SET RoomId = @col1, NestId = @col2, TenantId = @col3; 
  
LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/Review.csv' INTO TABLE Review
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2) 
  SET Rating = @col1, PostedBy = @col2; 

LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/UserReviews.csv' INTO TABLE UserReview
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2) 
  SET ReviewId = @col1, UserId = @col2; 

LOAD DATA LOCAL INFILE  '/Users/qianwang/course/CS5200/project/pm3/pm3_oct21/data/ApartmentReview.csv' INTO TABLE ApartmentReview
  FIELDS TERMINATED BY ',' 
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES
  (@col1,@col2) 
  SET ReviewId = @col1, ApartmentId = @col2; 



