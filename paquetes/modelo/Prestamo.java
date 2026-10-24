/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;
import java.time.LocalDate;


/**
 *
 * @author mateo
 */



/**
 * Clase que representa un préstamo dentro del sistema.
 * Corresponde a la tabla PRESTAMO.
 */
public class Prestamo {

    private int idPrestamo;
    private int idReserva;
    private int idEquipo;
    private String estado;
    private LocalDate fechaEntregaReal;
    private LocalDate fechaDevolucionEsperada;
    private LocalDate fechaDevolucionReal;

    public Prestamo() {
    }

    // Constructor completo.
    public Prestamo(int idPrestamo, int idReserva, int idEquipo, String estado,
            LocalDate fechaEntregaReal, LocalDate fechaDevolucionEsperada,
            LocalDate fechaDevolucionReal) {
        this.idPrestamo = idPrestamo;
        this.idReserva = idReserva;
        this.idEquipo = idEquipo;
        this.estado = estado;
        this.fechaEntregaReal = fechaEntregaReal;
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
        this.fechaDevolucionReal = fechaDevolucionReal;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public int getIdReserva() {
        return idReserva;
    }

    public void setIdReserva(int idReserva) {
        this.idReserva = idReserva;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public LocalDate getFechaEntregaReal() {
        return fechaEntregaReal;
    }

    public void setFechaEntregaReal(LocalDate fechaEntregaReal) {
        this.fechaEntregaReal = fechaEntregaReal;
    }

    public LocalDate getFechaDevolucionEsperada() {
        return fechaDevolucionEsperada;
    }

    public void setFechaDevolucionEsperada(LocalDate fechaDevolucionEsperada) {
        this.fechaDevolucionEsperada = fechaDevolucionEsperada;
    }

    public LocalDate getFechaDevolucionReal() {
        return fechaDevolucionReal;
    }

    public void setFechaDevolucionReal(LocalDate fechaDevolucionReal) {
        this.fechaDevolucionReal = fechaDevolucionReal;
    }
}
