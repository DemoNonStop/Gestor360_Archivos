
package main;

import javax.swing.*;
import java.awt.*;

public class MainPanelPlaceholder extends JPanel {

    private static final long serialVersionUID = 1L;

    public MainPanelPlaceholder() {
        setLayout(new BorderLayout());
        setBackground(new Color(245, 247, 250)); // fondo general suave

        // Título principal
        JLabel lblNoticias = new JLabel("📰 Últimas noticias", SwingConstants.CENTER);
        lblNoticias.setFont(new Font("Segoe UI Emoji", Font.BOLD, 26));
        lblNoticias.setForeground(new Color(33, 37, 41)); // gris oscuro
        lblNoticias.setBorder(BorderFactory.createEmptyBorder(20, 10, 10, 10));
        add(lblNoticias, BorderLayout.NORTH);

        // Contenedor de noticias
        JPanel newsPanel = new JPanel();
        newsPanel.setLayout(new BoxLayout(newsPanel, BoxLayout.Y_AXIS));
        newsPanel.setBackground(new Color(245, 247, 250));
        newsPanel.setBorder(BorderFactory.createEmptyBorder(10, 40, 20, 40));

        // Añadimos cada noticia como panel independiente
        newsPanel.add(createNewsItem("🆕 Nuevo módulo de reportes disponible."));
        newsPanel.add(createNewsItem("🔁 Recuerda actualizar los datos regularmente."));
        newsPanel.add(createNewsItem("📦 Gestión de stock mejorada en esta versión."));
        newsPanel.add(createNewsItem("🗓 Ahora puedes exportar incidencias desde el módulo de Reportes."));
        newsPanel.add(createNewsItem("👥 Nuevo panel de Recursos Humanos con control de vacaciones."));
        newsPanel.add(createNewsItem("✅ ¡Gracias por utilizar Gestor360!"));

        // Scroll si hay muchas noticias
        JScrollPane scroll = new JScrollPane(newsPanel);
        scroll.setBorder(null);
        scroll.getVerticalScrollBar().setUnitIncrement(16);
        scroll.setBackground(new Color(245, 247, 250));
        scroll.getViewport().setBackground(new Color(245, 247, 250));

        add(scroll, BorderLayout.CENTER);
    }

    // Crea un componente de noticia con estilo coherente
    private JPanel createNewsItem(String text) {
        JPanel panel = new JPanel(new BorderLayout());
        panel.setBackground(new Color(232, 238, 247)); // azul hielo
        panel.setBorder(BorderFactory.createEmptyBorder(10, 15, 10, 15));
        panel.setMaximumSize(new Dimension(Integer.MAX_VALUE, 60));

        JLabel lbl = new JLabel(text);
        lbl.setFont(new Font("Segoe UI Emoji", Font.PLAIN, 16));
        lbl.setForeground(new Color(52, 58, 64)); // gris oscuro
        panel.add(lbl, BorderLayout.CENTER);

        return panel;
    }
}
