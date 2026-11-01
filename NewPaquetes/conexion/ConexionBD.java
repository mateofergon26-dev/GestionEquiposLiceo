/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package conexion;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;


/**
 *
 * @author mateo
 * 
 * Clase encargada de centralizar la conexión con la base de datos.
 * 
 */
public class ConexionBD {

    private static final String URL
            = "jdbc:mysql://localhost:3306/gestion_equipos";

    private static final String USUARIO = "root";

    private static final String CONTRASENA = "";

    public static Connection conectar() throws SQLException {

        return DriverManager.getConnection(
                URL,
                USUARIO,
                CONTRASENA
        );
    }
}
