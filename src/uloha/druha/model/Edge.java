package uloha.druha.model;

import java.awt.*;

/**
 * Trida pouzivana pri orezavani a pri vyplnovani pomoci scan line algoritmu
 */
public class Edge {

    private Point point1;
    private Point point2;

    public Edge(Point point1, Point point2) {
        this.point1 = point1;
        this.point2 = point2;
    }

    /**
     * Zjistí, zda je hrana vodorovná
     *
     * @return true pokud je vodorovná, jinak false
     */
    public boolean isHorizontal() {
        return point1.y == point2.y;
    }

    /**
     * Zorientuje hranu odshora dolů
     */
    public void orientate() {
        if (point1.getY() > point2.getY()) {
            Point p = point1;
            point1 = point2;
            point2 = p;
        }
    }

    /**
     * Zjistí, zda existuje průsečík scanline s hranou
     *
     * @param y y-ová souřadnice scanline
     * @return true pokud průsečík existuje, jinak false
     */
    public boolean intersectionExists(int y) {
        return (y >= point1.y && y < point2.y);
    }

    /**
     * Vypočítá a vrátí x-ovou souřadnici průsečíku se scanline
     *
     * @param y y-ová souřadnice scanline
     * @return souřadnice x
     */
    public int getIntersection(int y) {
        float k = (point2.x - point1.x) / (float) (point2.y - point1.y);
        float q = (point1.x - (k * point1.y));
        float x = (k * y) + q;
        return (int) x;
    }

    /**
     * Zjistí, na které straně přímky tvořené touto úsečkou se nachází bod z parametru
     *
     * @param point testovaný bod
     * @return true pokud se nachází uvnitř (za předpokladu správné orientace)
     */
    public boolean inside(Point point) {
        Point v1 = new Point(point2.x - point1.x, point2.y - point1.y);
        Point n1 = new Point(-v1.y, v1.x);
        Point v2 = new Point(point.x - point1.x, point.y - point1.y);
        return n1.x * v2.x + n1.y * v2.y < 0.0;
    }

    /**
     * Vypočítání průsečíku dvou hran
     *
     * @param v1 první bod druhé hrany
     * @param v2 druhý bod druhé hrany
     * @return průsečík
     */
    public Point getIntersection(Point v1, Point v2) {
        float x0 = ((v1.x * v2.y - v1.y * v2.x) * (point1.x - point2.x) - (point1.x * point2.y - point1.y * point2.x) * (v1.x - v2.x))
                / (float) ((v1.x - v2.x) * (point1.y - point2.y) - (point1.x - point2.x) * (v1.y - v2.y));

        float y0 = ((v1.x * v2.y - v1.y * v2.x) * (point1.y - point2.y) - (point1.x * point2.y - point1.y * point2.x) * (v1.y - v2.y))
                / (float) ((v1.x - v2.x) * (point1.y - point2.y) - (point1.x - point2.x) * (v1.y - v2.y));

        return new Point(Math.round(x0), Math.round(y0));
    }
}
