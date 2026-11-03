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

/**
 *
 * @author ariel
 */
public class ReporteFallaDAO {

    public boolean registrarReporte(ReporteFalla reporte) {

        String sql = "INSERT INTO REPORTE_FALLA "
                + "(fecha_reporte, descripcion_falla, estado_RF, id_usuario, id_equipo) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

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
}