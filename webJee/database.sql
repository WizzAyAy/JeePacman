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
INSERT into Cosmetics (price, name) VALUES (2.00,'Black Walls');
INSERT into Cosmetics (price, name) VALUES (2.00,'Blue Walls');
INSERT into Cosmetics (price, name) VALUES (2.00,'Red Walls');
INSERT into Cosmetics (price, name) VALUES (2.00,'Yellow Walls');
INSERT into Cosmetics (price, name) VALUES (2.00,'Green Walls');
INSERT into Cosmetics (price, name) VALUES (5.00,'Silver Walls');
INSERT into Cosmetics (price, name) VALUES (15.00,'Gold Walls');
INSERT into Cosmetics (price, name) VALUES (150.00,'Diamond Walls');

INSERT into Cosmetics (price, name) VALUES (2.00,'Black Floor');
INSERT into Cosmetics (price, name) VALUES (2.00,'Blue Floor');
INSERT into Cosmetics (price, name) VALUES (2.00,'Red Floor');
INSERT into Cosmetics (price, name) VALUES (2.00,'Yellow Floor');
INSERT into Cosmetics (price, name) VALUES (2.00,'Green Floor');
INSERT into Cosmetics (price, name) VALUES (5.00,'Silver Floor');
INSERT into Cosmetics (price, name) VALUES (15.00,'Gold Floor');
INSERT into Cosmetics (price, name) VALUES (150.00,'Diamond Floor');

INSERT into Cosmetics (price, name) VALUES (2.00,'Black Skin');
INSERT into Cosmetics (price, name) VALUES (2.00,'Blue Skin');
INSERT into Cosmetics (price, name) VALUES (2.00,'Red Skin');
INSERT into Cosmetics (price, name) VALUES (2.00,'Yellow Skin');
INSERT into Cosmetics (price, name) VALUES (2.00,'Green Skin');
INSERT into Cosmetics (price, name) VALUES (5.00,'Silver Skin');
INSERT into Cosmetics (price, name) VALUES (15.00,'Gold Skin');
INSERT into Cosmetics (price, name) VALUES (150.00,'Diamond Skin');

INSERT into Cosmetics (price, name) VALUES (2.00,'Black Eyes');
INSERT into Cosmetics (price, name) VALUES (2.00,'Blue Eyes');
INSERT into Cosmetics (price, name) VALUES (2.00,'Red Eyes');
INSERT into Cosmetics (price, name) VALUES (2.00,'Yellow Eyes');
INSERT into Cosmetics (price, name) VALUES (2.00,'Green Eyes');
INSERT into Cosmetics (price, name) VALUES (5.00,'Silver Eyes');
INSERT into Cosmetics (price, name) VALUES (15.00,'Gold Eyes');
INSERT into Cosmetics (price, name) VALUES (150.00,'Diamond Eyes');


# Games
INSERT into Games (score) VALUES (31541);
INSERT into Games (score) VALUES (0);

	
	# Player cosmetics
INSERT into PlayerCosmetics (idCosmetic, idPlayer) VALUES (1,1);
INSERT into PlayerCosmetics (idCosmetic, idPlayer) VALUES (2,1);
	
	# Player Games
INSERT into GamePlayers (idGame, idPlayer) VALUES (1,1);
INSERT into GamePlayers (idGame, idPlayer) VALUES (1,2);
INSERT into GamePlayers (idGame, idPlayer) VALUES (2,1);