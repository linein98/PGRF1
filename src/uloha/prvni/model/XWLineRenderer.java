package uloha.prvni.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import static java.lang.Math.*;

/**
 * Trida implementujici algoritmus umoznujici jednoduchy antialiasing
 *
 * https://en.wikipedia.org/wiki/Xiaolin_Wu%27s_line_algorithm
 * https://en.wikipedia.org/wiki/Bresenham%27s_line_algorithm
 */
public class XWLineRenderer implements Renderer {

    private BufferedImage image;

    public XWLineRenderer(BufferedImage image) {
        this.image = image;
    }

    /**
     * Vykresleni jednoho pixelu
     *
     * @param x     souradnice X
     * @param y     souradnice Y
     * @param color barva pixelu
     */
    @Override
    public void drawPixel(int x, int y, int color) {
        if ((x >= 0 && x < image.getWidth()) && (y >= 0 && y < image.getHeight()))
            image.setRGB(x, y, color);
    }

    /**
     * Vykresleni usecky pomoci dvou bodu
     *
     * @param start pocatecni bod
     * @param end   koncovy bod
     * @param color barva usecky
     */
    @Override
    public void drawLine(Point start, Point end, int color) {
        drawLine(start.x, start.y, end.x, end.y, new Color(color));
    }

    private void plot(double x, double y, double alpha, Color color) {
        drawPixel((int) x, (int) y, getColor(color, alpha));
    }

    /**
     * Metoda na namichani barvy
     * (1 - alpha) * Jedna_slozka_z_RGB_barvy_pozadi + alpha * Jedna_slozka_ze_vstupni_barvy_zadanou_v_RGB
     *
     * @param color barva pixelu
     * @param alpha 0 <= alpha <= 1
     * @return  vraci barvu v RGB na int
     */
    private int getColor(Color color, double alpha) {
        int r = (int) ((1 - alpha) * 0 + alpha * color.getRed());
        int g = (int) ((1 - alpha) * 0 + alpha * color.getGreen());
        int b = (int) ((1 - alpha) * 0 + alpha * color.getBlue());

        return new Color(r, g, b).getRGB();
    }

    private double fpart(double x) {
        if(x < 0)
            return (1 - (x - floor(x)));

        return x - floor(x);
    }

    private double rfpart(double x) {
        return 1 - fpart(x);
    }

    private void drawLine(int x0, int y0, int x1, int y1, Color color) {
        boolean steep = abs(y1 - y0) > abs(x1 - x0);

        if (steep) {
            int a = x0;
            x0 = y0;
            y0 = a;

            a = x1;
            x1 = y1;
            y1 = a;
        }

        if (x0 > x1) {
            int a = x0;
            x0 = x1;
            x1 = a;

            a = y0;
            y0 = y1;
            y1 = a;
        }

        double dx = x1 - x0;
        double dy = y1 - y0;
        double gradient = dy / dx;
        int xend = round(x0);
        int yend = (int) (y0 + gradient * (xend - x0));
        int xpxl1 = xend;
        double intery = yend + gradient;
        xend = round(x1);
        //yend = (int) (y1 + gradient * (xend - x1));
        int xpxl2 = xend;

        for (double x = xpxl1; x <= xpxl2 - 1; x++) {
            if(steep){
                plot(intery, x, rfpart(intery), color);
                plot(intery + 1, x, fpart(intery), color);
            } else {
                plot(x, intery, rfpart(intery), color);
                plot(x, intery + 1, fpart(intery), color);
            }
            intery = intery + gradient;
        }
    }
}
