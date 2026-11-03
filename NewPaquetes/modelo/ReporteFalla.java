/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

import java.time.LocalDate;

/**
 *
 * @author ariel
 */
public class ReporteFalla {
    
     private int idReporte;
    private LocalDate fechaReporte;
    private String descripcionFalla;
    private String estado;
    private int idUsuario;
    private int idEquipo;

    public ReporteFalla() {
    }

    public ReporteFalla(LocalDate fechaReporte,
            String descripcionFalla, String estado,
            int idUsuario, int idEquipo) {

        this.fechaReporte = fechaReporte;
        this.descripcionFalla = descripcionFalla;
        this.estado = estado;
        this.idUsuario = idUsuario;
        this.idEquipo = idEquipo;
    }

    public int getIdReporte() {
        return idReporte;
    }

    public void setIdReporte(int idReporte) {
        this.idReporte = idReporte;
    }

    public LocalDate getFechaReporte() {
        return fechaReporte;
    }

    public void setFechaReporte(LocalDate fechaReporte) {
        this.fechaReporte = fechaReporte;
    }

    public String getDescripcionFalla() {
        return descripcionFalla;
    }

    public void setDescripcionFalla(String descripcionFalla) {
        this.descripcionFalla = descripcionFalla;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public int getIdUsuario() {
        return idUsuario;
    }

    public void setIdUsuario(int idUsuario) {
        this.idUsuario = idUsuario;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }
}

