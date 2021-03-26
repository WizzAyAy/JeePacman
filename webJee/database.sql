# DROP DATABASE if need be
DROP DATABASE pacman;

# Creation of the database

CREATE DATABASE pacman DEFAULT CHARACTER SET utf8 COLLATE utf8_general_ci;

USE pacman;

# Creation of the tables

CREATE TABLE  pacman.Cosmetics (
 id INT( 10 ) NOT NULL AUTO_INCREMENT ,
 price FLOAT( 2 ) NOT NULL ,
 name VARCHAR( 30 ) NOT NULL ,
 PRIMARY KEY ( id ),
 UNIQUE ( name )
) ENGINE = INNODB;

CREATE TABLE  pacman.Player (
 id INT( 10 ) NOT NULL AUTO_INCREMENT ,
 token VARCHAR( 100 ) ,
 email VARCHAR( 60 ) NOT NULL ,
 password VARCHAR( 64 ) NOT NULL ,
 pseudo VARCHAR( 20 ) NOT NULL ,
 PRIMARY KEY ( id ),
 UNIQUE ( pseudo ),
 UNIQUE ( email )
) ENGINE = INNODB;

CREATE TABLE  pacman.PlayerCosmetics (
 idCosmetic INT( 10 ) NOT NULL ,
 idPlayer INT( 10 ) NOT NULL ,
 PRIMARY KEY ( idCosmetic, idPlayer ),
 FOREIGN KEY (idCosmetic)
 	REFERENCES Cosmetics(id)
 	ON DELETE CASCADE,
 FOREIGN KEY (idPlayer)
 	REFERENCES Player(id)
 	ON DELETE CASCADE
) ENGINE = INNODB;

CREATE TABLE  pacman.Games (
 id INT( 20 ) NOT NULL AUTO_INCREMENT ,
 score INT( 60 ) NOT NULL ,
 PRIMARY KEY ( id )
) ENGINE = INNODB;

CREATE TABLE  pacman.GamePlayers (
 idGame INT( 10 ) NOT NULL ,
 idPlayer INT( 10 ) NOT NULL ,
 PRIMARY KEY ( idGame, idPlayer ),
 FOREIGN KEY (idGame)
 	REFERENCES Games(id)
 	ON DELETE CASCADE,
 FOREIGN KEY (idPlayer)
 	REFERENCES Player(id)
 	ON DELETE CASCADE
) ENGINE = INNODB;

# Examples 
	# Players
INSERT Into Player (email, password, pseudo) VALUES ('bob@random.com', MD5('SomethingNormallyHashedInFront'), 'bob');
INSERT Into Player (email, password, pseudo) VALUES ('alice@random.com', MD5('SomethingNormallyHashedInFront'), 'alice');

	# Cosmetics
INSERT into Cosmetics (price, name) VALUES (28.00,'Spocks ears');
INSERT into Cosmetics (price, name) VALUES (24.99,'Top Hat');
INSERT into Cosmetics (price, name) VALUES (100,'Assiimov skin');
INSERT into Cosmetics (price, name) VALUES (2000,'Dragon lord');
INSERT into Cosmetics (price, name) VALUES (850,'fire serpent');
INSERT into Cosmetics (price, name) VALUES (0.20,'safari mesh');

# Games
INSERT into Games (score) VALUES (31541);
INSERT into Games (score) VALUES (0);
INSERT into Games (score) VALUES (69420);
	
	# Player cosmetics
INSERT into PlayerCosmetics (idCosmetic, idPlayer) VALUES (1,1);
INSERT into PlayerCosmetics (idCosmetic, idPlayer) VALUES (2,1);
	
	# Player Games
INSERT into GamePlayers (idGame, idPlayer) VALUES (1,1);
INSERT into GamePlayers (idGame, idPlayer) VALUES (1,2);
INSERT into GamePlayers (idGame, idPlayer) VALUES (2,1);
INSERT into GamePlayers (idGame, idPlayer) VALUES (3,2);