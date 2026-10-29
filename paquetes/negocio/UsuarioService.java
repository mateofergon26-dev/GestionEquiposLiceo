/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package negocio;

import modelo.UsuarioModelo;
import dao.UsuarioDAO;
import java.util.List;

public class UsuarioService {

    /**
     * Crea una instancia en UsuarioDAO para acceder a a base de datos
     */
    private UsuarioDAO usuarioDAO = new UsuarioDAO();

    /**
     * Recibe el usuario desde controller y lo envia al DAO para realizar el
     * registro en la base de datos
     */
    public void registrarUsuario(UsuarioModelo usuario) {
        usuarioDAO.registrarUsuario(usuario);
    }

    /**
     * Solicita a DAO la lista de los usuarios que hay en la base de datos
     */
    public List<UsuarioModelo> consultarUsuarios() {
        return usuarioDAO.consultarUsuarios();
    }

    /**
     * Recibe al usuario desde controller y lo envia a DAO para actualizar sus
     * datos en la base de datos
     */
    public void modificarUsuario(UsuarioModelo usuario) {
        usuarioDAO.modificarUsuario(usuario);
    }

    /**
     * Cambia el estado del usuario de activo a inactivo
     */
    public void darDeBajaUsuario(int idUsuario) {
        usuarioDAO.darDeBajaUsuario(idUsuario);
    }

    public UsuarioModelo iniciarSesion(String email, String contrasena) {
        return usuarioDAO.iniciarSesion(email, contrasena);
    }
    
}
