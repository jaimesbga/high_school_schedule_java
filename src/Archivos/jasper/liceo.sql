-- phpMyAdmin SQL Dump
-- version 3.2.4
-- http://www.phpmyadmin.net
--
-- Servidor: localhost
-- Tiempo de generación: 20-05-2011 a las 03:44:24
-- Versión del servidor: 5.1.41
-- Versión de PHP: 5.3.1

SET SQL_MODE="NO_AUTO_VALUE_ON_ZERO";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8 */;

--
-- Base de datos: `liceo`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `actividadesdiarias`
--

CREATE TABLE IF NOT EXISTS `actividadesdiarias` (
  `id` int(5) NOT NULL,
  `fecha` date NOT NULL,
  `dia_semana` int(2) NOT NULL,
  `id_seccion` int(10) NOT NULL,
  `tipo_actividad` int(2) NOT NULL,
  `observacion` varchar(100) NOT NULL,
  PRIMARY KEY (`id`),
  KEY `id_seccion` (`id_seccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `actividadesdiarias`
--

INSERT INTO `actividadesdiarias` (`id`, `fecha`, `dia_semana`, `id_seccion`, `tipo_actividad`, `observacion`) VALUES
(1, '2011-05-16', 1, 1, 1, ''),
(2, '2011-05-17', 2, 1, 1, ''),
(3, '2011-05-25', 3, 1, 2, '');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `bloque`
--

CREATE TABLE IF NOT EXISTS `bloque` (
  `id_bloque` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  `descripcion` varchar(50) DEFAULT NULL,
  `estado` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_bloque`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `bloque`
--

INSERT INTO `bloque` (`id_bloque`, `numero`, `descripcion`, `estado`) VALUES
(1, 1, '7-8', 1),
(2, 2, '8-9', 1),
(3, 3, '9-10', 1),
(4, 4, '10-11', 1),
(5, 5, '11-12', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `estudiante`
--

CREATE TABLE IF NOT EXISTS `estudiante` (
  `id_persona` int(11) NOT NULL,
  `numero_lista` int(11) DEFAULT NULL,
  `estado` int(11) NOT NULL DEFAULT '1',
  `nombre_representante` varchar(100) DEFAULT NULL,
  `cedula_representante` varchar(10) DEFAULT NULL,
  `telefono_representante` varchar(15) DEFAULT NULL,
  `id_seccion` int(11) NOT NULL,
  PRIMARY KEY (`id_persona`),
  KEY `fk_estudiante_persona` (`id_persona`),
  KEY `fk_estudiante_seccion` (`id_seccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `estudiante`
--

INSERT INTO `estudiante` (`id_persona`, `numero_lista`, `estado`, `nombre_representante`, `cedula_representante`, `telefono_representante`, `id_seccion`) VALUES
(1, 1, 1, '', '', '', 1),
(2, 2, 1, '', '', '', 1),
(3, 3, 1, '', '', '', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `horario`
--

CREATE TABLE IF NOT EXISTS `horario` (
  `id_horario` int(11) NOT NULL,
  `id_materia` int(11) DEFAULT NULL,
  `id_bloque` int(11) NOT NULL,
  `id_seccion` int(11) NOT NULL,
  `id_persona` int(11) DEFAULT NULL,
  `dia_semana` int(11) NOT NULL,
  `aula` varchar(10) DEFAULT NULL,
  PRIMARY KEY (`id_horario`),
  KEY `fk_horario_materia` (`id_materia`),
  KEY `fk_horario_bloque` (`id_bloque`),
  KEY `fk_horario_seccion` (`id_seccion`),
  KEY `fk_horario_profesor` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `horario`
--

INSERT INTO `horario` (`id_horario`, `id_materia`, `id_bloque`, `id_seccion`, `id_persona`, `dia_semana`, `aula`) VALUES
(1, 1, 1, 1, NULL, 1, NULL),
(2, 1, 2, 1, NULL, 1, NULL),
(3, 2, 3, 1, NULL, 1, NULL),
(4, 2, 4, 1, NULL, 1, NULL),
(5, 2, 5, 1, NULL, 1, NULL),
(6, 2, 1, 1, NULL, 2, NULL),
(7, 2, 2, 1, NULL, 2, NULL),
(8, 1, 1, 1, NULL, 3, NULL),
(9, 2, 2, 1, NULL, 3, NULL),
(10, 3, 3, 1, NULL, 3, NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `inasistencias`
--

CREATE TABLE IF NOT EXISTS `inasistencias` (
  `id_actividadesdiarias` int(10) NOT NULL,
  `id_horario` int(10) NOT NULL,
  `id_estudiante` int(10) NOT NULL,
  KEY `id_actividadesdiarias` (`id_actividadesdiarias`),
  KEY `id_horario` (`id_horario`),
  KEY `id_estudiante` (`id_estudiante`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `inasistencias`
--

INSERT INTO `inasistencias` (`id_actividadesdiarias`, `id_horario`, `id_estudiante`) VALUES
(1, 1, 1),
(1, 2, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `materia`
--

CREATE TABLE IF NOT EXISTS `materia` (
  `id_materia` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estado` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_materia`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `materia`
--

INSERT INTO `materia` (`id_materia`, `nombre`, `estado`) VALUES
(1, 'Matematica', 1),
(2, 'Castellano', 1),
(3, 'Biologia', 1),
(4, 'Fisica', 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `persona`
--

CREATE TABLE IF NOT EXISTS `persona` (
  `id_persona` int(11) NOT NULL,
  `nombre` varchar(200) NOT NULL,
  `fecha_nacimiento` date NOT NULL,
  `direccion` varchar(200) DEFAULT NULL,
  `telefono` varchar(15) DEFAULT NULL,
  `correo` varchar(50) DEFAULT NULL,
  `sexo` varchar(1) NOT NULL,
  `cedula` varchar(10) NOT NULL,
  PRIMARY KEY (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `persona`
--

INSERT INTO `persona` (`id_persona`, `nombre`, `fecha_nacimiento`, `direccion`, `telefono`, `correo`, `sexo`, `cedula`) VALUES
(1, 'Alumno1', '2011-05-01', '', '', '', 'M', '123'),
(2, 'Alumno2', '2011-05-01', '', '', '', 'F', '456'),
(3, 'Alumno3', '2011-05-01', '', '', '', 'M', '789');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `profesor`
--

CREATE TABLE IF NOT EXISTS `profesor` (
  `id_persona` int(11) NOT NULL,
  `titulo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_persona`),
  KEY `fk_profesor_persona` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `profesor`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `seccion`
--

CREATE TABLE IF NOT EXISTS `seccion` (
  `id_seccion` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `periodo` int(11) NOT NULL,
  `estado` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_seccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `seccion`
--

INSERT INTO `seccion` (`id_seccion`, `nombre`, `periodo`, `estado`) VALUES
(1, 'A', 2011, 1);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `sistema`
--

CREATE TABLE IF NOT EXISTS `sistema` (
  `nombre` varchar(50) NOT NULL,
  `valor` varchar(50) NOT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `sistema`
--


-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `clave` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

--
-- Volcar la base de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre`, `usuario`, `clave`) VALUES
(1, 'Administrador', 'admin', '123');

--
-- Filtros para las tablas descargadas (dump)
--

--
-- Filtros para la tabla `actividadesdiarias`
--
ALTER TABLE `actividadesdiarias`
  ADD CONSTRAINT `actividadesdiarias_ibfk_2` FOREIGN KEY (`id_seccion`) REFERENCES `seccion` (`id_seccion`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `estudiante`
--
ALTER TABLE `estudiante`
  ADD CONSTRAINT `estudiante_ibfk_2` FOREIGN KEY (`id_seccion`) REFERENCES `seccion` (`id_seccion`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `estudiante_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `horario`
--
ALTER TABLE `horario`
  ADD CONSTRAINT `horario_ibfk_4` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`id_materia`) REFERENCES `materia` (`id_materia`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `horario_ibfk_2` FOREIGN KEY (`id_bloque`) REFERENCES `bloque` (`id_bloque`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `horario_ibfk_3` FOREIGN KEY (`id_seccion`) REFERENCES `seccion` (`id_seccion`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `inasistencias`
--
ALTER TABLE `inasistencias`
  ADD CONSTRAINT `inasistencias_ibfk_3` FOREIGN KEY (`id_estudiante`) REFERENCES `estudiante` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `inasistencias_ibfk_1` FOREIGN KEY (`id_actividadesdiarias`) REFERENCES `actividadesdiarias` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `inasistencias_ibfk_2` FOREIGN KEY (`id_horario`) REFERENCES `horario` (`id_horario`) ON DELETE CASCADE ON UPDATE CASCADE;

--
-- Filtros para la tabla `profesor`
--
ALTER TABLE `profesor`
  ADD CONSTRAINT `profesor_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
