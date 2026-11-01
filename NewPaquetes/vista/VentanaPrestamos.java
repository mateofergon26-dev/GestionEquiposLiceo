/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package vista;

import controlador.PrestamoController;
import modelo.Prestamo;

import java.awt.BorderLayout;
import java.awt.FlowLayout;
import java.time.LocalDate;
import java.util.ArrayList;

import javax.swing.BorderFactory;
import javax.swing.BoxLayout;
import javax.swing.JButton;
import javax.swing.JCheckBox;
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
 * Interfaz gráfica para registrar préstamos, registrar devoluciones y
 * consultar los préstamos (Capa de Presentación). No accede a la base de
 * datos: todo se pide al PrestamoController.
 */
public class VentanaPrestamos extends JFrame {

    // Controlador al que la ventana le pide las operaciones.
    private PrestamoController controller;

    // Campos para registrar un préstamo.
    private JTextField txtIdReserva;
    private JTextField txtIdEquipo;
    private JTextField txtFechaEsperada;

    // Campos para registrar una devolución.
    private JLabel lblSeleccion;
    private JCheckBox chkIncidencia;
    private JComboBox<String> cmbTipo;
    private JTextField txtDescripcion;

    // Tabla donde se muestran los préstamos.
    private JTable tabla;
    private DefaultTableModel modeloTabla;

    // ID del préstamo seleccionado en la tabla (-1 si no hay ninguno).
    private int idSeleccionado = -1;

    public VentanaPrestamos() {
        controller = new PrestamoController();

        setTitle("Gestión de préstamos y devoluciones");
        setSize(1000, 580);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null);
        setLayout(new BorderLayout(10, 10));

