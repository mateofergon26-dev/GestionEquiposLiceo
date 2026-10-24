/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import dao.IncidenciaDAO;
import dao.PrestamoDAO;
import modelo.Incidencia;

import java.util.ArrayList;


/**
 *
 * @author mateo
 */



/**
 * Clase que contiene las reglas de negocio de las incidencias.
 *
 * registrarIncidencia devuelve null si salió bien, o el motivo del error.
 */
public class IncidenciaService {

    private IncidenciaDAO incidenciaDAO;
    private PrestamoDAO prestamoDAO;

    public IncidenciaService() {
        incidenciaDAO = new IncidenciaDAO();
        prestamoDAO = new PrestamoDAO();
    }

    // Registra una incidencia sobre un préstamo existente.
    public String registrarIncidencia(Incidencia incidencia) {

        // El tipo debe ser uno de los permitidos por el diseño.
        String tipo = incidencia.getTipo();
        if (tipo == null
                || !(tipo.equals("retraso") || tipo.equals("no_devuelto") || tipo.equals("dano"))) {
            return "El tipo debe ser retraso, no_devuelto o dano.";
        }

        // La descripción es opcional, pero tiene un largo máximo.
        String descripcion = incidencia.getDescripcion();
        if (descripcion != null && descripcion.length() > 255) {
            return "La descripción no puede superar los 255 caracteres.";
        }
        incidencia.setDescripcion(
                (descripcion == null || descripcion.trim().isEmpty()) ? null : descripcion.trim());

        // El préstamo asociado debe existir.
        if (prestamoDAO.buscarPorId(incidencia.getIdPrestamo()) == null) {
            return "El préstamo " + incidencia.getIdPrestamo() + " no existe.";
        }

        if (!incidenciaDAO.registrar(incidencia)) {
            return "No se pudo registrar la incidencia.";
        }

        return null;
    }

    // Obtiene la lista de incidencias registradas.
    public ArrayList<Incidencia> listarIncidencias() {
        return incidenciaDAO.listar();
    }
}
