-- phpMyAdmin SQL Dump
-- version 5.2.0
-- https://www.phpmyadmin.net/
--
-- Servidor: localhost:3306
-- Tiempo de generación: 29-11-2022 a las 05:56:33
-- Versión del servidor: 10.4.24-MariaDB
-- Versión de PHP: 8.1.6

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `cuponex`
--
drop database if exists cuponex;
create database cuponex;
use cuponex;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `catalogo`
--

CREATE TABLE `catalogo` (
  `idCatalogo` int(11) NOT NULL,
  `idCategoria` int(11) DEFAULT NULL,
  `nombre` varchar(500) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `catalogo`
--

INSERT INTO `catalogo` (`idCatalogo`, `idCategoria`, `nombre`) VALUES
(1, NULL, 'categoria_E'),
(2, NULL, 'estatus_Promocion'),
(3, NULL, 'estatus_Empresa'),
(101, 1, 'Super Mercado'),
(102, 1, 'Restaurante'),
(103, 1, 'Farmacia'),
(104, 1, 'Cafeteria'),
(201, 2, 'activo'),
(202, 2, 'inactivo'),
(301, 3, 'activo'),
(302, 3, 'inactivo');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `empresa`
--

CREATE TABLE `empresa` (
  `idEmpresa` int(11) NOT NULL,
  `nombre` varchar(30) DEFAULT NULL,
  `nombreCom` varchar(30) DEFAULT NULL,
  `representante` varchar(100) DEFAULT NULL,
  `correo` varchar(200) DEFAULT NULL,
  `direccion` varchar(500) DEFAULT NULL,
  `codigo_P` varchar(5) DEFAULT NULL,
  `ciudad` varchar(300) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `paginaW` varchar(500) DEFAULT NULL,
  `rfc` varchar(500) DEFAULT NULL,
  `idEstatusE` int(11) DEFAULT NULL,
  `idCategoriaE` int(11) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `empresa`
--

INSERT INTO `empresa` (`idEmpresa`, `nombre`, `nombreCom`, `representante`, `correo`, `direccion`, `codigo_P`, `ciudad`, `telefono`, `paginaW`, `rfc`, `idEstatusE`, `idCategoriaE`) VALUES
(1, 'Corporativo Soriana', 'Soriana', 'Ricardo Martín Bringas', 'claudiaigr@soriana.com', 'Adolfo Lopez Mateos 201', '64651', 'Mexico', '2281743940', 'https://www.organizacionsoriana.com/equipo_directivo.html', '3102-A', 301, 101),
(2, 'Farmacias Similares S.A de C.V', 'Farmacias Similares', 'Manuel Riberto Quiroga', 'drsimi@gmail.com', 'Independencia No. 26', '03630', 'Mexico', '2281932846', 'https://farmaciasdesimilares.com/#!/', '3251-B', 301, 103);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `promocion`
--

CREATE TABLE `promocion` (
  `idPromocion` int(11) NOT NULL,
  `nPromocion` varchar(50) DEFAULT NULL,
  `descripcion` varchar(300) DEFAULT NULL,
  `fecha_In` date DEFAULT NULL,
  `fecha_Fn` date DEFAULT NULL,
  `restricciones` varchar(300) DEFAULT NULL,
  `porcentDesc` int(11) DEFAULT NULL,
  `costoProm` int(11) DEFAULT NULL,
  `tipoProm` varchar(40) DEFAULT NULL,
  `idCategoriaE` int(11) DEFAULT NULL,
  `is_active` boolean DEFAULT NULL,
  `imagen` longblob DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sucursal`
--

CREATE TABLE `sucursal` (
  `idSucursal` int(11) NOT NULL,
  `idEmpresa` int(11) DEFAULT NULL,
  `nombre` varchar(50) DEFAULT NULL,
  `direccion` varchar(300) DEFAULT NULL,
  `codigo_P` varchar(5) DEFAULT NULL,
  `colonia` varchar(500) DEFAULT NULL,
  `ciudad` varchar(100) DEFAULT NULL,
  `telefono` varchar(10) DEFAULT NULL,
  `latitud` float DEFAULT NULL,
  `longitud` float DEFAULT NULL,
  `encargado` varchar(100) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `sucursal`
--

INSERT INTO `sucursal` (`idSucursal`, `idEmpresa`, `nombre`, `direccion`, `codigo_P`, `colonia`, `ciudad`, `telefono`, `latitud`, `longitud`, `encargado`) VALUES
(1, 1, 'afsaafsad', 'Siempre viva no.6', '91030', 'jgfjj', 'lklkj', '2283614879', 92.756, 81.345, 'yo');

INSERT INTO `sucursal` (`idSucursal`, `idEmpresa`, `nombre`, `direccion`, `codigo_P`, `colonia`, `ciudad`, `telefono`, `latitud`, `longitud`, `encargado`) VALUES
(2, 1, 'sucursal 2', 'Siempre viva no.7', '91031', 'asdasd', 'asdasd', '2283614880', 92.760, 81.350, 'alguien');

INSERT INTO `sucursal` (`idSucursal`, `idEmpresa`, `nombre`, `direccion`, `codigo_P`, `colonia`, `ciudad`, `telefono`, `latitud`, `longitud`, `encargado`) VALUES
(3, 1, 'sucursal 3', 'Siempre viva no.8', '91030', 'jccccccccc', 'ccccc', '2283614878', 92.556, 81.545, 'el');
-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `uadmin`
--

CREATE TABLE `uadmin` (
  `idUAdmin` int(11) NOT NULL,
  `nombre` varchar(25) DEFAULT NULL,
  `apellidoP` varchar(25) DEFAULT NULL,
  `apellidoM` varchar(25) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `password` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `uadmin`
--

INSERT INTO `uadmin` (`idUAdmin`, `nombre`, `apellidoP`, `apellidoM`, `correo`, `password`) VALUES
(1, 'Roberto', 'Mendez', 'Martinez', 'roberto@gmail.com', '54321'),
(3, 'Juanito', 'Andorro', 'Cortez', 'juanito@gmail.com', '12345');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuariom`
--

CREATE TABLE `usuariom` (
  `idUserM` int(11) NOT NULL,
  `nombre` varchar(25) DEFAULT NULL,
  `apellidoP` varchar(200) DEFAULT NULL,
  `apellidoM` varchar(200) DEFAULT NULL,
  `telefono` varchar(50) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `direccion` varchar(600) DEFAULT NULL,
  `fechaNac` date DEFAULT NULL,
  `password` varchar(15) DEFAULT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

--
-- Volcado de datos para la tabla `usuariom`
--

INSERT INTO `usuariom` (`idUserM`, `nombre`, `apellidoP`, `apellidoM`, `telefono`, `correo`, `direccion`, `fechaNac`, `password`) VALUES
(1, 'Jazmin', 'Gomez', 'Baez', '2283614879', 'jazmin@gmail.com', 'calle girasoles num 56', '1999-11-12', '12345'),
(2, 'Pablo', 'Ortiz', 'Paez', '2282392843', 'pablo@gmail.com', 'calle tornasoles num 29', '2000-06-11', '98765'),
(3, 'Lechugin', 'Romero', 'Mancilla', '2282234313', 'lechun@gmail.com', 'calle mirasoles num 21', '2000-03-20', '43231');

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `catalogo`
--
ALTER TABLE `catalogo`
  ADD PRIMARY KEY (`idCatalogo`);

--
-- Indices de la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD PRIMARY KEY (`idEmpresa`),
  ADD KEY `fk_empresa1` (`idEstatusE`),
  ADD KEY `fk_empresa2` (`idCategoriaE`);

--
-- Indices de la tabla `promocion`
--
ALTER TABLE `promocion`
  ADD PRIMARY KEY (`idPromocion`),
  ADD KEY `fk_promocion1` (`idCategoriaE`);

--
-- Indices de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD PRIMARY KEY (`idSucursal`),
  ADD KEY `fk_sucursal` (`idEmpresa`);

--
-- Indices de la tabla `uadmin`
--
ALTER TABLE `uadmin`
  ADD PRIMARY KEY (`idUAdmin`);

--
-- Indices de la tabla `usuariom`
--
ALTER TABLE `usuariom`
  ADD PRIMARY KEY (`idUserM`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `empresa`
--
ALTER TABLE `empresa`
  MODIFY `idEmpresa` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `promocion`
--
ALTER TABLE `promocion`
  MODIFY `idPromocion` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `sucursal`
--
ALTER TABLE `sucursal`
  MODIFY `idSucursal` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=2;

--
-- AUTO_INCREMENT de la tabla `uadmin`
--
ALTER TABLE `uadmin`
  MODIFY `idUAdmin` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- AUTO_INCREMENT de la tabla `usuariom`
--
ALTER TABLE `usuariom`
  MODIFY `idUserM` int(11) NOT NULL AUTO_INCREMENT, AUTO_INCREMENT=4;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `empresa`
--
ALTER TABLE `empresa`
  ADD CONSTRAINT `fk_empresa1` FOREIGN KEY (`idEstatusE`) REFERENCES `catalogo` (`idCatalogo`),
  ADD CONSTRAINT `fk_empresa2` FOREIGN KEY (`idCategoriaE`) REFERENCES `catalogo` (`idCatalogo`);

--
-- Filtros para la tabla `promocion`
--
ALTER TABLE `promocion`
  ADD CONSTRAINT `fk_promocion1` FOREIGN KEY (`idCategoriaE`) REFERENCES `catalogo` (`idCatalogo`);

--
-- Filtros para la tabla `sucursal`
--
ALTER TABLE `sucursal`
  ADD CONSTRAINT `fk_sucursal` FOREIGN KEY (`idEmpresa`) REFERENCES `empresa` (`idEmpresa`);
COMMIT;

--
-- 
--

CREATE TABLE `promocion_sucursal` (
  `idPromocion` int(11) NOT NULL,
  `idSucursal` int(11) NOT NULL,
  FOREIGN KEY (`idPromocion`) REFERENCES `promocion`(`idPromocion`),
  FOREIGN KEY (`idSucursal`) REFERENCES `sucursal`(`idSucursal`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4;

insert into promocion VALUES (null, 'promo1', 'una promocion', '2022-12-1', '2022-12-20', '', 5, 2000, 'descuento', 1, true, null);
insert into promocion_sucursal VALUES (1, 1);


/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
