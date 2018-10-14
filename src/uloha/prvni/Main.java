package uloha.prvni;

import uloha.prvni.controller.Controller;
import uloha.prvni.model.ObjectsDisplay;
import uloha.prvni.view.Window;

import javax.swing.*;

/**
 * Inicializace jednotlivich komponent - View(Window), Model(ObjectsDisplay), Controller
 *
 * @author Tomas Brabec
 */
public class Main {
    public static void main(String[] args) {
        int width = 800, height = 600;

        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                Window window = new Window(width, height, "PRGF1 - Úloha první");
                ObjectsDisplay objectsDisplay = new ObjectsDisplay(width, height);
                Controller controller = new Controller(window, objectsDisplay);
            }
        });
    }
}
