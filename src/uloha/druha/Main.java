package uloha.druha;

import uloha.druha.controller.Controller;
import uloha.druha.model.ObjectsDisplay;
import uloha.druha.view.Window;

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
        int width = 800, height = 600;

        SwingUtilities.invokeLater(() -> {
            Window window = new Window(width, height, "PRGF1 - Úloha druhá");
            ObjectsDisplay objectsDisplay = new ObjectsDisplay(width, height);
            Controller controller = new Controller(window, objectsDisplay);
        });
    }
}
