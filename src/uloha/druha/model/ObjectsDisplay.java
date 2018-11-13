package uloha.druha.model;

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
     * orezavaci polygon - zadany napevno v kodu
     */
    private Polygon clipPolygon;
    /**
     * uzivatelem zadany polygon
     */
    private Polygon polygon;
    private Renderer renderer;
    private SeedFill seedFill;
    private ScanLine scanLine;

    /**
     * @param width  sirka platna
     * @param height vyska platna
     */
    public ObjectsDisplay(int width, int height) {
        image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        renderer = new Renderer(image);
        seedFill = new SeedFill(image);
        scanLine = new ScanLine(this);
        initClipPolygon();
        polygon = new Polygon();
    }

    /**
     * inicializace orezavaciho polygonu - nastaveni jeho bodu
     */
    private void initClipPolygon() {
        clipPolygon = new Polygon();
        clipPolygon.addPoint(new Point(10, 10));
        clipPolygon.addPoint(new Point(200, 550));
        clipPolygon.addPoint(new Point(450, 300));
    }

    public BufferedImage getImage() {
        return image;
    }

    public Renderer getRenderer() {
        return renderer;
    }

    public Polygon getPolygon() {
        return polygon;
    }

    public int getPolygonSize() {
        return polygon.getPoints().size();
    }

    public void setPolygon(Polygon polygon) {
        this.polygon = polygon;
    }

    public void refresh() {
        Graphics g = image.getGraphics();
        g.setColor(Color.BLACK);
        g.clearRect(0, 0, image.getWidth(), image.getHeight());
        render();
    }

    /**
     * metoda vykresli oba n-uhelniky a nasledne pokud ma orezavany polygon alespon 2 body
     * se vykresli i jeho orezana podoba
     */
    private void render() {
        drawPolygon(clipPolygon, 0x00FFFF);
        drawPolygon(polygon, 0xFF00FF);
        if (getPolygonSize() >= 2) {
            scanLine.init(clip(), 0x1F1F00, 0xFFFF00);
            scanLine.fill();
        }
    }

    public void drawPolygon(Polygon polygon, int color) {
        drawPolygon(polygon.getPoints(), color);
    }

    private void drawPolygon(List<Point> points, int color) {
        for (int i = 0; i < points.size() - 1; i++)
            renderer.drawLine(points.get(i), points.get(i + 1), color);

        if (points.size() > 2)
            renderer.drawLine(points.get(0), points.get(points.size() - 1), color);
    }

    public void seedFill(Point point) {
        seedFill.fill(point.x, point.y, 0x2F002F, 0x005F5F);
    }


    /**
     * metoda na orezani n-uhelniku
     * Zdroj peudokodu: https://en.wikipedia.org/wiki/Sutherland%E2%80%93Hodgman_algorithm
     *
     * @return vraci orezany polygon (n-uhelnik)
     */
    private Polygon clip() {
        List<Point> output = polygon.getCopyOfPoints();
        List<Edge> edges = clipPolygon.getEdges();
        for (Edge edge : edges) {
            List<Point> input = new ArrayList<>(output);
            output.clear();

            if (input.size() < 2)
                return new Polygon(output);

            Point s = input.get(input.size() - 1);
            for (Point e : input) {
                if (edge.inside(e)) {
                    if (!edge.inside(s)) {
                        output.add(edge.getIntersection(s, e));
                    }
                    output.add(e);
                } else if (edge.inside(s)) {
                    output.add(edge.getIntersection(s, e));
                }
                s = e;
            }
        }
        return new Polygon(output);
    }
}
