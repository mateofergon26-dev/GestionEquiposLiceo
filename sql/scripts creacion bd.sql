-- =====================================================================
-- Sistema de gestión de reservas y préstamos de equipos informáticos
-- Script de creación de base de datos
-- =====================================================================

DROP DATABASE IF EXISTS gestion_equipos;
CREATE DATABASE gestion_equipos;
USE gestion_equipos;

-- =====================================================================
-- 1. USUARIO
-- =====================================================================
CREATE TABLE USUARIO (
    id_usuario   INT AUTO_INCREMENT PRIMARY KEY,
    nombre       VARCHAR(50)  NOT NULL,
    apellido     VARCHAR(50)  NOT NULL,
    email        VARCHAR(100) NOT NULL UNIQUE,
    contrasena   VARCHAR(255) NOT NULL,
    rol          VARCHAR(20)  NOT NULL,
    CONSTRAINT chk_usuario_rol CHECK (rol IN ('administrador', 'solicitante'))
);

-- =====================================================================
-- 2. EQUIPO
-- =====================================================================
CREATE TABLE EQUIPO (
    id_equipo     INT AUTO_INCREMENT PRIMARY KEY,
    nombre        VARCHAR(50) NOT NULL,
    tipo          VARCHAR(30) NOT NULL,
    numero_serie  VARCHAR(50),
    estado_E      VARCHAR(20) NOT NULL,
    ubicacion     VARCHAR(50),
    CONSTRAINT chk_equipo_estado CHECK (estado_E IN ('disponible','reservado','prestado','en_reparacion','de_baja'))
);

-- =====================================================================
-- 3. RESERVA
-- =====================================================================
CREATE TABLE RESERVA (
    id_reserva       INT AUTO_INCREMENT PRIMARY KEY,
    fecha_solicitud  DATE NOT NULL,
    fecha_inicio     DATE NOT NULL,
    fecha_fin        DATE NOT NULL,
    estado_R         VARCHAR(20) NOT NULL,
    motivo_rechazo   VARCHAR(255),
    id_usuario       INT NOT NULL,
    CONSTRAINT fk_reserva_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario),
    CONSTRAINT chk_reserva_estado CHECK (estado_R IN ('pendiente','aprobada','rechazada','cancelada')),
    CONSTRAINT chk_reserva_fechas CHECK (fecha_fin >= fecha_inicio)
);

-- =====================================================================
-- 4. RESERVA_EQUIPO (traducción de la relación N:N "para")
-- =====================================================================
CREATE TABLE RESERVA_EQUIPO (
    id_reserva  INT NOT NULL,
    id_equipo   INT NOT NULL,
    PRIMARY KEY (id_reserva, id_equipo),
    CONSTRAINT fk_resequipo_reserva FOREIGN KEY (id_reserva) REFERENCES RESERVA(id_reserva),
    CONSTRAINT fk_resequipo_equipo  FOREIGN KEY (id_equipo)  REFERENCES EQUIPO(id_equipo)
);

-- =====================================================================
-- 5. PRESTAMO (nace de la pareja reserva-equipo, por la agregación)
-- =====================================================================
CREATE TABLE PRESTAMO (
    id_prestamo                 INT AUTO_INCREMENT PRIMARY KEY,
    id_reserva                  INT NOT NULL,
    id_equipo                   INT NOT NULL,
    estado_P                    VARCHAR(20) NOT NULL,
    fecha_entrega_real          DATE NOT NULL,
    fecha_devolucion_esperada   DATE NOT NULL,
    fecha_devolucion_real       DATE,
    CONSTRAINT fk_prestamo_resequipo FOREIGN KEY (id_reserva, id_equipo)
        REFERENCES RESERVA_EQUIPO(id_reserva, id_equipo),
    CONSTRAINT uq_prestamo_resequipo UNIQUE (id_reserva, id_equipo),
    CONSTRAINT chk_prestamo_estado CHECK (estado_P IN ('activo','devuelto','vencido'))
);

