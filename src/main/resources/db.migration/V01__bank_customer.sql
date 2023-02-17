CREATE TABLE customer (
       id VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
       first_name VARCHAR(128),
       last_name VARCHAR(128),
       date_of_birth date,
       active boolean);


CREATE TABLE account (
       id VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
       customer_id VARCHAR(36),
       initial_credit bigint ,
       balance bigint ,
       FOREIGN KEY (customer_id) REFERENCES customer (id));


 CREATE TABLE transaction (
       id VARCHAR(36) DEFAULT (UUID()) PRIMARY KEY,
	   amount bigint,
       account_id VARCHAR(36),
       transaction_status VARCHAR(128),
       transaction_time date,
       initial_credit bigint ,
       balance bigint ,
       FOREIGN KEY (account_id) REFERENCES account (id));

 INSERT INTO customer (first_name,last_name,date_of_birth,active) value("Oleksii", "Malchev","1988-01-13",true);



