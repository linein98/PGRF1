package uloha.treti.model.solids;

import javafx.scene.paint.Color;
import transforms.Point3D;

/**
 * Vykresleni pramerticke funkce
 * https://en.wikipedia.org/wiki/Butterfly_curve_(transcendental)
 *
 * @author Tomas Brabec
 */
public class ParametricGraph extends Solid {

    public ParametricGraph() {
        int i = 0;
        int points = 1000;
        for (double a = 0; a < points; a++) {
            double t = 12 * i * Math.PI / points;
            double x = (Math.sin(t) * (Math.pow(Math.E, Math.cos(t)) - 2 * Math.cos(4 * t) - Math.pow(Math.sin(t / 12), 5)));
            double y = (Math.cos(t) * (Math.pow(Math.E, Math.cos(t)) - 2 * Math.cos(4 * t) - Math.pow(Math.sin(t / 12), 5)));
            vertices.add(new Point3D(x / 3, 0, y / 3));
            if (a > 0) {
                indices.add(i);
                indices.add(++i);
            }
        }
        color = Color.MAGENTA;
    }
}
