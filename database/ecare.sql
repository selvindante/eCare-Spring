CREATE DATABASE  IF NOT EXISTS `ecare` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ecare`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ecare
-- ------------------------------------------------------
-- Server version	5.5.40-log

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `client` (
  `name` varchar(60) DEFAULT NULL,
  `lastname` varchar(60) DEFAULT NULL,
  `date` date DEFAULT NULL,
  `passport` bigint(10) DEFAULT NULL,
  `address` varchar(60) DEFAULT NULL,
  `email` varchar(60) NOT NULL,
  `password` varchar(60) NOT NULL,
  `client_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `role` varchar(45) NOT NULL,
  `amount` int(10) NOT NULL DEFAULT '0',
  `enabled` tinyint(1) NOT NULL DEFAULT '1',
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `client_id_UNIQUE` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=3328 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES ('Operator','1',NULL,NULL,'','admin','$2a$10$991vQrXGj0Gcx7Wx6aXJzO41AsEGB/m42oRIOt/U..ORunj.4kuVu',1391,'ROLE_ADMIN',0,1),('Angus','Young','1955-03-31',2132435465,'Scotland','young@mail.com','$2a$10$zLj8rLOrEsFmN5zJ5ghZhuUbQXiC5TIOqJBm29jxJ.kXRJc27pa.e',3312,'ROLE_USER',1000,1),('Richard','Blackmore','1945-04-14',7612534032,'Great Britain','ritchieb@mail.com','$2a$10$QJ9r.HWCVVm2jwUsy4emzeSUNjR.AMWZSuZ5BzrMYRZ8xzbw6Y1Q2',3313,'ROLE_USER',1550,1),('Saul','Hudson','1965-06-23',3290127835,'Great Britain, London','hudson@mail.com','$2a$10$Q132xlw8aAcCJTjTpGHyXepDkot6IZPQ3hrWmD8atTvwv5gfuwriG',3314,'ROLE_USER',150,1),('Brian','Carroll','1969-05-13',7348261743,'USA','bucket@mail.com','$2a$10$QJ9r.HWCVVm2jwUsy4emzeSUNjR.AMWZSuZ5BzrMYRZ8xzbw6Y1Q2',3315,'ROLE_USER',1000,1);
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `connected_option`
--

DROP TABLE IF EXISTS `connected_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `connected_option` (
  `contract_id` bigint(11) NOT NULL,
  `option_id` bigint(11) NOT NULL,
  KEY `option_id_idx` (`option_id`),
  KEY `connected_option_idx` (`contract_id`),
  KEY `connection_idx` (`contract_id`),
  CONSTRAINT `link_with_contract` FOREIGN KEY (`contract_id`) REFERENCES `contract` (`contract_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `connected_option`
--

