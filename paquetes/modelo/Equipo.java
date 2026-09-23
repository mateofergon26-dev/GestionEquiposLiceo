/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ariel
 */


public class Equipo {

    private int idEquipo;
    private String nombre;
    private String tipo;
    private String numeroSerie;
    private String estado;
    private String ubicacion;

    public Equipo() {
    }

    public Equipo(int idEquipo, String nombre, String tipo,
            String numeroSerie, String estado, String ubicacion) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.numeroSerie = numeroSerie;
        this.estado = estado;
        this.ubicacion = ubicacion;
    }

    public int getIdEquipo() {
        return idEquipo;
    }

    public void setIdEquipo(int idEquipo) {
        this.idEquipo = idEquipo;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String tipo) {
        this.tipo = tipo;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numeroSerie) {
        this.numeroSerie = numeroSerie;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String estado) {
        this.estado = estado;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
}
