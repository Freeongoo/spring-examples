create table user_details (id integer not null auto_increment, email varchar(255), first_Name varchar(255), last_Name varchar(255), password varchar(255), primary key (id)) ENGINE=InnoDB;

INSERT INTO user_details(email,first_Name,last_Name,password) VALUES ('admin@admin.com','admin','admin','admin');

INSERT INTO user_details(email,first_Name,last_Name,password) VALUES ('john@gmail.com','john','doe','johndoe');

INSERT INTO user_details(email,first_Name,last_Name,password) VALUES ('sham@yahoo.com','sham','tis','shamtis');