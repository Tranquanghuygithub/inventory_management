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
-- Table structure for table `menu`
--

DROP TABLE IF EXISTS `menu`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
 SET character_set_client = utf8mb4 ;
CREATE TABLE `menu` (
  `ID` int(10) NOT NULL AUTO_INCREMENT,
  `PARENT_ID` int(10) NOT NULL,
  `URL` varchar(255) NOT NULL,
  `NAME` varchar(255) NOT NULL,
  `ORDER_INDEX` int(10) NOT NULL DEFAULT '1',
  `ACTIVE_FLAG` int(10) NOT NULL DEFAULT '1',
  `CREATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  `UPDATE_DATE` timestamp NOT NULL DEFAULT CURRENT_TIMESTAMP,
  PRIMARY KEY (`ID`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `menu`
--

LOCK TABLES `menu` WRITE;
/*!40000 ALTER TABLE `menu` DISABLE KEYS */;
INSERT INTO `menu` VALUES (1,0,'/product','Sản phẩm',1,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(2,0,'/stock','Kho',2,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(3,0,'/management','Quản lý',3,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(4,1,'/product-info/list','Danh sách sản phẩm',2,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(5,1,'/category/list','Danh sách category',1,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(6,1,'/category/edit','Sửa',-1,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(7,1,'/category/view','Xem',-1,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(8,1,'/category/add','Thêm mới',-1,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(9,1,'/category/save','Lưu',-1,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(10,1,'/category/delete','Xoá',-1,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(12,2,'/goods-issue/list','Danh sách xuất kho',2,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(13,2,'/product-in-stock/list','Sản phẩm trong kho',3,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(14,2,'/history','Lịch sử kho',4,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(15,3,'/user/list','Danh sách user',1,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(16,3,'/menu/list','Danh sách menu',3,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(17,3,'/role/list','Danh sách quyền',2,1,'2020-02-14 14:02:02','2020-02-14 14:02:02'),(18,1,'/product-info/edit','Sửa',-1,1,'2020-02-18 07:50:49','2020-02-18 07:50:49'),(19,1,'/product-info/view','Xem',-1,1,'2020-02-18 07:50:49','2020-02-18 07:50:49'),(20,1,'/product-info/add','Thêm mới',-1,1,'2020-02-18 07:50:49','2020-02-18 07:50:49'),(21,1,'/product-info/save','Lưu',-1,1,'2020-02-18 07:50:49','2020-02-18 07:50:49'),(22,1,'/product-info/delete','Xoá',-1,1,'2020-02-18 07:50:49','2020-02-18 07:50:49'),(23,2,'/goods-receipt/list','Danh sách nhập kho',1,1,'2020-02-20 07:48:24','2020-02-20 07:48:24'),(24,2,'/goods-receipt/view','Xem',-1,1,'2020-02-20 07:48:24','2020-02-20 07:48:24'),(25,2,'/goods-receipt/add','Thêm mới',-1,1,'2020-02-20 07:48:24','2020-02-20 07:48:24'),(26,2,'/goods-receipt/save','Lưu',-1,1,'2020-02-20 07:48:24','2020-02-20 07:48:24'),(34,2,'/goods-receipt/edit','Sửa',-1,1,'2020-02-20 14:51:26','2020-02-20 14:51:26'),(35,2,'/goods-receipt/export','Xuất Báo Cáo',-1,1,'2020-02-21 17:18:14','2020-02-21 17:18:14'),(36,2,'/goods-issue/view','Xem',-1,1,'2020-02-22 08:47:11','2020-02-22 08:47:11'),(37,2,'/goods-issue/add','Thêm mới',-1,1,'2020-02-22 08:47:11','2020-02-22 08:47:11'),(38,2,'/goods-issue/save','Lưu',-1,1,'2020-02-22 08:47:11','2020-02-22 08:47:11'),(39,2,'/goods-issue/edit','Sửa',-1,1,'2020-02-22 08:47:11','2020-02-22 08:47:11'),(40,2,'/goods-issue/export','Xuất Báo cáo',-1,1,'2020-02-22 08:47:11','2020-02-22 08:47:11'),(45,3,'/user/save','Save',-1,1,'2020-02-22 15:52:56','2020-02-22 15:52:56'),(46,3,'/user/edit','Edit',-1,1,'2020-02-22 15:52:56','2020-02-22 15:52:56'),(47,3,'/user/view','View',-1,1,'2020-02-22 15:52:56','2020-02-22 15:52:56'),(48,3,'/user/add','Add',-1,1,'2020-02-22 15:52:56','2020-02-22 15:52:56'),(49,3,'/role/edit','Sửa',-1,1,'2020-02-23 16:38:45','2020-02-23 16:38:45'),(50,3,'/role/view','xem',-1,1,'2020-02-23 16:38:45','2020-02-23 16:38:45'),(51,3,'/role/add','thêm',-1,1,'2020-02-23 16:38:45','2020-02-23 16:38:45'),(52,3,'/role/save','Lưu',-1,1,'2020-02-23 16:39:23','2020-02-23 16:39:23'),(53,3,'/role/delete','Xóa',-1,1,'2020-02-24 17:39:18','2020-02-24 17:39:18'),(54,3,'/user/delete','Xóa',-1,1,'2020-02-24 17:40:54','2020-02-24 17:40:54'),(56,3,'/menu/change-status','Thay đổi trạng thái',-1,1,'2020-02-25 14:24:36','2020-02-25 14:24:36'),(57,3,'/menu/permission','Menu Permission',-1,1,'2020-02-25 14:24:36','2020-02-25 14:24:36'),(58,3,'/menu/update-permission','Thay đổi permission',-1,1,'2020-02-25 15:12:39','2020-02-25 15:12:39');
/*!40000 ALTER TABLE `menu` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2020-02-26 13:08:18
