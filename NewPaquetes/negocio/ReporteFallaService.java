/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dao.ReporteFallaDAO;
import modelo.ReporteFalla;
import java.time.LocalDate;

/**
 *
 * @author ariel
 */
public class ReporteFallaService {

    private ReporteFallaDAO reporteDAO = new ReporteFallaDAO();

    public boolean registrarReporte(int idUsuario, int idEquipo,
            String descripcion) {

        if (idUsuario <= 0 || idEquipo <= 0) {
            return false;
        }

        if (descripcion == null || descripcion.trim().isEmpty()) {
            return false;
        }

        ReporteFalla reporte = new ReporteFalla(
                LocalDate.now(),
                descripcion.trim(),
                "Pendiente",
                idUsuario,
                idEquipo
        );

        return reporteDAO.registrarReporte(reporte);
    }
}
