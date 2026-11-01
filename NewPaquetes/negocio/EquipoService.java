/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

/**
 *
 * @author ariel
 */

import dao.EquipoDAO;
import modelo.Equipo;

import java.util.ArrayList;

/**
 * Clase que contiene las operaciones relacionadas con los equipos.
 */
public class EquipoService {

    // Objeto que permite acceder a las operaciones de la base de datos relacionadas con los equipos
    private EquipoDAO equipoDAO;

    // Constructor
    public EquipoService() {
        equipoDAO = new EquipoDAO();
    }

    // Registra un equipo en el sistema.
    public boolean registrarEquipo(Equipo equipo) {

        // Verifica que el nombre del equipo no sea nulo ni esté vacio
        if (equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            return false;
        }

        // Verifica que el tipo de equipo no sea nulo ni esté vacio
        if (equipo.getTipo() == null || equipo.getTipo().isEmpty()) {
            return false;
        }

        // Establece el estado inicial del equipo como disponible.
        equipo.setEstado("disponible");

        // Llama al método registrar del DAO para guardar el equipo
        return equipoDAO.registrar(equipo);
    }

    // Obtiene la lista de equipos registrados en el sistema.
    public ArrayList<Equipo> listarEquipos() {

        // Llama al DAO para recuperar los equipos y devolverlos en una lista.
        return equipoDAO.listar();
    }

    // Modifica los datos de un equipo existente.
    public boolean modificarEquipo(Equipo equipo) {

        // Verifica que el nombre del equipo no sea nulo ni esté vacio.
        if (equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            return false;
        }

        // Verifica que el tipo de equipo no sea nulo ni esté vacio.
        if (equipo.getTipo() == null || equipo.getTipo().isEmpty()) {
            return false;
        }

        // Llama al DAO para modificar los datos del equipo.
        return equipoDAO.modificar(equipo);
    }

    // Da de baja un equipo utilizando su identificador.
    public boolean darDeBajaEquipo(int idEquipo) {

        // Llama al DAO para realizar la baja del equipo indicado.
        return equipoDAO.darDeBaja(idEquipo);
    }
}
