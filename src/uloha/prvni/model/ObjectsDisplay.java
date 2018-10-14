package uloha.prvni.model;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.ArrayList;
import java.util.List;

/**
 * Model - stara se o vykreslovani objektu a uchovani objektu v pameti
 *
 * @author Tomas Brabec
 */
public class ObjectsDisplay {

    private BufferedImage image;
    /**
     * Pomocna promenna - uchovava co za objekt se ma vykreslit
     */
    private ObjectType objectName;
    /**
     * List pro praci s objekty
     */
    private List<ObjectRender> objects;

    /**
     * @param width  sirka platna
     * @param height vyska platna
     */
    public ObjectsDisplay(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        objectName = ObjectType.Line;
        objects = new ArrayList<>();
    }

    public BufferedImage getImage() {
        return image;
    }

    public ObjectType getObjectName() {
        return objectName;
    }

    public void setObjectName(ObjectType objectName) {
        this.objectName = objectName;
    }

    public Polygon getLastPolygon() {
        return (Polygon) objects.get(objects.size() - 1);
    }

    /**
     * Zjisteni, jestli je uz nejaky polygon vytvoren
     *
     * @return vraci true pokud je Polygon posledni objekt v listu
     */
    public boolean isPolygonLast() {
        if (objects.size() > 0)
            return objects.get(objects.size() - 1) instanceof Polygon;
        else
            return false;
    }

    /**
     * Vykresleni usecky pomoci dvou bodu
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

        for (int v = 0; v < steps; v++) {
            drawPixel(Math.round(x), Math.round(y), color);
            x = x + xin;
            y = y + yin;
        }
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
     * Pridani objektu do listu
     *
     * @param or objekt implementujici rozhrani ObjectRender
     */
    public void addObject(ObjectRender or) {
        objects.add(or);
    }

    /**
     * Prekresleni platna
     */
    public void refresh() {
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.clearRect(0, 0, 800, 600);
        render();
    }

    /**
     * Vykresleni objektu ulozenych v listu
     */
    public void render() {
        int color;
        for (ObjectRender object : objects) {
            List<Point> points = object.getPoints();
            if (object instanceof Line) {
                color = 0xFFFF00;
                for (int i = 0; i < points.size() - 1; i++)
                    drawLine(points.get(i), points.get(i + 1), color);
            } else if (object instanceof Polygon || object instanceof RegularPolygon) {
                color = (object instanceof Polygon) ? 0x00FFFF : 0xFF00FF;
                for (int i = 0; i < points.size() - 1; i++)
                    drawLine(points.get(i), points.get(i + 1), color);

                if (points.size() > 2)
                    drawLine(points.get(0), points.get(points.size() - 1), color);
            }
        }
    }

    /**
     * Smazani vsech objektu v listu a prekresleni platna
     */
    public void delete() {
        objects.clear();
        refresh();
    }

    /**
     * Metoda pro docasne vykresleni (pravidelneho) mnohouhelniku
     *
     * @param points seznam bodu pro vykresleni mnohouhelniku
     */
    public void render(List<Point> points) {
        int color = 0xFF00FF;

        for (int i = 0; i < points.size() - 1; i++)
            drawLine(points.get(i), points.get(i + 1), color);

        if (points.size() > 2)
            drawLine(points.get(0), points.get(points.size() - 1), color);
    }
}
