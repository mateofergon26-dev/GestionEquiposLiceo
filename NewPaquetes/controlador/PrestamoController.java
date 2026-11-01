/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import modelo.Prestamo;
import negocio.PrestamoService;

import java.time.LocalDate;
import java.time.format.DateTimeParseException;

import java.util.ArrayList;

/**
 *
 * @author mateo
 */



/**
 * Clase encargada de controlar las operaciones relacionadas con los préstamos.
 * Los métodos que modifican datos devuelven null si salió bien, o el mensaje
 * de error para que la vista se lo muestre a la persona usuaria.
 */
public class PrestamoController {

    // Objeto que permite acceder a las operaciones de la lógica de negocio.
    private PrestamoService prestamoService;

    public PrestamoController() {
        prestamoService = new PrestamoService();
    }

    // Registra un préstamo. La fecha llega como texto con formato aaaa-mm-dd.
    public String registrarPrestamo(int idReserva, int idEquipo,
            String fechaDevolucionEsperada) {

        LocalDate fecha;

        try {
            fecha = LocalDate.parse(fechaDevolucionEsperada.trim());
        } catch (DateTimeParseException | NullPointerException e) {
            return "Fecha inválida. Use el formato aaaa-mm-dd (por ejemplo 2026-10-30).";
        }

        return prestamoService.registrarPrestamo(idReserva, idEquipo, fecha);
    }

    // Obtiene todos los préstamos registrados.
    public ArrayList<Prestamo> listarPrestamos() {
        return prestamoService.listarPrestamos();
    }

    // Registra la devolución de un préstamo (tipoIncidencia es null si no hay incidencia).
    public String registrarDevolucion(int idPrestamo, String tipoIncidencia,
            String descripcion) {
        return prestamoService.registrarDevolucion(idPrestamo, tipoIncidencia, descripcion);
    }
}
