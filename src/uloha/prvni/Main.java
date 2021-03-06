package uloha.prvni;

import uloha.prvni.controller.Controller;
import uloha.prvni.model.ObjectsDisplay;
import uloha.prvni.view.Window;

import javax.swing.*;

/**
 * Inicializace jednotlivich komponent - View(Window), Model(ObjectsDisplay), Controller
 * https://www.youtube.com/watch?v=Mk3qkQROb_k
 * https://github.com/linein98/PGRF1
 *
 * @author Tomas Brabec
 */
public class Main {
    public static void main(String[] args) {
        int width = 1024, height = 768;

        SwingUtilities.invokeLater(() -> {
            Window window = new Window(width, height, "PRGF1 - Úloha první");
            ObjectsDisplay objectsDisplay = new ObjectsDisplay(width, height);
            Controller controller = new Controller(window, objectsDisplay);
        });
    }
}
