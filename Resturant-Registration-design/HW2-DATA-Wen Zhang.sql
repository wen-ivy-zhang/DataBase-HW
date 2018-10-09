#DATA BASE HOMEWORK TWO BY WEN ZHANG
USE Restaurants;

INSERT INTO Users(UserName, Password, FirstName, LastName, Email, Phone)
VALUES('Ivy', '123456', 'Ivy', 'Zhang', 'Ivyzhangwen@gmail.com', '3456789901'),
('TT', '234567', 'Tina', 'Lee', 'tinalee@hotmail.com', '3349008990');


INSERT INTO Companies(CompanyName,About)
VALUES('Bill Company', 'Bill company was founded in 1990 by Bill Greens'),
('Bob Company', 'Bob company was founded in 1988, and the CEO of this company is Bob Stevens');


INSERT INTO Restaurants(Name, Description, Menu,
  Hours, Active, Cuisine, Street1, Street2, City, State, Zip, CompanyName)
  VALUES('Boiling Point',  'Hot pot from China',  'Lamp pot: $13, Beef pot: & 15, Pork pot: $14, Vegetable pot: $12',
  '9 am to 9 pm',  TRUE, 'ASIAN', '135 main street' , '#105',  'Redmond',  'WA',  '98052',   'Bill Company'),
  ('Boiling Dot',  'Fast food restaurant',  'Burgers: $8, Sandwiches: $7, Fried Chips: $6, Chicken Nugglets:$7',
  '8 am to 10 pm', TRUE,  'AMERICAN',  '136 main street',  '#140',  'Kirkland',' WA',  '98034',  'Bill Company'),
  ('Boiling Got',  'European restaurant',  'Steak: $18, Sandwiches: $17, Fish: $20, Chicken:$12',
  '11 am to 10 pm', TRUE,  'EUROPEAN',  '137 main street',  '#140',  'Kirkland',' WA',  '98034',  'Bob Company'),
  ('Boiling Foods',  'Hispanic restaurant',  'Steak: $13, Sandwiches: $10, Fish: $15, Chicken:$12',
  '11 am to 9 pm', TRUE,  'HISPANIC',  '138 main street',  '#160',  'Kirkland',' WA',  '98034',  'Bob Company'),
  ('Boiling Everything',  'African restaurant',  'Potatos: $6, Sandwiches: $9, Fish: $6, Chicken:$8',
  '10 am to 7 pm', TRUE,  'AFRICAN',  '139 main street',  '#170',  'Kirkland',' WA',  '98034',  'Bob Company');
  
  INSERT INTO TakeOutRestaurant( RestaurantId,MaxWaitTime)
  VALUES(1, 1),
   (2,  2);
   
   INSERT INTO FoodCartRestaurant(RestaurantId)
   VALUES(4),
   (2);
   
   INSERT INTO SitDownRestaurant(RestaurantId, Capacity)
   VALUES(3, 20),
   (5, 10);
   
   INSERT INTO Reviews(Content, Rating, UserName, RestaurantId)
   VALUES('This is a good restaurant food is amazing!', 4.8, 'Ivy', 1),
   (' Food is not fresh never go again', 1.5, 'TT', 4);
  
   INSERT INTO Recommendations( UserName, RestaurantId)
   VALUES('Ivy', 1),
   ('TT', 5);
   
   INSERT INTO Reservations(Start, End, Size, UserName, RestaurantId)
   VALUES('2018-10-01 12:20:10', '2018-10-01 13:30:00', 4, 'Ivy', 3),
   ('2018-10-02 17:40:00', '2018-10-02 18:40:00',3, 'TT',5);
   
   
  LOAD DATA LOCAL INFILE '/Users/wenzhang/Desktop/5200DataBase/hw2/creditcards.csv' INTO TABLE CreditCards
  FIELDS TERMINATED BY ',' ENCLOSED BY '"'
  LINES TERMINATED BY '\n'
  IGNORE 1 LINES;
  
  
   
   
   
  
   
  
  
  
  
  
  
  
  
  
  
