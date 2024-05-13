CREATE DATABASE  IF NOT EXISTS `develop` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `develop`;
-- MySQL dump 10.13  Distrib 8.0.31, for Win64 (x86_64)
--
-- Host: localhost    Database: develop
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `account`
--

DROP TABLE IF EXISTS `account`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `account` (
  `number` bigint NOT NULL,
  `balance` double NOT NULL,
  `client_nit` bigint NOT NULL,
  `initial_balance` double NOT NULL,
  `state` bit(1) NOT NULL,
  `type` varchar(255) NOT NULL,
  PRIMARY KEY (`number`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `account`
--

LOCK TABLES `account` WRITE;
/*!40000 ALTER TABLE `account` DISABLE KEYS */;
INSERT INTO `account` VALUES (1002,2000,345678901,2000,_binary '\0','CORRIENTE'),(1003,1500,234567890,1500,_binary '','AHORRO'),(1004,3000,456789012,3000,_binary '','CORRIENTE'),(1005,8800,123456789,8800,_binary '','AHORRO'),(1006,1800,123456789,1800,_binary '\0','CORRIENTE'),(1008,8500,123456789,8500,_binary '\0','CORRIENTE'),(1001,8800,123456789,8800,_binary '\0','CORRIENTE'),(1009,8800,123456789,8800,_binary '','AHORRO'),(1011,8577,123456789,8800,_binary '','AHORRO');
/*!40000 ALTER TABLE `account` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `client`
--

DROP TABLE IF EXISTS `client`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `client` (
  `nit` bigint NOT NULL,
  `address` varchar(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL,
  `name` varchar(255) NOT NULL,
  `password` varchar(255) NOT NULL,
  `phone_number` bigint DEFAULT NULL,
  `state` bit(1) NOT NULL,
  PRIMARY KEY (`nit`)
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `client`
--

LOCK TABLES `client` WRITE;
/*!40000 ALTER TABLE `client` DISABLE KEYS */;
INSERT INTO `client` VALUES (123456789,'123 Main Street','MALE','John Doe','password123',1234567890,_binary '\0'),(987654321,'456 Elm Street','FEMALE','Alice Smith','alice123',9876543210,_binary ''),(456789012,'789 Oak Avenue','MALE','Bob Johnson','bob456',4567890123,_binary ''),(234567890,'321 Maple Lane','OTHER','Emily Brown','emily789',2345678901,_binary ''),(567890123,'654 Pine Road','MALE','Michael Wilson','michael567',5678901234,_binary ''),(345678901,'987 Beach Medellin','OTHER','Sophia Lee','sophia123',3456789012,_binary ''),(1245,'Test Address','MALE','testName','testPassword',123456789,_binary ''),(748596,'987 Birch Boulevard','OTHER','Norman Pale','pws1234',85967452,_binary '');
/*!40000 ALTER TABLE `client` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `movement`
--

DROP TABLE IF EXISTS `movement`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `movement` (
  `id` bigint NOT NULL AUTO_INCREMENT,
  `date` date NOT NULL,
  `initial_balance` double NOT NULL,
  `type` varchar(255) NOT NULL,
  `value` double NOT NULL,
  `account_number` bigint NOT NULL,
  PRIMARY KEY (`id`),
  KEY `FKt7emx63h1am1e7d6ml8cfmlh4` (`account_number`)
) ENGINE=MyISAM AUTO_INCREMENT=16 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `movement`
--

LOCK TABLES `movement` WRITE;
/*!40000 ALTER TABLE `movement` DISABLE KEYS */;
INSERT INTO `movement` VALUES (10,'2024-05-12',8800,'RETIRO',-111,1011),(11,'2024-05-12',8689,'RETIRO',-112,1011);
/*!40000 ALTER TABLE `movement` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-05-13 16:14:03
