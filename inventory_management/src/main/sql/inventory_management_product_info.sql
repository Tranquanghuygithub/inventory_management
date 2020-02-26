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
-- Table structure for table `product_info`
--

DROP TABLE IF EXISTS `product_info`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `product_info` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `CATE_ID` int(10) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `CODE` varchar(255) NOT NULL,
  `DESCRIPTION` text,
  `IMG_URL` varchar(255) NOT NULL,
  `ACTIVE_FLAG` int(10) NOT NULL DEFAULT '1',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`),
  KEY `FKPRODUCT_IN949901` (`CATE_ID`),
  CONSTRAINT `FKPRODUCT_IN949901` FOREIGN KEY (`CATE_ID`) REFERENCES `category` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `product_info`
--

LOCK TABLES `product_info` WRITE;
/*!40000 ALTER TABLE `product_info` DISABLE KEYS */;
INSERT INTO `product_info` VALUES (1,5,'Huawei P30 Pro - ChÃ­nh hÃ£ng','Huawei P30','dsfad','/upload/1582047787205_platinum.png',1,'2020-02-18 17:27:11','2020-02-21 08:41:49'),(2,3,'Giay The Thao','fiasudfhi','asdasdasdas','/upload/1582088272271_1575139597080.jpg',1,'2020-02-19 04:57:52','2020-02-19 04:57:52'),(3,9,'Giày Th&#7875; Thao Cao C&#7845;p Nam Bitis Hunter X - Summer 2K19 ADVENTURE COLLECTION - Orange DSMH01100CAM (Cam)','DSMH01100CAM','Giày Th&#7875; Thao Cao C&#7845;p Nam Bitis Hunter X - Summer 2K19 ADVENTURE COLLECTION - Orange DSMH01100CAM (Cam)','/upload/1582255325418_1575385202441.jpg',1,'2020-02-21 03:22:05','2020-02-21 08:47:10'),(4,9,'Bittis Hunter X','BTHT','daiusdhaosihdnlaksdnla','/upload/1582273030486_1575140483997.jpg',1,'2020-02-21 08:17:10','2020-02-21 08:17:10'),(5,10,'XiaoMi Mi8 120GB','XiaoMi Mi8 1','From China','/upload/1582274673911_1575361859715.jpg',1,'2020-02-21 08:44:34','2020-02-24 15:48:19'),(6,5,'SamSung Galaxy S12','SSS12','Form Korea','/upload/1582274807173_1575312568033.jpg',1,'2020-02-21 08:46:47','2020-02-24 17:08:09');
/*!40000 ALTER TABLE `product_info` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-26 13:08:19
