package uloha.druha.controller;

import uloha.druha.model.ObjectsDisplay;
import uloha.druha.model.Polygon;
import uloha.druha.view.Window;

import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Timer;
import java.util.TimerTask;

/**
 * Controller - stara se o integraci mezi View a Model
 *
 * @author Tomas Brabec
 */
public class Controller {

    private Window window;
    private ObjectsDisplay objectsDisplay;
    /**
     * Pocatecni bod pri tazeni mysi - pouziva se pri vykreslovani n-uhelniku
     */
    private Point point;

    private static final int frameRate = 1000 / 30;

    public Controller(Window window, ObjectsDisplay objectsDisplay) {
        this.window = window;
        this.objectsDisplay = objectsDisplay;
        startRefreshingCanvas();
        setActions();
        objectsDisplay.refresh();
    }

    /**
     * Pravidelne obnovovani obrazu
     */
    private void startRefreshingCanvas() {
        new Timer().schedule(new TimerTask() {
            @Override
            public void run() {
                window.getCanvas().getGraphics().drawImage(objectsDisplay.getImage(), 0, 0, null);
            }
        }, 0, frameRate);
    }

    /**
     * Nastaveni listeneru
     */
    private void setActions() {
        window.getCanvas().addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e))
                    point = e.getPoint();
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    if (objectsDisplay.getPolygonSize() >= 2)
                        objectsDisplay.getPolygon().addPoint(e.getPoint());
                    else
                        objectsDisplay.setPolygon(new Polygon(point, e.getPoint()));

                    objectsDisplay.refresh();
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (SwingUtilities.isRightMouseButton(e)) {
                    /*Thread thread = new Thread(new Runnable() {
                        public void run() {
                            objectsDisplay.seedFill(e.getPoint());
                        }
                    });
                    thread.start();*/
                    objectsDisplay.refresh();
                    objectsDisplay.seedFill(e.getPoint());
                    //objectsDisplay.render();
                }
            }
        });

        window.getCanvas().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseDragged(MouseEvent e) {
                if (SwingUtilities.isLeftMouseButton(e)) {
                    objectsDisplay.refresh();
                    if (objectsDisplay.getPolygonSize() == 0) {
                        objectsDisplay.getRenderer().drawLine(point, e.getPoint(), 0xFF0000);
                    } else if (objectsDisplay.getPolygonSize() >= 2) {
                        Point point1 = objectsDisplay.getPolygon().getPoint(true);
                        Point point2 = objectsDisplay.getPolygon().getPoint(false);
                        objectsDisplay.getRenderer().drawLine(point1, e.getPoint(), 0xFF0000);
                        objectsDisplay.getRenderer().drawLine(point2, e.getPoint(), 0xFF0000);
                    } else {
                        objectsDisplay.getRenderer().drawLine(point, e.getPoint(), 0xFF0000);
                    }
                }
            }
        });

        window.getCanvas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if (e.getKeyCode() == 67) {
                    objectsDisplay.getPolygon().getPoints().clear();
                    objectsDisplay.refresh();
                }
            }
        });
    }
}
