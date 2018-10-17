package uloha.prvni.view;

import uloha.prvni.model.ObjectType;

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
     * Obsahuje platno
     */
    private JPanel panel;
    /**
     * Obsahuje vystupy pro uzivatele
     */
    private JPanel infoPanel;
    private Canvas canvas;
    /**
     * Zobrazeni souradnic
     */
    private JLabel lblXY;
    /**
     * Zobrazeni informace pro uzivatele co za tvar kresly
     */
    private JLabel lblSelected;

    public Window(int width, int height, String title) {
        frame = new JFrame(title);
        frame.setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        panel = new JPanel();
        panel.setLayout(new BorderLayout());
        //panel.setBackground(Color.BLACK);
        frame.add(panel, BorderLayout.CENTER);
        infoPanel = new JPanel();
        infoPanel.setLayout(new BoxLayout(infoPanel, BoxLayout.X_AXIS));
        //infoPanel.setBackground(Color.DARK_GRAY);
        frame.add(infoPanel, BorderLayout.SOUTH);

        canvas = new Canvas();
        canvas.setSize(width, height);
        panel.add(canvas, BorderLayout.CENTER);

        lblXY = new JLabel();
        lblXY.setForeground(Color.BLACK);
        lblXY.setBorder(BorderFactory.createEmptyBorder(2, 8, 2, 2));
        lblXY.setText("x: 0, y: 0");
        infoPanel.add(lblXY);

        infoPanel.add(Box.createHorizontalGlue());

        lblSelected = new JLabel();
        lblSelected.setForeground(Color.BLACK);
        lblSelected.setBorder(BorderFactory.createEmptyBorder(2, 2, 2, 8));
        lblSelected.setText(String.format("[1] %s, [2] %s, [3] %s, [A] %s, [C] %s",
                ObjectType.Line.getName(), ObjectType.Polygon.getName(),
                ObjectType.RegularPolygon.getName(), "Změna algoritmu", "Smazat vše"));
        infoPanel.add(lblSelected);

        frame.setResizable(false);
        frame.pack();
        frame.setLocationRelativeTo(null);
        frame.setVisible(true);
        canvas.requestFocus();
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public JLabel getLblXY() {
        return lblXY;
    }

    public JLabel getLblSelected() {
        return lblSelected;
    }
}
