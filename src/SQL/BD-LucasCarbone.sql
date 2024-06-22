CREATE DATABASE  IF NOT EXISTS `lk-cajeros` /*!40100 DEFAULT CHARACTER SET utf8mb3 */ /*!80016 DEFAULT ENCRYPTION='N' */;
USE `lk-cajeros`;
-- MySQL dump 10.13  Distrib 8.0.34, for Win64 (x86_64)
--
-- Host: 127.0.0.1    Database: lk-cajeros
-- ------------------------------------------------------
-- Server version	8.0.31

/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!50503 SET NAMES utf8 */;
/*!40103 SET @OLD_TIME_ZONE=@@TIME_ZONE */;
/*!40103 SET TIME_ZONE='+00:00' */;
/*!40014 SET @OLD_UNIQUE_CHECKS=@@UNIQUE_CHECKS, UNIQUE_CHECKS=0 */;
/*!40014 SET @OLD_FOREIGN_KEY_CHECKS=@@FOREIGN_KEY_CHECKS, FOREIGN_KEY_CHECKS=0 */;
/*!40101 SET @OLD_SQL_MODE=@@SQL_MODE, SQL_MODE='NO_AUTO_VALUE_ON_ZERO' */;
/*!40111 SET @OLD_SQL_NOTES=@@SQL_NOTES, SQL_NOTES=0 */;

--
-- Table structure for table `cajero`
--

DROP TABLE IF EXISTS `cajero`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cajero` (
  `id_cajero` int NOT NULL AUTO_INCREMENT,
  `status` tinyint NOT NULL,
  `cantidad_dinero` decimal(12,2) NOT NULL,
  PRIMARY KEY (`id_cajero`)
) ENGINE=InnoDB AUTO_INCREMENT=9 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cajero`
--

