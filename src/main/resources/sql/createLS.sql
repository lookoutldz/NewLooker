DROP DATABASE
IF EXISTS looksteam;

CREATE DATABASE looksteam DEFAULT CHARACTER
SET utf8mb4;

USE looksteam;

CREATE TABLE player (
	steamid VARCHAR (255) PRIMARY KEY NOT NULL,
	communityvisibilitystate INT,
	profilestate INT,
	personaname VARCHAR (255),
	lastlogoff INT,
	commentpermission INT,
	profileurl VARCHAR (255),
	avatar VARCHAR (255),
	avatarmedium VARCHAR (255),
	avatarfull VARCHAR (255),
	personastate INT,
	realname VARCHAR (255),
	primaryclanid VARCHAR (255),
	timecreated INT,
	personastateflags INT,
	gameextrainfo VARCHAR (255),
	gameserverip VARCHAR (255),
	gameid INT,
	loccountrycode VARCHAR (255),
	locstatecode VARCHAR (255),
	loccityid VARCHAR (255),
	steamlevel INT,
	gameprice INT
);

CREATE TABLE app (
	appid INT PRIMARY KEY NOT NULL,
	appname VARCHAR (255),
	chname VARCHAR (255),
	price INT,
	img_icon_url VARCHAR (255),
	img_logo_url VARCHAR (255),
	pic_logobar VARCHAR(255),
	pic_screenshot VARCHAR(5120)
);

CREATE TABLE game_schema (
	appid INT NOT NULL,
	achname VARCHAR (255) NOT NULL,
	defaultvalue INT,
	displayName VARCHAR (1024),
	hidden INT,
	description VARCHAR(2048),
	icon VARCHAR (255),
	icongray VARCHAR (255),
	PRIMARY KEY (appid, achname)
);

CREATE TABLE owned_game (
	steamid VARCHAR (255) NOT NULL,
	appid INT NOT NULL,
	appname VARCHAR(255),
	playtime_2week INT,
	playtime_forever INT,
	img_icon_url VARCHAR (255),
	img_logo_url VARCHAR (255),
	has_community_visible_state TINYINT (1),
	PRIMARY KEY (steamid, appid)
);

CREATE TABLE player_achi (
	steamid VARCHAR (255) NOT NULL,
	appid INT NOT NULL,
	achname VARCHAR (255) NOT NULL,
	description VARCHAR(2048),
	achieved INT,
	unlocktime INT,
	PRIMARY KEY (steamid, appid, achname)
);

CREATE TABLE friend (
	steamid VARCHAR (255) NOT NULL,
	friendsteamid VARCHAR (255) NOT NULL,
	relationship VARCHAR (255),
	friend_since INT,
	PRIMARY KEY (steamid, friendsteamid)
);

CREATE INDEX unlocktime ON player_achi(unlocktime);
CREATE INDEX achieved ON player_achi(achieved);
CREATE INDEX achi_appid ON player_achi(appid);
CREATE INDEX game_appid ON owned_game(appid);
CREATE INDEX schema_appid ON game_schema(appid);

CREATE INDEX game_steamid ON owned_game(steamid);
CREATE INDEX achi_steamid ON player_achi(steamid);