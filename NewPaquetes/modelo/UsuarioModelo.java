/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package modelo;

public class UsuarioModelo {

    private int idUsuario;
    private String nombre;
    private String apellido;
    private String email;
    private String contrasena;
    private String rol;
    
    public UsuarioModelo(int idU, String nom, String a, String e, String c, String r){
        idUsuario = idU;
        nombre = nom;
        apellido = a;
        email = e;
        contrasena = c;
        rol = r;
    }

    public UsuarioModelo() {
    }

    public int getidUsuario(){
        return idUsuario;
    }

    public void setidUsuario(int idU) {
        idUsuario = idU;
    }
    
    public String getnombre(){
        return nombre;
    }
    
    public void setnombre(String nom){
        nombre = nom;
    }
    
    public String getapellido(){
        return apellido;
    }
    
    public void setapellido(String a){
        apellido = a;
    }
    
    public String getemail(){
        return email;
    }
    
    public void setemail(String e){
        email = e;
    }
    
    public String getcontrasena(){
        return contrasena;
    }
    
    public void setcontrasena(String c){
        contrasena = c;
    }
    
    public String getrol(){
        return rol;
    }
    
    public void setrol(String r){
        rol = r;
    }
    
}
