CREATE DATABASE  IF NOT EXISTS `ecare_test` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `ecare_test`;
-- MySQL dump 10.13  Distrib 5.6.17, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: ecare_test
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
  PRIMARY KEY (`client_id`),
  UNIQUE KEY `email_UNIQUE` (`email`),
  UNIQUE KEY `client_id_UNIQUE` (`client_id`)
) ENGINE=InnoDB AUTO_INCREMENT=314 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contract`
--

LOCK TABLES `contract` WRITE;
/*!40000 ALTER TABLE `contract` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=196 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `option1`
--

LOCK TABLES `option1` WRITE;
/*!40000 ALTER TABLE `option1` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=220 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `tariff`
--

LOCK TABLES `tariff` WRITE;
/*!40000 ALTER TABLE `tariff` DISABLE KEYS */;
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

-- Dump completed on 2014-12-03 15:15:05
