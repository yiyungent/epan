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
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `realfile` */

insert  into `realfile`(`id`,`signKey`,`sourceType`,`filePath`,`fileSize`) values (2,'3455bb57e4b4906bbea67b58cca78fa8',0,'/epan-hdfs/2021/06/21/a2f3e266-769f-4d1d-83db-813899913651',214092195);

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(500) NOT NULL,
  `password` varchar(2000) NOT NULL,
  `diskSize` bigint(20) NOT NULL DEFAULT '0',
  `usedDiskSize` bigint(20) NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4;

/*Data for the table `userinfo` */

insert  into `userinfo`(`id`,`userName`,`password`,`diskSize`,`usedDiskSize`) values (1,'admin','admin',10737418240,0),(2,'mike','mike',10737418240,0);

/*Table structure for table `virtualfile` */

DROP TABLE IF EXISTS `virtualfile`;

CREATE TABLE `virtualfile` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `parentId` int(10) unsigned NOT NULL DEFAULT '0',
  `fileType` int(10) unsigned NOT NULL DEFAULT '0',
  `fileName` varchar(2000) NOT NULL,
  `userInfoId` int(11) NOT NULL,
  `realFileId` int(11) NOT NULL DEFAULT '0',
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `virtualfile` */

insert  into `virtualfile`(`id`,`parentId`,`fileType`,`fileName`,`userInfoId`,`realFileId`,`createTime`) values (5,0,0,'hadoop-2.7.3.tar.gz',1,2,'2021-06-21 06:13:56'),(6,0,1,'hadoop',1,0,'2021-06-21 06:14:18'),(7,6,0,'hadoop-2.7.3.tar.gz',1,2,'2021-06-21 06:14:30');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
