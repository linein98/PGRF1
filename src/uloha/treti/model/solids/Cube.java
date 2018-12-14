package uloha.treti.model.solids;

import javafx.scene.paint.Color;
import transforms.Point3D;

/**
 * Krychle
 *
 * @author Tomas Brabec
 */
public class Cube extends Solid {

    public Cube() {
        this(Color.YELLOW);
    }

    public Cube(Color color) {
        this.color = color;
        vertices.add(new Point3D(-1, -1, 1));
        vertices.add(new Point3D(1, -1, 1));
        vertices.add(new Point3D(1, 1, 1));
        vertices.add(new Point3D(-1, 1, 1));

        vertices.add(new Point3D(-1, -1, -1));
        vertices.add(new Point3D(1, -1, -1));
        vertices.add(new Point3D(1, 1, -1));
        vertices.add(new Point3D(-1, 1, -1));

        indices.add(0);
        indices.add(1);
        indices.add(1);
        indices.add(2);
        indices.add(2);
        indices.add(3);
        indices.add(3);
        indices.add(0);

        indices.add(4);
        indices.add(5);
        indices.add(5);
        indices.add(6);
        indices.add(6);
        indices.add(7);
        indices.add(7);
        indices.add(4);

        indices.add(0);
        indices.add(4);
        indices.add(1);
        indices.add(5);
        indices.add(2);
        indices.add(6);
        indices.add(3);
        indices.add(7);
    }
}
