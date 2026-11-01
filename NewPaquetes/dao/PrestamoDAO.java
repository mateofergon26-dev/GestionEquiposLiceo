/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import conexion.ConexionBD;
import modelo.Incidencia;
import modelo.Prestamo;

import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;

import java.util.ArrayList;


/**
 *
 * @author mateo
 */




/**
 * Clase encargada de realizar las operaciones sobre la tabla PRESTAMO.
 * Las operaciones que modifican varias tablas (registrar y devolver) se
 * ejecutan en una sola transacción: o se guardan todos los cambios o ninguno.
 */
public class PrestamoDAO {

    // Registra el préstamo y marca el equipo como 'prestado' (misma transacción).
    public boolean registrar(Prestamo prestamo) {

        String sqlPrestamo = "INSERT INTO PRESTAMO "
                + "(id_reserva, id_equipo, estado_P, fecha_entrega_real, fecha_devolucion_esperada) "
                + "VALUES (?, ?, 'activo', ?, ?)";

        String sqlEquipo = "UPDATE EQUIPO SET estado_E = 'prestado' WHERE id_equipo = ?";

        try (Connection conexion = ConexionBD.conectar()) {

            try {
                conexion.setAutoCommit(false);

                try (PreparedStatement sentencia = conexion.prepareStatement(sqlPrestamo)) {
                    sentencia.setInt(1, prestamo.getIdReserva());
                    sentencia.setInt(2, prestamo.getIdEquipo());
                    sentencia.setDate(3, Date.valueOf(prestamo.getFechaEntregaReal()));
                    sentencia.setDate(4, Date.valueOf(prestamo.getFechaDevolucionEsperada()));
                    sentencia.executeUpdate();
                }

                try (PreparedStatement sentencia = conexion.prepareStatement(sqlEquipo)) {
                    sentencia.setInt(1, prestamo.getIdEquipo());
                    sentencia.executeUpdate();
                }

                conexion.commit();
                return true;

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar préstamo: " + e.getMessage());
            return false;
        }
    }

    /**
     * Registra la devolución de un préstamo. En una sola transacción: 1) marca
     * el préstamo como 'devuelto' con la fecha de hoy, 2) registra la
     * incidencia, si se recibió una (puede ser null), 3) deja el equipo
     * 'en_reparacion' si el préstamo tiene alguna incidencia de tipo 'dano'; en
     * caso contrario lo deja 'disponible'.
     */
    public boolean registrarDevolucion(int idPrestamo, Incidencia incidencia) {

        String sqlIdEquipo = "SELECT id_equipo FROM PRESTAMO WHERE id_prestamo = ?";
        String sqlDevolver = "UPDATE PRESTAMO SET estado_P = 'devuelto', "
                + "fecha_devolucion_real = ? WHERE id_prestamo = ?";
        String sqlIncidencia = "INSERT INTO INCIDENCIA (id_prestamo, tipo, descripcion) "
                + "VALUES (?, ?, ?)";
        String sqlHayDano = "SELECT COUNT(*) FROM INCIDENCIA "
                + "WHERE id_prestamo = ? AND tipo = 'dano'";
        String sqlEquipo = "UPDATE EQUIPO SET estado_E = ? WHERE id_equipo = ?";

        try (Connection conexion = ConexionBD.conectar()) {

            try {
                conexion.setAutoCommit(false);

                // 1. Se obtiene el equipo asociado al préstamo.
                int idEquipo = -1;
                try (PreparedStatement sentencia = conexion.prepareStatement(sqlIdEquipo)) {
                    sentencia.setInt(1, idPrestamo);
                    try (ResultSet resultado = sentencia.executeQuery()) {
                        if (!resultado.next()) {
                            conexion.rollback();
                            return false;
                        }
                        idEquipo = resultado.getInt("id_equipo");
                    }
                }

                // 2. Se marca el préstamo como devuelto.
                try (PreparedStatement sentencia = conexion.prepareStatement(sqlDevolver)) {
                    sentencia.setDate(1, Date.valueOf(LocalDate.now()));
                    sentencia.setInt(2, idPrestamo);
                    sentencia.executeUpdate();
                }

                // 3. Si hay incidencia, se registra.
                if (incidencia != null) {
                    try (PreparedStatement sentencia = conexion.prepareStatement(sqlIncidencia)) {
                        sentencia.setInt(1, idPrestamo);
                        sentencia.setString(2, incidencia.getTipo());
                        sentencia.setString(3, incidencia.getDescripcion());
                        sentencia.executeUpdate();
                    }
                }

                // 4. Se consulta si el préstamo tiene alguna incidencia de daño.
                boolean hayDano = false;
                try (PreparedStatement sentencia = conexion.prepareStatement(sqlHayDano)) {
                    sentencia.setInt(1, idPrestamo);
                    try (ResultSet resultado = sentencia.executeQuery()) {
                        if (resultado.next()) {
                            hayDano = resultado.getInt(1) > 0;
                        }
                    }
                }

                // 5. Se actualiza el estado del equipo.
                try (PreparedStatement sentencia = conexion.prepareStatement(sqlEquipo)) {
                    sentencia.setString(1, hayDano ? "en_reparacion" : "disponible");
                    sentencia.setInt(2, idEquipo);
                    sentencia.executeUpdate();
                }

                conexion.commit();
                return true;

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar devolución: " + e.getMessage());
            return false;
        }
    }

    // Obtiene todos los préstamos registrados.
    public ArrayList<Prestamo> listar() {

        ArrayList<Prestamo> prestamos = new ArrayList<>();
        String sql = "SELECT * FROM PRESTAMO ORDER BY id_prestamo";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {
                prestamos.add(mapear(resultado));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar préstamos: " + e.getMessage());
        }

        return prestamos;
    }

    // Busca un préstamo por su id (devuelve null si no existe).
    public Prestamo buscarPorId(int idPrestamo) {

        String sql = "SELECT * FROM PRESTAMO WHERE id_prestamo = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idPrestamo);

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return mapear(resultado);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar préstamo: " + e.getMessage());
        }

        return null;
    }

    // ---------------------------------------------------------------
    // Consultas de apoyo para que el Service pueda validar las reglas
    // ---------------------------------------------------------------
    // Estado de una reserva (null si la reserva no existe).
    public String obtenerEstadoReserva(int idReserva) {
        Object valor = consultarUnValor(
                "SELECT estado_R FROM RESERVA WHERE id_reserva = ?", idReserva);
        return valor == null ? null : valor.toString();
    }

    // Estado de un equipo (null si el equipo no existe).
    public String obtenerEstadoEquipo(int idEquipo) {
        Object valor = consultarUnValor(
                "SELECT estado_E FROM EQUIPO WHERE id_equipo = ?", idEquipo);
        return valor == null ? null : valor.toString();
    }

    // Indica si el equipo forma parte de la reserva (tabla RESERVA_EQUIPO).
    public boolean existeReservaEquipo(int idReserva, int idEquipo) {
        return consultarUnValor(
                "SELECT 1 FROM RESERVA_EQUIPO WHERE id_reserva = ? AND id_equipo = ?",
                idReserva, idEquipo) != null;
    }

    // Indica si ya existe un préstamo para esa pareja reserva-equipo.
    public boolean existePrestamo(int idReserva, int idEquipo) {
        return consultarUnValor(
                "SELECT 1 FROM PRESTAMO WHERE id_reserva = ? AND id_equipo = ?",
                idReserva, idEquipo) != null;
    }

    // Ejecuta una consulta y devuelve la primera columna de la primera fila (o null).
    private Object consultarUnValor(String sql, Object... parametros) {

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            for (int i = 0; i < parametros.length; i++) {
                sentencia.setObject(i + 1, parametros[i]);
            }

            try (ResultSet resultado = sentencia.executeQuery()) {
                if (resultado.next()) {
                    return resultado.getObject(1);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error en la consulta: " + e.getMessage());
        }

        return null;
    }

    // Convierte una fila del ResultSet en un objeto Prestamo.
    private Prestamo mapear(ResultSet resultado) throws SQLException {

        Prestamo prestamo = new Prestamo();
        prestamo.setIdPrestamo(resultado.getInt("id_prestamo"));
        prestamo.setIdReserva(resultado.getInt("id_reserva"));
        prestamo.setIdEquipo(resultado.getInt("id_equipo"));
        prestamo.setEstado(resultado.getString("estado_P"));
        prestamo.setFechaEntregaReal(resultado.getDate("fecha_entrega_real").toLocalDate());
        prestamo.setFechaDevolucionEsperada(
                resultado.getDate("fecha_devolucion_esperada").toLocalDate());

        // La fecha de devolución real es nula mientras el préstamo siga activo.
        Date devolucionReal = resultado.getDate("fecha_devolucion_real");
        if (devolucionReal != null) {
            prestamo.setFechaDevolucionReal(devolucionReal.toLocalDate());
        }

        return prestamo;
    }
}
