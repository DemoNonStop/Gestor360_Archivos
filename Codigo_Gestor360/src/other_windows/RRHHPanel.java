package other_windows;

import javax.swing.*;
import javax.swing.border.EmptyBorder;
import javax.swing.table.DefaultTableModel;
import com.toedter.calendar.JCalendar;
import main.login_window;
import main.main_window;
import database.conecctionSQL;
import java.awt.*;
import java.awt.event.*;
import java.io.PrintWriter;
import java.sql.*;

public class RRHHPanel extends JPanel {

    private static final long serialVersionUID = 1L;
    private JPanel contentPane;
    private JTable tablaVacaciones;
    private DefaultTableModel model;
    private JTextField txtUsuario, txtFechaInicio, txtFechaFin;
    private JTextArea txtMotivo;

    public RRHHPanel(main_window mainWindow) {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 245, 255));

        contentPane = new JPanel(new BorderLayout(10, 10));
        contentPane.setBackground(new Color(245, 245, 255));
        contentPane.setBorder(new EmptyBorder(15, 15, 15, 15));
        add(contentPane, BorderLayout.CENTER);

        addHeader();
        addCenterPanel();
        loadVacaciones();
    }

    private void addHeader() {
        JPanel headerPanel = new JPanel(new BorderLayout());
        headerPanel.setBackground(new Color(0, 128, 255));

        JLabel lblTitle = new JLabel("👥 Recursos Humanos - Vacaciones", SwingConstants.CENTER);
        lblTitle.setFont(new Font("Segoe UI Emoji", Font.BOLD, 24));
        lblTitle.setForeground(Color.WHITE);

        headerPanel.add(lblTitle, BorderLayout.CENTER);
        contentPane.add(headerPanel, BorderLayout.NORTH);
    }

    private void addCenterPanel() {
        JPanel centerPanel = new JPanel(new BorderLayout(10, 10));
        centerPanel.setBackground(new Color(244, 246, 249));

        centerPanel.add(createFormPanel(), BorderLayout.WEST);
        centerPanel.add(createTablePanel(), BorderLayout.CENTER);
        centerPanel.add(createCalendarPanel(), BorderLayout.EAST);

        contentPane.add(centerPanel, BorderLayout.CENTER);
    }

    private JPanel createFormPanel() {
        JPanel formPanel = new JPanel(new GridBagLayout());
        formPanel.setBackground(new Color(244, 246, 249));
        GridBagConstraints gbc = new GridBagConstraints();
        gbc.insets = new Insets(8, 8, 8, 8);
        gbc.fill = GridBagConstraints.HORIZONTAL;
        gbc.gridx = 0;

        JLabel lblUsuario = new JLabel("Usuario:");
        lblUsuario.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 0;
        formPanel.add(lblUsuario, gbc);

        txtUsuario = new JTextField();
        txtUsuario.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtUsuario.setText(login_window.getCurrentUser() != null ? login_window.getCurrentUser() : "admin");
        gbc.gridy = 1;
        formPanel.add(txtUsuario, gbc);

        JLabel lblFechaInicio = new JLabel("Fecha inicio (YYYY-MM-DD):");
        lblFechaInicio.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 2;
        formPanel.add(lblFechaInicio, gbc);

        txtFechaInicio = new JTextField();
        txtFechaInicio.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 3;
        formPanel.add(txtFechaInicio, gbc);

        JLabel lblFechaFin = new JLabel("Fecha fin (YYYY-MM-DD):");
        lblFechaFin.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 4;
        formPanel.add(lblFechaFin, gbc);

        txtFechaFin = new JTextField();
        txtFechaFin.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        gbc.gridy = 5;
        formPanel.add(txtFechaFin, gbc);

        JLabel lblMotivo = new JLabel("Motivo:");
        lblMotivo.setFont(new Font("Segoe UI", Font.BOLD, 14));
        gbc.gridy = 6;
        formPanel.add(lblMotivo, gbc);

        txtMotivo = new JTextArea(4, 20);
        txtMotivo.setFont(new Font("Segoe UI", Font.PLAIN, 14));
        txtMotivo.setLineWrap(true);
        txtMotivo.setWrapStyleWord(true);
        JScrollPane scrollMotivo = new JScrollPane(txtMotivo);
        gbc.gridy = 7;
        formPanel.add(scrollMotivo, gbc);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(244, 246, 249));

        JButton btnEnviar = createStyledButton("📤 Enviar", new Color(0, 128, 255), new Color(0, 106, 212));
        btnEnviar.addActionListener(e -> enviarVacaciones());

        JButton btnLimpiar = createStyledButton("🧹 Limpiar", new Color(120, 120, 120), new Color(90, 90, 90));
        btnLimpiar.addActionListener(e -> limpiarCampos());

        buttonPanel.add(btnEnviar);
        buttonPanel.add(btnLimpiar);

        gbc.gridy = 8;
        formPanel.add(buttonPanel, gbc);

        return formPanel;
    }

    private JPanel createTablePanel() {
        JPanel tablePanel = new JPanel(new BorderLayout());
        tablePanel.setBackground(new Color(244, 246, 249));
        tablePanel.setBorder(BorderFactory.createTitledBorder("🗂 Historial de Vacaciones"));

        model = new DefaultTableModel(new String[]{"ID", "Usuario", "Inicio", "Fin", "Motivo", "Solicitado"}, 0);
        tablaVacaciones = new JTable(model);
        JScrollPane scroll = new JScrollPane(tablaVacaciones);
        tablePanel.add(scroll, BorderLayout.CENTER);

        JPanel buttonPanel = new JPanel();
        buttonPanel.setBackground(new Color(244, 246, 249));

        JButton btnVer = createStyledButton("🔍 Ver", new Color(0, 128, 255), new Color(0, 106, 212));
        btnVer.addActionListener(e -> verSolicitud());

        JButton btnEliminar = createStyledButton("🗑 Eliminar", new Color(200, 0, 0), new Color(160, 0, 0));
        btnEliminar.addActionListener(e -> eliminarVacacion());

        JButton btnExportar = createStyledButton("📁 Exportar", new Color(56, 142, 60), new Color(38, 120, 45));
        btnExportar.addActionListener(e -> exportarHistorialVacaciones());

        buttonPanel.add(btnVer);
        buttonPanel.add(btnEliminar);
        buttonPanel.add(btnExportar);

        tablePanel.add(buttonPanel, BorderLayout.SOUTH);
        return tablePanel;
    }

    private JPanel createCalendarPanel() {
        JCalendar calendar = new JCalendar();
        calendar.setPreferredSize(new Dimension(250, 200));

        JPanel calendarPanel = new JPanel();
        calendarPanel.setBackground(new Color(244, 246, 249));
        calendarPanel.setBorder(BorderFactory.createTitledBorder("📅 Calendario"));
        calendarPanel.add(calendar);

        return calendarPanel;
    }

    private JButton createStyledButton(String text, Color bg, Color hover) {
        JButton button = new JButton(text);
        button.setFont(new Font("Segoe UI Emoji", Font.BOLD, 14));
        button.setBackground(bg);
        button.setForeground(Color.WHITE);
        button.setFocusPainted(false);
        button.setBorderPainted(false);
        button.setContentAreaFilled(true);
        button.setCursor(Cursor.getPredefinedCursor(Cursor.HAND_CURSOR));
        button.setPreferredSize(new Dimension(140, 35));
        button.addMouseListener(new MouseAdapter() {
            public void mouseEntered(MouseEvent e) { button.setBackground(hover); }
            public void mouseExited(MouseEvent e) { button.setBackground(bg); }
        });
        return button;
    }

    private void enviarVacaciones() {
        String usuario = txtUsuario.getText().trim();
        String inicio = txtFechaInicio.getText().trim();
        String fin = txtFechaFin.getText().trim();
        String motivo = txtMotivo.getText().trim();

        if (usuario.isEmpty() || inicio.isEmpty() || fin.isEmpty()) {
            JOptionPane.showMessageDialog(this, "Por favor, completa todos los campos obligatorios.", "Campos vacíos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (!inicio.matches("\\d{4}-\\d{2}-\\d{2}") || !fin.matches("\\d{4}-\\d{2}-\\d{2}")) {
            JOptionPane.showMessageDialog(this, "Formato de fecha incorrecto. Usa YYYY-MM-DD.", "Formato inválido", JOptionPane.WARNING_MESSAGE);
            return;
        }
        if (inicio.compareTo(fin) >= 0) {
            JOptionPane.showMessageDialog(this, "La fecha de fin debe ser posterior a la de inicio.", "Fechas inválidas", JOptionPane.WARNING_MESSAGE);
            return;
        }

        try (Connection conn = conecctionSQL.getConnection();
             PreparedStatement ps = conn.prepareStatement("INSERT INTO vacaciones (usuario, fecha_inicio, fecha_fin, motivo) VALUES (?, ?, ?, ?)")) {
            ps.setString(1, usuario);
            ps.setString(2, inicio);
            ps.setString(3, fin);
            ps.setString(4, motivo);
            ps.executeUpdate();
            JOptionPane.showMessageDialog(this, "Vacaciones registradas correctamente.");
            limpiarCampos();
            loadVacaciones();
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al registrar vacaciones:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void loadVacaciones() {
        model.setRowCount(0);
        try (Connection conn = conecctionSQL.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery("SELECT * FROM vacaciones")) {
            while (rs.next()) {
                model.addRow(new Object[]{
                    rs.getInt("id"),
                    rs.getString("usuario"),
                    rs.getString("fecha_inicio"),
                    rs.getString("fecha_fin"),
                    rs.getString("motivo"),
                    rs.getTimestamp("fecha_solicitud")
                });
            }
        } catch (SQLException e) {
            JOptionPane.showMessageDialog(this, "Error al cargar historial:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void verSolicitud() {
        int fila = tablaVacaciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para ver los detalles.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        StringBuilder detalles = new StringBuilder();
        detalles.append("Detalles de la Solicitud:\n\n");
        detalles.append("ID: ").append(model.getValueAt(fila, 0)).append("\n");
        detalles.append("Usuario: ").append(model.getValueAt(fila, 1)).append("\n");
        detalles.append("Inicio: ").append(model.getValueAt(fila, 2)).append("\n");
        detalles.append("Fin: ").append(model.getValueAt(fila, 3)).append("\n");
        detalles.append("Motivo: ").append(model.getValueAt(fila, 4)).append("\n");
        detalles.append("Solicitado el: ").append(model.getValueAt(fila, 5)).append("\n");
        JOptionPane.showMessageDialog(this, detalles.toString(), "📋 Solicitud de Vacaciones", JOptionPane.INFORMATION_MESSAGE);
    }

    private void eliminarVacacion() {
        int fila = tablaVacaciones.getSelectedRow();
        if (fila == -1) {
            JOptionPane.showMessageDialog(this, "Selecciona una fila para eliminar.", "Sin selección", JOptionPane.WARNING_MESSAGE);
            return;
        }
        int confirm = JOptionPane.showConfirmDialog(this, "¿Eliminar esta solicitud de vacaciones?", "Confirmación", JOptionPane.YES_NO_OPTION);
        if (confirm == JOptionPane.YES_OPTION) {
            int id = (int) model.getValueAt(fila, 0);
            try (Connection conn = conecctionSQL.getConnection();
                 PreparedStatement ps = conn.prepareStatement("DELETE FROM vacaciones WHERE id = ?")) {
                ps.setInt(1, id);
                ps.executeUpdate();
                loadVacaciones();
                JOptionPane.showMessageDialog(this, "Solicitud eliminada.");
            } catch (SQLException e) {
                JOptionPane.showMessageDialog(this, "Error al eliminar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
            }
        }
    }

    private void exportarHistorialVacaciones() {
        if (model.getRowCount() == 0) {
            JOptionPane.showMessageDialog(this, "❗ No hay datos para exportar.", "Sin datos", JOptionPane.WARNING_MESSAGE);
            return;
        }
        try (PrintWriter writer = new PrintWriter("vacaciones_exportadas.txt")) {
            for (int i = 0; i < model.getRowCount(); i++) {
                writer.println("------");
                writer.println("ID: " + model.getValueAt(i, 0));
                writer.println("Usuario: " + model.getValueAt(i, 1));
                writer.println("Inicio: " + model.getValueAt(i, 2));
                writer.println("Fin: " + model.getValueAt(i, 3));
                writer.println("Motivo: " + model.getValueAt(i, 4));
                writer.println("Fecha solicitud: " + model.getValueAt(i, 5));
            }
            JOptionPane.showMessageDialog(this, "📁 Exportación completada: vacaciones_exportadas.txt", "Éxito", JOptionPane.INFORMATION_MESSAGE);
        } catch (Exception e) {
            JOptionPane.showMessageDialog(this, "❌ Error al exportar:\n" + e.getMessage(), "Error", JOptionPane.ERROR_MESSAGE);
        }
    }

    private void limpiarCampos() {
        txtFechaInicio.setText("");
        txtFechaFin.setText("");
        txtMotivo.setText("");
    }
}
