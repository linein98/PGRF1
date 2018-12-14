package uloha.treti.model.solids;

import javafx.scene.paint.Color;
import transforms.Cubic;
import transforms.Mat4;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

/**
 * Krivky
 *
 * @author Tomas Brabec
 */
public class Cubic3D extends Solid {
    private List<Point3D> points = new ArrayList<>();

    public Cubic3D(CubicName cubicName) {
        switch (cubicName) {
            case BEZIER:
                points.add(new Point3D(-1, -1, -1));
                points.add(new Point3D(-1, -1, 0.5));
                points.add(new Point3D(1, -1, -0.5));
                points.add(new Point3D(1, -1, 1));
                color = Color.CYAN;
                create(Cubic.BEZIER);
                break;
            case COONS:
                points.add(new Point3D(1, 1, -1));

                points.add(new Point3D(-1, 1, -1));
                points.add(new Point3D(1, 1, 1));

                points.add(new Point3D(-1, 1, 1));

                color = Color.YELLOW;
                create(Cubic.COONS);
                break;
            case FERGUSON:
                //dva body
                points.add(new Point3D(-1, 0, -1));
                points.add(new Point3D(1, 0, 1));
                //dva vektory
                points.add(new Point3D(-1, 0, 1));
                points.add(new Point3D(2, 0, 1));
                color = Color.RED;
                create(Cubic.FERGUSON);
                break;
        }
    }

    private void create(Mat4 matrix) {
        List<Cubic> cubics = new ArrayList<>();
        for (int i = 0; i < points.size() - 3; i += 3) {
            cubics.add(new Cubic(matrix, points.get(i), points.get(i + 1), points.get(i + 2), points.get(i + 3)));
        }

        int i = 0;
        for (Cubic cubic : cubics) {
            Point3D p1 = cubic.compute(0);
            for (double a = 0.01; a <= 2; a += 0.01) {
                Point3D p2 = cubic.compute(a);
                vertices.add(p1);
                vertices.add(p2);
                p1 = p2;
                indices.add(i);
                indices.add(++i);
            }
        }
    }
}
