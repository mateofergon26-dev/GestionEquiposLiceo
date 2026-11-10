/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelo.ReporteFalla;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.Date;
import java.sql.ResultSet;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author ariel
 */
public class ReporteFallaDAO {

    public boolean registrarReporte(ReporteFalla reporte) {

        String sql = "INSERT INTO REPORTE_FALLA "
                + "(fecha_reporte, descripcion_falla, estado_RF, id_usuario, id_equipo) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setDate(1, Date.valueOf(reporte.getFechaReporte()));
            ps.setString(2, reporte.getDescripcionFalla());
            ps.setString(3, reporte.getEstado());
            ps.setInt(4, reporte.getIdUsuario());
            ps.setInt(5, reporte.getIdEquipo());

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al registrar el reporte: " + e.getMessage());
            return false;
        }
    }

    // Obtiene todos los reportes registrados.
    public List<ReporteFalla> listarReportes() {

        List<ReporteFalla> reportes = new ArrayList<>();

        String sql = "SELECT * FROM REPORTE_FALLA "
                + "ORDER BY fecha_reporte DESC";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {

                ReporteFalla reporte = new ReporteFalla();

                reporte.setIdReporte(rs.getInt("id_reporte"));
                reporte.setFechaReporte(
                        rs.getDate("fecha_reporte").toLocalDate());
                reporte.setDescripcionFalla(
                        rs.getString("descripcion_falla"));
                reporte.setEstado(rs.getString("estado_RF"));
                reporte.setIdUsuario(rs.getInt("id_usuario"));
                reporte.setIdEquipo(rs.getInt("id_equipo"));

                reportes.add(reporte);
            }

        } catch (Exception e) {
            System.out.println("Error al listar reportes: " + e.getMessage());
        }

        return reportes;
    }

    // Actualiza el estado de un reporte.
    public boolean actualizarEstado(int idReporte, String estado) {

        String sql = "UPDATE REPORTE_FALLA "
                + "SET estado_RF = ? WHERE id_reporte = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, estado);
            ps.setInt(2, idReporte);

            return ps.executeUpdate() > 0;

        } catch (Exception e) {
            System.out.println("Error al actualizar el reporte: " + e.getMessage());
            return false;
        }
    }

}
