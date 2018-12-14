package uloha.treti.model.solids;

import javafx.beans.property.BooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.scene.paint.Color;
import transforms.Point3D;

import java.util.ArrayList;
import java.util.List;

/**
 * Abstraktni trida - dedi z ni vsechny objekty
 *
 * @author Tomas Brabec
 */
public abstract class Solid {
    final List<Point3D> vertices = new ArrayList<>();
    final List<Integer> indices = new ArrayList<>();
    BooleanProperty visibility = new SimpleBooleanProperty(true);
    Color color;

    public List<Point3D> getVertices() {
        return vertices;
    }

    public List<Integer> getIndices() {
        return indices;
    }

    public Color getColor() {
        return color;
    }

    public boolean getVisibility() {
        return visibility.get();
    }

    public BooleanProperty visibilityProperty() {
        return visibility;
    }

    public void setVisibility(boolean visibility) {
        this.visibility.set(visibility);
    }
}
