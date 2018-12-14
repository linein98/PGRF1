package uloha.treti.controller;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.CheckBox;
import javafx.scene.control.RadioButton;
import javafx.scene.input.KeyEvent;
import javafx.scene.input.MouseEvent;
import javafx.scene.layout.Pane;
import transforms.Mat4Identity;
import uloha.treti.model.Renderer;

import java.net.URL;
import java.util.ResourceBundle;

/**
 * Obsulha gui
 *
 * @author Tomas Brabec
 */
public class Controller implements Initializable {

    @FXML
    private Pane pane;
    @FXML
    private RadioButton rbPersp;
    @FXML
    private RadioButton rbOrtho;
    @FXML
    private CheckBox cbCube;
    @FXML
    private CheckBox cbAxis;
    @FXML
    private CheckBox cbParam;
    @FXML
    private CheckBox cbBazi;
    @FXML
    private CheckBox cbCoon;
    @FXML
    private CheckBox cbFerg;

    private Renderer renderer;
    private double x, y;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        renderer = new Renderer(1024, 768);
        pane.getChildren().add(renderer.getCanvas());
        renderer.bindVisibility(cbCube, cbAxis, cbParam, cbBazi, cbCoon, cbFerg);
        renderer.repaint();
    }

    public void onMousePressed(MouseEvent mouseEvent) {
        x = mouseEvent.getX();
        y = mouseEvent.getY();
        pane.requestFocus();
    }

    public void onMouseDragged(MouseEvent mouseEvent) {
        switch (mouseEvent.getButton()) {
            case PRIMARY:
                double azimuthDiff = (x - mouseEvent.getX()) / 5000.0;
                double zenithDiff = (y - mouseEvent.getY()) / 5000.0;
                renderer.moveCamera(azimuthDiff, zenithDiff);
                renderer.repaint();
                break;
            case SECONDARY:
                double rotX = (x - mouseEvent.getX()) / -2000.0;
                double rotY = (y - mouseEvent.getY()) / -2000.0;
                renderer.rotateModel(rotX, rotY);
                renderer.repaint();
                break;
        }
    }

    public void resetClick(ActionEvent actionEvent) {
        renderer.resetCamera();
        renderer.repaint();
    }

    public void rbChange(ActionEvent actionEvent) {
        if (actionEvent.getSource() == rbPersp) {
            renderer.setPerspProjection();
        } else {
            renderer.setOrthoProjection();
        }
        renderer.repaint();
    }

    public void resetModelClick(ActionEvent actionEvent) {
        renderer.setModel(new Mat4Identity());
        renderer.repaint();
    }

    public void cbClick(ActionEvent actionEvent) {
        renderer.repaint();
    }

    public void onKeyPressed(KeyEvent keyEvent) {
        renderer.moveCamera(keyEvent.getCode());
    }
}
