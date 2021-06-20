/*
SQLyog Ultimate v12.09 (64 bit)
MySQL - 5.7.30-log : Database - epandb
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`epandb` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `epandb`;

/*Table structure for table `realfile` */

DROP TABLE IF EXISTS `realfile`;

CREATE TABLE `realfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `signKey` varchar(1000) NOT NULL,
  `sourceType` int(11) NOT NULL DEFAULT '0',
  `filePath` varchar(5000) NOT NULL,
  `fileSize` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `realfile` */

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(500) NOT NULL,
  `password` varchar(2000) NOT NULL,
  `diskSize` bigint(20) NOT NULL DEFAULT '0',
  `usedDiskSize` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `userinfo` */

/*Table structure for table `virtualfile` */

DROP TABLE IF EXISTS `virtualfile`;

CREATE TABLE `virtualfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000',
  `fileType` int(10) unsigned zerofill NOT NULL DEFAULT '0000000000',
  `fileName` varchar(2000) NOT NULL,
  `userInfoId` int(11) NOT NULL,
  `realFileId` int(11) NOT NULL,
  `createTime` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

/*Data for the table `virtualfile` */

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
