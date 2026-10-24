/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelo.Incidencia;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ArrayList;


/**
 *
 * @author mateo
 */



/**
 * Clase encargada de realizar las operaciones sobre la tabla INCIDENCIA.
 */
public class IncidenciaDAO {

    /**
     * Registra una incidencia. Si el tipo es 'no_devuelto', en la misma
     * transacción el préstamo (si estaba 'activo') pasa a estado 'vencido'.
     */
    public boolean registrar(Incidencia incidencia) {

        String sqlIncidencia = "INSERT INTO INCIDENCIA (id_prestamo, tipo, descripcion) "
                + "VALUES (?, ?, ?)";

        String sqlVencido = "UPDATE PRESTAMO SET estado_P = 'vencido' "
                + "WHERE id_prestamo = ? AND estado_P = 'activo'";

        try (Connection conexion = ConexionBD.conectar()) {

            try {
                conexion.setAutoCommit(false);

                try (PreparedStatement sentencia = conexion.prepareStatement(sqlIncidencia)) {
                    sentencia.setInt(1, incidencia.getIdPrestamo());
                    sentencia.setString(2, incidencia.getTipo());
                    sentencia.setString(3, incidencia.getDescripcion());
                    sentencia.executeUpdate();
                }

                if ("no_devuelto".equals(incidencia.getTipo())) {
                    try (PreparedStatement sentencia = conexion.prepareStatement(sqlVencido)) {
                        sentencia.setInt(1, incidencia.getIdPrestamo());
                        sentencia.executeUpdate();
                    }
                }

                conexion.commit();
                return true;

            } catch (SQLException e) {
                conexion.rollback();
                throw e;
            }

        } catch (SQLException e) {
            System.out.println("Error al registrar incidencia: " + e.getMessage());
            return false;
        }
    }

    // Obtiene todas las incidencias registradas.
    public ArrayList<Incidencia> listar() {

        ArrayList<Incidencia> incidencias = new ArrayList<>();
        String sql = "SELECT * FROM INCIDENCIA ORDER BY id_incidencia";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                Incidencia incidencia = new Incidencia();
                incidencia.setIdIncidencia(resultado.getInt("id_incidencia"));
                incidencia.setIdPrestamo(resultado.getInt("id_prestamo"));
                incidencia.setTipo(resultado.getString("tipo"));
                incidencia.setDescripcion(resultado.getString("descripcion"));

                incidencias.add(incidencia);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar incidencias: " + e.getMessage());
        }

        return incidencias;
    }
}


