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

CREATE TABLE IF NOT EXISTS `bloque` (
  `id_bloque` int(11) NOT NULL,
  `numero` int(11) NOT NULL,
  `descripcion` varchar(50) DEFAULT NULL,
  `estado` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_bloque`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;


INSERT INTO `bloque` (`id_bloque`, `numero`, `descripcion`, `estado`) VALUES
(1, 1, '07:00 - 07:45', 1),
(2, 2, '07:45 - 08:30', 1),
(3, 3, '08:40 - 09:25', 1),
(4, 4, '09:25 - 10:10', 1),
(5, 5, '10:15 - 11:00', 1),
(6, 6, '11:00 - 11:45', 1),
(7, 7, '11:50 - 12:35', 1),
(8, 8, '12:35 - 01:20', 1),
(9, 9, '01:20 - 02:05', 1),
(10, 10, '02:05 - 02:50', 1),
(11, 11, '02:55 - 03:40', 1),
(12, 12, '03:40 - 04:25', 1),
(13, 13, '04:30 - 05:15', 1),
(14, 14, '05:15 - 06:00', 1);

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

CREATE TABLE IF NOT EXISTS `inasistencias` (
  `id_actividadesdiarias` int(10) NOT NULL,
  `id_horario` int(10) NOT NULL,
  `id_estudiante` int(10) NOT NULL,
  KEY `id_actividadesdiarias` (`id_actividadesdiarias`),
  KEY `id_horario` (`id_horario`),
  KEY `id_estudiante` (`id_estudiante`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `materia` (
  `id_materia` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `estado` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_materia`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

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

CREATE TABLE IF NOT EXISTS `profesor` (
  `id_persona` int(11) NOT NULL,
  `titulo` int(11) DEFAULT NULL,
  PRIMARY KEY (`id_persona`),
  KEY `fk_profesor_persona` (`id_persona`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `seccion` (
  `id_seccion` int(11) NOT NULL,
  `nombre` varchar(20) NOT NULL,
  `periodo` int(11) NOT NULL,
  `estado` int(11) NOT NULL DEFAULT '1',
  PRIMARY KEY (`id_seccion`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `sistema` (
  `nombre` varchar(50) NOT NULL,
  `valor` varchar(50) NOT NULL,
  PRIMARY KEY (`nombre`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

CREATE TABLE IF NOT EXISTS `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(100) DEFAULT NULL,
  `usuario` varchar(50) DEFAULT NULL,
  `clave` varchar(50) DEFAULT NULL,
  PRIMARY KEY (`id_usuario`)
) ENGINE=InnoDB DEFAULT CHARSET=latin1;

INSERT INTO `usuario` (`id_usuario`, `nombre`, `usuario`, `clave`) VALUES
(1, 'Administrador', 'admin', '123');

ALTER TABLE `actividadesdiarias`
  ADD CONSTRAINT `actividadesdiarias_ibfk_2` FOREIGN KEY (`id_seccion`) REFERENCES `seccion` (`id_seccion`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `estudiante`
  ADD CONSTRAINT `estudiante_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `estudiante_ibfk_2` FOREIGN KEY (`id_seccion`) REFERENCES `seccion` (`id_seccion`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `horario`
  ADD CONSTRAINT `horario_ibfk_1` FOREIGN KEY (`id_materia`) REFERENCES `materia` (`id_materia`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `horario_ibfk_2` FOREIGN KEY (`id_bloque`) REFERENCES `bloque` (`id_bloque`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `horario_ibfk_3` FOREIGN KEY (`id_seccion`) REFERENCES `seccion` (`id_seccion`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `horario_ibfk_4` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `inasistencias`
  ADD CONSTRAINT `inasistencias_ibfk_1` FOREIGN KEY (`id_actividadesdiarias`) REFERENCES `actividadesdiarias` (`id`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `inasistencias_ibfk_2` FOREIGN KEY (`id_horario`) REFERENCES `horario` (`id_horario`) ON DELETE CASCADE ON UPDATE CASCADE,
  ADD CONSTRAINT `inasistencias_ibfk_3` FOREIGN KEY (`id_estudiante`) REFERENCES `estudiante` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE;

ALTER TABLE `profesor`
  ADD CONSTRAINT `profesor_ibfk_1` FOREIGN KEY (`id_persona`) REFERENCES `persona` (`id_persona`) ON DELETE CASCADE ON UPDATE CASCADE;