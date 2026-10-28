/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.ReservaController;
import modelo.Reserva;
import java.util.ArrayList;
import java.util.Scanner;

/**
 *
 * @author ariel
 */
public class GestionReservas {

    public static void main(String[] args) {
        Scanner entrada = new Scanner(System.in);
        ReservaController controller = new ReservaController();

        int opcion;

        do {
            System.out.println("\n--- GESTION DE RESERVAS ---");
            System.out.println("1. Solicitar reserva");
            System.out.println("2. Listar reservas");
            System.out.println("3. Aprobar reserva");
            System.out.println("4. Rechazar reserva");
            System.out.println("5. Cancelar reserva");
            System.out.println("6. Salir");
            System.out.print("Seleccione una opcion: ");

            opcion = entrada.nextInt();

            switch (opcion) {
                case 1:
                    System.out.print("ID del usuario: ");
                    int idUsuario = entrada.nextInt();

                    System.out.print("ID del equipo: ");
                    int idEquipo = entrada.nextInt();

                    if (controller.registrarReserva(idUsuario, idEquipo)) {
                        System.out.println("Reserva registrada.");
                    } else {
                        System.out.println("No se pudo registrar la reserva.");
                    }
                    break;

                case 2:
                    ArrayList<Reserva> reservas = controller.listarReservas();

                    if (reservas.isEmpty()) {
                        System.out.println("No hay reservas registradas.");
                    } else {
                        for (Reserva reserva : reservas) {
                            System.out.println(
                                "ID: " + reserva.getIdReserva()
                                + " | Usuario: " + reserva.getIdUsuario()
                                + " | Equipo: " + reserva.getIdEquipo()
                                + " | Estado: " + reserva.getEstado()
                            );
                        }
                    }
                    break;

                case 3:
                    System.out.print("ID de la reserva a aprobar: ");
                    int idAprobar = entrada.nextInt();

                    if (controller.aprobarReserva(idAprobar)) {
                        System.out.println("Reserva aprobada.");
                    } else {
                        System.out.println("No se pudo aprobar la reserva.");
                    }
                    break;

                case 4:
                    System.out.print("ID de la reserva a rechazar: ");
                    int idRechazar = entrada.nextInt();

                    if (controller.rechazarReserva(idRechazar)) {
                        System.out.println("Reserva rechazada.");
                    } else {
                        System.out.println("No se pudo rechazar la reserva.");
                    }
                    break;

                case 5:
                    System.out.print("ID de la reserva a cancelar: ");
                    int idCancelar = entrada.nextInt();

                    if (controller.cancelarReserva(idCancelar)) {
                        System.out.println("Reserva cancelada.");
                    } else {
                        System.out.println("No se pudo cancelar la reserva.");
                    }
                    break;

                case 6:
                    System.out.println("Saliendo...");
                    break;

                default:
                    System.out.println("Opcion incorrecta.");
            }

        } while (opcion != 6);

        entrada.close();
    }
}