LOCK TABLES `cajero` WRITE;
/*!40000 ALTER TABLE `cajero` DISABLE KEYS */;
INSERT INTO `cajero` VALUES (1,1,32197.00),(2,1,4000.00),(3,0,0.00),(4,1,15500.00),(5,1,100.00),(6,1,22222.00),(7,0,0.00),(8,1,10450.00);
/*!40000 ALTER TABLE `cajero` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `permisos`
--

DROP TABLE IF EXISTS `permisos`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `permisos` (
  `id_permisos` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id_permisos`)
) ENGINE=InnoDB AUTO_INCREMENT=3 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `permisos`
--

LOCK TABLES `permisos` WRITE;
/*!40000 ALTER TABLE `permisos` DISABLE KEYS */;
INSERT INTO `permisos` VALUES (1,'user'),(2,'admin');
/*!40000 ALTER TABLE `permisos` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `cliente`
--

DROP TABLE IF EXISTS `cliente`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `cliente` (
  `id_cliente` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  `password` varchar(255) NOT NULL,
  `saldo` decimal(12,2) NOT NULL,
  `clientCode` varchar(45) NOT NULL,
  `id_permisos` int NOT NULL,
  PRIMARY KEY (`id_cliente`),
  KEY `fk_cliente_permisos1_idx` (`id_permisos`),
  CONSTRAINT `fk_cliente_permisos1` FOREIGN KEY (`id_permisos`) REFERENCES `permisos` (`id_permisos`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `cliente`
--

LOCK TABLES `cliente` WRITE;
/*!40000 ALTER TABLE `cliente` DISABLE KEYS */;
INSERT INTO `cliente` VALUES (1,'admin','admin2024',2924.00,'287364782gajd',2),(3,'test','123',20.00,'cd30df5862',1),(4,'test2','123',6450.00,'d957d31cc1',1),(5,'test3','123',4750.00,'d1c4b5f638',1);
/*!40000 ALTER TABLE `cliente` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `transaccion`
--

DROP TABLE IF EXISTS `transaccion`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `transaccion` (
  `id_transaccion` int NOT NULL AUTO_INCREMENT,
  `id_cajero` int NOT NULL,
  `id_cliente` int NOT NULL,
  `clientCode_receptor` varchar(45) NOT NULL,
  `fecha_y_hora` datetime NOT NULL,
  `monto` decimal(12,2) NOT NULL,
  PRIMARY KEY (`id_transaccion`),
  KEY `fk_transaccion_cajero1_idx` (`id_cajero`),
  KEY `fk_transaccion_cliente1_idx` (`id_cliente`),
  CONSTRAINT `fk_transaccion_cajero1` FOREIGN KEY (`id_cajero`) REFERENCES `cajero` (`id_cajero`),
  CONSTRAINT `fk_transaccion_cliente1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`)
) ENGINE=InnoDB AUTO_INCREMENT=6 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `transaccion`
--

LOCK TABLES `transaccion` WRITE;
/*!40000 ALTER TABLE `transaccion` DISABLE KEYS */;
INSERT INTO `transaccion` VALUES (1,1,1,'d1c4b5f638','2024-06-20 00:00:00',500.00),(2,1,5,'cd30df5862','2024-06-20 00:00:00',500.00),(3,1,5,'287364782gajd','2024-06-20 00:00:00',250.00),(4,1,1,'d957d31cc1','2024-06-21 00:00:00',100.00),(5,1,1,'d957d31cc1','2024-06-21 00:00:00',100.00);
/*!40000 ALTER TABLE `transaccion` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `registro_movimiento`
--

DROP TABLE IF EXISTS `registro_movimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `registro_movimiento` (
  `id_registro_movimiento` int NOT NULL AUTO_INCREMENT,
  `id_cajero` int NOT NULL,
  `id_typeMovimiento` int NOT NULL,
  `id_transaccion` int DEFAULT NULL,
  `id_cliente` int NOT NULL,
  `monto` decimal(12,2) DEFAULT NULL,
  `fecha_y_hora` datetime NOT NULL,
  PRIMARY KEY (`id_registro_movimiento`),
  KEY `fk_movimiento_cliente1_idx` (`id_cliente`),
  KEY `fk_movimiento_typeMovimiento1_idx` (`id_typeMovimiento`),
  KEY `fk_movimiento_transaccion1_idx` (`id_transaccion`),
  KEY `fk_registro_movimiento_cajero1_idx` (`id_cajero`),
  CONSTRAINT `fk_movimiento_cliente1` FOREIGN KEY (`id_cliente`) REFERENCES `cliente` (`id_cliente`),
  CONSTRAINT `fk_movimiento_transaccion1` FOREIGN KEY (`id_transaccion`) REFERENCES `transaccion` (`id_transaccion`),
  CONSTRAINT `fk_movimiento_typeMovimiento1` FOREIGN KEY (`id_typeMovimiento`) REFERENCES `typemovimiento` (`id_typeMovimiento`),
  CONSTRAINT `fk_registro_movimiento_cajero1` FOREIGN KEY (`id_cajero`) REFERENCES `cajero` (`id_cajero`)
) ENGINE=InnoDB AUTO_INCREMENT=65 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `registro_movimiento`
--

LOCK TABLES `registro_movimiento` WRITE;
/*!40000 ALTER TABLE `registro_movimiento` DISABLE KEYS */;
INSERT INTO `registro_movimiento` VALUES (1,2,2,NULL,5,232323.00,'2024-06-20 14:34:48'),(2,2,2,NULL,3,232323.00,'2024-06-20 14:34:48'),(3,8,2,NULL,1,5000.00,'2024-06-20 00:00:00'),(4,8,1,NULL,1,1500.00,'2024-06-20 00:00:00'),(5,8,1,NULL,1,1000.00,'2024-06-20 00:00:00'),(6,8,2,NULL,1,1500.00,'2024-06-20 00:00:00'),(7,1,2,NULL,1,2000.00,'2024-06-20 00:00:00'),(8,1,2,NULL,1,200.00,'2024-06-20 00:00:00'),(9,1,1,NULL,1,3.00,'2024-06-20 00:00:00'),(10,1,1,NULL,1,3.00,'2024-06-20 00:00:00'),(11,8,1,NULL,1,250.00,'2024-06-20 00:00:00'),(12,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(13,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(14,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(15,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(16,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(17,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(18,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(19,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(20,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(21,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(22,1,1,NULL,1,250.00,'2024-06-20 00:00:00'),(23,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(24,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(25,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(26,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(27,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(28,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(29,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(30,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(31,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(32,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(33,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(34,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(35,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(36,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(37,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(38,1,2,NULL,1,250.00,'2024-06-20 00:00:00'),(39,8,2,NULL,3,200.00,'2024-06-20 00:00:00'),(40,1,2,NULL,3,250.00,'2024-06-20 00:00:00'),(41,1,2,NULL,3,250.00,'2024-06-20 00:00:00'),(42,4,2,NULL,3,250.00,'2024-06-20 00:00:00'),(43,1,2,NULL,3,250.00,'2024-06-20 00:00:00'),(44,1,2,NULL,3,250.00,'2024-06-20 00:00:00'),(45,1,2,NULL,3,250.00,'2024-06-20 00:00:00'),(46,4,2,NULL,4,250.00,'2024-06-20 00:00:00'),(47,1,2,NULL,4,6000.00,'2024-06-20 00:00:00'),(48,8,1,NULL,4,450.00,'2024-06-20 00:00:00'),(49,8,2,NULL,4,450.00,'2024-06-20 00:00:00'),(50,1,2,NULL,3,23.00,'2024-06-20 00:00:00'),(51,1,2,NULL,5,3.00,'2024-06-20 00:00:00'),(52,4,2,NULL,5,5000.00,'2024-06-20 00:00:00'),(53,1,2,NULL,5,6.00,'2024-06-20 00:00:00'),(54,1,1,NULL,5,12.00,'2024-06-20 00:00:00'),(55,1,3,2,5,500.00,'2024-06-20 00:00:00'),(56,1,3,3,5,250.00,'2024-06-20 00:00:00'),(57,1,1,NULL,1,20.00,'2024-06-21 00:00:00'),(58,1,2,NULL,1,20.00,'2024-06-21 00:00:00'),(59,1,1,NULL,3,20.00,'2024-06-21 00:00:00'),(60,1,3,4,1,100.00,'2024-06-21 00:00:00'),(61,1,1,NULL,1,20.00,'2024-06-21 00:00:00'),(62,1,3,5,1,100.00,'2024-06-21 00:00:00'),(63,1,2,NULL,1,20.00,'2024-06-21 00:00:00'),(64,1,1,NULL,1,20.00,'2024-06-21 00:00:00');
/*!40000 ALTER TABLE `registro_movimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Table structure for table `typemovimiento`
--

DROP TABLE IF EXISTS `typemovimiento`;
/*!40101 SET @saved_cs_client     = @@character_set_client */;
/*!50503 SET character_set_client = utf8mb4 */;
CREATE TABLE `typemovimiento` (
  `id_typeMovimiento` int NOT NULL AUTO_INCREMENT,
  `nombre` varchar(45) NOT NULL,
  PRIMARY KEY (`id_typeMovimiento`)
) ENGINE=InnoDB AUTO_INCREMENT=4 DEFAULT CHARSET=utf8mb3;
/*!40101 SET character_set_client = @saved_cs_client */;

--
-- Dumping data for table `typemovimiento`
--

LOCK TABLES `typemovimiento` WRITE;
/*!40000 ALTER TABLE `typemovimiento` DISABLE KEYS */;
INSERT INTO `typemovimiento` VALUES (1,'retiro'),(2,'deposito'),(3,'transaccion');
/*!40000 ALTER TABLE `typemovimiento` ENABLE KEYS */;
UNLOCK TABLES;

--
-- Dumping events for database 'lk-cajeros'
--

--
-- Dumping routines for database 'lk-cajeros'
--
/*!40103 SET TIME_ZONE=@OLD_TIME_ZONE */;

/*!40101 SET SQL_MODE=@OLD_SQL_MODE */;
/*!40014 SET FOREIGN_KEY_CHECKS=@OLD_FOREIGN_KEY_CHECKS */;
/*!40014 SET UNIQUE_CHECKS=@OLD_UNIQUE_CHECKS */;
/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
/*!40111 SET SQL_NOTES=@OLD_SQL_NOTES */;

-- Dump completed on 2024-06-22 13:41:41
