CREATE DATABASE IF NOT EXISTS `caffe`;

use `caffe`;

CREATE TABLE `customer` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `prod_id` varchar(100) NOT NULL,
  `prod_name` varchar(100) NOT NULL,
  `type` varchar(45) NOT NULL,
  `quantity` int NOT NULL,
  `price` double NOT NULL,
  `date` date NOT NULL,
  `image` varchar(500) NOT NULL,
  `em_username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=64 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `employee` (
  `id` int NOT NULL AUTO_INCREMENT,
  `username` varchar(45) NOT NULL,
  `password` varchar(45) NOT NULL,
  `question` varchar(100) NOT NULL,
  `answer` varchar(100) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `product` (
  `id` int NOT NULL AUTO_INCREMENT,
  `prod_id` varchar(100) NOT NULL,
  `prod_name` varchar(100) NOT NULL,
  `type` varchar(45) NOT NULL,
  `stock` int NOT NULL,
  `price` double NOT NULL,
  `status` varchar(45) NOT NULL,
  `image` varchar(100) NOT NULL,
  `date` date NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=8 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;

CREATE TABLE `receipt` (
  `id` int NOT NULL AUTO_INCREMENT,
  `customer_id` int NOT NULL,
  `total` double NOT NULL,
  `date` date NOT NULL,
  `em_username` varchar(100) NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=MyISAM AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;