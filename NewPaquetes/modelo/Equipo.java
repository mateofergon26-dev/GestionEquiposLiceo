/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

/**
 *
 * @author ariel
 */

//Clase que representa un equipo dentro del sistema.
public class Equipo {

    private int idEquipo;
    private String nombre;
    private String tipo;
    private String numeroSerie;
    private String estado;
    private String ubicacion;

    public  Equipo() {
    }

    //Constructor
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

    public void setIdEquipo(int e) {
        idEquipo = e;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String n) {
        nombre = n;
    }

    public String getTipo() {
        return tipo;
    }

    public void setTipo(String t) {
        tipo = t;
    }

    public String getNumeroSerie() {
        return numeroSerie;
    }

    public void setNumeroSerie(String numS) {
        numeroSerie = numS;
    }

    public String getEstado() {
        return estado;
    }

    public void setEstado(String est) {
        estado = est;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubi) {
        ubicacion = ubi;
    }
    
    @Override
public String toString() {
    return nombre;
}
    
}
