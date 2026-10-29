/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelo.UsuarioModelo;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UsuarioDAO {

    /**
     * Registra los usuarios
     */
  public boolean registrarUsuario(UsuarioModelo usuario) {

    String sql = "INSERT INTO USUARIO (nombre, apellido, email, contrasena, rol) VALUES (?, ?, ?, ?, ?)";

    try (Connection conexion = ConexionBD.conectar(); PreparedStatement datos = conexion.prepareStatement(sql)) {

        // Coloca los datos
        datos.setString(1, usuario.getnombre());
        datos.setString(2, usuario.getapellido());
        datos.setString(3, usuario.getemail());
        datos.setString(4, usuario.getcontrasena());
        datos.setString(5, usuario.getrol());

        // Realiza el registro y verifica si se afectó al menos una fila
        int ru = datos.executeUpdate();
        
        System.out.println("El usuario fue registrado.");
        return ru > 0; 

    } catch (SQLException e) {
        System.out.println("Hubo un error al registrar el usuario: " + e.getMessage());
        return false; 
    }
}

    /**
     * Consulta los usuarios
     */
    /**
 * Consulta los usuarios activos
 */
public List<UsuarioModelo> consultarUsuarios() {

    List<UsuarioModelo> listaUsuarios = new ArrayList<>();

    String sql = "SELECT id_usuario, nombre, apellido, email, "
               + "contrasena, rol FROM USUARIO WHERE activo = TRUE";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet resultado = ps.executeQuery()) {

        while (resultado.next()) {
            UsuarioModelo usuario = new UsuarioModelo(
                    resultado.getInt("id_usuario"),
                    resultado.getString("nombre"),
                    resultado.getString("apellido"),
                    resultado.getString("email"),
                    resultado.getString("contrasena"),
                    resultado.getString("rol")
            );

            listaUsuarios.add(usuario);
        }

    } catch (SQLException e) {
        System.out.println(
                "Hubo un error al consultar los usuarios: "
                + e.getMessage()
        );
    }

    return listaUsuarios;
}

    /**
     * Modificar usuario
     */
    public boolean modificarUsuario(UsuarioModelo usuario) {

        String sql = "UPDATE USUARIO SET " + "nombre = ?, " + "apellido = ?, " + "email = ?, " + "contrasena = ?, " + "rol = ? " + "WHERE id_usuario = ?";

        try (Connection conexion = ConexionBD.conectar(); PreparedStatement datos = conexion.prepareStatement(sql)) {

            datos.setString(1, usuario.getnombre());
            datos.setString(2, usuario.getapellido());
            datos.setString(3, usuario.getemail());
            datos.setString(4, usuario.getcontrasena());
            datos.setString(5, usuario.getrol());
            datos.setInt(6, usuario.getidUsuario());

          int filasModificadas = datos.executeUpdate();

        if (filasModificadas > 0) {
            return true; // Se modificó correctamente
        } else {
            return false; // No se encontró el ID
        }
        
    } catch (SQLException e) {
        System.out.println("Hubo un error al modificar el usuario: " + e.getMessage());
        return false;
    }

    }

    /**
 * Dar de baja un usuario (baja lógica)
 */
public void darDeBajaUsuario(int idUsuario) {

    String sql = "UPDATE USUARIO SET activo = FALSE "
               + "WHERE id_usuario = ? AND activo = TRUE";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement datos = conexion.prepareStatement(sql)) {

        datos.setInt(1, idUsuario);

        int filasModificadas = datos.executeUpdate();

        if (filasModificadas > 0) {
            System.out.println("Usuario dado de baja correctamente.");
        } else {
            System.out.println(
                "El usuario no existe o ya se encuentra dado de baja."
            );
        }

    } catch (SQLException e) {
        System.out.println(
            "Error al dar de baja el usuario: " + e.getMessage()
        );
    }
}
    
    /**
     * Método iniciar sesión
     */
    public UsuarioModelo iniciarSesion(String email, String contrasena) {
    String sql = "SELECT id_usuario, nombre, apellido, email, " + "contrasena, rol FROM USUARIO " + "WHERE email = ? AND contrasena = ?";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement sentencia = conexion.prepareStatement(sql)) {

        sentencia.setString(1, email);
        sentencia.setString(2, contrasena);

        try (ResultSet resultado = sentencia.executeQuery()) {
            if (resultado.next()) {
                UsuarioModelo usuario = new UsuarioModelo();

                usuario.setidUsuario(resultado.getInt("id_usuario"));
                usuario.setnombre(resultado.getString("nombre"));
                usuario.setapellido(resultado.getString("apellido"));
                usuario.setemail(resultado.getString("email"));
                usuario.setcontrasena(resultado.getString("contrasena"));
                usuario.setrol(resultado.getString("rol"));

                return usuario;
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al iniciar sesión: " + e.getMessage());
    }

    return null;
}


   
}
