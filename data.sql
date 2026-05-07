CREATE DATABASE IF NOT EXISTS `quiz_application`;
USE `quiz_application`;

-- ----------------------------
-- Table structure for Student
-- ----------------------------
DROP TABLE IF EXISTS `Student`;

CREATE TABLE `Student` (
  `studentID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT NULL,
  `teacherID` int(11) DEFAULT NULL,
  PRIMARY KEY (`studentID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Student` VALUES
(1,1,1),
(3,7,1);

-- ----------------------------
-- Table structure for Teacher
-- ----------------------------
DROP TABLE IF EXISTS `Teacher`;

CREATE TABLE `Teacher` (
  `teacherID` int(11) NOT NULL AUTO_INCREMENT,
  `userID` int(11) DEFAULT NULL,
  PRIMARY KEY (`teacherID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `Teacher` VALUES
(1,2);

-- ----------------------------
-- Table structure for accountUser
-- ----------------------------
DROP TABLE IF EXISTS `accountUser`;

CREATE TABLE `accountUser` (
  `userID` int(11) NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `firstname` varchar(45) NOT NULL,
  `middleName` varchar(45) NOT NULL,
  `lastname` varchar(45) DEFAULT NULL,
  `roles` varchar(10) NOT NULL,
  PRIMARY KEY (`userID`),
  UNIQUE KEY `username_UNIQUE` (`username`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `accountUser` VALUES
(1,'Vince2026','rendervoid2026','Vince Michael','B','Aquilizan','Student'),
(2,'yuzuki','teacher12','Yuzuki','Kouta','Aquili','Teacher'),
(6,'Admin','Admin','Jeff','C','Corazon','Admin'),
(7,'li2026','student12','Mary Jane','Li','Lourder','Student');

-- ----------------------------
-- Table structure for answerLog
-- ----------------------------
DROP TABLE IF EXISTS `answerLog`;

CREATE TABLE `answerLog` (
  `logID` int(11) NOT NULL AUTO_INCREMENT,
  `quizID` int(11) DEFAULT NULL,
  `studentID` int(11) DEFAULT NULL,
  `studentAnswer` varchar(45) DEFAULT NULL,
  `timestamp` time DEFAULT NULL,
  PRIMARY KEY (`logID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for progress
-- ----------------------------
DROP TABLE IF EXISTS `progress`;

CREATE TABLE `progress` (
  `progressID` int(11) NOT NULL AUTO_INCREMENT,
  `studentID` int(11) NOT NULL,
  `quizGroupID` int(11) NOT NULL,
  `quizCounter` int(11) NOT NULL,
  `currentTimestamp` time DEFAULT NULL,
  `status` varchar(20) NOT NULL,
  PRIMARY KEY (`progressID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

-- ----------------------------
-- Table structure for quizGroup
-- ----------------------------
DROP TABLE IF EXISTS `quizGroup`;

CREATE TABLE `quizGroup` (
  `quizGroupID` int(11) NOT NULL AUTO_INCREMENT,
  `teacherID` int(11) DEFAULT NULL,
  `quizName` varchar(100) DEFAULT NULL,
  `hasTime` tinyint(1) DEFAULT NULL,
  `timestamp` int(11) DEFAULT NULL,
  `published` tinyint(1) DEFAULT NULL,
  `deadline` date DEFAULT NULL,
  PRIMARY KEY (`quizGroupID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `quizGroup` VALUES
(21,1,'Programming I',0,0,1,'2026-05-06'),
(23,1,'Web 1',0,0,NULL,'2026-05-07');

-- ----------------------------
-- Table structure for quizes
-- ----------------------------
DROP TABLE IF EXISTS `quizes`;

CREATE TABLE `quizes` (
  `quizesID` int(11) NOT NULL AUTO_INCREMENT,
  `quizGroupID` int(11) DEFAULT NULL,
  `quizType` varchar(45) DEFAULT NULL,
  `displayQuestion` longtext,
  `questionList` longtext,
  `quizAnswer` varchar(45) DEFAULT NULL,
  PRIMARY KEY (`quizesID`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

INSERT INTO `quizes` VALUES
(29,16,'1','What is 1+1 is equal to ?','2,1,3,12','0'),
(30,16,'2','Are you real?','','0'),
(31,18,'1','What is div?','A countainer,Amlm,Q3_text,ads kasdkl','0'),
(32,21,'1','What does CPU stand for?',' Central Processing Unit,Computer Personal Unit,Central Power Unit,Control Processing Utility','0'),
(33,21,'2','In binary, the number 1010 equals 10 in decimal.','','0'),
(34,21,'2','Python is a compiled programming language.','','1'),
(35,21,'1','Which of the following is NOT a programming paradigm?','Object-Oriented Programming,Functional Programming,Procedural Programming,Binary Programming','3'),
(36,21,'2','RAM is a type of volatile memory.','','0'),
(38,22,'2','Are you gay?','','1');
