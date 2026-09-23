/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

/**
 *
 * @author ariel
 */

import modelo.Equipo;
import negocio.EquipoService;

import java.util.ArrayList;

/**
 * Controla las operaciones relacionadas con los equipos.
 */
public class EquipoController {

    private EquipoService equipoService;

    public EquipoController() {
        equipoService = new EquipoService();
    }

    public boolean registrarEquipo(String nombre,
            String tipo,
            String numeroSerie,
            String ubicacion) {

        Equipo equipo = new Equipo();

        equipo.setNombre(nombre);
        equipo.setTipo(tipo);
        equipo.setNumeroSerie(numeroSerie);
        equipo.setUbicacion(ubicacion);

        return equipoService.registrarEquipo(equipo);
    }

    public ArrayList<Equipo> listarEquipos() {
        return equipoService.listarEquipos();
    }

    public boolean modificarEquipo(int id,
            String nombre,
            String tipo,
            String numeroSerie,
            String ubicacion) {

        Equipo equipo = new Equipo();

        equipo.setIdEquipo(id);
        equipo.setNombre(nombre);
        equipo.setTipo(tipo);
        equipo.setNumeroSerie(numeroSerie);
        equipo.setUbicacion(ubicacion);

        return equipoService.modificarEquipo(equipo);
    }

    public boolean darDeBajaEquipo(int id) {
        return equipoService.darDeBajaEquipo(id);
    }
}
