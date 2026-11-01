/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Reserva;
import negocio.ReservaService;
import java.util.ArrayList;

/**
 *
 * @author ariel
 */
public class ReservaController {

    private ReservaService reservaService;

    public ReservaController() {
        reservaService = new ReservaService();
    }

    public boolean registrarReserva(
            int idUsuario,
            int idEquipo,
            java.time.LocalDate fechaInicio,
            java.time.LocalDate fechaFin) {

        return reservaService.registrarReserva(
                idUsuario,
                idEquipo,
                fechaInicio,
                fechaFin);
    }

    public ArrayList<Reserva> listarReservas() {
        return reservaService.listarReservas();
    }

    public boolean aprobarReserva(int idReserva) {
        return reservaService.aprobarReserva(idReserva);
    }

    public boolean rechazarReserva(int idReserva) {
        return reservaService.rechazarReserva(idReserva);
    }

    public boolean cancelarReserva(int idReserva) {
        return reservaService.cancelarReserva(idReserva);
    }
    
    public ArrayList<Reserva> listarReservasPorUsuario(int idUsuario) {
    return reservaService.listarReservasPorUsuario(idUsuario);
}
    
}
