CREATE DATABASE db_accounts; 

USE db_accounts;

CREATE TABLE ACCOUNTS (
   id BIGINT NOT NULL AUTO_INCREMENT,
   first_name VARCHAR(30) NOT NULL,
   last_name  VARCHAR(30) NOT NULL,
   email VARCHAR(30) NOT NULL,
   date_of_birth Date NOT NULL,
   PRIMARY KEY (id));
   
COMMIT;