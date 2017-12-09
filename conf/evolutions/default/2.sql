c
insert into category (id,name) values ( 1,'Rice' );
insert into category (id,name) values ( 2,'Chicken' );
insert into category (id,name) values ( 3,'Curry' );
insert into category (id,name) values ( 4,'Vegetarian' );
insert into category (id,name) values ( 5,'Beef' );
insert into category (id,name) values ( 6,'House Specials' );
insert into category (id,name) values ( 7,'Noddle' );

insert into product (id,category_id,name,description,stock,price) values ( 1,5,'Beef Bowl','Large Beef with noodle',100,5.00 );
insert into product (id,category_id,name,description,stock,price) values ( 2,5,'Beef Noodle','Beef with Noodle',45,9.00 );
insert into product (id,category_id,name,description,stock,price) values ( 3,1,'Rice with sauce','Brown Sauce',5,9.00 );
insert into product (id,category_id,name,description,stock,price) values ( 4,3,'Curry Malasyan','Curry Beef',45,9.00 );
insert into product (id,category_id,name,description,stock,price) values ( 5,3,'Curry Chuicken','Chicken',5,9.00 );
insert into product (id,category_id,name,description,stock,price) values ( 7,6,'House special with mushroom','Musrroooms',50,9.00 );
insert into product (id,category_id,name,description,stock,price) values ( 8,7,'Ramen','Ramen',45,799.00 );
insert into product (id,category_id,name,description,stock,price) values ( 9,3,'Curry Malysian','Chicken',5,9.00 );
insert into product (id,category_id,name,description,stock,price) values ( 10,5,'Beef Rice','Rice with beef',10,9.00 );
insert into product (id,category_id,name,description,stock,price) values ( 11,4,'Veg','Veg food',5,8.00 );
insert into product (id,category_id,name,description,stock,price) values ( 12,2,'Chicken Ball','Chicken Ball',50,29.00 );

insert into user (email,name,password,role) values ( 'admin@products.com', 'Alice Admin', 'password', 'admin' );
insert into user (email,name,password,role) values ( 'manager@products.com', 'Bob Manager', 'password', 'manager' );
insert into user (email,name,password,role) values ( 'customer@products.com', 'Charlie Customer', 'password', 'customer' );