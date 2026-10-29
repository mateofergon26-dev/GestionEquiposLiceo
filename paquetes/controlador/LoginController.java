/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package controlador;
import negocio.UsuarioService;
import modelo.UsuarioModelo;

public class LoginController {

    private UsuarioService usuarioService = new UsuarioService();

    public UsuarioModelo iniciarSesion(String email, String contrasena) {
        return usuarioService.iniciarSesion(email, contrasena);
    }
}
