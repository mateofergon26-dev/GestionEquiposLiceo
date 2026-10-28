/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package dao;

import conexion.ConexionBD;
import modelo.Reserva;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

/**
 *
 * @author ariel
 */
public class ReservaDAO {



public boolean registrar(Reserva reserva) {

    String sqlReserva = "INSERT INTO RESERVA "
            + "(id_usuario, fecha_solicitud, fecha_inicio, "
            + "fecha_fin, estado_R) "
            + "VALUES (?, CURDATE(), CURDATE(), CURDATE(), 'pendiente')";

    String sqlEquipo = "INSERT INTO RESERVA_EQUIPO "
            + "(id_reserva, id_equipo) VALUES (?, ?)";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(
                 sqlReserva, java.sql.Statement.RETURN_GENERATED_KEYS)) {

        ps.setInt(1, reserva.getIdUsuario());
        ps.executeUpdate();

        try (ResultSet rs = ps.getGeneratedKeys()) {
            if (rs.next()) {
                int idReserva = rs.getInt(1);

                try (PreparedStatement psEquipo =
                        conexion.prepareStatement(sqlEquipo)) {

                    psEquipo.setInt(1, idReserva);
                    psEquipo.setInt(2, reserva.getIdEquipo());
                    psEquipo.executeUpdate();

                    return true;
                }
            }
        }

    } catch (SQLException e) {
        System.out.println("Error al registrar reserva: "
                + e.getMessage());
    }

    return false;
}


public ArrayList<Reserva> listar() {
    ArrayList<Reserva> reservas = new ArrayList<>();

    String sql = "SELECT r.id_reserva, r.id_usuario, "
               + "re.id_equipo, r.estado_R "
               + "FROM RESERVA r "
               + "INNER JOIN RESERVA_EQUIPO re "
               + "ON r.id_reserva = re.id_reserva";

    try (Connection conexion = ConexionBD.conectar();
         PreparedStatement ps = conexion.prepareStatement(sql);
         ResultSet rs = ps.executeQuery()) {

        while (rs.next()) {
            Reserva reserva = new Reserva();

            reserva.setIdReserva(rs.getInt("id_reserva"));
            reserva.setIdUsuario(rs.getInt("id_usuario"));
            reserva.setIdEquipo(rs.getInt("id_equipo"));
            reserva.setEstado(rs.getString("estado_R"));

            reservas.add(reserva);
        }

    } catch (SQLException e) {
        System.out.println("Error al listar reservas: " + e.getMessage());
    }

    return reservas;
}

    public boolean cambiarEstado(int idReserva, String nuevoEstado) {
        String sql = "UPDATE RESERVA SET estado_R = ? "
                + "WHERE id_reserva = ? AND estado_R = 'pendiente'";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setString(1, nuevoEstado);
            ps.setInt(2, idReserva);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cambiar estado: " + e.getMessage());
            return false;
        }
    }

    public boolean cancelar(int idReserva) {
        String sql = "UPDATE RESERVA SET estado_R = 'cancelada' "
                + "WHERE id_reserva = ? "
                + "AND estado_R IN ('pendiente', 'aprobada') "
                + "AND NOT EXISTS "
                + "(SELECT 1 FROM PRESTAMO "
                + "WHERE PRESTAMO.id_reserva = RESERVA.id_reserva)";

        try (Connection conexion = ConexionBD.conectar();
             PreparedStatement ps = conexion.prepareStatement(sql)) {

            ps.setInt(1, idReserva);

            return ps.executeUpdate() > 0;

        } catch (SQLException e) {
            System.out.println("Error al cancelar reserva: " + e.getMessage());
            return false;
        }
    }
}
