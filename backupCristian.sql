-- --------------------------------------------------------
-- Host:                         127.0.0.1
-- Versión del servidor:         8.0.36 - MySQL Community Server - GPL
-- SO del servidor:              Win64
-- HeidiSQL Versión:             12.6.0.6765
-- --------------------------------------------------------

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET NAMES utf8 */;
/*!50503 SET NAMES utf8mb4 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

-- Volcando estructura para tabla partes.clientes
CREATE TABLE IF NOT EXISTS `clientes` (
  `nif` varchar(9) COLLATE utf8mb4_spanish_ci NOT NULL,
  `nombre` varchar(40) COLLATE utf8mb4_spanish_ci NOT NULL,
  `email` varchar(40) COLLATE utf8mb4_spanish_ci NOT NULL,
  `telefono` varchar(12) COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla partes.clientes: ~2 rows (aproximadamente)
DELETE FROM `clientes`;
INSERT INTO `clientes` (`nif`, `nombre`, `email`, `telefono`) VALUES
	('76581933Y', 'John Doe', 'john@example.com', '654987123'),
	('40967544J', 'Juan Nadie', 'juan@email.com', '987456123');

-- Volcando estructura para tabla partes.empleado
CREATE TABLE IF NOT EXISTS `empleado` (
  `nif` varchar(9) COLLATE utf8mb4_spanish_ci NOT NULL,
  `correo` varchar(40) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `nombre` varchar(40) COLLATE utf8mb4_spanish_ci NOT NULL,
  `telefono` varchar(11) COLLATE utf8mb4_spanish_ci DEFAULT NULL,
  `codigo` varchar(6) COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`nif`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla partes.empleado: ~3 rows (aproximadamente)
DELETE FROM `empleado`;
INSERT INTO `empleado` (`nif`, `correo`, `nombre`, `telefono`, `codigo`) VALUES
	('76581933Y', 'cristianchantada@gmail.com', 'Cristian Varela', '622222396', 'OP1234'),
	('40967544J', 'lupita@gmail.com', 'Guadalupe Casas', '982441361', 'OP5678');

-- Volcando estructura para tabla partes.localizaciones
CREATE TABLE IF NOT EXISTS `localizaciones` (
  `id` int NOT NULL AUTO_INCREMENT,
  `direccion` varchar(80) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT '',
  `cp` varchar(5) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT '',
  `localidad` varchar(80) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT '',
  `provincia` varchar(80) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT '',
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla partes.localizaciones: ~2 rows (aproximadamente)
DELETE FROM `localizaciones`;
INSERT INTO `localizaciones` (`id`, `direccion`, `cp`, `localidad`, `provincia`) VALUES
	(1, 'Avenida de Monforte', '27500', 'Chantada', 'Lugo'),
	(2, 'Avenida de Lalín', '27500', 'Chantada', 'Lugo');

-- Volcando estructura para tabla partes.materiales
CREATE TABLE IF NOT EXISTS `materiales` (
  `id` int NOT NULL AUTO_INCREMENT,
  `desc` tinytext COLLATE utf8mb4_spanish_ci NOT NULL,
  `ctdad` int NOT NULL DEFAULT (0),
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla partes.materiales: ~3 rows (aproximadamente)
DELETE FROM `materiales`;
INSERT INTO `materiales` (`id`, `desc`, `ctdad`) VALUES
	(1, 'sacos de cemento', 10),
	(2, 'palé de ladrillo(200)', 1),
	(3, 'manguera de riego(10m)', 1);

-- Volcando estructura para tabla partes.partes
CREATE TABLE IF NOT EXISTS `partes` (
  `id` int NOT NULL AUTO_INCREMENT,
  `cliente` varchar(9) CHARACTER SET utf8mb4 COLLATE utf8mb4_spanish_ci NOT NULL,
  `Obra` tinytext COLLATE utf8mb4_spanish_ci NOT NULL,
  `empleado` varchar(9) COLLATE utf8mb4_spanish_ci NOT NULL,
  `fecha` date NOT NULL,
  `materiales` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `servicios` text COLLATE utf8mb4_spanish_ci NOT NULL,
  `vehiculo` varchar(7) COLLATE utf8mb4_spanish_ci NOT NULL DEFAULT '0',
  `estado` enum('En proceso','Terminado') COLLATE utf8mb4_spanish_ci NOT NULL,
  `localizacion` int NOT NULL DEFAULT '0',
  PRIMARY KEY (`id`),
  KEY `FK_partes_clientes` (`cliente`),
  KEY `Empleado` (`empleado`),
  KEY `Localizacion` (`localizacion`),
  KEY `Vehiculo` (`vehiculo`),
  CONSTRAINT `Empleado` FOREIGN KEY (`empleado`) REFERENCES `empleado` (`nif`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `FK_partes_clientes` FOREIGN KEY (`cliente`) REFERENCES `clientes` (`nif`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Localizacion` FOREIGN KEY (`localizacion`) REFERENCES `localizaciones` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  CONSTRAINT `Vehiculo` FOREIGN KEY (`vehiculo`) REFERENCES `vehiculos` (`matricula`) ON DELETE CASCADE ON UPDATE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla partes.partes: ~1 rows (aproximadamente)
DELETE FROM `partes`;
INSERT INTO `partes` (`id`, `cliente`, `Obra`, `empleado`, `fecha`, `materiales`, `servicios`, `vehiculo`, `estado`, `localizacion`) VALUES
	(1, '76581933Y', 'Revisar enchufes FdV', '40967544J', '2024-03-08', 'Material: Cinta aislante | Cantidad: 1', '
 SERVICIO: Retirada de enchufes viejos|  FECHA INICIO: Fri Mar 08 19:34:56 CET 2024|  FECHA FIN: Fri Mar 08 19:34:58 CET 2024
', '4378JHX', 'Terminado', 1),
	(2, '40967544J', 'Desatascar retrete', '76581933Y', '2024-03-10', 'Material: Bomba aspiración | Cantidad: 1', ' SERVICIO: Desatascamos wc con bomba de aspiración y limpiamos local|  FECHA INICIO: Sun Mar 10 19:39:24 CET 2024|  FECHA FIN: Sun Mar 10 19:39:28 CET 2024', '4378JHX', 'En Proceso', 2);;

-- Volcando estructura para tabla partes.servicios
CREATE TABLE IF NOT EXISTS `servicios` (
  `id` int NOT NULL AUTO_INCREMENT,
  `horaEntrada` time NOT NULL,
  `horaSalida` time NOT NULL,
  `fecha` date NOT NULL,
  `descripcion` text COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`id`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla partes.servicios: ~2 rows (aproximadamente)
DELETE FROM `servicios`;clientes
INSERT INTO `servicios` (`id`, `horaEntrada`, `horaSalida`, `fecha`, `descripcion`) VALUES
	(1, '09:00:00', '12:00:00', '2024-01-16', 'Reparación pared'),
	(2, '16:00:00', '18:00:00', '2024-01-16', 'Limpieza y riego');

-- Volcando estructura para tabla partes.vehiculos
CREATE TABLE IF NOT EXISTS `vehiculos` (
  `matricula` varchar(7) COLLATE utf8mb4_spanish_ci NOT NULL,
  `marca` varchar(40) COLLATE utf8mb4_spanish_ci NOT NULL,
  `modelo` varchar(40) COLLATE utf8mb4_spanish_ci NOT NULL,
  PRIMARY KEY (`matricula`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_spanish_ci;

-- Volcando datos para la tabla partes.vehiculos: ~3 rows (aproximadamente)
DELETE FROM `vehiculos`;
INSERT INTO `vehiculos` (`matricula`, `marca`, `modelo`) VALUES
	('0688KHX', 'Hyundai', 'Tucson'),
	('3995CXC', 'Ford', 'Focus'),
	('4378JHX', 'VW', 'Golf');

/*!40103 SET TIME_ZONE=IFNULL(@OLD_TIME_ZONE, 'system') */;
/*!40101 SET SQL_MODE=IFNULL(@OLD_SQL_MODE, '') */;
/*!40014 SET FOREIGN_KEY_CHECKS=IFNULL(@OLD_FOREIGN_KEY_CHECKS, 1) */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40111 SET SQL_NOTES=IFNULL(@OLD_SQL_NOTES, 1) */;
