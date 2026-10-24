/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;
import dao.PrestamoDAO;
import modelo.Incidencia;
import modelo.Prestamo;

import java.time.LocalDate;

import java.util.ArrayList;


/**
 *
 * @author mateo
 */




/**
 * Clase que contiene las reglas de negocio de los préstamos.
 *
 * Los métodos que modifican datos devuelven un String: null si la operación
 * salió bien, o el motivo del error para mostrárselo a la persona usuaria.
 */
public class PrestamoService {

    private PrestamoDAO prestamoDAO;

    public PrestamoService() {
        prestamoDAO = new PrestamoDAO();
    }

    // Registra un préstamo a partir de una pareja reserva-equipo.
    public String registrarPrestamo(int idReserva, int idEquipo,
            LocalDate fechaDevolucionEsperada) {

        // La fecha de devolución es obligatoria y no puede ser anterior a hoy.
        if (fechaDevolucionEsperada == null) {
            return "Debe indicar la fecha de devolución esperada.";
        }
        if (fechaDevolucionEsperada.isBefore(LocalDate.now())) {
            return "La fecha de devolución esperada no puede ser anterior a hoy.";
        }

        // La reserva debe existir y estar aprobada.
        String estadoReserva = prestamoDAO.obtenerEstadoReserva(idReserva);
        if (estadoReserva == null) {
            return "La reserva " + idReserva + " no existe.";
        }
        if (!estadoReserva.equals("aprobada")) {
            return "La reserva debe estar aprobada (estado actual: " + estadoReserva + ").";
        }

        // El equipo debe formar parte de esa reserva.
        if (!prestamoDAO.existeReservaEquipo(idReserva, idEquipo)) {
            return "El equipo " + idEquipo + " no forma parte de la reserva " + idReserva + ".";
        }

        // No puede haber dos préstamos para la misma pareja reserva-equipo.
        if (prestamoDAO.existePrestamo(idReserva, idEquipo)) {
            return "Ya existe un préstamo para esa reserva y ese equipo.";
        }

        // El equipo debe estar disponible (o reservado) para poder entregarse.
        String estadoEquipo = prestamoDAO.obtenerEstadoEquipo(idEquipo);
        if (estadoEquipo == null
                || !(estadoEquipo.equals("disponible") || estadoEquipo.equals("reservado"))) {
            return "El equipo no está disponible para préstamo (estado actual: "
                    + estadoEquipo + ").";
        }

        // Se arma el préstamo: la entrega es hoy.
        Prestamo prestamo = new Prestamo();
        prestamo.setIdReserva(idReserva);
        prestamo.setIdEquipo(idEquipo);
        prestamo.setFechaEntregaReal(LocalDate.now());
        prestamo.setFechaDevolucionEsperada(fechaDevolucionEsperada);

        if (!prestamoDAO.registrar(prestamo)) {
            return "No se pudo registrar el préstamo.";
        }

        return null;
    }

    // Obtiene la lista de préstamos registrados.
    public ArrayList<Prestamo> listarPrestamos() {
        return prestamoDAO.listar();
    }

    /**
     * Registra la devolución de un préstamo. tipoIncidencia es null si el
     * equipo vuelve sin problemas; si no, puede ser 'retraso' o 'dano'.
     */
    public String registrarDevolucion(int idPrestamo, String tipoIncidencia,
            String descripcion) {

        Prestamo prestamo = prestamoDAO.buscarPorId(idPrestamo);
        if (prestamo == null) {
            return "El préstamo " + idPrestamo + " no existe.";
        }
        if (prestamo.getEstado().equals("devuelto")) {
            return "El préstamo ya fue devuelto.";
        }

        Incidencia incidencia = null;

        if (tipoIncidencia != null) {

            if (!tipoIncidencia.equals("retraso") && !tipoIncidencia.equals("dano")) {
                return "Al registrar una devolución, la incidencia solo puede ser de tipo retraso o dano.";
            }
            if (descripcion != null && descripcion.length() > 255) {
                return "La descripción no puede superar los 255 caracteres.";
            }

            incidencia = new Incidencia();
            incidencia.setIdPrestamo(idPrestamo);
            incidencia.setTipo(tipoIncidencia);
            incidencia.setDescripcion(
                    (descripcion == null || descripcion.trim().isEmpty()) ? null : descripcion.trim());
        }

        if (!prestamoDAO.registrarDevolucion(idPrestamo, incidencia)) {
            return "No se pudo registrar la devolución.";
        }

        return null;
    }
}
