# Create the schema if necessary.
# LAST modified OCT  23 10:52pm
CREATE SCHEMA IF NOT EXISTS GroupNest;
USE GroupNest;

# Drop tables if necessary.
DROP TABLE IF EXISTS CreditCard;
DROP TABLE IF EXISTS UserReview;
DROP TABLE IF EXISTS ApartmentReview;
DROP TABLE IF EXISTS Review;
DROP TABLE IF EXISTS RoomReservation;
DROP TABLE IF EXISTS Nest;
DROP TABLE IF EXISTS Room;
DROP TABLE IF EXISTS ApartmentListing;
DROP TABLE IF EXISTS Apartment;
DROP TABLE IF EXISTS FloorPlan;
DROP TABLE IF EXISTS Landlord;
DROP TABLE IF EXISTS Tenant;
DROP TABLE IF EXISTS University;
DROP TABLE IF EXISTS User;

# Create tables if necessary.
CREATE TABLE User (
UserId	INT UNSIGNED NOT NULL AUTO_INCREMENT,
FirstName VARCHAR(255) NOT NULL,
LastName VARCHAR(255) NOT NULL,
Email VARCHAR(255),
CONSTRAINT pk_User_UserId 
  PRIMARY KEY (UserId)
);

CREATE TABLE CreditCard (
CardNumber VARCHAR(19) ,#NOT NULL, # max number of credit card digits is 19
ExpirationDate DATE,#NOT NULL, # time is not needed for exp date
UserId INT UNSIGNED,
CONSTRAINT pk_CreditCard_CardNumber 
  PRIMARY KEY (CardNumber),
CONSTRAINT fk_CreditCard_User_UserId
  FOREIGN KEY (UserId)
  REFERENCES User (UserId)
  ON UPDATE CASCADE ON DELETE CASCADE # row updated/deleted if data in parent table updated/deleted
);

/**
CREATE TABLE Address (
AddressId	INT UNSIGNED NOT NULL AUTO_INCREMENT,
Street1 VARCHAR(255) NOT NULL, # assume max of 255 letters for a street address
Street2 VARCHAR(255),
City VARCHAR(50) NOT NULL, # assume max 50 letters for a city's name
State ENUM('AL', 'AK', 'AZ', 'AR', 'CA', 'CO', 'CT', 'DE', 'FL', 'GA', 'HI', 'ID', 'IL', 'IN', 'IA', 'KS', 'KY', 'LA', 'ME', 'MD', 'MA', 'MI', 'MN', 'MS', 'MO', 'MT', 'NE', 'NV', 'NH', 'NJ', 'NM', 'NY', 'NC', 'ND', 'OH', 'OK', 'OR', 'PA', 'RI', 'SC', 'SD', 'TN', 'TX', 'UT', 'VT', 'VA', 'WA', 'WV', 'WI', 'WY') NOT NULL, # only US 50 states
Zip VARCHAR(10) NOT NULL, # allowed format: XXXXX or XXXXX-XXXX
CONSTRAINT pk_Address_AddressId
  PRIMARY KEY (AddressId)
);
*/

CREATE TABLE University (
UniversityId INT UNSIGNED NOT NULL AUTO_INCREMENT,
Name VARCHAR(255) NOT NULL,
Address VARCHAR(255),
City VARCHAR(255),
State VARCHAR(255),
Zip VARCHAR(255) NOT NULL,
CONSTRAINT pk_University_UniversityId
  PRIMARY KEY (UniversityId)
);

