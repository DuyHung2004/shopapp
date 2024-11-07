-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Server version:               8.0.40 - MySQL Community Server - GPL
-- Server OS:                    Linux
-- HeidiSQL Version:             12.8.0.6908
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;


-- Dumping database structure for shopapp
CREATE DATABASE IF NOT EXISTS `shopapp` /*!40100 DEFAULT CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `shopapp`;

-- Dumping structure for table shopapp.categories
CREATE TABLE IF NOT EXISTS `categories` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '' COMMENT 'Ten danh muc',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=7 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.categories: ~4 rows (approximately)
INSERT INTO `categories` (`id`, `name`) VALUES
	(1, 'iphone'),
	(2, 'samsung'),
	(3, 'phukien'),
	(4, 'laptop');

-- Dumping structure for table shopapp.orders
CREATE TABLE IF NOT EXISTS `orders` (
  `id` int NOT NULL AUTO_INCREMENT,
  `user_id` int DEFAULT NULL,
  `fullname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `phone_number` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `note` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `order_date` date DEFAULT NULL,
  `status` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `total_money` float DEFAULT NULL,
  `shipping_method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `shipping_address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `shipping_date` date DEFAULT NULL,
  `tracking_number` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `payment_method` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  `active` tinyint(1) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `orders_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`),
  CONSTRAINT `orders_chk_1` CHECK ((`total_money` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=23 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.orders: ~14 rows (approximately)
INSERT INTO `orders` (`id`, `user_id`, `fullname`, `email`, `phone_number`, `address`, `note`, `order_date`, `status`, `total_money`, `shipping_method`, `shipping_address`, `shipping_date`, `tracking_number`, `payment_method`, `active`) VALUES
	(1, 5, 'nguyen duy ', 'dsfn sdf', '0517283895', 'hoang  ha noi', 'van chuyen gio hanh chinh', '2024-10-10', 'pending', 50000, 'tiet kiem', ' so 8', '2024-10-14', 'WKsAVsoDhw', 'cod', 1),
	(2, 5, 'nguyen duy hung', 'asdf', '0517283895', 'hoang long ha noi', 'van chuyen gio hanh chinh', '2024-10-10', 'pending', 500000, 'tiet kiem', 'nha so 8', '2024-10-14', 'tWGyVA3OgB', 'cod', 1),
	(3, 5, 'nguyen duy hung', 'asdf', '0517283895', 'hoang long ha noi', 'van chuyen gio hanh chinh', '2024-10-10', 'pending', 500000, 'tiet kiem', 'nha so 8', '2024-10-14', 'O5z62XCbYT', 'cod', 0),
	(4, 5, 'nguyen duy ', 'dsfn sdf', '0517283895', 'hoang  ha noi', 'van chuyen gio hanh chinh', '2024-10-10', 'pending', 50000, 'tiet kiem', ' so 8', '2024-10-14', 'ExR9g33WVJ', 'cod', 1),
	(7, 5, 'nguyen duy hung', 'asdf', '0517283895', 'hoang long ha noi', 'van chuyen gio hanh chinh', '2024-10-10', 'pending', 500000, 'tiet kiem', 'nha so 8', '2024-10-14', 'BShMluPOVr', 'cod', 1),
	(8, 1, 'nguyen duy ', 'dsfn sdf', '0517283895', 'hoang  ha noi', 'van chuyen gio hanh chinh', '2024-10-10', 'pending', 50000, 'tiet kiem', ' so 8', '2024-10-14', 'WghQ7NZ4zf', 'cod', 1),
	(9, 1, 'nguyen duy ', 'dsfn sdf', '0517283895', 'hoang  ha noi', 'van chuyen gio hanh chinh', '2024-10-11', 'pending', 50000, 'tiet kiem', ' so 8', '2024-10-15', 'ltzDHAlbND', 'cod', 1),
	(10, 2, 'hungas', '', '09517295721', 'hoang long', 'gio hanh chinh', '2024-10-26', 'pending', 20000000, 'giao tiet kiem', 'so 8', '2024-10-30', 'b8p466Umum', 'cod', 1),
	(13, 2, 'Hung333', NULL, '0384867824', 'Khu chợ Hoàng Long', 'giao gio hanh chinh', '2024-10-26', 'pending', 12000000, 'express', 'nha so 8', '2024-10-30', 'ralrqjSbM4', 'cod', 1),
	(18, 2, 'Hung652034', NULL, '0384867824', 'Khu chợ Hoàng Long', 'giao gio hanh chinh', '2024-10-26', 'pending', 12000000, 'economy', 'nha so 8', '2024-10-30', 'l8F0f11pCT', 'transfer', 1),
	(19, 2, 'Hung652034', NULL, '0384867824', 'Khu chợ Hoàng Long', 'giao gio hanh chinh', '2024-10-26', 'pending', 12000000, 'economy', 'nha so 8', '2024-10-30', 'Ialjnripxu', 'transfer', 1),
	(20, 2, 'hungahsdhf', NULL, '0384867824', 'Khu chợ Hoàng Long', 'giao gio hanh chinh', '2024-10-27', 'pending', 12000000, 'economy', 'nha so 8', '2024-10-31', 'ntIYMGrFil', 'transfer', 1),
	(21, 2, 'Hung652034', NULL, '0384867824', 'Khu chợ Hoàng Long', 'giao gio hanh chinh', '2024-10-27', 'pending', 10000000, 'express', 'nha so 8', '2024-10-31', 'Q3YN6n1q6T', 'transfer', 1),
	(22, 2, 'gadf', NULL, '0384867824', 'Khu chợ Hoàng Long', 'giao gio hanh chinh', '2024-10-27', 'pending', 9040000, 'economy', 'nha so 8', '2024-10-31', 'NAQYNsfhgc', 'cod', 1);

-- Dumping structure for table shopapp.orders_details
CREATE TABLE IF NOT EXISTS `orders_details` (
  `id` int NOT NULL AUTO_INCREMENT,
  `order_id` int DEFAULT NULL,
  `product_id` int DEFAULT NULL,
  `price` float DEFAULT NULL,
  `number_of_products` int DEFAULT NULL,
  `total_money` float DEFAULT NULL,
  `color` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  PRIMARY KEY (`id`),
  KEY `order_id` (`order_id`),
  KEY `product_id` (`product_id`),
  CONSTRAINT `orders_details_ibfk_1` FOREIGN KEY (`order_id`) REFERENCES `orders` (`id`),
  CONSTRAINT `orders_details_ibfk_2` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`),
  CONSTRAINT `orders_details_chk_1` CHECK ((`price` >= 0)),
  CONSTRAINT `orders_details_chk_2` CHECK ((`number_of_products` > 0)),
  CONSTRAINT `orders_details_chk_3` CHECK ((`total_money` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=14 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.orders_details: ~13 rows (approximately)
INSERT INTO `orders_details` (`id`, `order_id`, `product_id`, `price`, `number_of_products`, `total_money`, `color`) VALUES
	(1, 1, 15, 5000000, 2, 10000000, 'den'),
	(2, 2, 16, 5000000, 2, 10000000, 'den'),
	(3, 13, 16, 5000000, 2, 10000000, 'den'),
	(4, 18, 21, 4000000, 1, 4000000, ''),
	(5, 18, 20, 4000000, 2, 8000000, ''),
	(6, 19, 21, 4000000, 1, 4000000, ''),
	(7, 19, 20, 4000000, 2, 8000000, ''),
	(8, 20, 21, 4000000, 1, 4000000, ''),
	(9, 20, 20, 4000000, 2, 8000000, ''),
	(10, 21, 16, 5000000, 1, 5000000, ''),
	(11, 21, 15, 5000000, 1, 5000000, ''),
	(12, 22, 23, 4520000, 2, 9040000, ''),
	(13, 22, 16, 5000000, 2, 10000000, 'den');

-- Dumping structure for table shopapp.products
CREATE TABLE IF NOT EXISTS `products` (
  `id` int NOT NULL AUTO_INCREMENT,
  `name` varchar(350) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL COMMENT 'Ten san pham',
  `price` float NOT NULL,
  `thumbnail` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci,
  `create_at` datetime DEFAULT NULL,
  `update_at` datetime DEFAULT NULL,
  `category_id` int DEFAULT NULL,
  `createat` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `category_id` (`category_id`),
  CONSTRAINT `products_ibfk_1` FOREIGN KEY (`category_id`) REFERENCES `categories` (`id`),
  CONSTRAINT `products_chk_1` CHECK ((`price` >= 0))
) ENGINE=InnoDB AUTO_INCREMENT=33 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.products: ~7 rows (approximately)
INSERT INTO `products` (`id`, `name`, `price`, `thumbnail`, `description`, `create_at`, `update_at`, `category_id`, `createat`) VALUES
	(15, 'iphone18', 5000000, 'f092100b-9c35-46b9-98c4-d1629bd0e9c9_combosac1.jpg', 'day la 1 san pham tot, asdfbnccansdasdkcn s cc................\r\n', '2024-10-08 02:24:46', '2024-10-08 02:24:46', 1, NULL),
	(16, 'iphone19', 5000000, 'bc8629c8-e28e-45c8-b350-42e2780b78f1_dt1.jpg', 'day la 1 san pham tot, asdfbnccansdasdkcn s cc................', '2024-10-08 17:36:07', '2024-10-08 17:36:07', 1, NULL),
	(20, 'laptop dell', 4000000, '53e65725-3049-4915-b34a-7de318cc48a3_laptop3.jpg', 'day la 1 san pham tot, asdfbnccansdasdkcn s cc................', '2024-10-21 01:05:46', '2024-10-21 01:05:46', 4, NULL),
	(21, 'laptop dell 2', 4000000, 'b7e50440-930e-429f-9f35-c841e356b678_laptop2.jpg', 'day la 1 san pham tot, asdfbnccansdasdkcn s cc................', '2024-10-21 02:14:49', '2024-10-21 02:14:49', 4, NULL),
	(22, 'phu kien 1', 4520000, 'd7746627-8f1f-4d4d-81b5-1d55e5fa277b_laptop2.jpg', 'day la 1 san pham tot, asdfbnccansdasdkcn s cc................', '2024-10-27 22:04:13', '2024-10-27 22:04:13', 3, NULL),
	(23, 'phu kien 2', 4520000, 'a8b08638-0496-46e2-8e7d-0395236afab4_laptop2.jpg', 'day la 1 san pham tot, asdfbnccansdasdkcn s cc................', '2024-10-27 22:16:34', '2024-10-27 22:16:34', 3, NULL),
	(24, 'iphone 12', 6009510, 'a50b5b19-81b8-47d0-a768-6637f36e7e5d_dt3.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:53:57', '2024-11-07 16:53:57', 1, NULL),
	(25, 'iphone 13', 6009510, 'c1293f68-672f-41f3-a80a-e7fda6373946_dt3.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:54:20', '2024-11-07 16:54:20', 1, NULL),
	(26, 'iphone 14', 6009510, 'aa885cf4-9343-44a4-bc85-8b11a93b5d87_dt3.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:54:24', '2024-11-07 16:54:24', 1, NULL),
	(27, 'iphone 15', 6009510, '73710825-dd23-47f9-be45-49d02646ca40_dt3.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:54:28', '2024-11-07 16:54:28', 1, NULL),
	(28, 'iphone 16', 6009510, '98cea50a-0da4-4f75-9800-fa948b38315c_dt3.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:54:35', '2024-11-07 16:54:35', 1, NULL),
	(29, 'samsung 1', 6009510, 'f8f1bc7a-aea9-4d6c-ab59-e7db70f252f8_dt3.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:54:52', '2024-11-07 16:54:52', 2, NULL),
	(30, 'samsung 2', 6009510, '5ac419c5-7fca-45bc-adb1-9ce7209193ba_dt3.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:54:58', '2024-11-07 16:54:58', 2, NULL),
	(31, 'samsung 3', 6009510, 'c3a4294f-a9f4-455c-8205-da4505caae5c_dt3.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:55:50', '2024-11-07 16:55:50', 2, NULL),
	(32, 'samsung 4', 6009510, '7ce60620-6b7d-4e20-bab3-488362d17e48_dt2.jpg', 'avbausnbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbbb', '2024-11-07 16:56:26', '2024-11-07 16:56:26', 2, NULL);

-- Dumping structure for table shopapp.product_images
CREATE TABLE IF NOT EXISTS `product_images` (
  `id` int NOT NULL AUTO_INCREMENT,
  `product_id` int DEFAULT NULL,
  `image_url` varchar(300) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `fk_product_images_product_id` (`product_id`),
  CONSTRAINT `fk_product_images_product_id` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=75 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.product_images: ~21 rows (approximately)
INSERT INTO `product_images` (`id`, `product_id`, `image_url`) VALUES
	(26, 15, 'f092100b-9c35-46b9-98c4-d1629bd0e9c9_combosac1.jpg'),
	(27, 15, 'a3d858d9-6fc6-43a4-970f-51b952570371_dt1.jpg'),
	(28, 15, '6d869b73-e144-4f77-a7f1-a4952ecf2a01_dt3.jpg'),
	(29, 15, '0d4d1957-44c0-4649-8eb7-cd0ed4183686_dt4.jpg'),
	(30, 16, 'bc8629c8-e28e-45c8-b350-42e2780b78f1_dt1.jpg'),
	(31, 16, 'f52dd52d-2017-4e82-a651-4ae819e232ff_dt3.jpg'),
	(32, 16, '12c080ee-f281-4263-8203-a5e3adee74db_dt4.jpg'),
	(33, 15, '5bb7395a-fe9e-4955-8ab2-5da40fbd3ae1_iphone-15-pro-max-white-thumbnew-600x600.jpg'),
	(34, 16, '8081bbb5-154d-4845-8efa-c333acf349c6_iphone-15-pro-max-white-thumbnew-600x600.jpg'),
	(35, 16, '88d7a0a6-7cc9-4d54-9ae7-44939aa34437_iphone-15-pro-max-white-thumbnew-600x600.jpg'),
	(36, 16, '2d0271ad-09ad-4a64-a324-d27c80982092_capsac1.jpg'),
	(39, 20, 'b94c61d0-c7a2-459f-b3c9-fff3650b25a8_laptop2.jpg'),
	(40, 20, '53e65725-3049-4915-b34a-7de318cc48a3_laptop3.jpg'),
	(41, 20, 'af9a8564-f940-405a-a4d5-d617a99af35a_laptop.jpg'),
	(42, 20, 'f09d2544-3e20-49b0-ba29-4444df83863a_laptop.jpg'),
	(43, 21, 'b7e50440-930e-429f-9f35-c841e356b678_laptop2.jpg'),
	(44, 21, 'a16b2bfc-4b71-4f72-8a9c-93b701323e20_laptop3.jpg'),
	(45, 22, 'd7746627-8f1f-4d4d-81b5-1d55e5fa277b_laptop2.jpg'),
	(46, 22, '1f800913-9354-4a98-a82c-d2578da49a62_laptop3.jpg'),
	(47, 23, 'a8b08638-0496-46e2-8e7d-0395236afab4_laptop2.jpg'),
	(48, 23, 'ecd14721-c99b-4e46-9c32-b712097067c8_laptop3.jpg'),
	(49, 24, 'a50b5b19-81b8-47d0-a768-6637f36e7e5d_dt3.jpg'),
	(50, 24, '7f4ff8a5-63dc-4c97-8877-fabb0ac925e4_dt2.jpg'),
	(51, 24, '39d8b7bc-335f-4a6e-bfd3-51087d9ebbc8_dt1.jpg'),
	(52, 25, 'c1293f68-672f-41f3-a80a-e7fda6373946_dt3.jpg'),
	(53, 25, '51d8b522-30ed-4f53-9569-357ffb68763e_dt2.jpg'),
	(54, 25, '8c78b9a0-af29-4481-9471-6de82fad45ba_dt1.jpg'),
	(55, 26, 'aa885cf4-9343-44a4-bc85-8b11a93b5d87_dt3.jpg'),
	(56, 26, '8c836707-6944-4b76-a005-9b7a43b2059c_dt2.jpg'),
	(57, 26, '886ef442-08b1-4bc1-b097-396a11ac9129_dt1.jpg'),
	(58, 27, '73710825-dd23-47f9-be45-49d02646ca40_dt3.jpg'),
	(59, 27, '14ec85c1-e0ef-4446-a3ee-193dd8bb37f9_dt2.jpg'),
	(60, 27, '43f9d01f-1241-48ca-b014-079aa384fec1_dt1.jpg'),
	(61, 28, '98cea50a-0da4-4f75-9800-fa948b38315c_dt3.jpg'),
	(62, 28, 'ebe931a5-7501-4bb4-b5b1-ed1d39685561_dt2.jpg'),
	(63, 28, '34b33069-2092-41a0-ab5a-66fcbadedeb5_dt1.jpg'),
	(64, 29, 'f8f1bc7a-aea9-4d6c-ab59-e7db70f252f8_dt3.jpg'),
	(65, 29, 'a1fe0913-34db-460d-bddb-0e7d07cd3a60_dt2.jpg'),
	(66, 29, '4578d512-20dc-40aa-8f71-ff0d81377de2_dt1.jpg'),
	(67, 30, '5ac419c5-7fca-45bc-adb1-9ce7209193ba_dt3.jpg'),
	(68, 30, 'dad4d4e7-9a99-4714-a2a6-aa734a78269e_dt2.jpg'),
	(69, 30, '54db5cd5-ef87-4042-8a0d-4e13d7f52bc0_dt1.jpg'),
	(70, 31, 'c3a4294f-a9f4-455c-8205-da4505caae5c_dt3.jpg'),
	(71, 31, '1b13c7d0-227b-4e9c-9d6a-4e0d39a0adac_dt2.jpg'),
	(72, 31, '96b91677-1430-4b90-8c77-44115be7703d_dt1.jpg'),
	(73, 32, '7ce60620-6b7d-4e20-bab3-488362d17e48_dt2.jpg'),
	(74, 32, '7ae4e019-35e1-48a8-aabd-dfbc498ef959_dt1.jpg');

-- Dumping structure for table shopapp.roles
CREATE TABLE IF NOT EXISTS `roles` (
  `id` int NOT NULL,
  `name` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.roles: ~2 rows (approximately)
INSERT INTO `roles` (`id`, `name`) VALUES
	(1, 'user'),
	(2, 'admin');

-- Dumping structure for table shopapp.social_accounts
CREATE TABLE IF NOT EXISTS `social_accounts` (
  `id` int NOT NULL AUTO_INCREMENT,
  `provider` varchar(20) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ten nha social network',
  `provider_id` varchar(50) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `email` varchar(150) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'email tai khoan',
  `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL COMMENT 'ten nguoi dung',
  `user_id` int DEFAULT NULL,
  `user_id_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  UNIQUE KEY `UK_ce69mw78sdsym6nqw6r2ic958` (`user_id_id`),
  KEY `user_id` (`user_id`),
  CONSTRAINT `FKhhnipg3xoimlbtrt9apsmjpa4` FOREIGN KEY (`user_id_id`) REFERENCES `users` (`id`),
  CONSTRAINT `social_accounts_ibfk_1` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.social_accounts: ~0 rows (approximately)

-- Dumping structure for table shopapp.tokens
CREATE TABLE IF NOT EXISTS `tokens` (
  `id` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `expiration_date` datetime(6) DEFAULT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.tokens: ~0 rows (approximately)

-- Dumping structure for table shopapp.users
CREATE TABLE IF NOT EXISTS `users` (
  `id` int NOT NULL AUTO_INCREMENT,
  `fullname` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `phonenumber` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL,
  `address` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci DEFAULT '',
  `pass` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_general_ci NOT NULL DEFAULT '',
  `created_at` datetime DEFAULT NULL,
  `updated_at` datetime DEFAULT NULL,
  `is_active` int NOT NULL,
  `date_of_birth` date DEFAULT NULL,
  `facebook_account_id` int DEFAULT '0',
  `google_account_id` int DEFAULT '0',
  `role_id` int DEFAULT NULL,
  PRIMARY KEY (`id`),
  KEY `role_id` (`role_id`),
  CONSTRAINT `users_ibfk_1` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=15 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

-- Dumping data for table shopapp.users: ~8 rows (approximately)
INSERT INTO `users` (`id`, `fullname`, `phonenumber`, `address`, `pass`, `created_at`, `updated_at`, `is_active`, `date_of_birth`, `facebook_account_id`, `google_account_id`, `role_id`) VALUES
	(1, 'nguyen duy hung', '0387986597', 'Hoang Long', '$2a$10$gFbEUvybKhaDtxVf1T7thOUgvZeDgyaVACmPLsK4Sz2bOaljT1LG2', '2024-09-26 20:08:17', '2024-09-26 20:08:17', 0, '2004-10-13', 0, 0, 1),
	(2, 'admin', '1900168888', 'Hoang Long', '$2a$10$NdpBx/pnp8dvJOhKTpFAf.pmrMbY03U0bck.kN0wzzkW.GXy9ekk.', '2024-09-28 16:48:22', '2024-09-28 16:48:22', 0, '2004-10-13', 0, 0, 2),
	(5, 'huneqw3g53', '0384102856', 'Hoang Long', '$2a$10$0yNopvyZSMBpJWUyQeCmPOQ/4H05y9VOmA6u6.I8Ria9LmsFLXd7G', '2024-10-10 18:07:46', '2024-10-10 18:07:46', 0, '2004-10-13', 0, 0, 1),
	(6, 'Hung333', '0976544401', 'Khu chợ Hoàng Long', '$2a$10$FeslwKJ57UEZoQK1qnS2puIg6.bPuqTrnArbnRzM6BeJ5t9xtyVF6', '2024-10-17 03:34:41', '2024-10-17 03:34:41', 0, '2024-10-08', 0, 0, 1),
	(7, 'Hung652034', '0976544461', 'khu chợ hoàng long phú xuyên hà nội', '$2a$10$F3Z.bTcqxd0IcKCLiG4FA.8cmU2nJLyllNhncC0L3dxBRhDzVi.LS', '2024-10-17 15:52:23', '2024-10-17 15:52:23', 0, '2024-10-10', 0, 0, 1),
	(8, 'Hung652034', '0976544467', 'khu chợ hoàng long phú xuyên hà nội', '$2a$10$kSZ1O/Rlm9dsKfjQMMwGg.VeN3NVPGV/vbjjhqJ87pRdeuFZ1/fGu', '2024-10-17 15:57:36', '2024-10-17 15:57:36', 0, '2024-10-10', 0, 0, 1),
	(9, 'Hung333', '0976544635', 'Khu chợ Hoàng Long', '$2a$10$ShCm4hWvOhtw3KDwjiVdFeQXmm1jzmyybqxBVzDXjr6Z0X5DF/iIK', '2024-10-17 16:52:10', '2024-10-17 16:52:10', 0, '2024-10-01', 0, 0, 1),
	(12, 'Hung333', '0976544406', 'Khu chợ Hoàng Long', '$2a$10$LLlQb0ryhqMiE.VEKZOaaOlLDJNmVne/ypPMrZDqKTBXNMro1up8W', '2024-10-17 19:26:58', '2024-10-17 19:26:58', 0, '2024-10-09', 0, 0, 1),
	(13, 'asdjfa', '0976546351', 'Khu chợ Hoàng Long', '$2a$10$RQNwppD0D6mfOpJQN0BoGexc.dWLBOJ/46PG9n3pVUhgDY0E2RUIq', '2024-11-07 03:07:19', '2024-11-07 03:07:19', 0, '2024-11-27', 0, 0, 1),
	(14, 'agsdf', '0976546352', 'khu chợ', '$2a$10$vPO9XjkB.eHbom4AizqHZebfzYQyDTuP1aGpxgWITJQeJFGAWGkR6', '2024-11-07 03:14:31', '2024-11-07 03:57:05', 0, '2024-11-05', 0, 0, 1);

-- Dumping structure for trigger shopapp.update_thumbnail_after_insert
SET @OLDTMP_SQL_MODE=@@SQL_MODE, SQL_MODE='ONLY_FULL_GROUP_BY,STRICT_TRANS_TABLES,NO_ZERO_IN_DATE,NO_ZERO_DATE,ERROR_FOR_DIVISION_BY_ZERO,NO_ENGINE_SUBSTITUTION';
DELIMITER //
CREATE TRIGGER `update_thumbnail_after_insert` AFTER INSERT ON `product_images` FOR EACH ROW BEGIN
    DECLARE v_thumbnail VARCHAR(255);

    -- Lấy image_url đầu tiên của product vừa được thêm vào
    SELECT image_url INTO v_thumbnail
    FROM product_images
    WHERE product_id = NEW.product_id
    LIMIT 1;

    -- Cập nhật thumbnail trong bảng products
    UPDATE products
    SET thumbnail = v_thumbnail
    WHERE id = NEW.product_id;
END//
DELIMITER ;
SET SQL_MODE=@OLDTMP_SQL_MODE;

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
