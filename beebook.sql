CREATE DATABASE  IF NOT EXISTS `bookStore2` ;
USE `bookStore2`;


-- Table structure for table `author`
--
DROP TABLE IF EXISTS `author`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `author` (
                          `id` int NOT NULL AUTO_INCREMENT,
                          `created_by` varchar(100) DEFAULT NULL,
                          `created_date` datetime(6) DEFAULT NULL,
                          `updated_by` varchar(100) DEFAULT NULL,
                          `updated_date` datetime(6) DEFAULT NULL,
                          `biography` varchar(10000) NOT NULL,
                          `name` varchar(100) NOT NULL,
                          `status` bit(1) DEFAULT NULL,
                          PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `blog`
--

DROP TABLE IF EXISTS `blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog` (
                        `id` int NOT NULL AUTO_INCREMENT,
                        `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                        `created_date` datetime(6) DEFAULT NULL,
                        `status` bit(1) DEFAULT NULL,
                        `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                        `updated_date` datetime(6) DEFAULT NULL,
                        `avatar` varchar(255) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                        `detail_description` longtext CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                        `seo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                        `short_description` varchar(3000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                        `title` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                        `category_blog_id` int DEFAULT NULL,
                        PRIMARY KEY (`id`),
                        KEY `FKacv04twhvx3lfcinfgiorumoe` (`category_blog_id`),
                        CONSTRAINT `FKacv04twhvx3lfcinfgiorumoe` FOREIGN KEY (`category_blog_id`) REFERENCES `category_blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
--
-- Table structure for table `blog_images`
--

DROP TABLE IF EXISTS `blog_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `blog_images` (
                               `id` int NOT NULL AUTO_INCREMENT,
                               `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                               `created_date` datetime(6) DEFAULT NULL,
                               `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                               `updated_date` datetime(6) DEFAULT NULL,
                               `path` varchar(200) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                               `title` varchar(500) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                               `blogId` int DEFAULT NULL,
                               `status` bit(1) DEFAULT NULL,
                               `blog_id` int DEFAULT NULL,
                               PRIMARY KEY (`id`),
                               KEY `FKc6sowbfwd9c60y1wky0p40fhv` (`blogId`),
                               KEY `FKick7fmlc9g77g6yxscfssqqwu` (`blog_id`),
                               CONSTRAINT `FKc6sowbfwd9c60y1wky0p40fhv` FOREIGN KEY (`blogId`) REFERENCES `blog` (`id`),
                               CONSTRAINT `FKick7fmlc9g77g6yxscfssqqwu` FOREIGN KEY (`blog_id`) REFERENCES `blog` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=11 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `category`
--

DROP TABLE IF EXISTS `category`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                            `created_date` datetime DEFAULT NULL,
                            `updated_date` datetime DEFAULT NULL,
                            `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                            `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                            `seo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                            `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                            `status` bit(1) DEFAULT NULL,
                            PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=61 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `category_blog`
--

DROP TABLE IF EXISTS `category_blog`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `category_blog` (
                                 `id` int NOT NULL AUTO_INCREMENT,
                                 `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                 `created_date` datetime(6) DEFAULT NULL,
                                 `status` bit(1) DEFAULT NULL,
                                 `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                 `updated_date` datetime(6) DEFAULT NULL,
                                 `description` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                 `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                                 `seo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                                 PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=12 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `contact`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `contact` (
                           `id` int NOT NULL AUTO_INCREMENT,
                           `name` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                           `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                           `massage` varchar(1000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                           `created_date` datetime DEFAULT NULL,
                           `updated_date` datetime DEFAULT NULL,
                           `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                           `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                           `status` bit(1) DEFAULT NULL,
                           PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=44 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `contact`


DROP TABLE IF EXISTS `manufacturer`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `manufacturer` (
                                `id` int NOT NULL AUTO_INCREMENT,
                                `created_by` varchar(100) DEFAULT NULL,
                                `created_date` datetime(6) DEFAULT NULL,
                                `updated_by` varchar(100) DEFAULT NULL,
                                `updated_date` datetime(6) DEFAULT NULL,
                                `address` varchar(1000) NOT NULL,
                                `name` varchar(100) NOT NULL,
                                `status` bit(1) DEFAULT NULL,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `manufacturer`
--
DROP TABLE IF EXISTS `products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products` (
                            `id` int NOT NULL AUTO_INCREMENT,
                            `title` varchar(100) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                            `short_description` varchar(3000) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                            `detail_description` longtext CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                            `price` decimal(13,2) NOT NULL,
                            `status` tinyint(1) DEFAULT '1',
                            `created_date` datetime DEFAULT NULL,
                            `updated_date` datetime DEFAULT NULL,
                            `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                            `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                            `category_id` int DEFAULT NULL,
                            `avatar` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                            `publication_year` varchar(10) COLLATE utf8mb4_bin NOT NULL,
                            `seo` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                            `release_date` date DEFAULT NULL,
                            `quantity` int DEFAULT NULL,
                            `author_id` int DEFAULT NULL,
                            `manufacturer_id` int DEFAULT NULL,
                            `promotion_id` int DEFAULT NULL,
                            PRIMARY KEY (`id`),
                            KEY `fk_1category_Nproducts_idx` (`category_id`),
                            KEY `FK349h4nldfqhip5m79o7swci56` (`author_id`),
                            KEY `FK66j9cu8i4a1smpjx25x160hip` (`manufacturer_id`),
                            KEY `FK29528cf325gklw0smnbu3wr5o` (`promotion_id`),
                            CONSTRAINT `FK29528cf325gklw0smnbu3wr5o` FOREIGN KEY (`promotion_id`) REFERENCES `promotion` (`id`),
                            CONSTRAINT `FK349h4nldfqhip5m79o7swci56` FOREIGN KEY (`author_id`) REFERENCES `author` (`id`),
                            CONSTRAINT `FK66j9cu8i4a1smpjx25x160hip` FOREIGN KEY (`manufacturer_id`) REFERENCES `manufacturer` (`id`),
                            CONSTRAINT `fk_1category_Nproducts` FOREIGN KEY (`category_id`) REFERENCES `category` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB AUTO_INCREMENT=760 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `products_images`
--

DROP TABLE IF EXISTS `products_images`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `products_images` (
                                   `id` int NOT NULL AUTO_INCREMENT,
                                   `title` varchar(500) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                   `path` varchar(200) CHARACTER SET utf8mb3 COLLATE utf8mb3_general_ci NOT NULL,
                                   `product_id` int NOT NULL,
                                   `created_date` datetime DEFAULT NULL,
                                   `updated_date` datetime DEFAULT NULL,
                                   `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                   `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                   `status` bit(1) DEFAULT NULL,
                                   PRIMARY KEY (`id`),
                                   KEY `fk_1products_Nproducts_images_idx` (`product_id`),
                                   CONSTRAINT `FKjac7pn534bktj4tvkxqvydglf` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=81 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


DROP TABLE IF EXISTS `promotion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `promotion` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `created_by` varchar(100) DEFAULT NULL,
                             `created_date` datetime(6) DEFAULT NULL,
                             `updated_by` varchar(100) DEFAULT NULL,
                             `updated_date` datetime(6) DEFAULT NULL,
                             `end_date` date DEFAULT NULL,
                             `name` varchar(1000) NOT NULL,
                             `percent` double DEFAULT NULL,
                             `start_date` date DEFAULT NULL,
                             `status` bit(1) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=5 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_0900_ai_ci;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `roles`
--

DROP TABLE IF EXISTS `roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `roles` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                         `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                         `created_date` datetime(6) DEFAULT NULL,
                         `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                         `updated_date` datetime(6) DEFAULT NULL,
                         `status` bit(1) DEFAULT NULL,
                         `description` varchar(45) COLLATE utf8mb4_bin NOT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `name_UNIQUE` (`name`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `roles`
--

LOCK TABLES `roles` WRITE;
/*!40000 ALTER TABLE `roles` DISABLE KEYS */;
INSERT INTO `roles` VALUES (3,'ADMIN',NULL,NULL,NULL,NULL,NULL,''),(4,'USER',NULL,NULL,NULL,NULL,NULL,''),(5,'GUEST',NULL,NULL,NULL,NULL,NULL,' ');
/*!40000 ALTER TABLE `roles` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `saleorder`
--

DROP TABLE IF EXISTS `saleorder`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saleorder` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `code` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                             `customer_name` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                             `customer_address` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                             `customer_phone` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                             `customer_email` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                             `status` tinyint(1) DEFAULT NULL,
                             `created_date` datetime DEFAULT NULL,
                             `updated_date` datetime DEFAULT NULL,
                             `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                             `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                             `payment_status` enum('PENDING','SHIPPING','DELIVERED','CANCELLED') COLLATE utf8mb4_bin DEFAULT NULL,
                             `reason` varchar(255) COLLATE utf8mb4_bin DEFAULT NULL,
                             `total_price` decimal(38,2) DEFAULT NULL,
                             `payment_type` enum('ONLINE','OFFLINE') COLLATE utf8mb4_bin DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=136 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;


--
-- Table structure for table `saleorder_products`
--

DROP TABLE IF EXISTS `saleorder_products`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `saleorder_products` (
                                      `id` int NOT NULL AUTO_INCREMENT,
                                      `saleorder_id` int NOT NULL,
                                      `product_id` int NOT NULL,
                                      `quantity` int NOT NULL,
                                      `updated_date` datetime DEFAULT NULL,
                                      `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                      `total` double DEFAULT NULL,
                                      `status` bit(1) DEFAULT NULL,
                                      `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                                      `created_date` datetime(6) DEFAULT NULL,
                                      PRIMARY KEY (`id`),
                                      KEY `FKnyfdau9vc46jkiwvrgj1ns85v` (`product_id`),
                                      KEY `FKnpyir3q973iv4wq49ltw0kcrd` (`saleorder_id`),
                                      CONSTRAINT `FKnpyir3q973iv4wq49ltw0kcrd` FOREIGN KEY (`saleorder_id`) REFERENCES `saleorder` (`id`),
                                      CONSTRAINT `FKnyfdau9vc46jkiwvrgj1ns85v` FOREIGN KEY (`product_id`) REFERENCES `products` (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=133 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `subscribe`
--

DROP TABLE IF EXISTS `subscribe`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `subscribe` (
                             `id` int NOT NULL AUTO_INCREMENT,
                             `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                             `created_date` datetime DEFAULT NULL,
                             `updated_date` datetime DEFAULT NULL,
                             `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                             `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                             `status` bit(1) DEFAULT NULL,
                             PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=107 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users`
--

DROP TABLE IF EXISTS `users`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users` (
                         `id` int NOT NULL AUTO_INCREMENT,
                         `username` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                         `status` tinyint(1) DEFAULT NULL,
                         `created_date` datetime DEFAULT NULL,
                         `updated_date` datetime DEFAULT NULL,
                         `created_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                         `updated_by` varchar(100) COLLATE utf8mb4_bin DEFAULT NULL,
                         `password` varchar(100) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                         `email` varchar(45) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin NOT NULL,
                         `address` varchar(1000) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                         `phone` varchar(10) CHARACTER SET utf8mb4 COLLATE utf8mb4_bin DEFAULT NULL,
                         PRIMARY KEY (`id`),
                         UNIQUE KEY `username_UNIQUE` (`username`),
                         UNIQUE KEY `email_UNIQUE` (`email`),
                         UNIQUE KEY `UKj562wwmipqt96rkoqbo0jc34` (`email`)
) ENGINE=InnoDB AUTO_INCREMENT=52 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Table structure for table `users_roles`
--

DROP TABLE IF EXISTS `users_roles`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `users_roles` (
                               `user_id` int NOT NULL,
                               `role_id` int NOT NULL,
                               PRIMARY KEY (`user_id`,`role_id`),
                               KEY `fk_1roles_Nusers_roles_idx` (`role_id`),
                               CONSTRAINT `fk_1roles_Nusers_roles` FOREIGN KEY (`role_id`) REFERENCES `roles` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
                               CONSTRAINT `fk_1users_Nusers_roles` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_bin;
/*!40101 SET character_set_client = @saved_cs_client */;
