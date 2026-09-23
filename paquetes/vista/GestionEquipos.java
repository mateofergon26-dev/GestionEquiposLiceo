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

/**
 * Interfaz para gestionar equipos.
 */
public class GestionEquipos {

    public static void main(String[] args) {

        Scanner entrada = new Scanner(System.in);
        EquipoController controller = new EquipoController();

        int opcion;

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

            opcion = entrada.nextInt();
            entrada.nextLine();

            switch (opcion) {

                case 1:

                    System.out.println();
                    System.out.println("--- REGISTRAR EQUIPO ---");

                    System.out.print("Nombre: ");
                    String nombre = entrada.nextLine();

                    System.out.print("Tipo: ");
                    String tipo = entrada.nextLine();

                    System.out.print("Numero de serie: ");
                    String numeroSerie = entrada.nextLine();

                    System.out.print("Ubicacion: ");
                    String ubicacion = entrada.nextLine();

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

                case 2:

                    System.out.println();
                    System.out.println("--- LISTA DE EQUIPOS ---");

                    ArrayList<Equipo> equipos
                            = controller.listarEquipos();

                    if (equipos.isEmpty()) {

                        System.out.println("No hay equipos registrados.");

                    } else {

                        for (Equipo equipo : equipos) {

                            System.out.println("---------------------------------");
                            System.out.println("ID: " + equipo.getIdEquipo());
                            System.out.println("Nombre: " + equipo.getNombre());
                            System.out.println("Tipo: " + equipo.getTipo());
                            System.out.println("Numero de serie: "
                                    + equipo.getNumeroSerie());
                            System.out.println("Estado: " + equipo.getEstado());
                            System.out.println("Ubicacion: "
                                    + equipo.getUbicacion());
                        }

                        System.out.println("---------------------------------");
                    }

                    break;

                case 3:

                    System.out.println();
                    System.out.println("--- MODIFICAR EQUIPO ---");

                    System.out.print("ID del equipo: ");
                    int idModificar = entrada.nextInt();
                    entrada.nextLine();

                    System.out.print("Nuevo nombre: ");
                    String nuevoNombre = entrada.nextLine();

                    System.out.print("Nuevo tipo: ");
                    String nuevoTipo = entrada.nextLine();

                    System.out.print("Nuevo numero de serie: ");
                    String nuevoNumeroSerie = entrada.nextLine();

                    System.out.print("Nueva ubicacion: ");
                    String nuevaUbicacion = entrada.nextLine();

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

                case 4:

                    System.out.println();
                    System.out.println("--- DAR DE BAJA EQUIPO ---");

                    System.out.print("ID del equipo: ");
                    int idBaja = entrada.nextInt();
                    entrada.nextLine();

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

                case 5:

                    System.out.println();
                    System.out.println("Programa finalizado.");

                    break;

                default:

                    System.out.println();
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 5);

        entrada.close();
    }
}
