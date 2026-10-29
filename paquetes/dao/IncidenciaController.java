/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import modelo.Incidencia;
import negocio.IncidenciaService;

import java.util.ArrayList;


/**
 *
 * @author mateo
 */



/**
 * Clase encargada de controlar las operaciones relacionadas con las incidencias.
 * registrarIncidencia devuelve null si salió bien, o el mensaje de error.
 */
public class IncidenciaController {

    // Objeto que permite acceder a las operaciones de la lógica de negocio.
    private IncidenciaService incidenciaService;

    public IncidenciaController() {
        incidenciaService = new IncidenciaService();
    }

    // Registra una incidencia sobre un préstamo.
    public String registrarIncidencia(int idPrestamo, String tipo, String descripcion) {

        // Se crea un objeto Incidencia y se cargan los datos recibidos.
        Incidencia incidencia = new Incidencia();
        incidencia.setIdPrestamo(idPrestamo);
        incidencia.setTipo(tipo);
        incidencia.setDescripcion(descripcion);

        // Se envía la incidencia al Service para realizar el registro.
        return incidenciaService.registrarIncidencia(incidencia);
    }

    // Obtiene todas las incidencias registradas.
    public ArrayList<Incidencia> listarIncidencias() {
        return incidenciaService.listarIncidencias();
    }
}