        // ---------- Panel: registrar préstamo ----------
        JPanel panelPrestamo = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 5));
        panelPrestamo.setBorder(BorderFactory.createTitledBorder(
                "Registrar préstamo (la reserva debe estar aprobada)"));

        txtIdReserva = new JTextField(6);
        txtIdEquipo = new JTextField(6);
        txtFechaEsperada = new JTextField(LocalDate.now().plusDays(7).toString(), 10);
        JButton btnPrestamo = new JButton("Registrar préstamo");

        panelPrestamo.add(new JLabel("ID reserva:"));
        panelPrestamo.add(txtIdReserva);
        panelPrestamo.add(new JLabel("ID equipo:"));
        panelPrestamo.add(txtIdEquipo);
        panelPrestamo.add(new JLabel("Devolución esperada (aaaa-mm-dd):"));
        panelPrestamo.add(txtFechaEsperada);
        panelPrestamo.add(btnPrestamo);

        // ---------- Panel: registrar devolución ----------
        JPanel panelDevolucion = new JPanel(new BorderLayout());
        panelDevolucion.setBorder(BorderFactory.createTitledBorder(
                "Registrar devolución (seleccione un préstamo en la tabla)"));

        lblSeleccion = new JLabel("Ningún préstamo seleccionado");
        chkIncidencia = new JCheckBox("Presenta incidencia");
        cmbTipo = new JComboBox<>(new String[]{"retraso", "dano"});
        txtDescripcion = new JTextField(20);
        JButton btnDevolucion = new JButton("Registrar devolución");

        // La incidencia solo se completa si se tilda el casillero.
        cmbTipo.setEnabled(false);
        txtDescripcion.setEnabled(false);
        chkIncidencia.addActionListener(e -> {
            cmbTipo.setEnabled(chkIncidencia.isSelected());
            txtDescripcion.setEnabled(chkIncidencia.isSelected());
        });

        JPanel filaSeleccion = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        filaSeleccion.add(lblSeleccion);

        JPanel filaCampos = new JPanel(new FlowLayout(FlowLayout.LEFT, 8, 3));
        filaCampos.add(chkIncidencia);
        filaCampos.add(new JLabel("Tipo:"));
        filaCampos.add(cmbTipo);
        filaCampos.add(new JLabel("Descripción:"));
        filaCampos.add(txtDescripcion);
        filaCampos.add(btnDevolucion);

        panelDevolucion.add(filaSeleccion, BorderLayout.NORTH);
        panelDevolucion.add(filaCampos, BorderLayout.CENTER);

        // ---------- Parte de arriba: los dos paneles uno debajo del otro ----------
        JPanel panelSuperior = new JPanel();
        panelSuperior.setLayout(new BoxLayout(panelSuperior, BoxLayout.Y_AXIS));
        panelSuperior.add(panelPrestamo);
        panelSuperior.add(panelDevolucion);
        add(panelSuperior, BorderLayout.NORTH);

        // ---------- Tabla (centro) ----------
        String[] columnas = {"ID", "ID reserva", "ID equipo", "Estado",
            "Entrega real", "Devolución esperada", "Devolución real"};
        modeloTabla = new DefaultTableModel(columnas, 0) {
            // Las celdas no se editan directamente en la tabla.
            @Override
            public boolean isCellEditable(int fila, int columna) {
                return false;
            }
        };
        tabla = new JTable(modeloTabla);
        add(new JScrollPane(tabla), BorderLayout.CENTER);

        // Al seleccionar una fila, se guarda cuál es el préstamo elegido.
        tabla.getSelectionModel().addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                seleccionarFila();
            }
        });

        // ---------- Acciones de los botones ----------
        btnPrestamo.addActionListener(e -> registrarPrestamo());
        btnDevolucion.addActionListener(e -> registrarDevolucion());

        // Carga inicial de la tabla.
        cargarTabla();
    }

    // Vuelve a leer los préstamos y rellena la tabla.
    private void cargarTabla() {
        idSeleccionado = -1;
        lblSeleccion.setText("Ningún préstamo seleccionado");
        modeloTabla.setRowCount(0);

        ArrayList<Prestamo> prestamos = controller.listarPrestamos();

        for (Prestamo p : prestamos) {
            modeloTabla.addRow(new Object[]{
                p.getIdPrestamo(),
                p.getIdReserva(),
                p.getIdEquipo(),
                p.getEstado(),
                p.getFechaEntregaReal().toString(),
                p.getFechaDevolucionEsperada().toString(),
                p.getFechaDevolucionReal() == null ? "" : p.getFechaDevolucionReal().toString()
            });
        }
    }

    // Guarda el ID del préstamo de la fila seleccionada.
    private void seleccionarFila() {
        int fila = tabla.getSelectedRow();

        if (fila < 0) {
            return;
        }

        idSeleccionado = (Integer) modeloTabla.getValueAt(fila, 0);
        lblSeleccion.setText("Préstamo seleccionado: #" + idSeleccionado
                + " (estado: " + modeloTabla.getValueAt(fila, 3) + ")");
    }

    // Muestra el resultado de una operación. Devuelve true si salió bien.
    private boolean mostrarResultado(String error, String mensajeExito) {
        if (error == null) {
            JOptionPane.showMessageDialog(this, mensajeExito);
            return true;
        }

        JOptionPane.showMessageDialog(this, error, "Error", JOptionPane.ERROR_MESSAGE);
        return false;
    }

    // Registro de un préstamo nuevo.
    private void registrarPrestamo() {
        int idReserva;
        int idEquipo;

        try {
            idReserva = Integer.parseInt(txtIdReserva.getText().trim());
            idEquipo = Integer.parseInt(txtIdEquipo.getText().trim());
        } catch (NumberFormatException e) {
            JOptionPane.showMessageDialog(this,
                    "El ID de reserva y el ID de equipo deben ser números.",
                    "Error", JOptionPane.ERROR_MESSAGE);
            return;
        }

        String error = controller.registrarPrestamo(idReserva, idEquipo,
                txtFechaEsperada.getText());

        if (mostrarResultado(error, "Préstamo registrado correctamente. El equipo quedó como prestado.")) {
            txtIdReserva.setText("");
            txtIdEquipo.setText("");
            cargarTabla();
        }
    }

    // Registro de la devolución del préstamo seleccionado.
    private void registrarDevolucion() {
        if (idSeleccionado == -1) {
            JOptionPane.showMessageDialog(this,
                    "Seleccione un préstamo de la tabla para registrar su devolución.",
                    "Aviso", JOptionPane.WARNING_MESSAGE);
            return;
        }

        int respuesta = JOptionPane.showConfirmDialog(this,
                "¿Desea registrar la devolución del préstamo " + idSeleccionado + "?",
                "Confirmar devolución", JOptionPane.YES_NO_OPTION);

        if (respuesta != JOptionPane.YES_OPTION) {
            return;
        }

        // Si no se tilda "Presenta incidencia", el tipo es null.
        String tipo = chkIncidencia.isSelected() ? (String) cmbTipo.getSelectedItem() : null;

        String error = controller.registrarDevolucion(idSeleccionado, tipo,
                txtDescripcion.getText());

        if (mostrarResultado(error, "Devolución registrada correctamente. Se actualizó el estado del equipo.")) {
            chkIncidencia.setSelected(false);
            cmbTipo.setEnabled(false);
            txtDescripcion.setText("");
            txtDescripcion.setEnabled(false);
            cargarTabla();
        }
    }

    // Punto de entrada: abre la ventana.
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> new VentanaPrestamos().setVisible(true));
    }
}
