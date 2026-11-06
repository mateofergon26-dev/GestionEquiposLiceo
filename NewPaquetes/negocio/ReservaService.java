/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dao.ReservaDAO;
import dao.EquipoDAO;

import modelo.Reserva;
import java.util.ArrayList;

/**
 *
 * @author ariel
 */
public class ReservaService {

    private ReservaDAO reservaDAO;
    private EquipoDAO equipoDAO;

   public ReservaService() {
    reservaDAO = new ReservaDAO();
    equipoDAO = new EquipoDAO();
}

    public boolean registrarReserva(
            int idUsuario,
            int idEquipo,
            java.time.LocalDate fechaInicio,
            java.time.LocalDate fechaFin) {

        if (idUsuario <= 0 || idEquipo <= 0
                || fechaInicio == null || fechaFin == null) {
            return false;
        }

        if (fechaInicio.isBefore(java.time.LocalDate.now())
                || fechaFin.isBefore(fechaInicio)) {
            return false;
        }

        if (!equipoDAO.estaDisponible(idEquipo)) {
    return false;
}
        
        Reserva reserva = new Reserva();
        reserva.setIdUsuario(idUsuario);
        reserva.setIdEquipo(idEquipo);
        reserva.setFechaInicio(fechaInicio);
        reserva.setFechaFin(fechaFin);
        reserva.setEstado("pendiente");

        return reservaDAO.registrar(reserva);
    }

    public ArrayList<Reserva> listarReservas() {
        return reservaDAO.listar();
    }

    public boolean aprobarReserva(int idReserva) {
        if (idReserva <= 0) {
            return false;
        }

        return reservaDAO.cambiarEstado(idReserva, "aprobada");
    }

    public boolean rechazarReserva(int idReserva) {
        if (idReserva <= 0) {
            return false;
        }

        return reservaDAO.cambiarEstado(idReserva, "rechazada");
    }

    public boolean cancelarReserva(int idReserva) {
        if (idReserva <= 0) {
            return false;
        }

        return reservaDAO.cancelar(idReserva);
    }

    public ArrayList<Reserva> listarReservasPorUsuario(int idUsuario) {
        return reservaDAO.listarPorUsuario(idUsuario);
    }

    public ArrayList<Reserva> listarAprobadasSinPrestamo() {
        return reservaDAO.listarAprobadasSinPrestamo();
    }

}
