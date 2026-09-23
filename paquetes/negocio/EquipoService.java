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
 * Contiene las operaciones relacionadas con los equipos.
 */
public class EquipoService {

    private EquipoDAO equipoDAO;

    public EquipoService() {
        equipoDAO = new EquipoDAO();
    }

    public boolean registrarEquipo(Equipo equipo) {

        if (equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            return false;
        }

        if (equipo.getTipo() == null || equipo.getTipo().isEmpty()) {
            return false;
        }

        equipo.setEstado("disponible");

        return equipoDAO.registrar(equipo);
    }

    public ArrayList<Equipo> listarEquipos() {
        return equipoDAO.listar();
    }

    public boolean modificarEquipo(Equipo equipo) {

        if (equipo.getNombre() == null || equipo.getNombre().isEmpty()) {
            return false;
        }

        if (equipo.getTipo() == null || equipo.getTipo().isEmpty()) {
            return false;
        }

        return equipoDAO.modificar(equipo);
    }

    public boolean darDeBajaEquipo(int idEquipo) {
        return equipoDAO.darDeBaja(idEquipo);
    }
}