LOCK TABLES `connected_option` WRITE;
/*!40000 ALTER TABLE `connected_option` DISABLE KEYS */;
INSERT INTO `connected_option` VALUES (1759,7102),(1759,7104),(1759,7103),(1759,7105),(1759,7106),(1760,7097),(1760,7098),(1760,7099),(1760,7101),(1761,7108),(1761,7109),(1761,7110),(1761,7112),(1758,7094),(1758,7095),(1758,7096);
/*!40000 ALTER TABLE `connected_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `contract`
--

DROP TABLE IF EXISTS `contract`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `contract` (
  `number` bigint(11) NOT NULL,
  `tariff_id` bigint(11) DEFAULT NULL,
  `blckd_by_cl` tinyint(1) NOT NULL,
  `blckd_by_op` tinyint(1) NOT NULL,
  `contract_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `client_id` bigint(11) NOT NULL,
  PRIMARY KEY (`contract_id`),
  UNIQUE KEY `contract_id_UNIQUE` (`contract_id`),
  UNIQUE KEY `number_UNIQUE` (`number`),
  KEY `cient_id_idx` (`client_id`),
  KEY `contract_idx` (`tariff_id`),
  CONSTRAINT `client_id` FOREIGN KEY (`client_id`) REFERENCES `client` (`client_id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`tariff_id`) REFERENCES `tariff` (`tariff_id`) ON DELETE SET NULL ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=1787 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
INSERT INTO `contract` VALUES (9210001122,2042,0,0,1758,3312),(9030002233,2043,0,0,1759,3312),(9050005511,2042,0,0,1760,3313),(9040002132,2044,0,0,1761,3314),(9080006754,NULL,0,0,1762,3314);
/*!40000 ALTER TABLE `contract` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `dependent_option`
--

DROP TABLE IF EXISTS `dependent_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `dependent_option` (
  `option_id` bigint(10) NOT NULL,
  `dependent_option_id` bigint(10) NOT NULL,
  KEY `dependence_with_option_idx` (`option_id`),
  CONSTRAINT `dependence_with_option` FOREIGN KEY (`option_id`) REFERENCES `option1` (`option_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `dependent_option`
--

LOCK TABLES `dependent_option` WRITE;
/*!40000 ALTER TABLE `dependent_option` DISABLE KEYS */;
INSERT INTO `dependent_option` VALUES (7102,7103),(7103,7102),(7102,7104),(7104,7102),(7103,7104),(7104,7103),(7108,7109),(7109,7108),(7108,7110),(7110,7108),(7109,7110),(7110,7109),(7118,7120),(7120,7118),(7118,7125),(7125,7118),(7125,7120),(7120,7125),(7121,7122),(7122,7121),(7121,7123),(7123,7121),(7122,7123),(7123,7122),(2,1),(2,3),(3,2),(1,2),(1,3),(3,1),(7200,7202),(7202,7200),(7200,7201),(7202,7201),(7201,7202),(7201,7200);
/*!40000 ALTER TABLE `dependent_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `incompatible_option`
--

DROP TABLE IF EXISTS `incompatible_option`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `incompatible_option` (
  `option_id` bigint(10) NOT NULL,
  `incompatible_option_id` bigint(10) NOT NULL,
  KEY `incompatibility_with_option_idx` (`option_id`),
  CONSTRAINT `incompatibility_with_option` FOREIGN KEY (`option_id`) REFERENCES `option1` (`option_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `incompatible_option`
--

LOCK TABLES `incompatible_option` WRITE;
/*!40000 ALTER TABLE `incompatible_option` DISABLE KEYS */;
INSERT INTO `incompatible_option` VALUES (7111,7112),(7112,7111),(7118,7121),(7121,7118),(7118,7122),(7122,7118),(7118,7123),(7123,7118),(7125,7121),(7121,7125),(7125,7122),(7122,7125),(7125,7123),(7123,7125),(7120,7121),(7121,7120),(7120,7122),(7122,7120),(7120,7123),(7123,7120),(7204,7203),(7203,7204);
/*!40000 ALTER TABLE `incompatible_option` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `option1`
--

DROP TABLE IF EXISTS `option1`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `option1` (
  `option_title` varchar(60) NOT NULL,
  `price` int(10) NOT NULL,
  `cnct_cost` int(10) NOT NULL,
  `option_id` bigint(11) NOT NULL AUTO_INCREMENT,
  `tariff_id` bigint(11) DEFAULT NULL,
  PRIMARY KEY (`option_id`),
  UNIQUE KEY `option_id_UNIQUE` (`option_id`),
  KEY `tariff_id_idx` (`tariff_id`),
  CONSTRAINT `tariff_id` FOREIGN KEY (`tariff_id`) REFERENCES `tariff` (`tariff_id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=7206 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option1`
--

LOCK TABLES `option1` WRITE;
/*!40000 ALTER TABLE `option1` DISABLE KEYS */;
INSERT INTO `option1` VALUES ('Standart calls',3,0,1,1),('Standart sms',1,0,2,1),('Standart mms',4,0,3,1),('Answerphone',4,40,7100,2042),('Autumn calls',3,100,7102,2043),('Autumn sms',1,35,7103,2043),('Autumn mms',5,50,7104,2043),('International calls',10,100,7105,2043),('Answerphone',4,40,7106,2043),('Internet',200,100,7107,2043),('Winter calls',2,120,7108,2044),('Winter sms',1,80,7109,2044),('Winter mms',6,150,7110,2044),('Winter internet',250,100,7111,2044),('Internet',200,250,7112,2044),('Summer calls',2,50,7118,2042),('Summer mms',3,60,7120,2042),('Hot calls',1,60,7121,2042),('Hot sms',1,40,7122,2042),('Hot mms',3,50,7123,2042),('Internet',200,100,7124,2042),('Summer sms',1,30,7125,2042),('Spring calls',2,150,7200,2061),('Spring sms',1,100,7201,2061),('Spring mms',4,120,7202,2061),('Spring internet',150,200,7203,2061),('Internet',200,150,7204,2061),('Answerphone',5,50,7205,2061);
/*!40000 ALTER TABLE `option1` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `tariff`
--

DROP TABLE IF EXISTS `tariff`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `tariff` (
  `tariff_title` varchar(60) NOT NULL,
  `price` int(10) NOT NULL,
  `tariff_id` bigint(11) NOT NULL AUTO_INCREMENT,
  PRIMARY KEY (`tariff_id`),
  UNIQUE KEY `tariff_id_UNIQUE` (`tariff_id`)
) ENGINE=InnoDB AUTO_INCREMENT=2062 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `tariff` WRITE;
/*!40000 ALTER TABLE `tariff` DISABLE KEYS */;
INSERT INTO `tariff` VALUES ('Standard',0,1),('Summer',200,2042),('Autumn',300,2043),('Winter',250,2044),('Spring',150,2061);
/*!40000 ALTER TABLE `tariff` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2014-12-03 15:14:42
