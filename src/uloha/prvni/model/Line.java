package uloha.prvni.model;

import java.awt.*;
import java.util.List;

/**
 * Trida predstavujici usecku zadanou dvema body
 *
 * @author Tomas Brabec
 */
public class Line implements ObjectRender {
    private Point start;
    private Point end;

    /**
     * Konstruktor tridy Line
     *
     * @param start pocatecni bod
     * @param end koncovy bod
     */
    public Line(Point start, Point end){
        this.start = start;
        this.end = end;
    }

    /**
     * Implementovana metoda rozhrani ObjectRender
     *
     * @return vraci seznam bodu
     */
    @Override
    public List<Point> getPoints() {
        return List.of(start, end);
    }
}
