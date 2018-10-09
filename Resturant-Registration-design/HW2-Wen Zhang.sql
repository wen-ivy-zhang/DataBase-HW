# DATA BASE HOME WORK TWO  BY WEN ZHANG
CREATE SCHEMA IF NOT EXISTS Restaurants;
USE Restaurants;

DROP TABLE IF EXISTS Reservations;
DROP TABLE IF EXISTS Recommendations;
DROP TABLE IF EXISTS Reviews;
DROP TABLE IF EXISTS FoodCartRestaurant;
DROP TABLE IF EXISTS TakeOutRestaurant;
DROP TABLE IF EXISTS SitDownRestaurant;
DROP TABLE IF EXISTS Restaurants;
DROP TABLE IF EXISTS Companies;
DROP TABLE IF EXISTS CreditCards;
DROP TABLE IF EXISTS Users;


CREATE TABLE Users(
 UserName VARCHAR(255),
 Password  VARCHAR(255),
 FirstName VARCHAR(255),
 LastName VARCHAR(255),
 Email        VARCHAR(500),
 Phone      VARCHAR(255),
 
 CONSTRAINT pk_Users_UserName PRIMARY KEY(UserName) 
);


CREATE TABLE CreditCards(
  CardNumber BIGINT,
  Expiration DATE,
  UserName VARCHAR(255),
  
  CONSTRAINT pk_CreditCards_CardNumber PRIMARY KEY(CardNumber),
  
  CONSTRAINT fk_CreditCards_UserName 
  FOREIGN KEY(UserName)
  REFERENCES Users(UserName)
  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Companies(
  CompanyName VARCHAR(255),
  About  TEXT,
  
  CONSTRAINT pk_Companies_CompanyName PRIMARY KEY(CompanyName)
);


CREATE TABLE Restaurants(
  RestaurantId INT AUTO_INCREMENT,
  Name VARCHAR(255),
  Description TEXT,
  Menu TEXT,
  Hours VARCHAR(255),
  Active BOOLEAN DEFAULT FALSE,
  Cuisine ENUM('AFRICAN', 'AMERICAN', 'ASIAN', 'EUROPEAN', 'HISPANIC'),
  Street1 VARCHAR(255),
  Street2 VARCHAR(255),
  City VARCHAR(255),
  State VARCHAR(255),
  Zip  INT,
  CompanyName VARCHAR(255),
  
  CONSTRAINT pk_Restaurants_RestaurantId PRIMARY KEY(RestaurantId),
  
  CONSTRAINT fk_Restaurants_CompanyName
  FOREIGN KEY(CompanyName) 
  REFERENCES Companies(CompanyName)
  ON UPDATE CASCADE ON DELETE SET NULL
);


CREATE TABLE TakeOutRestaurant(
  RestaurantId INT,
  MaxWaitTime INT,
  
  CONSTRAINT pk_TakeOutRestaurant_RestaurantId PRIMARY KEY(RestaurantId),
  CONSTRAINT fk_TakeOutRestaurant_RestaurantId 
  FOREIGN KEY (RestaurantId)
  REFERENCES Restaurants(RestaurantId)
  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE FoodCartRestaurant(
  RestaurantId INT,
  Licensed BOOLEAN DEFAULT TRUE,
  
  CONSTRAINT pk_FoodCartRestaurant_RestaurantId PRIMARY KEY(RestaurantId),
  
  CONSTRAINT fk_FoodCartRestaurant_RestaurantId
  FOREIGN KEY(RestaurantId)
  REFERENCES Restaurants(RestaurantId)
  ON UPDATE CASCADE ON DELETE CASCADE
);

CREATE TABLE SitDownRestaurant(
  RestaurantId INT,
  Capacity INT,
  
  CONSTRAINT pk_SitDownRestaurant_RestaurantId PRIMARY KEY(RestaurantId),
  CONSTRAINT fk_SitDownRestaurant_RestaurantId
  FOREIGN KEY(RestaurantId) 
  REFERENCES Restaurants(RestaurantId)
  ON UPDATE CASCADE ON DELETE CASCADE
);


CREATE TABLE Reviews(
  ReviewId INT AUTO_INCREMENT,
  Created TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
  Content VARCHAR(255),
  Rating DECIMAL(2,1),
  UserName VARCHAR(255),
  RestaurantId INT,
  
  CONSTRAINT pk_Reviews_ReviewId PRIMARY KEY (ReviewId),
  CONSTRAINT uq_Reviews_Review UNIQUE(UserName, RestaurantId),
  
  CONSTRAINT fk_Reviews_UserName 
  FOREIGN KEY(UserName)
  REFERENCES Users(UserName)
  ON UPDATE CASCADE ON DELETE SET NULL,
  
  CONSTRAINT fk_Reviews_RestaurantId 
  FOREIGN KEY(RestaurantId) 
  REFERENCES Restaurants(RestaurantId)
  ON UPDATE CASCADE ON DELETE SET NULL
);


CREATE TABLE Recommendations(
  RecommendationId INT AUTO_INCREMENT,
  UserName VARCHAR(255),
  RestaurantId INT,
  
  CONSTRAINT pk_Recommendations_RecommendationId PRIMARY KEY(RecommendationId),
  
  CONSTRAINT fk_Recommendations_UserName 
  FOREIGN KEY(UserName) 
  REFERENCES Users(UserName)
  ON UPDATE CASCADE ON DELETE SET NULL,
  
  CONSTRAINT fk_Recommendations_RestaurantId
  FOREIGN KEY(RestaurantId) 
  REFERENCES Restaurants(RestaurantId) 
  ON UPDATE CASCADE ON DELETE SET NULL
);

CREATE TABLE Reservations(
  ReservationId INT AUTO_INCREMENT,
  Start TIMESTAMP NOT NULL,
  End TIMESTAMP NULL,
  Size INT,
  UserName VARCHAR(255),
  RestaurantId INT,
  
  CONSTRAINT pk_Reservations_ReservationId PRIMARY KEY(ReservationId),
  
  CONSTRAINT fk_Reservations_UserName 
  FOREIGN KEY(UserName) 
  REFERENCES Users(UserName)
  ON UPDATE CASCADE ON DELETE CASCADE,
  
  CONSTRAINT fk_Reservations_RestaurantId 
  FOREIGN KEY(RestaurantId) 
  REFERENCES SitDownRestaurant(RestaurantId)
  ON UPDATE CASCADE ON DELETE CASCADE
);





