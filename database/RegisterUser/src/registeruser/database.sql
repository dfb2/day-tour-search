-- 1. Create first this database to your MySQL server:
create database javafxsample;
use javafxsample;
create table if not exists user(
	id int(11) not null auto_increment,
	username varchar(255) not null unique,
	last_name varchar(255) not null,
	first_name varchar(255) not null,
	password varchar(255) not null,
	created_at datetime not null default current_timestamp,
	primary key(id)
); 
-- 2. Add mysql-connector-java-6.0.6.jar to your dependencies.
-- 3. Update database credentials in Database.class.