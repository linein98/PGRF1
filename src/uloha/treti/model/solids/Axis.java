package uloha.treti.model.solids;

import javafx.scene.paint.Color;
import transforms.Point3D;

/**
 * Osy se sipkou
 *
 * @author Tomas Brabec
 */
public class Axis extends Solid {
    public Axis(AxisName axisName) {
        switch (axisName) {
            case X:
                color = Color.RED;
                vertices.add(new Point3D(0, 0, 0));
                vertices.add(new Point3D(1, 0, 0));
                vertices.add(new Point3D(0.95, 0.05, 0));
                vertices.add(new Point3D(0.95, -0.05, 0));
                vertices.add(new Point3D(0.95, 0, 0.05));
                vertices.add(new Point3D(0.95, 0, -0.05));
                indices.add(0);
                indices.add(1);
                indices.add(1);
                indices.add(2);
                indices.add(1);
                indices.add(3);
                indices.add(1);
                indices.add(4);
                indices.add(1);
                indices.add(5);
                break;
            case Y:
                color = Color.GREEN;
                vertices.add(new Point3D(0, 0, 0));
                vertices.add(new Point3D(0, 1, 0));
                vertices.add(new Point3D(0.05, 0.95, 0));
                vertices.add(new Point3D(-0.05, 0.95, 0));
                vertices.add(new Point3D(0, 0.95, 0.05));
                vertices.add(new Point3D(0, 0.95, -0.05));
                indices.add(0);
                indices.add(1);
                indices.add(1);
                indices.add(2);
                indices.add(1);
                indices.add(3);
                indices.add(1);
                indices.add(4);
                indices.add(1);
                indices.add(5);
                break;
            case Z:
                color = Color.BLUE;
                vertices.add(new Point3D(0, 0, 0));
                vertices.add(new Point3D(0, 0, 1));
                vertices.add(new Point3D(0.05, 0, 0.95));
                vertices.add(new Point3D(-0.05, 0, 0.95));
                vertices.add(new Point3D(0, 0.05, 0.95));
                vertices.add(new Point3D(0, -0.05, 0.95));
                indices.add(0);
                indices.add(1);
                indices.add(1);
                indices.add(2);
                indices.add(1);
                indices.add(3);
                indices.add(1);
                indices.add(4);
                indices.add(1);
                indices.add(5);
                break;
        }
    }
}
