package uloha.prvni.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Trida predstavujici pravidelny mnohouhelnik
 *
 * @author Tomas Brabec
 */
public class RegularPolygon implements ObjectData {

    /**
     * Seznam bodu tvorici pravidelny mnohouhelnik
     */
    private List<Point> points;

    public RegularPolygon() {
        points = new ArrayList<>();
    }

    public RegularPolygon(Point p1, Point p2) {
        points = calculatePoints(p1, p2, 3);
    }

    /**
     * Pri zavolani konstruktru se dopocitaji ostatni body tvorici pravidelny mnohouhelnik
     *
     * @param p1    stred
     * @param p2    jeden z vrcholu
     * @param count celkovy pocet vrcholu
     */
    public RegularPolygon(Point p1, Point p2, int count) {
        points = calculatePoints(p1, p2, count);
    }

    /**
     * Staticka metoda pro vypocet bodu tvorici pravidelny mnohouhelnik
     * Pouziva se i pro vykresleni nahledu
     *
     * @param p1    stred
     * @param p2    jeden z vrcholu
     * @param count pocet vrcholu
     * @return vraci seznam bodu
     */
    public static List<Point> calculatePoints(Point p1, Point p2, int count) {
        List<Point> cPoints = new ArrayList<>();
        double angel1 = 0.0;
        double angel2 = 360.0;
        double dx = p2.x - p1.x;
        double dy = p2.y - p1.y;
        double step = 360.0 / (double) count;
        while (angel1 < angel2) {
            angel1 += step;
            double x = dx * Math.cos(Math.toRadians(step)) + dy * Math.sin(Math.toRadians(step));
            double y = dy * Math.cos(Math.toRadians(step)) - dx * Math.sin(Math.toRadians(step));
            cPoints.add(new Point((int) dx + p1.x, (int) dy + p1.y));
            dx = x;
            dy = y;
        }
        return cPoints;
    }

    /**
     * Staticka metoda pro zjisteni poctu vrcholu na zaklade uhlu - polohy kurzoru
     *
     * @param p0 bod - aktualni poloho kurzoru
     * @param p1 stred
     * @param p2 jeden z vrcholu
     * @return vraci pocet vrcholu
     */
    public static int getCount(Point p0, Point p1, Point p2) {
        double p12 = Math.sqrt(Math.pow((p1.x - p0.x), 2) + Math.pow((p1.y - p0.y), 2));
        double p13 = Math.sqrt(Math.pow((p1.x - p2.x), 2) + Math.pow((p1.y - p2.y), 2));
        double p23 = Math.sqrt(Math.pow((p2.x - p0.x), 2) + Math.pow((p2.y - p0.y), 2));

        int degree = (int) (Math.acos(((Math.pow(p12, 2)) + (Math.pow(p13, 2)) - (Math.pow(p23, 2))) / (2 * p12 * p13)) * 180 / Math.PI);
        int count = 360;

        if (degree != 0)
            count = 360 / degree;

        if (count < 3)
            count = 3;

        return count;
    }

    /**
     * Implementovana metoda rozhrani ObjectData
     *
     * @return vraci seznam bodu
     */
    @Override
    public List<Point> getPoints() {
        return points;
    }
}
