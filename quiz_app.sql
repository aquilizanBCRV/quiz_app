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
  `studentID` int NOT NULL,
  `userID` int DEFAULT NULL,
  `teacherID` int DEFAULT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Student`
--

LOCK TABLES `Student` WRITE;
/*!40000 ALTER TABLE `Student` DISABLE KEYS */;
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
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `Teacher`
--

LOCK TABLES `Teacher` WRITE;
/*!40000 ALTER TABLE `Teacher` DISABLE KEYS */;
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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `accountUser`
--

LOCK TABLES `accountUser` WRITE;
/*!40000 ALTER TABLE `accountUser` DISABLE KEYS */;
INSERT INTO `accountUser` VALUES (1,'Vince2026','rendervoid2026','Vince Michael','Belangel',NULL,'Admin');
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
) ENGINE=InnoDB AUTO_INCREMENT=40 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `answerLog`
--

LOCK TABLES `answerLog` WRITE;
/*!40000 ALTER TABLE `answerLog` DISABLE KEYS */;
INSERT INTO `answerLog` VALUES (33,6,1,'1','00:00:03'),(34,8,1,'2','00:00:02'),(35,9,1,'2','00:00:02'),(36,11,1,'0','00:00:03'),(37,12,1,'1','00:00:22'),(38,11,1,'0','00:00:04'),(39,12,1,'1','00:00:02');
/*!40000 ALTER TABLE `answerLog` ENABLE KEYS */;
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
  `timestamp` datetime DEFAULT NULL,
  `deadline` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`quizGroupID`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizGroup`
--

LOCK TABLES `quizGroup` WRITE;
/*!40000 ALTER TABLE `quizGroup` DISABLE KEYS */;
INSERT INTO `quizGroup` VALUES (1,1,'Science 1',0,NULL,'2026-04-27 22:19:17.098711');
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
) ENGINE=InnoDB AUTO_INCREMENT=13 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `quizes`
--

LOCK TABLES `quizes` WRITE;
/*!40000 ALTER TABLE `quizes` DISABLE KEYS */;
INSERT INTO `quizes` VALUES (11,1,'2','Hello','','0'),(12,1,'2','Hello?','','1');
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

-- Dump completed on 2026-04-27  0:38:46
