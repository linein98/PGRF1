package uloha.treti.model;


import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.CheckBox;
import javafx.scene.input.KeyCode;
import javafx.scene.paint.Color;
import transforms.*;
import uloha.treti.model.solids.*;

import java.util.ArrayList;
import java.util.List;

/**
 * Vykreslovani objektu
 *
 * @author Tomas Brabec
 */
public class Renderer {

    private Canvas canvas;
    private GraphicsContext graphics;
    private List<Solid> solids;

    private Camera camera;

    private Mat4 projection;
    private Mat4 view;
    private Mat4 model;

    public Renderer(int width, int height) {
        canvas = new Canvas(width, height);
        graphics = canvas.getGraphicsContext2D();
        solids = new ArrayList<>();
        init();
    }

    private void init() {
        model = new Mat4Identity();
        setPerspProjection();
        resetCamera();
        clear();
    }

    public void bindVisibility(CheckBox... boxes) {
        Cube cube = new Cube();
        Axis ax = new Axis(AxisName.X);
        Axis ay = new Axis(AxisName.Y);
        Axis az = new Axis(AxisName.Z);
        ParametricGraph pg = new ParametricGraph();
        Cubic3D bazi = new Cubic3D(CubicName.BEZIER);
        Cubic3D coon = new Cubic3D(CubicName.COONS);
        Cubic3D ferg = new Cubic3D(CubicName.FERGUSON);

        cube.visibilityProperty().bind(boxes[0].selectedProperty());
        ax.visibilityProperty().bind(boxes[1].selectedProperty());
        ay.visibilityProperty().bind(boxes[1].selectedProperty());
        az.visibilityProperty().bind(boxes[1].selectedProperty());
        pg.visibilityProperty().bind(boxes[2].selectedProperty());
        bazi.visibilityProperty().bind(boxes[3].selectedProperty());
        coon.visibilityProperty().bind(boxes[4].selectedProperty());
        ferg.visibilityProperty().bind(boxes[5].selectedProperty());

        solids.add(ax);
        solids.add(ay);
        solids.add(az);
        solids.add(pg);
        solids.add(bazi);
        solids.add(coon);
        solids.add(ferg);
        solids.add(cube);
    }

    public void setPerspProjection() {
        projection = new Mat4PerspRH(Math.PI / 4, canvas.getHeight() / (float) canvas.getWidth(), 1, 200);
    }

    public void setOrthoProjection() {
        projection = new Mat4OrthoRH(canvas.getWidth() / 180, canvas.getHeight() / 180, 1, 200);
    }

    public Canvas getCanvas() {
        return canvas;
    }

    public void setModel(Mat4 model) {
        this.model = model;
    }

    public void moveCamera(double azimuthDiff, double zenithDiff) {
        double azimuth = camera.getAzimuth() + azimuthDiff;

        double zenith = camera.getZenith() + zenithDiff;
        if (zenith > Math.PI / 2)
            zenith = Math.PI / 2;
        if (zenith < -Math.PI / 2)
            zenith = -Math.PI / 2;

        camera = camera.withAzimuth(azimuth);
        camera = camera.withZenith(zenith);
        view = camera.getViewMatrix();
    }

    public void rotateModel(double x, double y) {
        model = model.mul(new Mat4RotXYZ(y, 0, x));
    }

    private void clear() {
        graphics.setFill(Color.BLACK);
        graphics.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());
    }

    public void resetCamera() {
        camera = new Camera(
                new Vec3D(0, -5, 2),
                Math.toRadians(90),
                Math.toRadians(-25),
                1, true
        );
        view = camera.getViewMatrix();
    }

    public void repaint() {
        clear();
        for (Solid solid : solids) {
            if (solid.getVisibility()) {
                List<Point3D> vertices = solid.getVertices();
                List<Integer> indices = solid.getIndices();
                for (int i = 0; i < indices.size(); i += 2) {
                    int index1 = indices.get(i);
                    int index2 = indices.get(i + 1);
                    Point3D a = vertices.get(index1);
                    Point3D b = vertices.get(index2);
                    drawLine(a, b, solid.getColor(), solid instanceof Axis);
                }
            }
        }
    }

    private void drawLine(Point3D a, Point3D b, Color color, boolean isAxis) {
        if (isAxis) {
            a = a.mul(view).mul(projection);
            b = b.mul(view).mul(projection);
        } else {
            a = a.mul(model).mul(view).mul(projection);
            b = b.mul(model).mul(view).mul(projection);
        }

        if (a.getW() <= 0 || b.getW() <= 0) return;

        if (!a.dehomog().isPresent() || !b.dehomog().isPresent()) {
            return;
        }
        Vec3D v1 = a.dehomog().get();
        Vec3D v2 = b.dehomog().get();

        // ořezání hodnot mimo X<-1,1> Y<-1,1> Z<0,1>
        if (Math.abs(v1.getX()) > 1 || Math.abs(v2.getX()) > 1) return;
        if (Math.abs(v1.getY()) > 1 || Math.abs(v2.getY()) > 1) return;
        if (v1.getZ() > 1 || v1.getZ() < 0 || v2.getZ() > 1 || v2.getZ() < 0) return;

        v1 = transformToWindow(v1);
        v2 = transformToWindow(v2);

        graphics.setLineWidth(2);
        graphics.setStroke(color);
        graphics.strokeLine(v1.getX(), v1.getY(), v2.getX(), v2.getY());
    }

    private Vec3D transformToWindow(Vec3D v) {
        return v.mul(new Vec3D(1, -1, 1)) // Y jde nahoru, chceme dolu
                .add(new Vec3D(1, 1, 0)) // (0,0) je uprostřed, chceme v rohu
                // máme <0, 2> -> vynásobíme polovinou velikosti plátna
                .mul(new Vec3D(canvas.getWidth() / 2f, canvas.getHeight() / 2f, 1));
    }

    public void moveCamera(KeyCode key) {
        switch (key) {
            case W:
                camera = camera.up(0.2);
                view = camera.getViewMatrix();
                repaint();
                break;
            case S:
                camera = camera.down(0.2);
                view = camera.getViewMatrix();
                repaint();
                break;
            case A:
                camera = camera.left(0.2);
                view = camera.getViewMatrix();
                repaint();
                break;
            case D:
                camera = camera.right(0.2);
                view = camera.getViewMatrix();
                repaint();
                break;
            case Q:
                camera = camera.forward(0.2);
                view = camera.getViewMatrix();
                repaint();
                break;
            case E:
                camera = camera.backward(0.2);
                view = camera.getViewMatrix();
                repaint();
                break;
        }
    }
}
