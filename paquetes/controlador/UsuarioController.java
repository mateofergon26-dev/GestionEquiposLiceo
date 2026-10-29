/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;

import java.util.List;
import negocio.UsuarioService;
import modelo.UsuarioModelo;


public class UsuarioController {

    /**
     * Crea una instancia de UsuarioService que lo hace para acceder a las
     * operaciones de gestión de usuario
     */
    private UsuarioService usuarioService = new UsuarioService();

    /**
     * Recibe los datos del usuario y los envia al service con la finalidad de
     * realizar el registro
     */
    public boolean registrarUsuario(UsuarioModelo usuario) {
        usuarioService.registrarUsuario(usuario);
        return false;
    }

    /**
     * Solicita al Service la lista de los usuarios
     */
    public List<UsuarioModelo> consultarUsuarios() {
        return usuarioService.consultarUsuarios();
    }

    /**
     * Recibe al usuario y se le modifican sus datos
     */
    public void modificarUsuario(UsuarioModelo usuario) {
        usuarioService.modificarUsuario(usuario);
    }

    /**
     * Recibe el ID del usuario y lo envia al Service 
     * para realizar la baja lógica
     */
    public void darDeBajaUsuario(int idUsuario) {
        usuarioService.darDeBajaUsuario(idUsuario);
    }

}