CREATE TABLE Tenant (
UserId INT UNSIGNED NOT NULL,
UniversityId INT UNSIGNED NOT NULL,
Major VARCHAR(255) NOT NULL,
Gender ENUM('Male', 'Female', 'Brand', 'Unknown') NOT NULL, # 3 options to choose from + NULL for unknown
Bio TEXT, # anything you want to share about yourself
#PeerTenantAvgRating DECIMAL(2,1), # 2 digits precision and 1 decimal digits for 0.0 to 5.0 rating range, avg rating from fellow tenants' review, updated daily perhaps
#LandlordAvgRating DECIMAL(2,1), # 2 digits precision and 1 decimal digits for 0.0 to 5.0 rating range, avg rating for landlords' review, updated daily perhaps
CONSTRAINT pk_Tenant_UserId
  PRIMARY KEY(UserId), 
CONSTRAINT fk_Tenant_UserId
  FOREIGN KEY (UserId)
  REFERENCES User (UserId)
  ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_Tenant_University_UniversityId
  FOREIGN KEY (UniversityId)
  REFERENCES University (UniversityId)
  #ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Landlord (
UserId INT UNSIGNED NOT NULL,
#TenantAvgRating DECIMAL(2,1), # 2 digits precision and 1 decimal digits for 0.0 to 5.0 rating range, avg rating from tenants' review, updated daily perhaps
CONSTRAINT pk_Landlord_UserId
  PRIMARY KEY(UserId),
CONSTRAINT fk_Landlord_UserId
  FOREIGN KEY (UserId)
  REFERENCES User (UserId)
  ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE FloorPlan (
FloorPlanId INT UNSIGNED NOT NULL AUTO_INCREMENT,
NumberOfBedrooms INT NOT NULL,
NumberOfBathrooms INT NOT NULL,
CONSTRAINT pk_FloorPlan_FloorPlanId
  PRIMARY KEY(FloorPlanId),
CONSTRAINT uq_FloorPlan_Plan
  UNIQUE (NumberOfBedrooms,NumberOfBathrooms)
);

CREATE TABLE Apartment (
ApartmentId INT UNSIGNED NOT NULL AUTO_INCREMENT,
FloorPlanId INT UNSIGNED NOT NULL,
Address VARCHAR(255),
City VARCHAR(255),
State VARCHAR(255),
Zip VARCHAR(255) NOT NULL,
Sqft INT UNSIGNED,
Name VARCHAR(255),
Description TEXT,
OwnerId INT UNSIGNED NOT NULL,
#Rating DECIMAL(2,1), # 2 digits precision and 1 decimal digits for 0.0 to 5.0 rating range, avg rating from tenants' review, updated daily perhaps
CONSTRAINT pk_Apartment_ApartmentId
  PRIMARY KEY(ApartmentId),
CONSTRAINT fk_Apartment_FloorPlan_FloorPlanId
  FOREIGN KEY (FloorPlanId)
  REFERENCES FloorPlan (FloorPlanId),
  #ON UPDATE CASCADE ON DELETE SET NULL, 
CONSTRAINT fk_Apartment_Landlord_OwnerId
  FOREIGN KEY (OwnerId)
  REFERENCES Landlord (UserId)
  ON UPDATE CASCADE ON DELETE CASCADE # if landlord is deleted, appts are deleted
);



CREATE TABLE ApartmentListing (
ListingId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ApartmentId INT UNSIGNED NOT NULL,
Title VARCHAR(255),  #NOT NULL,
AskingPrice DECIMAL(13,2) NOT NULL,
MoveInDate DATE NOT NULL,
LeaseTermInDays  INT,# UNSIGNED NOT NULL,
Content TEXT,# NOT NULL,
Contact VARCHAR(255),# NOT NULL, # required, either phone or email
IsClosed TINYINT(1) NOT NULL, # if closed, not shown to public, not available for lease
PostedBy INT UNSIGNED, #NOT NULL,
PostedDateTime TIMESTAMP,# NOT NULL, # for first time listing, later modification on the listing not changing this value
LastModifiedDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT pk_Listing_ListingId
  PRIMARY KEY(ListingId),
CONSTRAINT fk_Listing_Apartment_ApartmentId
  FOREIGN KEY (ApartmentId)
  REFERENCES Apartment (ApartmentId),
  #ON UPDATE CASCADE ON DELETE SET NULL,
CONSTRAINT fk_Listing_Landlord_PostedBy
  FOREIGN KEY (PostedBy)
  REFERENCES Landlord (UserId)
  ON UPDATE CASCADE ON DELETE CASCADE # if user is deleted, listings are deleted
);

CREATE TABLE Room (
RoomId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ListingId INT UNSIGNED NOT NULL,
Sqrt INT UNSIGNED,
RoomType ENUM('Master', 'Guest', 'Other') NOT NULL,
ShareBathroom BOOLEAN,
FloorType ENUM('Hardwood', 'Laminate', 'Carpet', 'Other'),
Description TEXT,
CONSTRAINT pk_Room_RoomId
  PRIMARY KEY(RoomId),
CONSTRAINT fk_Room_ApartmentListing_ListingId
  FOREIGN KEY (ListingId)
  REFERENCES ApartmentListing (ListingId)
  ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE Nest (
NestId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ListingId INT UNSIGNED NOT NULL,
CreatedBy INT UNSIGNED,
CreationDateTime TIMESTAMP,# NOT NULL, # for first time creation, later modification not changing this value
IsDeleted TINYINT(1) NOT NULL, # if deleted, all room reservations are deleted. if no nest is created under a listing, a new nest has to be created in order to put reservations. 
IsAcceptedByLandlord TINYINT(1) NOT NULL, # multiple nests under one listing is possible, the full nest has the highest possibility to be accepted by the landlord
LastModifiedDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT pk_Nest_NestId
  PRIMARY KEY(NestId),
CONSTRAINT fk_Nest_Listing_ListingId
  FOREIGN KEY (ListingId)
  REFERENCES ApartmentListing (ListingId)
  ON UPDATE CASCADE ON DELETE CASCADE, # if listing is deleted, nests are deleted
CONSTRAINT fk_Nest_Tenant_CreatedBy
  FOREIGN KEY (CreatedBy)
  REFERENCES Tenant (UserId)
  ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE RoomReservation (
ReservationId INT UNSIGNED NOT NULL AUTO_INCREMENT,
RoomId INT UNSIGNED NOT NULL,
TenantId INT UNSIGNED NOT NULL,
ReservationDateTime TIMESTAMP, # for first time reservation, later modification on the reservation not changing this value
NestId INT UNSIGNED NOT NULL, 
OfferedPrice DECIMAL(13,2), # a negotiable price that the tenant is willing to offer. should be lower than the apartment listing price
Contact VARCHAR(255), # contact for negotiation
IsCancelled TINYINT(1), # if cancelled, room under the nest can still be reserved by others, but at any time, only one active reservation for one room is allowed
LastModifiedDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
CONSTRAINT pk_RoomReservation_ReservationId
  PRIMARY KEY(ReservationId),
CONSTRAINT uq_RoomReservation_Reserve
  UNIQUE (RoomId, NestId),
CONSTRAINT fk_RoomReservation_Room_RoomId
  FOREIGN KEY (RoomId)
  REFERENCES Room (RoomId)
  ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_RoomReservation_Tenant_TenantId
  FOREIGN KEY (TenantId)
  REFERENCES Tenant (UserId)
  ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_RoomReservation_Nest_NestId
  FOREIGN KEY (NestId)
  REFERENCES Nest (NestId)
  ON UPDATE CASCADE ON DELETE CASCADE # if nest is deleted, reservations are deleted
);


CREATE TABLE Review (
ReviewId INT UNSIGNED NOT NULL AUTO_INCREMENT,
PostedBy INT UNSIGNED,
PostedDateTime TIMESTAMP NOT NULL DEFAULT CURRENT_TIMESTAMP, # for first time review, later modification not changing this value
LastModifiedDateTime TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
Content TEXT,
Rating DECIMAL(2,1) NOT NULL, # 2 digits precision and 1 decimal digits for 0.0 to 5.0 rating range
IsDeleted TINYINT(1), # if poster decided to have it deleted, not shown/calculated into user's average rating
CONSTRAINT pk_Review_ReviewId
  PRIMARY KEY(ReviewId),
CONSTRAINT fk_Review_User_PostedBy
  FOREIGN KEY (PostedBy)
  REFERENCES User (UserId)
  ON UPDATE CASCADE ON DELETE SET NULL
);


CREATE TABLE UserReview (
ReviewId INT UNSIGNED NOT NULL AUTO_INCREMENT,
UserId INT UNSIGNED NOT NULL, # either tenant or landlord. tenant can review tenant and landlord; landlord can review tenant
Type ENUM('Tenant','Landlord') NOT NULL,
CONSTRAINT pk_UserReview_ReviewId
  PRIMARY KEY(ReviewId),
CONSTRAINT fk_UserReview_ReviewId
  FOREIGN KEY(ReviewId)
  REFERENCES Review(ReviewId)
ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_UserReview_User_UserId
  FOREIGN KEY (UserId)
  REFERENCES User (UserId)
  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE ApartmentReview (
ReviewId INT UNSIGNED NOT NULL AUTO_INCREMENT,
ApartmentId INT UNSIGNED NOT NULL, # either tenant or landlord. tenant can review tenant and landlord; landlord can review tenant
CONSTRAINT pk_ApartmentReview_ReviewId
  PRIMARY KEY(ReviewId),
CONSTRAINT fk_ApartmentReview_ReviewId
  FOREIGN KEY(ReviewId)
  REFERENCES Review(ReviewId)
ON UPDATE CASCADE ON DELETE CASCADE,
CONSTRAINT fk_ApartmentReview_Apartment_ApartmentId
  FOREIGN KEY (ApartmentId)
  REFERENCES Apartment (ApartmentId)
  #ON UPDATE CASCADE ON DELETE SET NULL
);
