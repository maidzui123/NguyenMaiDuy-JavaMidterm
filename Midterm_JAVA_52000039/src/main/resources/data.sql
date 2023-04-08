-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Máy chủ: 127.0.0.1
-- Thời gian đã tạo: Th4 04, 2023 lúc 02:43 PM
-- Phiên bản máy phục vụ: 10.4.27-MariaDB
-- Phiên bản PHP: 8.2.0

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Cơ sở dữ liệu: `shoes_management`
--

-- --------------------------------------------------------

--
-- Cấu trúc bảng cho bảng `product`
--

CREATE TABLE `product` (
  `id` bigint(20) NOT NULL,
  `brand` varchar(255) DEFAULT NULL,
  `image` varchar(255) DEFAULT NULL,
  `name` varchar(255) DEFAULT NULL,
  `price` int(255) DEFAULT NULL,
  `gender` varchar(255) DEFAULT NULL
) ENGINE=MyISAM DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Đang đổ dữ liệu cho bảng `product`
--

INSERT INTO `product` (`id`, `brand`, `image`, `name`, `price`, `gender`) VALUES
(1, 'Nike', '/images/nike_1.png', 'AIR JORDAN 1 MID SE', 3959000, 'Men'),
(2, 'Nike', '/images/nike_2.png', 'NIKE AIR MAX 90', 3519000, 'Men'),
(3, 'Nike', '/images/nike_3.png', 'NIKE AIR MAX 90 FUTURA', 4409000, 'Men'),
(4, 'Adidas', '/images/adidas_1.png', 'NMD_V3 SHOES', 4200000, 'Men'),
(5, 'Adidas', '/images/adidas_2.png', 'NMD_R1 PRIMEBLUE SHOES', 3900000, 'Men'),
(6, 'Adidas', '/images/adidas_3.png', 'ULTRABOOST LIGHT SHOES', 5200000, 'Women'),
(7, 'Vans', '/images/vans_1.png', 'VANS CHECKERBOARD SLIP-ON CLASSIC BLACK', 1450000, 'Men'),
(8, 'Vans', '/images/vans_2.png', 'VANS CANVAS OLD SKOOL CLASSIC TRUE WHITE', 1750000, 'Women'),
(9, 'Nike', '/images/nike_4.png', 'NIKE AIR MAX 90 G NRG', 4409000, 'Women'),
(10, 'Nike', '/images/nike_5.png', 'Nike Air Force 1', 2929000, 'Women'),
(11, 'Adidas', '/images/adidas_4.png', 'STAN SMITH SHOES', 2600000, 'Women'),
(12, 'Adidas', '/images/adidas_5.png', 'SUPERSTAR SHOES', 2600000, 'Women');

--
-- Chỉ mục cho các bảng đã đổ
--

--
-- Chỉ mục cho bảng `product`
--
ALTER TABLE `product`
  ADD PRIMARY KEY (`id`);

--
-- AUTO_INCREMENT cho các bảng đã đổ
--

--
-- AUTO_INCREMENT cho bảng `product`
--
ALTER TABLE `product`
  MODIFY `id` bigint(20) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=13;
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
