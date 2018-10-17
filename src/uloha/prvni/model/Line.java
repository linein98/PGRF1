package uloha.prvni.model;

import java.awt.*;
import java.util.Arrays;
import java.util.List;

/**
 * Trida predstavujici usecku zadanou dvema body
 *
 * @author Tomas Brabec
 */
public class Line implements ObjectData {
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
     * Implementovana metoda rozhrani ObjectData
     *
     * @return vraci seznam bodu
     */
    @Override
    public List<Point> getPoints() {
        //return List.of(start, end); JDK 10

        return Arrays.asList(start, end);
    }
}
