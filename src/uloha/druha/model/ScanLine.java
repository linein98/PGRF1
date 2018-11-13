package uloha.druha.model;

import java.awt.*;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;


/**
 * Trida implementujici scan line
 *
 * @author Tomas Brabec
 */
public class ScanLine {
    //private Raster raster;
    //private List<Point> points;
    /**
     * barva vyplnene plochy n-uhelniku
     */
    private int fillColor;
    /**
     * barva hrany n-uhelniku
     */
    private int edgeColor;
    /**
     * vysledny n-uhelnik vznikli orezem
     */
    private Polygon polygon;
    private ObjectsDisplay objectsDisplay;

    public ScanLine(ObjectsDisplay objectsDisplay) {
        this.objectsDisplay = objectsDisplay;
    }

    /**
     * inicializace pred samotnym vyplnovanim
     *
     * @param polygon   vysledny n-uhelnik vznikli orezem
     * @param fillColor barva hrany n-uhelniku
     * @param edgeColor arva vyplnene plochy n-uhelniku
     */
    public void init(Polygon polygon, int fillColor, int edgeColor) {
        this.polygon = polygon;
        this.fillColor = fillColor;
        this.edgeColor = edgeColor;
    }

    /**
     * vyplnovaci algoritmus scan line
     */
    public void fill() {

        List<Edge> edges = new ArrayList<>();
        List<Point> points = polygon.getCopyOfPoints();
        if (points.size() < 2)
            return;
        // projet všechny body a vytvořit z nich hrany (jako polygon)
        // 0. a 1. bod budou první hrana; 1. a 2. bod budou druhá hrana
        // ...; poslední a 0. bod budou poslední hrana
        // ignorovat vodorovné hrany
        // vyvtořené hrany zorientovat a přidat do seznamu
        for (int i = 0; i < points.size(); i++) {
            Edge edge = new Edge(points.get(i), points.get((i + 1) % points.size()));
            if (!edge.isHorizontal()) {
                edge.orientate();
                edges.add(edge);
            }
        }

        // najít minimum a maximum pro Y
        int minY = points.get(0).y;
        int maxY = minY;
        // projet všechny body a najít minimální a maximální Y
        for (Point point : points) {
            if (minY > point.y)
                minY = point.y;

            if (maxY < point.y)
                maxY = point.y;
        }

        // pro všechna Y od min do max včetně
        for (int y = minY; y <= maxY; y++) {

            List<Integer> intersections = new ArrayList<>();
            // projít všechny hrany
            // pokud hrana má průsečík pro dané Y
            // tak vypočítáme průsečík a uložíme hodnotu do seznamu
            for (Edge edge : edges) {
                if (edge.intersectionExists(y))
                    intersections.add(edge.getIntersection(y));
            }

            Collections.sort(intersections);
            // nebo volitelně implementovat vlastní algoritmus na seřazení

            // vybarvení mezi průsečíky
            // spojení vždy sudého s lichým
            // 0. a 1.; 2. a 3.;...
            for (int i = 0; i < intersections.size(); i += 2) {
                objectsDisplay.getRenderer().drawLine(intersections.get(i), y, intersections.get(i + 1), y, fillColor);
            }

        }

        // obtáhnutí hranice
        //renderer.drawPolygon(points, edgeColor);
        objectsDisplay.drawPolygon(polygon, edgeColor);
    }
}
