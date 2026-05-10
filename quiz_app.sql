CREATE DATABASE  IF NOT EXISTS `quiz_application` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_0900_ai_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `quiz_application`;
-- MySQL dump 10.13  Distrib 8.0.45, for Linux (x86_64)
--
-- Host: localhost    Database: quiz_application
-- ------------------------------------------------------
-- Server version	8.0.45-0ubuntu0.24.04.1

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
-- Table structure for table `Student`
--

DROP TABLE IF EXISTS `Student`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Student` (
  `studentID` int NOT NULL AUTO_INCREMENT,
  `userID` int DEFAULT NULL,
  `teacherID` int DEFAULT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Student`
--

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;
INSERT INTO `Student` VALUES (1,1,1),(3,7,1);
/*!40000 ALTER TABLE `Student` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `Teacher`
--

DROP TABLE IF EXISTS `Teacher`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `Teacher` (
  `teacherID` int NOT NULL AUTO_INCREMENT,
  `userID` int DEFAULT NULL,
  PRIMARY KEY (`teacherID`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Teacher`
--

LOCK TABLES `Teacher` WRITE;
/*!40000 ALTER TABLE `Teacher` DISABLE KEYS */;
INSERT INTO `Teacher` VALUES (1,2);
/*!40000 ALTER TABLE `Teacher` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `accountUser`
--

DROP TABLE IF EXISTS `accountUser`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `accountUser` (
  `userID` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `middleName` varchar(45) NOT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `roles` varchar(10) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountUser`
--

LOCK TABLES `accountUser` WRITE;
/*!40000 ALTER TABLE `accountUser` DISABLE KEYS */;
INSERT INTO `accountUser` VALUES (1,'Vince2026','rendervoid2026','Vince Michael','B','Aquilizan','Student'),(2,'yuzuki','teacher12','Yuzuki','Kouta','Aquili','Teacher'),(6,'Admin','Admin','Jeff','C','Corazon','Admin'),(7,'li2026','student12','Mary Jane','Li','Lourder','Student');
/*!40000 ALTER TABLE `accountUser` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `answerLog`
--

DROP TABLE IF EXISTS `answerLog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `answerLog` (
  `logID` int NOT NULL AUTO_INCREMENT,
  `quizID` int DEFAULT NULL,
  `studentID` int DEFAULT NULL,
  `studentAnswer` varchar(45) DEFAULT NULL,
  `timestamp` time DEFAULT NULL,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB AUTO_INCREMENT=171 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answerLog`
--

LOCK TABLES `answerLog` WRITE;
/*!40000 ALTER TABLE `answerLog` DISABLE KEYS */;
INSERT INTO `answerLog` VALUES (161,32,1,'0','00:00:00'),(162,33,1,'0','00:00:00'),(163,34,1,'1','00:00:00'),(164,35,1,'3','00:00:00'),(165,36,1,'0','00:00:00'),(166,32,3,'0','00:00:00'),(167,33,3,'0','00:00:00'),(168,34,3,'1','00:00:00'),(169,35,3,'3','00:00:00'),(170,36,3,'0','00:00:00');
/*!40000 ALTER TABLE `answerLog` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `progress`
--

DROP TABLE IF EXISTS `progress`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `progress` (
  `progressID` int NOT NULL AUTO_INCREMENT,
  `studentID` int NOT NULL,
  `quizGroupID` int NOT NULL,
  `quizCounter` int NOT NULL,
  `currentTimestamp` time DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`progressID`)
) ENGINE=InnoDB AUTO_INCREMENT=87 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `progress`
--

LOCK TABLES `progress` WRITE;
/*!40000 ALTER TABLE `progress` DISABLE KEYS */;
INSERT INTO `progress` VALUES (84,1,21,4,'00:00:00','done'),(85,1,25,0,'00:00:00','done'),(86,3,21,4,'00:00:00','done');
/*!40000 ALTER TABLE `progress` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizGroup`
--

DROP TABLE IF EXISTS `quizGroup`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizGroup` (
  `quizGroupID` int NOT NULL AUTO_INCREMENT,
  `teacherID` int DEFAULT NULL,
  `quizName` varchar(100) DEFAULT NULL,
  `hasTime` tinyint DEFAULT NULL,
  `timestamp` int DEFAULT NULL,
  `published` tinyint DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  PRIMARY KEY (`quizGroupID`)
) ENGINE=InnoDB AUTO_INCREMENT=26 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizGroup`
--

LOCK TABLES `quizGroup` WRITE;
/*!40000 ALTER TABLE `quizGroup` DISABLE KEYS */;
INSERT INTO `quizGroup` VALUES (21,1,'Programming I',0,0,1,'2026-05-06'),(25,1,'Web 1',1,2,1,'2026-05-11');
/*!40000 ALTER TABLE `quizGroup` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `quizes`
--

DROP TABLE IF EXISTS `quizes`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `quizes` (
  `quizesID` int NOT NULL AUTO_INCREMENT,
  `quizGroupID` int DEFAULT NULL,
  `quizType` varchar(45) DEFAULT NULL,
  `displayQuestion` longtext,
  `questionList` longtext,
  `quizAnswer` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`quizesID`)
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizes`
--

LOCK TABLES `quizes` WRITE;
/*!40000 ALTER TABLE `quizes` DISABLE KEYS */;
INSERT INTO `quizes` VALUES (29,16,'1','What is 1+1 is equal to ?','2,1,3,12','0'),(30,16,'2','Are you real?','','0'),(31,18,'1','What is div?','A countainer,Amlm,Q3_text,ads kasdkl','0'),(32,21,'1','What does CPU stand for?',' Central Processing Unit,Computer Personal Unit,Central Power Unit,Control Processing Utility  \n\n','0'),(33,21,'2','In binary, the number 1010 equals 10 in decimal.','','0'),(34,21,'2',' Python is a compiled programming language.\n\n','','1'),(35,21,'1','Which of the following is NOT a programming paradigm?\n\n','Object-Oriented Programming\n\n,Functional Programming\n\n, Procedural Programming\n\n,Binary Programming  \n\n','3'),(36,21,'2','RAM is a type of volatile memory.\n\n','','0'),(39,25,'2','Is www is stand for World Wide Web?','','0');
/*!40000 ALTER TABLE `quizes` ENABLE KEYS */;
UNLOCK TABLES;
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2026-05-10 10:55:32
