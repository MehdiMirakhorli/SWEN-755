-- phpMyAdmin SQL Dump
-- version 4.0.10.8
-- http://www.phpmyadmin.net
--
-- Host: localhost
-- Generation Time: Nov 15, 2015 at 07:31 PM
-- Server version: 5.1.73
-- PHP Version: 5.3.3

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Database: `sj2416`
--

-- --------------------------------------------------------

--
-- Table structure for table `submissions`
--

CREATE TABLE IF NOT EXISTS `submissions` (
  `school` varchar(100) NOT NULL,
  `sec` int(100) NOT NULL,
  `name` varchar(100) NOT NULL,
  `type` varchar(100) NOT NULL,
  `due` varchar(50) NOT NULL,
  `ins` varchar(100) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `submissions`
--

INSERT INTO `submissions` (`school`, `sec`, `name`, `type`, `due`, `ins`) VALUES
('RIT', 1, 'somename', 'sometype', '2015-12-28 00:00:00', 'as');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
