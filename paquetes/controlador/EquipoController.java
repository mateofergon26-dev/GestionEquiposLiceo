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
 * Clase encargada de controlar las operaciones relacionadas con los equipos.
 */
public class EquipoController {

    // Objeto que permite acceder a las operaciones de la lógica de negocio.
    private EquipoService equipoService;

    /**
     * Constructor de la clase. Crea una instancia de EquipoService para poder
     * utilizar sus métodos.
     */
    public EquipoController() {
        equipoService = new EquipoService();
    }

    // Registra un nuevo equipo.
    public boolean registrarEquipo(String nombre,
            String tipo,
            String numeroSerie,
            String ubicacion) {

        // Se crea un objeto Equipo vacio.
        Equipo equipo = new Equipo();

        // Se cargan en el objeto los datos recibidos.
        equipo.setNombre(nombre);
        equipo.setTipo(tipo);
        equipo.setNumeroSerie(numeroSerie);
        equipo.setUbicacion(ubicacion);

        // Se envía el equipo al Service para realizar el registro.
        return equipoService.registrarEquipo(equipo);
    }

    // Obtiene todos los equipos registrados.
    public ArrayList<Equipo> listarEquipos() {

        // Se solicita al Service la lista de equipos.
        return equipoService.listarEquipos();
    }

    // Modifica los datos de un equipo existente.
    public boolean modificarEquipo(int id,
            String nombre,
            String tipo,
            String numeroSerie,
            String ubicacion) {

        // Se crea un objeto Equipo para guardar los nuevos datos.
        Equipo equipo = new Equipo();

        // Se cargan los datos del equipo.
        equipo.setIdEquipo(id);
        equipo.setNombre(nombre);
        equipo.setTipo(tipo);
        equipo.setNumeroSerie(numeroSerie);
        equipo.setUbicacion(ubicacion);

        // Se envía el equipo al Service para realizar la modificación.
        return equipoService.modificarEquipo(equipo);
    }

    //Da de baja un equipo de forma lógica.

    public boolean darDeBajaEquipo(int id) {

        // Se envía el ID al Service para realizar la baja lógica.
        return equipoService.darDeBajaEquipo(id);
    }
}
