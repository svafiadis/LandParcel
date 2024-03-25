CREATE SCHEMA `test`;
USE `test`;

CREATE TABLE landParcel (
objectID BIGINT NOT NULL AUTO_INCREMENT,
name varchar(100) NOT NULL,
status ENUM('SAVED', 'SHORT_LISTED', 'UNDER_CONSTRUCTION', 'APPROVED') NOT NULL,
area DOUBLE PRECISION NOT NULL,
constraints boolean,
PRIMARY KEY(objectID)
);

INSERT INTO landParcel (objectID,name,status,area,constraints) VALUES (1,'test','SAVED',23.5,true);
INSERT INTO landParcel (objectID,name,status,area,constraints) VALUES (2,'test2','SHORT_LISTED',10.0,true);