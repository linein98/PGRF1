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
    private List<ObjectData> objects;
    /**
     * Specifikovani algoritmu na vykreslovani
     */
    private Renderer renderer;

    /**
     * @param width  sirka platna
     * @param height vyska platna
     */
    public ObjectsDisplay(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        objectName = ObjectType.Line;
        objects = new ArrayList<>();
        renderer = new DDARenderer(image);
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

    public Renderer getRenderer() {
        return renderer;
    }

    public void setRenderer(Renderer renderer) {
        this.renderer = renderer;
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
     * Pridani objektu do listu
     *
     * @param or objekt implementujici rozhrani ObjectData
     */
    public void addObject(ObjectData or) {
        objects.add(or);
    }

    /**
     * Prekresleni platna
     */
    public void refresh() {
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
        render();
    }

    /**
     * Vykresleni objektu ulozenych v listu
     */
    private void render() {
        int color;
        for (ObjectData object : objects) {
            List<Point> points = object.getPoints();
            if (object instanceof Line) {
                color = 0xFFFF00;
                for (int i = 0; i < points.size() - 1; i++)
                    renderer.drawLine(points.get(i), points.get(i + 1), color);
            } else if (object instanceof Polygon || object instanceof RegularPolygon) {
                color = (object instanceof Polygon) ? 0x00FFFF : 0xFF00FF;
                for (int i = 0; i < points.size() - 1; i++)
                    renderer.drawLine(points.get(i), points.get(i + 1), color);

                if (points.size() > 2)
                    renderer.drawLine(points.get(0), points.get(points.size() - 1), color);
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
            renderer.drawLine(points.get(i), points.get(i + 1), color);

        if (points.size() > 2)
            renderer.drawLine(points.get(0), points.get(points.size() - 1), color);
    }
}
