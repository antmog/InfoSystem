-- users
DROP TABLE IF EXISTS `users`;
CREATE TABLE `users` (
  `USER_ID` int(11) NOT NULL AUTO_INCREMENT,
  `ADRESS` varchar(255) NOT NULL,
  `BALANCE` double NOT NULL,
  `BIRTH_DATE` date NOT NULL,
  `FIRST_NAME` varchar(255) NOT NULL,
  `LAST_NAME` varchar(255) NOT NULL,
  `LOGIN` varchar(255) NOT NULL,
  `MAIL` varchar(255) NOT NULL,
  `PASSPORT_ID` int(11) NOT NULL,
  `PASSWORD` varchar(255) NOT NULL,
  `ROLE` varchar(255) NOT NULL,
  `STATUS` varchar(255) NOT NULL,
  PRIMARY KEY (`USER_ID`),
  UNIQUE KEY `UK_csdx7w03xd46e93i1joonkgjq` (`PASSPORT_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=18 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `users`;

-- contracts
DROP TABLE IF EXISTS `contracts`;
CREATE TABLE `contracts` (
  `CONTRACT_ID` int(11) NOT NULL AUTO_INCREMENT,
  `PHONE_NUMBER` varchar(255) NOT NULL,
  `CONTRACT_PRICE` double NOT NULL,
  `CONTRACT_STATUS` varchar(255) NOT NULL,
  `CONTRACT_TARIFF_ID` int(11) NOT NULL,
  `CONTRACT_USER_ID` int(11) NOT NULL,
  PRIMARY KEY (`CONTRACT_ID`),
  KEY `FKlq9vwqp9phntpbnojufn9dg9b` (`CONTRACT_TARIFF_ID`),
  KEY `FK5lxif8h3iv2x325n3gdolyrnq` (`CONTRACT_USER_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `contracts`;

-- active options for contract
DROP TABLE IF EXISTS `active_options`;
CREATE TABLE `active_options` (
  `CONTRACT_ID` int(11) NOT NULL,
  `OPTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`CONTRACT_ID`,`OPTION_ID`),
  KEY `FK1er0wdb0mqnu0gqf9ptvln3u1` (`OPTION_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `active_options`;

-- tariffs
DROP TABLE IF EXISTS `tariffs`;
CREATE TABLE `tariffs` (
  `TARIFF_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255),
  `PRICE` double NOT NULL,
  `TARIFF_STATUS` varchar(255) NOT NULL,
  PRIMARY KEY (`TARIFF_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `tariffs`;

-- available options (all options for tariff)
DROP TABLE IF EXISTS `available_options`;
CREATE TABLE `available_options` (
  `TARIFF_ID` int(11) NOT NULL,
  `OPTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`TARIFF_ID`,`OPTION_ID`),
  KEY `FKlris6v3fsn8haf8s4ry6741xt` (`OPTION_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `available_options`;

-- options (all options)
DROP TABLE IF EXISTS `tariff_options`;
CREATE TABLE `tariff_options` (
  `OPTION_ID` int(11) NOT NULL AUTO_INCREMENT,
  `COSTOFADD` double NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `DESCRIPTION` varchar(255),
  `PRICE` double NOT NULL,
  PRIMARY KEY (`OPTION_ID`)
) ENGINE=MyISAM AUTO_INCREMENT=168 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `tariff_options`;

-- excluding options rules
DROP TABLE IF EXISTS`excluding_options`;
CREATE TABLE `excluding_options` (
  `OPTION_ID` int(11) NOT NULL,
  `EXCLUDING_OPTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`OPTION_ID`,`EXCLUDING_OPTION_ID`),
  KEY `FKj4q134nrqprtilqei8x4lc061` (`EXCLUDING_OPTION_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `excluding_options`;

-- related options rules
DROP TABLE IF EXISTS `related_options`;
CREATE TABLE `related_options` (
  `OPTION_ID` int(11) NOT NULL,
  `RELATED_OPTION_ID` int(11) NOT NULL,
  PRIMARY KEY (`OPTION_ID`,`RELATED_OPTION_ID`),
  KEY `FK3hn1qildtpof6an41wityig2o` (`RELATED_OPTION_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `related_options`;

-- list of options, for wich selected option is related
DROP TABLE IF EXISTS `is_related_for`;
CREATE TABLE `is_related_for` (
  `OPTION_ID` int(11) NOT NULL,
  `RELATED_FOR` int(11) NOT NULL,
  PRIMARY KEY (`OPTION_ID`,`RELATED_FOR`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `is_related_for`;

-- data for login tokens (remember me function)
DROP TABLE IF EXISTS `persistent_logins`;
CREATE TABLE `persistent_logins` (
  `username` varchar(64) NOT NULL,
  `series` varchar(64) NOT NULL,
  `token` varchar(64) NOT NULL,
  `last_used` timestamp NOT NULL,
  PRIMARY KEY (`series`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `persistent_logins`;

-- tariffs for advertisment profiles
DROP TABLE IF EXISTS `adv_profile_tariffs`;
CREATE TABLE `adv_profile_tariffs` (
  `IMG` varchar(255) DEFAULT NULL,
  `advProfile_ADV_PROFILE_ID` int(11) NOT NULL,
  `tariff_TARIFF_ID` int(11) NOT NULL,
  PRIMARY KEY (`advProfile_ADV_PROFILE_ID`,`tariff_TARIFF_ID`),
  KEY `FKde76pulkphfhiipwg1jrc5jdn` (`tariff_TARIFF_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `persistent_logins`;

-- advertisment profiles
DROP TABLE IF EXISTS `adv_profile`;
CREATE TABLE `adv_profile` (
  `ADV_PROFILE_ID` int(11) NOT NULL AUTO_INCREMENT,
  `NAME` varchar(255) NOT NULL,
  `STATUS` varchar(255) NOT NULL,
  PRIMARY KEY (`ADV_PROFILE_ID`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
TRUNCATE `adv_profile`;

