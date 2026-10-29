/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.EquipoController;
import modelo.Equipo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.awt.GridLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JScrollPane;
import javax.swing.JTable;
import javax.swing.JTextField;
import javax.swing.SwingUtilities;

import javax.swing.table.DefaultTableModel;


/**
 *
 * @author mateo
 */



/**
 * Interfaz gráfica para gestionar equipos (Capa de Presentación).
 * No accede a la base de datos: todas las operaciones se piden al
 * EquipoController, igual que la versión por consola.
 */
public class Ventanaequipos extends JFrame {

    // Controlador al que la ventana le pide las operaciones.
    private EquipoController controller;

    // Campos de texto del formulario.
    private JTextField txtNombre;
    private JTextField txtTipo;
    private JTextField txtNumeroSerie;
    private JTextField txtUbicacion;

    // Tabla donde se muestran los equipos.
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // ID del equipo seleccionado en la tabla (-1 si no hay ninguno).
    private int idSeleccionado = -1;

    public Ventanaequipos() {
        controller = new EquipoController();

        setTitle("Gestión de equipos");
        setSize(800, 500);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- Formulario (arriba) ----------
        JPanel panelFormulario = new JPanel(new GridLayout(4, 2, 5, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Datos del equipo"));

        txtNombre = new JTextField();
        txtTipo = new JTextField();
        txtNumeroSerie = new JTextField();
        txtUbicacion = new JTextField();

        panelFormulario.add(new JLabel("Nombre:"));
        panelFormulario.add(txtNombre);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(txtTipo);
        panelFormulario.add(new JLabel("Número de serie:"));
        panelFormulario.add(txtNumeroSerie);
        panelFormulario.add(new JLabel("Ubicación:"));
        panelFormulario.add(txtUbicacion);

        // ---------- Botones ----------
        JPanel panelBotones = new JPanel(new FlowLayout(FlowLayout.CENTER, 10, 5));
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnModificar = new JButton("Modificar");
        JButton btnBaja = new JButton("Dar de baja");
        JButton btnLimpiar = new JButton("Limpiar");

        panelBotones.add(btnRegistrar);
        panelBotones.add(btnModificar);
        panelBotones.add(btnBaja);
        panelBotones.add(btnLimpiar);

        JPanel panelSuperior = new JPanel(new BorderLayout());
        panelSuperior.add(panelFormulario, BorderLayout.CENTER);
        panelSuperior.add(panelBotones, BorderLayout.SOUTH);
        add(panelSuperior, BorderLayout.NORTH);

        // ---------- Tabla (centro) ----------
        String[] columnas = {"ID", "Nombre", "Tipo", "N° de serie", "Estado", "Ubicación"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            // Las celdas no se editan directamente en la tabla.
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Al seleccionar una fila, se cargan sus datos en el formulario.
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                cargarFilaSeleccionada();
            }
        });

        // ---------- Acciones de los botones ----------
        btnRegistrar.addActionListener(e -> registrar());
        btnModificar.addActionListener(e -> modificar());
        btnBaja.addActionListener(e -> darDeBaja());
        btnLimpiar.addActionListener(e -> limpiarFormulario());

        // Carga inicial de la tabla.
        cargarTabla();
    }

    // Vuelve a leer los equipos y rellena la tabla.
    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        ArrayList<Equipo> equipos = controller.listarEquipos();

        for (Equipo equipo : equipos) {
            modeloTabla.addRow(new Object[]{
                equipo.getIdEquipo(),
                equipo.getNombre(),
                equipo.getTipo(),
                equipo.getNumeroSerie(),
                equipo.getEstado(),
                equipo.getUbicacion()
            });
        }
    }

    // Pasa los datos de la fila seleccionada al formulario.
    private void cargarFilaSeleccionada() {
        int fila = tabla.getSelectedRow();

        if (fila < 0) {
            return;
        }

        idSeleccionado = (Integer) modeloTabla.getValueAt(fila, 0);
        txtNombre.setText(textoDe(modeloTabla.getValueAt(fila, 1)));
        txtTipo.setText(textoDe(modeloTabla.getValueAt(fila, 2)));
        txtNumeroSerie.setText(textoDe(modeloTabla.getValueAt(fila, 3)));
        txtUbicacion.setText(textoDe(modeloTabla.getValueAt(fila, 5)));
    }

    // Convierte un valor de la tabla a texto (si es null, devuelve vacío).
    private String textoDe(Object valor) {
        return valor == null ? "" : valor.toString();
    }

    // Alta de un equipo nuevo.
    private void registrar() {
        boolean ok = controller.registrarEquipo(
                txtNombre.getText().trim(),
                txtTipo.getText().trim(),
                txtNumeroSerie.getText().trim(),
                txtUbicacion.getText().trim());

        if (ok) {
            JOptionPane.showMessageDialog(this, "Equipo registrado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo registrar el equipo. Verifique que el nombre y el tipo no estén vacíos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Modificación del equipo seleccionado.
    private void modificar() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un equipo de la tabla para modificarlo.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        boolean ok = controller.modificarEquipo(
                idSeleccionado,
                txtNombre.getText().trim(),
                txtTipo.getText().trim(),
                txtNumeroSerie.getText().trim(),
                txtUbicacion.getText().trim());

        if (ok) {
            JOptionPane.showMessageDialog(this, "Equipo modificado correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo modificar el equipo. Verifique que el nombre y el tipo no estén vacíos.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Baja lógica del equipo seleccionado (previa confirmación).
    private void darDeBaja() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un equipo de la tabla para darlo de baja.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea dar de baja el equipo seleccionado?",
                "Confirmar baja", JOptionPane.YES_NO_OPTION);

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        if (controller.darDeBajaEquipo(idSeleccionado)) {
            JOptionPane.showMessageDialog(this, "Equipo dado de baja correctamente.");
            limpiarFormulario();
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this,
                    "No se pudo dar de baja el equipo.",
                    "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Vacía el formulario y quita la selección.
    private void limpiarFormulario() {
        txtNombre.setText("");
        txtTipo.setText("");
        txtNumeroSerie.setText("");
        txtUbicacion.setText("");
        idSeleccionado = -1;
        tabla.clearSelection();
    }

    // Punto de entrada: abre la ventana.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new Ventanaequipos().setVisible(true));
    }
}
