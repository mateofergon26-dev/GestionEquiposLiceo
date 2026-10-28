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

    public boolean registrarReserva(int idUsuario, int idEquipo) {
        return reservaService.registrarReserva(idUsuario, idEquipo);
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
}
