/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package gestionequiposliceo;

import java.util.List;

/**
 *
 * @author mateo
 * 
 * Clase de prueba del CRUD de Equipo. Ejecuta en orden: alta, consulta,
 * modificación y baja lógica, mostrando los resultados por consola para
 * verificar que cada paso se refleja correctamente en la base de datos.
 */
public class Main {
    
    public static void main(String[] args) {

        EquipoDAO dao = new EquipoDAO();

        // 
        // 1) ALTA: se registra un equipo nuevo
        // 
        System.out.println("=== ALTA ===");
        Equipo nuevo = new Equipo();
        nuevo.setNombre("Proyector Epson 04");
        nuevo.setTipo("proyector");
        nuevo.setNumeroSerie("SN-0006");
        nuevo.setEstadoE("disponible");
        nuevo.setUbicacion("Sala de audiovisuales");

        boolean altaExitosa = dao.insertar(nuevo);
        System.out.println("¿Se pudo dar de alta? " + altaExitosa);

        // 
        // 2) CONSULTA: se listan todos los equipos activos
        // 
        System.out.println("\n=== LISTADO COMPLETO ===");
        List<Equipo> equipos = dao.listarTodos();
        for (Equipo e : equipos) {
            System.out.println(e);
        }

        // Tomamos el id del último equipo insertado (el que acabamos de crear)
        // para poder buscarlo, modificarlo y darlo de baja a continuación.
        int idParaProbar = equipos.get(equipos.size() - 1).getIdEquipo();

        // 
        // 3) CONSULTA PUNTUAL: se busca ese equipo por id
        // 
        System.out.println("\n=== BUSQUEDA POR ID (" + idParaProbar + ") ===");
        Equipo encontrado = dao.buscarPorId(idParaProbar);
        System.out.println(encontrado);

        // 
        // 4) MODIFICACION: se cambia la ubicación del equipo encontrado
        // 
        System.out.println("\n=== MODIFICACION ===");
        encontrado.setUbicacion("Depósito central");
        boolean modificacionExitosa = dao.modificar(encontrado);
        System.out.println("¿Se pudo modificar? " + modificacionExitosa);
        System.out.println("Equipo actualizado: " + dao.buscarPorId(idParaProbar));

        // 
        // 5) BAJA LOGICA: se da de baja ese mismo equipo
        // 
        System.out.println("\n=== BAJA LOGICA ===");
        boolean bajaExitosa = dao.darDeBaja(idParaProbar);
        System.out.println("¿Se pudo dar de baja? " + bajaExitosa);

        // Verificamos que ya no aparezca en el listado de equipos activos
        System.out.println("\n=== LISTADO DESPUES DE LA BAJA (ya no deberia aparecer) ===");
        for (Equipo e : dao.listarTodos()) {
            System.out.println(e);
        }
    }
}
    
    
    
    
    