-- =====================================================================
-- 6. INCIDENCIA
-- =====================================================================
CREATE TABLE INCIDENCIA (
    id_incidencia  INT AUTO_INCREMENT PRIMARY KEY,
    id_prestamo    INT NOT NULL,
    tipo           VARCHAR(20) NOT NULL,
    descripcion    VARCHAR(255),
    CONSTRAINT fk_incidencia_prestamo FOREIGN KEY (id_prestamo) REFERENCES PRESTAMO(id_prestamo),
    CONSTRAINT chk_incidencia_tipo CHECK (tipo IN ('retraso','no_devuelto','dano'))
);

-- =====================================================================
-- 7. REPORTE_FALLA
-- =====================================================================
CREATE TABLE REPORTE_FALLA (
    id_reporte          INT AUTO_INCREMENT PRIMARY KEY,
    fecha_reporte       DATE NOT NULL,
    descripcion_falla   VARCHAR(255) NOT NULL,
    estado_RF           VARCHAR(20) NOT NULL,
    id_usuario          INT NOT NULL,
    id_equipo           INT NOT NULL,
    CONSTRAINT fk_reporte_usuario FOREIGN KEY (id_usuario) REFERENCES USUARIO(id_usuario),
    CONSTRAINT fk_reporte_equipo  FOREIGN KEY (id_equipo)  REFERENCES EQUIPO(id_equipo),
    CONSTRAINT chk_reporte_estado CHECK (estado_RF IN ('pendiente','en_revision','resuelto'))
);

-- =====================================================================
-- DATOS FICTICIOS
-- =====================================================================

-- Usuarios: un administrador y dos solicitantes
INSERT INTO USUARIO (nombre, apellido, email, contrasena, rol) VALUES
('Pablo', 'Gomez', 'pgomez@liceo.edu.uy', 'admin123', 'administrador'),
('Maria', 'Fernandez', 'mfernandez@liceo.edu.uy', 'user123', 'solicitante'),
('Lucas', 'Perez', 'lperez@liceo.edu.uy', 'user123', 'solicitante');

-- Equipos: varios, con distintos estados (para probar el CRUD y las reglas de negocio)
INSERT INTO EQUIPO (nombre, tipo, numero_serie, estado_E, ubicacion) VALUES
('Notebook Ceibal Acrab H719 - 01', 'notebook', 'SN-0001', 'disponible', 'Sala de informática'),
('Notebook Ceibal Acrab H719 - 02', 'notebook', 'SN-0002', 'disponible', 'Sala de informática'),
('Proyector Epson 03', 'proyector', 'SN-0003', 'disponible', 'Depósito'),
('Tablet institucional 07', 'tablet', 'SN-0004', 'en_reparacion', 'Depósito'),
('Notebook Ceibal Acrab H719 - 03', 'notebook', 'SN-0005', 'prestado', 'Sala de informática');

-- Reserva de ejemplo (Maria reserva la notebook 1 y el proyector juntos)
INSERT INTO RESERVA (fecha_solicitud, fecha_inicio, fecha_fin, estado_R, id_usuario) VALUES
('2026-09-01', '2026-09-05', '2026-09-07', 'aprobada', 2);

INSERT INTO RESERVA_EQUIPO (id_reserva, id_equipo) VALUES
(1, 1),
(1, 3);

-- Préstamo ya generado para la pareja (reserva 1, equipo 1) -- notebook entregada
INSERT INTO PRESTAMO (id_reserva, id_equipo, estado_P, fecha_entrega_real, fecha_devolucion_esperada) VALUES
(1, 1, 'activo', '2026-09-05', '2026-09-07');

-- Reporte de falla de ejemplo
INSERT INTO REPORTE_FALLA (fecha_reporte, descripcion_falla, estado_RF, id_usuario, id_equipo) VALUES
('2026-08-28', 'El equipo no enciende luego de cargarlo toda la noche', 'pendiente', 3, 5);
