/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;
import modelo.Equipo;
import conexion.ConexionBD;
/**
 *
 * @author ariel
 */

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 * Clase encargada de realizar las operaciones sobre la tabla EQUIPO de la base
 * de datos.
 */
public class EquipoDAO {

    //Registra un nuevo equipo en la base de datos.
    public boolean registrar(Equipo equipo) {

        // Consulta SQL para insertar un equipo.
        String sql = "INSERT INTO EQUIPO "
                + "(nombre, tipo, numero_serie, estado_E, ubicacion) "
                + "VALUES (?, ?, ?, ?, ?)";

        // Se abre la conexión y se prepara la consulta.
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            // Se asignan los valores a los parámetros (?).
            sentencia.setString(1, equipo.getNombre());
            sentencia.setString(2, equipo.getTipo());
            sentencia.setString(3, equipo.getNumeroSerie());
            sentencia.setString(4, equipo.getEstado());
            sentencia.setString(5, equipo.getUbicacion());

            // Ejecuta el INSERT.
            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {

            // Si ocurre un error, se muestra el mensaje.
            System.out.println("Error al registrar equipo: "
                    + e.getMessage());

            return false;
        }
    }

    //Obtiene todos los equipos almacenados en la base de datos.
    public ArrayList<Equipo> listar() {

        // Lista donde se guardarán los equipos obtenidos.
        ArrayList<Equipo> equipos = new ArrayList<>();

        // Consulta SQL para obtener todos los registros.
        String sql = "SELECT * FROM EQUIPO";

        // Se abre la conexión y se ejecuta la consulta.
        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet resultado = sentencia.executeQuery()) {

            // Recorre cada fila obtenida.
            while (resultado.next()) {

                // Se crea un objeto Equipo.
                Equipo equipo = new Equipo();

                // Se cargan los datos desde la BD al objeto.
                equipo.setIdEquipo(
                        resultado.getInt("id_equipo")
                );

                equipo.setNombre(
                        resultado.getString("nombre")
                );

                equipo.setTipo(
                        resultado.getString("tipo")
                );

                equipo.setNumeroSerie(
                        resultado.getString("numero_serie")
                );

                equipo.setEstado(
                        resultado.getString("estado_E")
                );

                equipo.setUbicacion(
                        resultado.getString("ubicacion")
                );

                // Se agrega el equipo a la lista.
                equipos.add(equipo);
            }

        } catch (SQLException e) {

            System.out.println("Error al listar equipos: "
                    + e.getMessage());
        }

        // Devuelve la lista completa.
        return equipos;
    }

    //Modifica los datos de un equipo.
    public boolean modificar(Equipo equipo) {

        // Consulta SQL para actualizar un equipo.
        String sql = "UPDATE EQUIPO SET "
                + "nombre = ?, "
                + "tipo = ?, "
                + "numero_serie = ?, "
                + "ubicacion = ? "
                + "WHERE id_equipo = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            // Se cargan los nuevos datos.
            sentencia.setString(1, equipo.getNombre());
            sentencia.setString(2, equipo.getTipo());
            sentencia.setString(3, equipo.getNumeroSerie());
            sentencia.setString(4, equipo.getUbicacion());

            // ID del equipo que se quiere modificar.
            sentencia.setInt(5, equipo.getIdEquipo());

            // Ejecuta el UPDATE.
            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println("Error al modificar equipo: "
                    + e.getMessage());

            return false;
        }
    }

    //Realiza la baja lógica de un equipo
    public boolean darDeBaja(int idEquipo) {

        // Consulta SQL para cambiar el estado.
        String sql = "UPDATE EQUIPO "
                + "SET estado_E = 'de_baja' "
                + "WHERE id_equipo = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            // Se indica qué equipo se dará de baja.
            sentencia.setInt(1, idEquipo);

            // Ejecuta el UPDATE.
            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {

            System.out.println("Error al dar de baja equipo: "
                    + e.getMessage());

            return false;
        }
    }
}
