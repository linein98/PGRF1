package uloha.prvni.model;

import java.awt.*;
import java.util.List;

/**
 * Rozhrani pro jednodusi praci s objekty pri vykreslovani a uchovavani objektu v pameti
 *
 * @author Tomas Brabec
 */
public interface ObjectRender {
    /**
     * @return vraci seznam bodu
     */
    List<Point> getPoints();
}
