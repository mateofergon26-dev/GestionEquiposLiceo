/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionequiposliceo;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author mateo
 * 
 * 
 * 
 * Clase DAO (Data Access Object) para la entidad Equipo. Corresponde a la Capa
 * de Acceso a Datos: es la única clase que ejecuta sentencias SQL directamente
 * sobre la tabla EQUIPO.
 */

public class EquipoDAO {
    
    // 
    // ALTA (Create)
    // 
    public boolean insertar(Equipo equipo) {
        if (!estadoValido(equipo.getEstadoE())) {
            System.out.println("Error al insertar equipo: estado_E inválido ('" + equipo.getEstadoE() + "'). "
                    + "Valores permitidos: disponible, reservado, prestado, en_reparacion, de_baja.");
            return false;
        }

        String sql = "INSERT INTO EQUIPO (nombre, tipo, numero_serie, estado_E, ubicacion) "
                + "VALUES (?, ?, ?, ?, ?)";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, equipo.getNombre());
            ps.setString(2, equipo.getTipo());
            ps.setString(3, equipo.getNumeroSerie());
            ps.setString(4, equipo.getEstadoE());
            ps.setString(5, equipo.getUbicacion());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al insertar equipo: " + e.getMessage());
            return false;
        }
    }

    // 
    // CONSULTA (Read) - listar todos los equipos activos (no dados de baja)
    // 
    public List<Equipo> listarTodos() {
        List<Equipo> equipos = new ArrayList<>();
        String sql = "SELECT * FROM EQUIPO WHERE estado_E <> 'de_baja'";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql); ResultSet rs = ps.executeQuery()) {

            while (rs.next()) {
                equipos.add(mapearEquipo(rs));
            }

        } catch (SQLException e) {
            System.out.println("Error al listar equipos: " + e.getMessage());
        }
        return equipos;
    }

    // 
    // CONSULTA (Read) - buscar un equipo puntual por su id
    // 
    public Equipo buscarPorId(int idEquipo) {
        String sql = "SELECT * FROM EQUIPO WHERE id_equipo = ?";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);

            try (ResultSet rs = ps.executeQuery()) {
                if (rs.next()) {
                    return mapearEquipo(rs);
                }
            }

        } catch (SQLException e) {
            System.out.println("Error al buscar equipo: " + e.getMessage());
        }
        return null; // no se encontró ningún equipo con ese id
    }

    // 
    // MODIFICACION (Update)
    // 
    public boolean modificar(Equipo equipo) {
        if (!estadoValido(equipo.getEstadoE())) {
            System.out.println("Error al modificar equipo: estado_E inválido ('" + equipo.getEstadoE() + "'). "
                    + "Valores permitidos: disponible, reservado, prestado, en_reparacion, de_baja.");
            return false;
        }

        String sql = "UPDATE EQUIPO SET nombre = ?, tipo = ?, numero_serie = ?, "
                + "estado_E = ?, ubicacion = ? WHERE id_equipo = ?";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setString(1, equipo.getNombre());
            ps.setString(2, equipo.getTipo());
            ps.setString(3, equipo.getNumeroSerie());
            ps.setString(4, equipo.getEstadoE());
            ps.setString(5, equipo.getUbicacion());
            ps.setInt(6, equipo.getIdEquipo());

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al modificar equipo: " + e.getMessage());
            return false;
        }
    }

    // 
    // BAJA LOGICA (Delete logico) - no se borra la fila, se cambia el estado
    // 
    public boolean darDeBaja(int idEquipo) {
        String sql = "UPDATE EQUIPO SET estado_E = 'de_baja' WHERE id_equipo = ?";

        try (Connection con = ConexionBD.obtenerConexion(); PreparedStatement ps = con.prepareStatement(sql)) {

            ps.setInt(1, idEquipo);

            int filasAfectadas = ps.executeUpdate();
            return filasAfectadas > 0;

        } catch (SQLException e) {
            System.out.println("Error al dar de baja el equipo: " + e.getMessage());
            return false;
        }
    }

    // 
    // Método auxiliar privado: valida que el estado sea uno de los permitidos
    // por el dominio definido en el diseño (mismo CHECK que tiene la tabla en MySQL).
    // Validar acá, además de en la base, permite dar un mensaje claro al usuario
    // sin depender de interpretar el texto de una SQLException.
    // 
    private boolean estadoValido(String estado) {
        if (estado == null) {
            return false;
        }
        return estado.equals("disponible")
                || estado.equals("reservado")
                || estado.equals("prestado")
                || estado.equals("en_reparacion")
                || estado.equals("de_baja");
    }

    // 
    // Método auxiliar privado: convierte una fila del ResultSet en un objeto Equipo
    // (evita repetir este mismo código en listarTodos() y en buscarPorId())
    // 
    private Equipo mapearEquipo(ResultSet rs) throws SQLException {
        Equipo equipo = new Equipo();
        equipo.setIdEquipo(rs.getInt("id_equipo"));
        equipo.setNombre(rs.getString("nombre"));
        equipo.setTipo(rs.getString("tipo"));
        equipo.setNumeroSerie(rs.getString("numero_serie"));
        equipo.setEstadoE(rs.getString("estado_E"));
        equipo.setUbicacion(rs.getString("ubicacion"));
        return equipo;
    }
    
    

}

    
    
    
    
    
    
    

