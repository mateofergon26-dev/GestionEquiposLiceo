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
 * Realiza las operaciones sobre la tabla EQUIPO.
 */
public class EquipoDAO {

    /**
     * Registra un equipo.
     *
     * @param equipo equipo a registrar
     * @return true si se registró correctamente
     */
    public boolean registrar(Equipo equipo) {

        String sql = "INSERT INTO EQUIPO "
                + "(nombre, tipo, numero_serie, estado_E, ubicacion) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, equipo.getNombre());
            sentencia.setString(2, equipo.getTipo());
            sentencia.setString(3, equipo.getNumeroSerie());
            sentencia.setString(4, equipo.getEstado());
            sentencia.setString(5, equipo.getUbicacion());

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al registrar equipo: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Obtiene todos los equipos.
     *
     * @return lista de equipos
     */
    public ArrayList<Equipo> listar() {

        ArrayList<Equipo> equipos = new ArrayList<>();

        String sql = "SELECT * FROM EQUIPO";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql); ResultSet resultado = sentencia.executeQuery()) {

            while (resultado.next()) {

                Equipo equipo = new Equipo();

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

                equipos.add(equipo);
            }

        } catch (SQLException e) {
            System.out.println("Error al listar equipos: "
                    + e.getMessage());
        }

        return equipos;
    }

    /**
     * Modifica los datos de un equipo.
     *
     * @param equipo equipo a modificar
     * @return true si se modificó correctamente
     */
    public boolean modificar(Equipo equipo) {

        String sql = "UPDATE EQUIPO SET "
                + "nombre = ?, "
                + "tipo = ?, "
                + "numero_serie = ?, "
                + "ubicacion = ? "
                + "WHERE id_equipo = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setString(1, equipo.getNombre());
            sentencia.setString(2, equipo.getTipo());
            sentencia.setString(3, equipo.getNumeroSerie());
            sentencia.setString(4, equipo.getUbicacion());
            sentencia.setInt(5, equipo.getIdEquipo());

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al modificar equipo: "
                    + e.getMessage());
            return false;
        }
    }

    /**
     * Realiza la baja lógica de un equipo.
     *
     * @param idEquipo identificador del equipo
     * @return true si se realizó correctamente
     */
    public boolean darDeBaja(int idEquipo) {

        String sql = "UPDATE EQUIPO "
                + "SET estado_E = 'de_baja' "
                + "WHERE id_equipo = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement sentencia = conexion.prepareStatement(sql)) {

            sentencia.setInt(1, idEquipo);

            sentencia.executeUpdate();

            return true;

        } catch (SQLException e) {
            System.out.println("Error al dar de baja equipo: "
                    + e.getMessage());
            return false;
        }
    }
}
