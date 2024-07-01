-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Host: 127.0.0.1
-- Generation Time: Jul 01, 2024 at 06:52 PM
-- Server version: 10.4.32-MariaDB
-- PHP Version: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Database: `bidding_system`
--

-- --------------------------------------------------------

--
-- Table structure for table `bid`
--

CREATE TABLE `bid` (
  `id` bigint(20) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `amount` double NOT NULL,
  `bid_time` datetime(6) NOT NULL,
  `comment` text DEFAULT NULL,
  `status` enum('ACCEPTED','LOST','PENDING','REJECTED','TOP','WIN') NOT NULL,
  `session_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `bid`
--

INSERT INTO `bid` (`id`, `created_by`, `amount`, `bid_time`, `comment`, `status`, `session_id`) VALUES
(37, 1, 1, '2024-07-01 17:05:12.000000', 'Bid rejected due the amount lower then price!', 'REJECTED', 1),
(38, 1, 192, '2024-07-01 17:05:26.000000', NULL, 'ACCEPTED', 1),
(39, 1, 1, '2024-07-01 17:34:10.000000', 'Bid rejected due the amount lower then price!', 'REJECTED', 2),
(40, 1, 1, '2024-07-01 17:43:57.000000', 'Bid rejected due the amount lower then price!', 'REJECTED', 2),
(41, 1, 601, '2024-07-01 17:44:36.000000', NULL, 'TOP', 2),
(42, 1, 282, '2024-07-01 17:44:58.000000', NULL, 'TOP', 3),
(43, 1, 1, '2024-07-01 18:35:06.000000', 'Bid rejected due the amount lower then price!', 'REJECTED', 7),
(44, 1, 1, '2024-07-01 18:35:31.000000', 'Bid rejected due the amount lower then price!', 'REJECTED', 8),
(45, 1, 2, '2024-07-01 20:28:50.000000', 'Bid rejected due the amount lower then price!', 'REJECTED', 1),
(46, 1, 3, '2024-07-01 20:29:02.000000', 'Bid rejected due the amount lower then price!', 'REJECTED', 1),
(47, 1, 1, '2024-07-01 20:29:37.000000', 'Bid rejected due the amount lower then price!', 'REJECTED', 1),
(48, 1, 604.25, '2024-07-01 20:36:11.000000', NULL, 'TOP', 1);

-- --------------------------------------------------------

--
-- Table structure for table `item`
--

CREATE TABLE `item` (
  `id` bigint(20) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` double DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `item`
--

INSERT INTO `item` (`id`, `created_by`, `description`, `name`, `price`) VALUES
(1, 2, NULL, 'Tasty Cotton Towels', 191.33),
(2, 1, NULL, 'Tasty Steel Shoes', 600.56),
(3, 1, NULL, 'Small Wooden Sausages', 281.27);

-- --------------------------------------------------------

--
-- Table structure for table `session`
--

CREATE TABLE `session` (
  `id` bigint(20) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `description` text DEFAULT NULL,
  `duration_minute` bigint(20) DEFAULT NULL,
  `start_time` datetime(6) NOT NULL,
  `title` varchar(255) NOT NULL,
  `item_id` bigint(20) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `session`
--

INSERT INTO `session` (`id`, `created_by`, `description`, `duration_minute`, `start_time`, `title`, `item_id`) VALUES
(1, 1, 'Oregon protocol leverage leverage leverage', 1, '2024-07-01 13:36:02.000000', 'Refined Granite Chair', 1),
(2, 1, 'Serbia Manager Central CSS value-added', 10, '2024-07-03 23:34:29.000000', 'Handmade Cotton Pizza', 2),
(3, 1, 'SDD value-added Supervisor Toys', 10, '2024-07-03 23:34:29.000000', 'Generic Cotton Mouse', 3),
(4, 1, 'drive out-of-the-box Bedfordshire Malaysia\ndrive out-of-the-box Bedfordshire Malaysia\ndrive out-of-the-box Bedfordshire Malaysia\ndrive out-of-the-box Bedfordshire Malaysia\ndrive out-of-the-box Bedfordshire Malaysia\ndrive out-of-the-box Bedfordshire Malaysia', 10, '2024-07-03 23:34:29.000000', 'Licensed Concrete Car', 3),
(5, 1, 'User-friendly Street SCSI', 10, '2024-07-03 23:34:29.000000', 'Ergonomic Frozen Chicken', 3),
(6, 1, 'Walks 6th', 10, '2024-07-03 23:34:29.000000', 'Fantastic Concrete Car', 3),
(7, 1, 'redefine lavender grey', 10, '2024-07-03 23:34:29.000000', 'Refined Granite Salad', 3),
(8, 1, 'Stravenue Account', 10, '2024-07-03 23:34:29.000000', 'Incredible Concrete Bacon', 3),
(9, 1, 'Fundamental Sleek', 10, '2024-07-01 13:35:41.000000', 'Generic Rubber Cheese', 3);

-- --------------------------------------------------------

--
-- Table structure for table `user`
--

CREATE TABLE `user` (
  `id` bigint(20) NOT NULL,
  `created_by` bigint(20) DEFAULT NULL,
  `email` varchar(255) DEFAULT NULL,
  `first_name` varchar(255) DEFAULT NULL,
  `last_name` varchar(255) DEFAULT NULL,
  `password` varchar(255) DEFAULT NULL,
  `role` enum('ADMIN','BIDDER','USER') DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Dumping data for table `user`
--

INSERT INTO `user` (`id`, `created_by`, `email`, `first_name`, `last_name`, `password`, `role`) VALUES
(1, NULL, 'admin', 'Randy', 'Homenick', '$2a$10$/lqP71iWn/OiB4y70k1cCO1eERb2JQFCtzm7NNrFATSFpB9D6KrKu', 'ADMIN'),
(2, NULL, 'user@yahoo.com', 'Hattie', 'Langosh', '$2a$10$ZvtnUeDjMzlLMH8m9t4U7ehUGCHW6b89g8tVH0NPCL/S9fBLvIwK6', 'USER'),
(3, NULL, 'bidder@yahoo.com', 'Jenifer', 'King', '$2a$10$WI.TZ1Cofk7jDtrmXmAk4.pbX8AqU6XrIaLSjhi6SfAxEhn9vZnhG', 'BIDDER'),
(6, NULL, 'rnreak@gmail.com', 'Vireak', 'Vireak', '$2a$10$4KcZcui9xWctYVBDoC5P8.nxZw4YR7q/Bc6z40d5B9woGk4.TroJa', 'ADMIN');

--
-- Indexes for dumped tables
--

--
-- Indexes for table `bid`
--
ALTER TABLE `bid`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKor2n10utjhbm8kpdsi628ra1d` (`session_id`);

--
-- Indexes for table `item`
--
ALTER TABLE `item`
  ADD PRIMARY KEY (`id`);

--
-- Indexes for table `session`
--
ALTER TABLE `session`
  ADD PRIMARY KEY (`id`),
  ADD KEY `FKqy0rg3x3kjc2fo24a977d5vd5` (`item_id`);

--
-- Indexes for table `user`
--
ALTER TABLE `user`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT for dumped tables
--

--
-- AUTO_INCREMENT for table `bid`
--
ALTER TABLE `bid`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=49;

--
-- AUTO_INCREMENT for table `item`
--
ALTER TABLE `item`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT for table `session`
--
ALTER TABLE `session`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=10;

--
-- AUTO_INCREMENT for table `user`
--
ALTER TABLE `user`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=7;

--
-- Constraints for dumped tables
--

--
-- Constraints for table `bid`
--
ALTER TABLE `bid`
  ADD CONSTRAINT `FKor2n10utjhbm8kpdsi628ra1d` FOREIGN KEY (`session_id`) REFERENCES `session` (`id`);

--
-- Constraints for table `session`
--
ALTER TABLE `session`
  ADD CONSTRAINT `FKqy0rg3x3kjc2fo24a977d5vd5` FOREIGN KEY (`item_id`) REFERENCES `item` (`id`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
