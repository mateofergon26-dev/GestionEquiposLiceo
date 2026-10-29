-- phpMyAdmin SQL Dump
-- version 5.2.1
-- https://www.phpmyadmin.net/
--
-- Servidor: 127.0.0.1
-- Tiempo de generación: 29-10-2026 a las 19:51:21
-- Versión del servidor: 10.4.32-MariaDB
-- Versión de PHP: 8.2.12

SET SQL_MODE = "NO_AUTO_VALUE_ON_ZERO";
START TRANSACTION;
SET time_zone = "+00:00";


/*!40101 SET @OLD_CHARACTER_SET_CLIENT=@@CHARACTER_SET_CLIENT */;
/*!40101 SET @OLD_CHARACTER_SET_RESULTS=@@CHARACTER_SET_RESULTS */;
/*!40101 SET @OLD_COLLATION_CONNECTION=@@COLLATION_CONNECTION */;
/*!40101 SET NAMES utf8mb4 */;

--
-- Base de datos: `gestion_equipos`
--

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `equipo`
--

CREATE TABLE `equipo` (
  `id_equipo` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `tipo` varchar(30) NOT NULL,
  `numero_serie` varchar(50) DEFAULT NULL,
  `estado_E` varchar(20) NOT NULL,
  `ubicacion` varchar(50) DEFAULT NULL
) ;

--
-- Volcado de datos para la tabla `equipo`
--

INSERT INTO `equipo` (`id_equipo`, `nombre`, `tipo`, `numero_serie`, `estado_E`, `ubicacion`) VALUES
(1, 'Notebook Ceibal Acrab H719 - 01', 'notebook', 'SN-0001', 'disponible', 'Sala de informática'),
(2, 'Notebook Ceibal Acrab H719 - 02', 'notebook', 'SN-0002', 'disponible', 'Sala de informática'),
(3, 'Proyector Epson 03', 'proyector', 'SN-0003', 'disponible', 'Depósito'),
(4, 'Tablet institucional 07', 'tablet', 'SN-0004', 'en_reparacion', 'Depósito'),
(5, 'Notebook Ceibal Acrab H719 - 03', 'notebook', 'SN-0005', 'prestado', 'Sala de informática'),
(6, 'Notebook Ceibal Electra', 'notebook', 'SN-0006', 'de_baja', 'Casa de Ariel');

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `incidencia`
--

CREATE TABLE `incidencia` (
  `id_incidencia` int(11) NOT NULL,
  `id_prestamo` int(11) NOT NULL,
  `tipo` varchar(20) NOT NULL,
  `descripcion` varchar(255) DEFAULT NULL
) ;

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `prestamo`
--

CREATE TABLE `prestamo` (
  `id_prestamo` int(11) NOT NULL,
  `id_reserva` int(11) NOT NULL,
  `id_equipo` int(11) NOT NULL,
  `estado_P` varchar(20) NOT NULL,
  `fecha_entrega_real` date NOT NULL,
  `fecha_devolucion_esperada` date NOT NULL,
  `fecha_devolucion_real` date DEFAULT NULL
) ;

--
-- Volcado de datos para la tabla `prestamo`
--

INSERT INTO `prestamo` (`id_prestamo`, `id_reserva`, `id_equipo`, `estado_P`, `fecha_entrega_real`, `fecha_devolucion_esperada`, `fecha_devolucion_real`) VALUES
(1, 1, 1, 'activo', '2026-09-05', '2026-09-07', NULL);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reporte_falla`
--

CREATE TABLE `reporte_falla` (
  `id_reporte` int(11) NOT NULL,
  `fecha_reporte` date NOT NULL,
  `descripcion_falla` varchar(255) NOT NULL,
  `estado_RF` varchar(20) NOT NULL,
  `id_usuario` int(11) NOT NULL,
  `id_equipo` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `reporte_falla`
--

INSERT INTO `reporte_falla` (`id_reporte`, `fecha_reporte`, `descripcion_falla`, `estado_RF`, `id_usuario`, `id_equipo`) VALUES
(1, '2026-08-28', 'El equipo no enciende luego de cargarlo toda la noche', 'pendiente', 3, 5);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva`
--

CREATE TABLE `reserva` (
  `id_reserva` int(11) NOT NULL,
  `fecha_solicitud` date NOT NULL,
  `fecha_inicio` date NOT NULL,
  `fecha_fin` date NOT NULL,
  `estado_R` varchar(20) NOT NULL,
  `motivo_rechazo` varchar(255) DEFAULT NULL,
  `id_usuario` int(11) NOT NULL
) ;

--
-- Volcado de datos para la tabla `reserva`
--

INSERT INTO `reserva` (`id_reserva`, `fecha_solicitud`, `fecha_inicio`, `fecha_fin`, `estado_R`, `motivo_rechazo`, `id_usuario`) VALUES
(1, '2026-09-01', '2026-09-05', '2026-09-07', 'aprobada', NULL, 2);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `reserva_equipo`
--

