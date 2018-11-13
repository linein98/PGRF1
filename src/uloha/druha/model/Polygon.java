package uloha.druha.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida predstavujici n-uhelnik
 *
 * @author Tomas Brabec
 */
public class Polygon {
    private List<Point> points;

    public Polygon() {
        points = new ArrayList<>();
    }

    public Polygon(Point start, Point end) {
        points = new ArrayList<>();
        points.add(start);
        points.add(end);
    }

    public Polygon(List<Point> points) {
        this.points = points;
    }

    /**
     * Metoda vraci bud prvni nebo posledni bod z n-uhelnik
     *
     * @param first pokud je true vrati prvni bod jinak posledni
     * @return prvni nebo posledni bod n-uhelnik
     */
    public Point getPoint(boolean first) {
        return (first) ? points.get(0) : points.get(points.size() - 1);
    }

    /**
     * Pridani bodu do listu
     *
     * @param point pridani dalsiho bodu tvoricho n-uhelnik
     */
    public void addPoint(Point point) {
        points.add(point);
    }

    /**
     * getter vracejici seznam bodu
     *
     * @return seznam bodu tvorici n-uhelnik
     */
    public List<Point> getPoints() {
        return points;
    }

    /**
     * vrati kopii, aby nedoslo k zmene bodu originalniho n-uhelniku
     *
     * @return kopie seznamu bodu tvorici n-uhelnik
     */
    public List<Point> getCopyOfPoints() {
        return new ArrayList<>(points);
    }

    /**
     * vrati seznam hran
     *
     * @return seznam hran
     */
    public List<Edge> getEdges() {
        List<Edge> edges = new ArrayList<>();
        Point last = getPoint(false);
        for (Point p : points) {
            edges.add(new Edge(last, p));
            last = p;
        }
        return edges;
    }
}
