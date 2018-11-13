package uloha.druha.model;

import java.awt.*;
import java.awt.image.BufferedImage;

/**
 * Trida resici vykreslovani usecek a jednotlivych bodu
 *
 * @author Tomas Brabec
 */
public class Renderer {

    private BufferedImage image;

    public Renderer(BufferedImage image) {
        this.image = image;
    }

    /**
     * Vykresleni jednoho pixelu
     *
     * @param x     souradnice X
     * @param y     souradnice Y
     * @param color barva pixelu
     */
    private void drawPixel(int x, int y, int color) {
        if ((x >= 0 && x < image.getWidth()) && (y >= 0 && y < image.getHeight()))
            image.setRGB(x, y, color);
    }

    /**
     * Vykresleni usecky pomoci dvou bodu za pomoci DDA algoritmu
     *
     * @param start pocatecni bod
     * @param end   koncovy bod
     * @param color barva usecky
     */
    public void drawLine(Point start, Point end, int color) {
        drawLine(start.x, start.y, end.x, end.y, color);
    }

    public void drawLine(int x1, int y1, int x2, int y2, int color) {
        int dx = x2 - x1;
        int dy = y2 - y1;
        int steps;

        if (Math.abs(dx) > Math.abs(dy))
            steps = Math.abs(dx);
        else
            steps = Math.abs(dy);

        float xin = dx / (float) steps;
        float yin = dy / (float) steps;

        float x = x1, y = y1;

        for (int v = 0; v <= steps; v++) {
            drawPixel(Math.round(x), Math.round(y), color);
            x = x + xin;
            y = y + yin;
        }
    }
}
