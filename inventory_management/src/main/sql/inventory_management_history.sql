CREATE DATABASE  IF NOT EXISTS `inventory_management` /*!40100 DEFAULT CHARACTER SET utf8 */;
USE `inventory_management`;
-- MySQL dump 10.13  Distrib 8.0.12, for Win64 (x86_64)
--
-- Host: localhost    Database: inventory_management
-- ------------------------------------------------------
-- Server version	8.0.12

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
 SET NAMES utf8 ;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `history`
--

DROP TABLE IF EXISTS `history`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `history` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `ACTION_NAME` varchar(255) NOT NULL,
  `TYPE` int(10) NOT NULL,
  `PRODUCT_ID` int(10) NOT NULL,
  `QTY` int(10) NOT NULL,
  `PRICE` decimal(19,0) NOT NULL,
  `ACTIVE_FLAG` int(10) NOT NULL DEFAULT '1',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FKHISTORY812233` (`PRODUCT_ID`),
  CONSTRAINT `FKHISTORY812233` FOREIGN KEY (`PRODUCT_ID`) REFERENCES `product_info` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `history`
--

LOCK TABLES `history` WRITE;
/*!40000 ALTER TABLE `history` DISABLE KEYS */;
INSERT INTO `history` VALUES (34,'Add',1,4,5,1000000,1,'2020-02-21 11:36:05','2020-02-21 11:36:05'),(35,'Edit',1,4,5,100000,1,'2020-02-21 11:36:14','2020-02-21 11:36:14'),(36,'Edit',1,4,2,100000,1,'2020-02-21 11:36:38','2020-02-21 11:36:38'),(37,'Edit',1,4,2,200000,1,'2020-02-21 14:24:19','2020-02-21 14:24:19'),(38,'Add',1,2,5,1000000,1,'2020-02-21 14:25:22','2020-02-21 14:25:22'),(39,'Add',1,4,5,1000000,1,'2020-02-21 14:25:56','2020-02-21 14:25:56'),(40,'Add',2,2,3,1000000,1,'2020-02-22 09:13:56','2020-02-22 09:13:56'),(41,'Edit',1,2,5,1000000,1,'2020-02-22 09:15:17','2020-02-22 09:15:17'),(42,'Add',1,4,5,1000000,1,'2020-02-22 09:16:57','2020-02-22 09:16:57'),(43,'Add',2,2,5,1000000,1,'2020-02-22 09:21:29','2020-02-22 09:21:29');
/*!40000 ALTER TABLE `history` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-26 13:08:20