/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import negocio.ReporteFallaService;
import modelo.ReporteFalla;
import java.util.List;

/**
 *
 * @author ariel
 */
public class ReporteFallaController {

    private ReporteFallaService reporteService = new ReporteFallaService();

    public boolean registrarReporte(int idUsuario, int idEquipo,
            String descripcion) {

        return reporteService.registrarReporte(
                idUsuario, idEquipo, descripcion);
    }

    public List<ReporteFalla> listarReportes() {
        return reporteService.listarReportes();
    }

    public boolean actualizarEstado(int idReporte, String estado) {

        return reporteService.actualizarEstado(idReporte, estado);
    }

}
