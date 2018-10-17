package uloha.prvni.model;

import java.awt.*;

/**
 * Rozhrani umoznujici implementaci ruznych algoritmu na vykreslovani usecky
 *
 * @author Tomas Brabec
 */
public interface Renderer {

    void drawPixel(int x, int y, int color);
    void drawLine(Point start, Point end, int color);
}
