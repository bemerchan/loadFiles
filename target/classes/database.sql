CREATE SCHEMA upload AUTHORIZATION upload;

CREATE TABLE upload."load" (
	id serial4 NOT NULL,
	start_date timestamp NOT NULL,
	end_date timestamp NULL,
	status varchar(30) NOT NULL,
	file_name varchar(300) NOT NULL,
	error varchar(4000) NULL,
	CONSTRAINT load_pk PRIMARY KEY (id)
);


CREATE TABLE upload.load_data (
	id serial4 NOT NULL,
	record_id varchar(30) NOT NULL,
	price numeric(20, 2) NULL,
	start_time timestamp NULL,
	"name" varchar(300) NULL,
	description varchar(4000) NULL,
	nickname varchar(300) NULL,
	load_id int4 NOT NULL,
	CONSTRAINT load_data_pk PRIMARY KEY (id),
	CONSTRAINT load_data_fk FOREIGN KEY (load_id) REFERENCES upload."load"(id)
);