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
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `realfile` */

insert  into `realfile`(`id`,`signKey`,`sourceType`,`filePath`,`fileSize`) values (1,'324bc65f180d1aa3f3e87051eb98b1cd',1,'/epan-upyun/2021/07/13/ad9abf17-93d9-4b3d-b0d3-dc909efde7c8',28886);

/*Table structure for table `userinfo` */

DROP TABLE IF EXISTS `userinfo`;

CREATE TABLE `userinfo` (
  `id` int(11) NOT NULL AUTO_INCREMENT,
  `userName` varchar(500) NOT NULL,
  `password` varchar(2000) NOT NULL,
  `diskSize` bigint(20) NOT NULL DEFAULT '0',
  `usedDiskSize` bigint(20) NOT NULL DEFAULT '0',
  `ipAddress` varchar(2000) NOT NULL,
  `createTime` datetime NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `userinfo` */

insert  into `userinfo`(`id`,`userName`,`password`,`diskSize`,`usedDiskSize`,`ipAddress`,`createTime`) values (1,'root','root',10485760,57772,'127.0.0.1','2021-07-12 18:24:04'),(4,'中文','dewr',10485760,0,'127.0.0.1','2021-07-13 18:24:09'),(5,'呵呵','erqre',10485760,0,'0:0:0:0:0:0:0:1','2021-07-13 10:21:54');

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
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `virtualfile` */

insert  into `virtualfile`(`id`,`parentId`,`fileType`,`fileName`,`userInfoId`,`realFileId`,`createTime`) values (1,0,1,'maps',1,0,'2021-07-13 09:31:51'),(2,1,0,'你们喜欢的大地图(10p).tmx',1,1,'2021-07-13 09:41:29'),(3,1,0,'你们喜欢的大地图(10p) (1).tmx',1,1,'2021-07-13 10:27:22');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
