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
-- Table structure for table `student`
--

CREATE TABLE IF NOT EXISTS `student` (
  `id` int(10) NOT NULL,
  `userName` varchar(100) NOT NULL,
  `password` varchar(200) NOT NULL,
  `lName` varchar(50) NOT NULL,
  `fName` varchar(50) NOT NULL,
  `topic` varchar(50) NOT NULL,
  `link` varchar(100) NOT NULL,
  `status` varchar(50) NOT NULL,
  `time` varchar(50) NOT NULL,
  `school` varchar(50) NOT NULL,
  `section` int(50) NOT NULL,
  `role` int(10) NOT NULL,
  `session` varchar(50) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8;

--
-- Dumping data for table `student`
--

INSERT INTO `student` (`id`, `userName`, `password`, `lName`, `fName`, `topic`, `link`, `status`, `time`, `school`, `section`, `role`, `session`) VALUES
(1, 'sj2416', '123', 'Jhaveri', 'Sweta', '', '', 'alive', '2015-11-13 00:00:00', 'RIT', 1, 2, 'plkm52vn1rpncgafsq9f3tqh02'),
(2, 've8951', '123', 'Elango', 'Vivekanand', '', '', 'alive', '2015-11-12 00:00:00', 'RIT', 1, 1, 'loegjb7bcp9mvt1eocsiof9ag5');

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
