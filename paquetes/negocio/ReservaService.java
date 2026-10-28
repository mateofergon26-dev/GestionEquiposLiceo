/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dao.ReservaDAO;
import modelo.Reserva;
import java.util.ArrayList;

/**
 *
 * @author ariel
 */
public class ReservaService {

    private ReservaDAO reservaDAO;

    public ReservaService() {
        reservaDAO = new ReservaDAO();
    }

    public boolean registrarReserva(int idUsuario, int idEquipo) {
        if (idUsuario <= 0 || idEquipo <= 0) {
            System.out.println("El usuario y el equipo deben ser válidos.");
            return false;
        }

        Reserva reserva = new Reserva();
        reserva.setIdUsuario(idUsuario);
        reserva.setIdEquipo(idEquipo);
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
}
