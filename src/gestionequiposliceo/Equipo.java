/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionequiposliceo;

/**
 *
 * @author mateo
 * 
 * 
 * Clase modelo que representa un Equipo informático. Sus atributos corresponden
 * a los definidos en el diccionario de datos de la Entrega 2 (tabla EQUIPO).
 */
public class Equipo {
    
    private int idEquipo;
    private String nombre;
    private String tipo;
    private String numeroSerie;
    private String estadoE;
    private String ubicacion;
    
    // Constructor vacío (útil para cuando todavía no se conoce el id, por ejemplo al dar de alta)
    public Equipo() {
    }
    
    // Constructor completo (útil para cuando se recupera un equipo ya existente de la base)
    public Equipo(int idEquipo, String nombre, String tipo, String numeroSerie, String estadoE, String ubicacion) {
        this.idEquipo = idEquipo;
        this.nombre = nombre;
        this.tipo = tipo;
        this.numeroSerie = numeroSerie;
        this.estadoE = estadoE;
        this.ubicacion = ubicacion;
    }
    
    
    // Getters y setters
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

    public String getEstadoE() {
        return estadoE;
    }

    public void setEstadoE(String estadoE) {
        this.estadoE = estadoE;
    }

    public String getUbicacion() {
        return ubicacion;
    }

    public void setUbicacion(String ubicacion) {
        this.ubicacion = ubicacion;
    }
    
    // Sobrescribimos toString() para que sea fácil imprimir un equipo por consola al probarlo
    @Override
    public String toString() {
        return "Equipo{"
                + "idEquipo=" + idEquipo
                + ", nombre='" + nombre + '\''
                + ", tipo='" + tipo + '\''
                + ", numeroSerie='" + numeroSerie + '\''
                + ", estadoE='" + estadoE + '\''
                + ", ubicacion='" + ubicacion + '\''
                + '}';
    }
}