CREATE TABLE `reserva_equipo` (
  `id_reserva` int(11) NOT NULL,
  `id_equipo` int(11) NOT NULL
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_general_ci;

--
-- Volcado de datos para la tabla `reserva_equipo`
--

INSERT INTO `reserva_equipo` (`id_reserva`, `id_equipo`) VALUES
(1, 1),
(1, 3);

-- --------------------------------------------------------

--
-- Estructura de tabla para la tabla `usuario`
--

CREATE TABLE `usuario` (
  `id_usuario` int(11) NOT NULL,
  `nombre` varchar(50) NOT NULL,
  `apellido` varchar(50) NOT NULL,
  `email` varchar(100) NOT NULL,
  `contrasena` varchar(255) NOT NULL,
  `rol` varchar(20) NOT NULL,
  `activo` tinyint(1) NOT NULL DEFAULT 1
) ;

--
-- Volcado de datos para la tabla `usuario`
--

INSERT INTO `usuario` (`id_usuario`, `nombre`, `apellido`, `email`, `contrasena`, `rol`, `activo`) VALUES
(1, 'Pablo', 'Gomez', 'pgomez@liceo.edu.uy', 'admin123', 'administrador', 1),
(2, 'Maria', 'Fernandez', 'mfernandez@liceo.edu.uy', 'user123', 'solicitante', 1),
(3, 'Lucas', 'Perez', 'lperez@liceo.edu.uy', 'user123', 'solicitante', 1),
(4, 'Felipe', 'Islas', 'felipe@gmail.com', '12345678', 'solicitante', 1),
(5, 'Juan', 'Hernandez', 'jh@gmail.com', '12345678', 'Solicitante', 1);

--
-- Índices para tablas volcadas
--

--
-- Indices de la tabla `equipo`
--
ALTER TABLE `equipo`
  ADD PRIMARY KEY (`id_equipo`);

--
-- Indices de la tabla `incidencia`
--
ALTER TABLE `incidencia`
  ADD PRIMARY KEY (`id_incidencia`),
  ADD KEY `fk_incidencia_prestamo` (`id_prestamo`);

--
-- Indices de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD PRIMARY KEY (`id_prestamo`),
  ADD UNIQUE KEY `uq_prestamo_resequipo` (`id_reserva`,`id_equipo`);

--
-- Indices de la tabla `reporte_falla`
--
ALTER TABLE `reporte_falla`
  ADD PRIMARY KEY (`id_reporte`),
  ADD KEY `fk_reporte_usuario` (`id_usuario`),
  ADD KEY `fk_reporte_equipo` (`id_equipo`);

--
-- Indices de la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD PRIMARY KEY (`id_reserva`),
  ADD KEY `fk_reserva_usuario` (`id_usuario`);

--
-- Indices de la tabla `reserva_equipo`
--
ALTER TABLE `reserva_equipo`
  ADD PRIMARY KEY (`id_reserva`,`id_equipo`),
  ADD KEY `fk_resequipo_equipo` (`id_equipo`);

--
-- Indices de la tabla `usuario`
--
ALTER TABLE `usuario`
  ADD PRIMARY KEY (`id_usuario`),
  ADD UNIQUE KEY `email` (`email`);

--
-- AUTO_INCREMENT de las tablas volcadas
--

--
-- AUTO_INCREMENT de la tabla `equipo`
--
ALTER TABLE `equipo`
  MODIFY `id_equipo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `incidencia`
--
ALTER TABLE `incidencia`
  MODIFY `id_incidencia` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `prestamo`
--
ALTER TABLE `prestamo`
  MODIFY `id_prestamo` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reporte_falla`
--
ALTER TABLE `reporte_falla`
  MODIFY `id_reporte` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `reserva`
--
ALTER TABLE `reserva`
  MODIFY `id_reserva` int(11) NOT NULL AUTO_INCREMENT;

--
-- AUTO_INCREMENT de la tabla `usuario`
--
ALTER TABLE `usuario`
  MODIFY `id_usuario` int(11) NOT NULL AUTO_INCREMENT;

--
-- Restricciones para tablas volcadas
--

--
-- Filtros para la tabla `incidencia`
--
ALTER TABLE `incidencia`
  ADD CONSTRAINT `fk_incidencia_prestamo` FOREIGN KEY (`id_prestamo`) REFERENCES `prestamo` (`id_prestamo`);

--
-- Filtros para la tabla `prestamo`
--
ALTER TABLE `prestamo`
  ADD CONSTRAINT `fk_prestamo_resequipo` FOREIGN KEY (`id_reserva`,`id_equipo`) REFERENCES `reserva_equipo` (`id_reserva`, `id_equipo`);

--
-- Filtros para la tabla `reporte_falla`
--
ALTER TABLE `reporte_falla`
  ADD CONSTRAINT `fk_reporte_equipo` FOREIGN KEY (`id_equipo`) REFERENCES `equipo` (`id_equipo`),
  ADD CONSTRAINT `fk_reporte_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `reserva`
--
ALTER TABLE `reserva`
  ADD CONSTRAINT `fk_reserva_usuario` FOREIGN KEY (`id_usuario`) REFERENCES `usuario` (`id_usuario`);

--
-- Filtros para la tabla `reserva_equipo`
--
ALTER TABLE `reserva_equipo`
  ADD CONSTRAINT `fk_resequipo_equipo` FOREIGN KEY (`id_equipo`) REFERENCES `equipo` (`id_equipo`),
  ADD CONSTRAINT `fk_resequipo_reserva` FOREIGN KEY (`id_reserva`) REFERENCES `reserva` (`id_reserva`);
COMMIT;

/*!40101 SET CHARACTER_SET_CLIENT=@OLD_CHARACTER_SET_CLIENT */;
/*!40101 SET CHARACTER_SET_RESULTS=@OLD_CHARACTER_SET_RESULTS */;
/*!40101 SET COLLATION_CONNECTION=@OLD_COLLATION_CONNECTION */;
