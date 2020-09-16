/*
SQLyog Community v13.1.6 (64 bit)
MySQL - 10.4.13-MariaDB : Database - ppd
*********************************************************************
*/

/*!40101 SET NAMES utf8 */;

/*!40101 SET SQL_MODE=''*/;

/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;
CREATE DATABASE /*!32312 IF NOT EXISTS*/`ppd` /*!40100 DEFAULT CHARACTER SET utf8mb4 */;

USE `ppd`;

/*Table structure for table `contract` */

DROP TABLE IF EXISTS `contract`;

CREATE TABLE `contract` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `dateCreation` date DEFAULT NULL,
  `dateExpiration` date DEFAULT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `user` bigint(20) unsigned NOT NULL,
  `customer` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `user` (`user`),
  KEY `customer` (`customer`),
  CONSTRAINT `contract_ibfk_1` FOREIGN KEY (`user`) REFERENCES `user` (`id`),
  CONSTRAINT `contract_ibfk_2` FOREIGN KEY (`customer`) REFERENCES `customer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4;

/*Data for the table `contract` */

insert  into `contract`(`id`,`dateCreation`,`dateExpiration`,`amount`,`user`,`customer`) values 
(1,'2020-07-01','2021-07-01',11000,1,3),
(2,'2020-07-09','2021-07-09',13000,1,2),
(3,'2020-07-10','2021-07-10',5397,1,7);

/*Table structure for table `contractitem` */

DROP TABLE IF EXISTS `contractitem`;

CREATE TABLE `contractitem` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `itemNumber` bigint(20) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `quantity` bigint(20) DEFAULT NULL,
  `amount` decimal(10,0) DEFAULT NULL,
  `product` bigint(20) unsigned NOT NULL,
  `contract` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `product` (`product`),
  KEY `contract` (`contract`),
  CONSTRAINT `contractitem_ibfk_1` FOREIGN KEY (`product`) REFERENCES `product` (`id`),
  CONSTRAINT `contractitem_ibfk_2` FOREIGN KEY (`contract`) REFERENCES `contract` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4;

/*Data for the table `contractitem` */

insert  into `contractitem`(`id`,`itemNumber`,`price`,`quantity`,`amount`,`product`,`contract`) values 
(1,1,3000,3,9000,2,1),
(2,2,1000,2,2000,3,1),
(3,1,3000,3,9000,2,2),
(4,2,1000,4,4000,3,2),
(5,1,1799,3,5397,8,3);

/*Table structure for table `customer` */

DROP TABLE IF EXISTS `customer`;

CREATE TABLE `customer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `adress` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4;

/*Data for the table `customer` */

insert  into `customer`(`id`,`firstName`,`lastName`,`adress`,`phoneNumber`) values 
(1,'Nikola','Smoljanoivc','Moljakovcka Staze 100','+38176231412'),
(2,'Dimitrije','Aleksic','Knez Mihailova 21','+38165321421'),
(3,'Nikola','Filipovski','Karađorđeva 213','+38162321442'),
(4,'Milos','Klisura','Ulica Kralja Petra Prvog 21','+38162213412'),
(5,'Petar','Jeremic','Jove Ilica 21','+381632316214'),
(7,'Nemanja','Vasiljevic','Milivoje Radovic 56a','+3816576213');

/*Table structure for table `manufacturer` */

DROP TABLE IF EXISTS `manufacturer`;

CREATE TABLE `manufacturer` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `adress` varchar(100) DEFAULT NULL,
  `phoneNumber` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4;

/*Data for the table `manufacturer` */

insert  into `manufacturer`(`id`,`name`,`adress`,`phoneNumber`) values 
(1,'Blue Buffalo Company','Champs-Élysées','+381641259900'),
(2,'Colgate-Palmolive Company','Lombard Street','+381633322787'),
(3,'Mars Petcare','Abbey Road','+381667821341'),
(4,'Nestlé','Fifth Avenue','+381652314213'),
(5,'The J.M. Smucker Company','Santa Monica Boulevard','+381643124214'),
(6,'Beaphar','Ginza','+381641414212'),
(7,'WellPet LLC','Beale Street','+381648684131'),
(8,'Diamond Pet Foods','Bourbon Street','+381644214112'),
(9,'PetGuard','Via Monte Napoleone','+381641412543'),
(10,'Harringtons','Hollywood Walk of Fame','+381644153325');

/*Table structure for table `product` */

DROP TABLE IF EXISTS `product`;

CREATE TABLE `product` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `name` varchar(100) DEFAULT NULL,
  `price` decimal(10,0) DEFAULT NULL,
  `manufacturer` bigint(20) unsigned NOT NULL,
  PRIMARY KEY (`id`),
  KEY `manufacturer` (`manufacturer`),
  CONSTRAINT `product_ibfk_1` FOREIGN KEY (`manufacturer`) REFERENCES `manufacturer` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4;

/*Data for the table `product` */

insert  into `product`(`id`,`name`,`price`,`manufacturer`) values 
(1,'Pet Bed',1000,3),
(2,'Dog Seat Cover',3000,1),
(3,'Multifunction Biting Toys',1000,1),
(4,'Dog Toothbrush',200,10),
(5,'Pet Grooming',760,2),
(6,'Pet Painting',100,5),
(7,'Novelty Cat Beds',999,8),
(8,'Dog Jackets',1799,8),
(11,'Cat Litter Mat',2132,6),
(12,'Personalized Dog Collar',1670,1);

/*Table structure for table `user` */

DROP TABLE IF EXISTS `user`;

CREATE TABLE `user` (
  `id` bigint(20) unsigned NOT NULL AUTO_INCREMENT,
  `firstName` varchar(100) DEFAULT NULL,
  `lastName` varchar(100) DEFAULT NULL,
  `username` varchar(100) DEFAULT NULL,
  `password` varchar(100) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=2 DEFAULT CHARSET=utf8mb4;

/*Data for the table `user` */

insert  into `user`(`id`,`firstName`,`lastName`,`username`,`password`) values 
(1,'Marko','Smoljanovic','a','a');

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;
