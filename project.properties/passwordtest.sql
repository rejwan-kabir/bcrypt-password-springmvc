/* create Database test character set utf8 collate utf8_general_ci; */

DROP TABLE IF EXISTS User;

CREATE TABLE User (id integer auto_increment primary key, username varchar(100) not null, password char(60) not null ) character set utf8 collate utf_general_ci; 