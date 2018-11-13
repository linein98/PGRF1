package uloha.druha.view;

import javax.swing.*;
import java.awt.*;

/**
 * View - inicializace GUI
 *
 * @author Tomas Brabec
 */
public class Window {

    private JFrame frame;
    /**
     * zobrazeni platna
     */
    private JPanel panel;
    /**
     * informace o ovladani
     */
    private JPanel infoPanel;
    private Canvas canvas;
    private JLabel infoLabel;

    /**
     * inicializace okna
     *
     * @param width  sirka platna
     * @param height vyska platna
     * @param title  titulek okna
     */
    public Window(int width, int height, String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        frame.add(panel, BorderLayout.CENTER);
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        frame.add(infoPanel, BorderLayout.SOUTH);

        canvas = new Canvas();
        canvas.setSize(width, height);
        panel.add(canvas, BorderLayout.CENTER);

        infoLabel = new JLabel();
        infoLabel.setForeground(Color.BLACK);
        infoLabel.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 2));
        infoLabel.setText("[LMB] Draw polygon, [RMB] Seed fill, [C] Clear");
        infoPanel.add(infoLabel);

        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        canvas.requestFocus();
    }

    public Canvas getCanvas() {
        return canvas;
    }
}
