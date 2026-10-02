    /*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

/**
 *
 * @author ariel
 */

import controlador.EquipoController;
import modelo.Equipo;

import java.util.ArrayList;
import java.util.Scanner;

//Interfaz para gestionar equipos.
public class GestionEquipos {

    // Metodo principal donde comienza la ejecucion del programa.
    public static void main(String[] args) {

        // Crea un Scanner para recibir datos desde el teclado.
        Scanner entrada = new Scanner(System.in);

        // Crea el controlador que se utilizara para gestionar los equipos.
        EquipoController controller = new EquipoController();

        // Variable que guarda la opcion seleccionada por el usuario.
        int opcion;

        // Comienza un ciclo que muestra el menu al menos una vez.
        do {

            System.out.println();
            System.out.println("=================================");
            System.out.println("       GESTION DE EQUIPOS");
            System.out.println("=================================");
            System.out.println("1. Registrar equipo");
            System.out.println("2. Listar equipos");
            System.out.println("3. Modificar equipo");
            System.out.println("4. Dar de baja equipo");
            System.out.println("5. Salir");
            System.out.println("=================================");
            System.out.print("Seleccione una opcion: ");

            // Lee la opcion numerica ingresada por el usuario.
            opcion = entrada.nextInt();

            // Consume el salto de linea pendiente despues de leer un numero.
            entrada.nextLine();

            // Evalua la opcion seleccionada y ejecuta el caso correspondiente.
            switch (opcion) {

                // Caso para registrar un equipo.
                case 1:

                    System.out.println();
                    System.out.println("--- REGISTRAR EQUIPO ---");

                    System.out.print("Nombre: ");

                    // Guarda el nombre ingresado.
                    String nombre = entrada.nextLine();

                    System.out.print("Tipo: ");

                    // Guarda el tipo ingresado.
                    String tipo = entrada.nextLine();

                    System.out.print("Numero de serie: ");

                    // Guarda el numero de serie ingresado.
                    String numeroSerie = entrada.nextLine();

                    System.out.print("Ubicacion: ");

                    // Guarda la ubicacion ingresada.
                    String ubicacion = entrada.nextLine();

                    // Intenta registrar el equipo mediante el controlador.
                    if (controller.registrarEquipo(
                            nombre,
                            tipo,
                            numeroSerie,
                            ubicacion)) {

                        System.out.println("Equipo registrado correctamente.");

                    } else {

                        System.out.println("No se pudo registrar el equipo.");
                    }

                    break;

                // Caso para listar los equipos.
                case 2:

                    System.out.println();
                    System.out.println("--- LISTA DE EQUIPOS ---");

                    // Declara la lista que almacenara los equipos.
                    ArrayList<Equipo> equipos
                            = controller.listarEquipos();

                    // Comprueba si la lista de equipos esta vacia.
                    if (equipos.isEmpty()) {

                        System.out.println("No hay equipos registrados.");

                    } else {

                        // Recorre la lista de equipos usando un for tradicional.
                        for (int i = 0; i < equipos.size(); i++) {

                            // Obtiene el equipo correspondiente a la posicion actual.
                            Equipo equipo = equipos.get(i);

                            // Muestra una separacion entre los equipos.
                            System.out.println("---------------------------------");

                            // Muestra el ID del equipo.
                            System.out.println("ID: " + equipo.getIdEquipo());

                            // Muestra el nombre del equipo.
                            System.out.println("Nombre: " + equipo.getNombre());

                            // Muestra el tipo de equipo.
                            System.out.println("Tipo: " + equipo.getTipo());

                            // Muestra el numero de serie del equipo.
                            System.out.println("Numero de serie: "
                                    + equipo.getNumeroSerie());

                            // Muestra el estado del equipo.
                            System.out.println("Estado: " + equipo.getEstado());

                            // Muestra la ubicacion del equipo.
                            System.out.println("Ubicacion: "
                                    + equipo.getUbicacion());
                        }

                        System.out.println("---------------------------------");
                    }

                    break;

                // Caso para modificar un equipo.
                case 3:

                    System.out.println();
                    System.out.println("--- MODIFICAR EQUIPO ---");

                    System.out.print("ID del equipo: ");

                    // Lee el ID del equipo que se desea modificar.
                    int idModificar = entrada.nextInt();

                    // Consume el salto de linea pendiente.
                    entrada.nextLine();

                    System.out.print("Nuevo nombre: ");

                    // Guarda el nuevo nombre.
                    String nuevoNombre = entrada.nextLine();

                    System.out.print("Nuevo tipo: ");

                    // Guarda el nuevo tipo.
                    String nuevoTipo = entrada.nextLine();

                    System.out.print("Nuevo numero de serie: ");

                    // Guarda el nuevo numero de serie.
                    String nuevoNumeroSerie = entrada.nextLine();

                    System.out.print("Nueva ubicacion: ");

                    // Guarda la nueva ubicacion.
                    String nuevaUbicacion = entrada.nextLine();

                    // Intenta modificar el equipo mediante el controlador.
                    if (controller.modificarEquipo(
                            idModificar,
                            nuevoNombre,
                            nuevoTipo,
                            nuevoNumeroSerie,
                            nuevaUbicacion)) {

                        System.out.println("Equipo modificado correctamente.");

                    } else {

                        System.out.println("No se pudo modificar el equipo.");
                    }

                    break;

                // Caso para dar de baja un equipo.
                case 4:

                    System.out.println();
                    System.out.println("--- DAR DE BAJA EQUIPO ---");

                    System.out.print("ID del equipo: ");

                    // Lee el ID del equipo que se desea dar de baja.
                    int idBaja = entrada.nextInt();

                    // Consume el salto de linea pendiente.
                    entrada.nextLine();

                    // Intenta dar de baja el equipo mediante el controlador.
                    if (controller.darDeBajaEquipo(idBaja)) {

                        System.out.println(
                                "Equipo dado de baja correctamente."
                        );

                    } else {

                        System.out.println(
                                "No se pudo dar de baja el equipo."
                        );
                    }

                    break;

                // Caso para salir del programa.
                case 5:

                    System.out.println();
                    System.out.println("Programa finalizado.");

                    break;

                // Caso que se ejecuta cuando la opcion no es valida.
                default:

                    System.out.println();
                    System.out.println("Opcion incorrecta.");
            }

            // Repite el menu mientras la opcion seleccionada no sea 5.
        } while (opcion != 5);

        // Cierra el Scanner al finalizar el programa.
        entrada.close();
    }
}
