/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.IncidenciaController;
import modelo.Incidencia;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.JButton;
import javax.swing.JComboBox;
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
 * Interfaz gráfica para registrar y consultar incidencias (Capa de
 * Presentación). No accede a la base de datos: todo se pide al
 * IncidenciaController.
 */
public class VentanaIncidencias extends JFrame {

    // Controlador al que la ventana le pide las operaciones.
    private IncidenciaController controller;

    // Campos del formulario.
    private JTextField txtIdPrestamo;
    private JComboBox<String> cmbTipo;
    private JTextField txtDescripcion;

    // Tabla donde se muestran las incidencias.
    private DefaultTableModel modeloTabla;

    public VentanaIncidencias() {
        controller = new IncidenciaController();

        setTitle("Incidencias de préstamos");
        setSize(1000, 480);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- Formulario (arriba) ----------
        JPanel panelFormulario = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        panelFormulario.setBorder(BorderFactory.createTitledBorder("Registrar incidencia"));

        txtIdPrestamo = new JTextField(6);
        cmbTipo = new JComboBox<>(new String[]{"retraso", "no_devuelto", "dano"});
        txtDescripcion = new JTextField(22);
        JButton btnRegistrar = new JButton("Registrar");
        JButton btnActualizar = new JButton("Actualizar lista");

        panelFormulario.add(new JLabel("ID préstamo:"));
        panelFormulario.add(txtIdPrestamo);
        panelFormulario.add(new JLabel("Tipo:"));
        panelFormulario.add(cmbTipo);
        panelFormulario.add(new JLabel("Descripción:"));
        panelFormulario.add(txtDescripcion);
        panelFormulario.add(btnRegistrar);
        panelFormulario.add(btnActualizar);
        add(panelFormulario, BorderLayout.NORTH);

        // ---------- Tabla (centro) ----------
        String[] columnas = {"ID", "ID préstamo", "Tipo", "Descripción"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            // Las celdas no se editan directamente en la tabla.
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        JTable tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // ---------- Nota informativa (abajo) ----------
        JLabel lblNota = new JLabel(
                "<html>Una incidencia 'no_devuelto' marca el préstamo como vencido. "
                + "El estado del equipo se actualiza al registrar la devolución.</html>");
        lblNota.setBorder(BorderFactory.createEmptyBorder(0, 10, 10, 10));
        add(lblNota, BorderLayout.SOUTH);

        // ---------- Acciones de los botones ----------
        btnRegistrar.addActionListener(e -> registrar());
        btnActualizar.addActionListener(e -> cargarTabla());

        // Carga inicial de la tabla.
        cargarTabla();
    }

    // Vuelve a leer las incidencias y rellena la tabla.
    private void cargarTabla() {
        modeloTabla.setRowCount(0);

        ArrayList<Incidencia> incidencias = controller.listarIncidencias();

        for (Incidencia i : incidencias) {
            modeloTabla.addRow(new Object[]{
                i.getIdIncidencia(),
                i.getIdPrestamo(),
                i.getTipo(),
                i.getDescripcion() == null ? "" : i.getDescripcion()
            });
        }
    }

    // Registro de una incidencia nueva.
    private void registrar() {
        int idPrestamo;

        try {
            idPrestamo = Integer.parseInt(txtIdPrestamo.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El ID de préstamo debe ser un número.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String tipo = (String) cmbTipo.getSelectedItem();
        String error = controller.registrarIncidencia(idPrestamo, tipo, txtDescripcion.getText());

        if (error == null) {
            JOptionPane.showMessageDialog(this, "Incidencia registrada correctamente.");
            txtIdPrestamo.setText("");
            txtDescripcion.setText("");
            cargarTabla();
        } else {
            JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    // Punto de entrada: abre la ventana.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaIncidencias().setVisible(true));
    }
}
