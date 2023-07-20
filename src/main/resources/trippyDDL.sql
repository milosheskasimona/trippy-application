CREATE SCHEMA trippySH;

CREATE TABLE trippySH.city(
	id SERIAL PRIMARY KEY,
	name VARCHAR UNIQUE,
	createdOn DATE,
	deletedOn DATE DEFAULT NULL
);

CREATE TABLE trippySH.user(
	id SERIAL PRIMARY KEY,
	name VARCHAR,
	username VARCHAR UNIQUE NOT NULL,
	emailAddress VARCHAR UNIQUE NOT NULL,
	cityFK INTEGER,
	createdOn DATE,
	deletedOn DATE DEFAULT NULL,
	FOREIGN KEY (cityFK)
	 	REFERENCES trippySH.city(id)
);

CREATE TABLE trippySH.venue(
	id SERIAL PRIMARY KEY,
	name VARCHAR,
	address VARCHAR,
	cityFK INTEGER,
	createdByUserFK INTEGER,
	createdOn DATE,
	deletedOn DATE DEFAULT NULL,
	venueType INTEGER,
	phoneNumber VARCHAR,
	emailAddress VARCHAR UNIQUE,
	website VARCHAR,

	
	FOREIGN KEY (cityFK)
	 	REFERENCES trippySH.city(id),
	FOREIGN KEY (createdByUserFK)
	 	REFERENCES trippySH.user(id)

);

CREATE TABLE trippySH.review(
	id SERIAL PRIMARY KEY,
	venueFK INTEGER,
	userFK INTEGER,
	rating INTEGER,
	reviewComment VARCHAR, 
	createdOn DATE,
	deletedOn DATE DEFAULT NULL,
	
	FOREIGN KEY (venueFK)
	 	REFERENCES trippySH.venue(id),
	FOREIGN KEY (userFK)
	 	REFERENCES trippySH.user(id)
);

