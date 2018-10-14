package uloha.prvni.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida predstavujici mnohouhelnik
 *
 * @author Tomas Brabec
 */
public class Polygon implements ObjectRender {

    /**
     * Seznam bodu tvorici mnohouhelnik
     */
    private List<Point> points;

    /**
     * V konstruktoru je vytvorena usecka a az nasledne jsou pridavany dalsi body
     *
     * @param start pocatecni bod
     * @param end   koncovu bod
     */
    public Polygon(Point start, Point end) {
        points = new ArrayList<>();
        points.add(start);
        points.add(end);

    }

    /**
     * Pridani bodu do listu
     *
     * @param point pridani dalsiho bodu tvoricho mnohouhelnik
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /*public Point getPoint(int index) {
        return points.get(index);
    }*/

    /**
     * Metoda vraci bud prvni nebo posledni bod z mnohouhelniku
     *
     * @param first pokud je true vrati prvni bod jinak posledni
     * @return prvni nebo posledni bod mnohouhelniku
     */
    public Point getPoint(boolean first) {
        return (first) ? points.get(0) : points.get(points.size() - 1);
    }

    /**
     * Implementovana metoda rozhrani ObjectRender
     *
     * @return vraci seznam bodu
     */
    @Override
    public List<Point> getPoints() {
        return points;
    }
}
