/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author mateo
 */


/**
 * Clase que representa una incidencia ocurrida durante un préstamo
 * (retraso, equipo no devuelto o daño). Corresponde a la tabla INCIDENCIA.
 */
public class Incidencia {

    private int idIncidencia;
    private int idPrestamo;
    private String tipo;
    private String descripcion;

    public Incidencia() {
    }

    // Constructor completo.
    public Incidencia(int idIncidencia, int idPrestamo, String tipo, String descripcion) {
        this.idIncidencia = idIncidencia;
        this.idPrestamo = idPrestamo;
        this.tipo = tipo;
        this.descripcion = descripcion;
    }

    public int getIdIncidencia() {
        return idIncidencia;
    }

    public void setIdIncidencia(int idIncidencia) {
        this.idIncidencia = idIncidencia;
    }

    public int getIdPrestamo() {
        return idPrestamo;
    }

    public void setIdPrestamo(int idPrestamo) {
        this.idPrestamo = idPrestamo;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getDescripcion() {
        return descripcion;
    }

    public void setDescripcion(String descripcion) {
        this.descripcion = descripcion;
    }
}
