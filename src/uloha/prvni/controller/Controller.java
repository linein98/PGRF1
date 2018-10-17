package uloha.prvni.controller;

import uloha.prvni.model.*;
import uloha.prvni.model.Polygon;
import uloha.prvni.view.Window;

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

    /**
     * Reference na View
     */
    private Window window;
    /**
     * Reference na Model
     */
    private ObjectsDisplay objectsDisplay;
    /**
     * Pocatecni bod pri tazeni mysi - pouziva se pri vykreslovani usecky a mnohouhelniku
     */
    private Point point;
    /**
     * Pomocna promenna pro vykreslovani pravidelneho mnohouhelniku
     */
    private int click = 0;
    /**
     * Bod stredu pravidelneho mnohouhelniku
     */
    private Point p1;
    /**
     * Bod vzdaleny od stredu pravidelneho mnohouhelniku - urcuje polomer a polohu jednoho z vrcholu
     */
    private Point p2;

    /**
     * Obnovovaci frekvence
     */
    private static final int frameRate = 1000 / 30;

    public Controller(Window window, ObjectsDisplay objectsDisplay) {
        this.window = window;
        this.objectsDisplay = objectsDisplay;
        objectsDisplay.setObjectName(ObjectType.Line);
        startRefreshingCanvas();
        setActions();
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
                point = e.getPoint();
                window.getLblXY().setText("x: " + e.getX() + ", y: " + e.getY());
            }

            @Override
            public void mouseReleased(MouseEvent e) {
                switch (objectsDisplay.getObjectName()) {
                    case Line:
                        objectsDisplay.addObject(new Line(point, e.getPoint()));
                        objectsDisplay.refresh();
                        break;
                    case Polygon:
                        if (objectsDisplay.isPolygonLast()) {
                            objectsDisplay.getLastPolygon().addPoint(e.getPoint());
                        } else {
                            objectsDisplay.addObject(new Polygon(point, e.getPoint()));
                        }
                        objectsDisplay.refresh();
                        break;
                }
            }

            @Override
            public void mouseClicked(MouseEvent e) {
                if (objectsDisplay.getObjectName() == ObjectType.RegularPolygon) {
                    switch (click) {
                        case 0:
                            p1 = e.getPoint();
                            click = 1;
                            break;
                        case 1:
                            p2 = e.getPoint();
                            click = 2;
                            break;
                        case 3:
                            objectsDisplay.addObject(new RegularPolygon(p1, p2, RegularPolygon.getCount(e.getPoint(), p1, p2)));
                            click = 0;
                            objectsDisplay.refresh();
                            break;
                    }
                }
            }
        });

        window.getCanvas().addMouseMotionListener(new MouseMotionAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                if (objectsDisplay.getObjectName() == ObjectType.RegularPolygon) {
                    switch (click) {
                        case 1:
                            objectsDisplay.refresh();
                            objectsDisplay.getRenderer().drawLine(p1, e.getPoint(), 0xFF0000);
                            objectsDisplay.render(RegularPolygon.calculatePoints(p1, e.getPoint(), 300));
                            break;
                        case 2:
                            objectsDisplay.refresh();
                            objectsDisplay.getRenderer().drawLine(p1, p2, 0xFF0000);
                            objectsDisplay.render(RegularPolygon.calculatePoints(p1, p2, 300));
                            click = 3;
                            break;
                        case 3:
                            objectsDisplay.refresh();
                            objectsDisplay.getRenderer().drawLine(p1, p2, 0xFF0000);
                            objectsDisplay.render(RegularPolygon.calculatePoints(p1, p2, RegularPolygon.getCount(e.getPoint(), p1, p2)));
                            break;
                    }
                }
            }

            @Override
            public void mouseDragged(MouseEvent e) {
                switch (objectsDisplay.getObjectName()) {
                    case Line:
                        objectsDisplay.refresh();
                        objectsDisplay.getRenderer().drawLine(point, e.getPoint(), 0xFF0000);
                        break;
                    case Polygon:
                        objectsDisplay.refresh();
                        if (objectsDisplay.isPolygonLast()) {
                            Point point1 = objectsDisplay.getLastPolygon().getPoint(true);
                            Point point2 = objectsDisplay.getLastPolygon().getPoint(false);
                            objectsDisplay.getRenderer().drawLine(point1, e.getPoint(), 0xFF0000);
                            objectsDisplay.getRenderer().drawLine(point2, e.getPoint(), 0xFF0000);
                        } else {
                            objectsDisplay.getRenderer().drawLine(point, e.getPoint(), 0xFF0000);
                        }
                        break;
                }
                window.getLblXY().setText("x: " + e.getX() + ", y: " + e.getY());
            }
        });

        window.getCanvas().addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                switch (e.getKeyCode()) {
                    case 49:
                        objectsDisplay.setObjectName(ObjectType.Line);
                        window.getLblSelected().setText(ObjectType.Line.getName());
                        break;
                    case 50:
                        objectsDisplay.setObjectName(ObjectType.Polygon);
                        window.getLblSelected().setText(ObjectType.Polygon.getName());
                        break;
                    case 51:
                        objectsDisplay.setObjectName(ObjectType.RegularPolygon);
                        window.getLblSelected().setText(ObjectType.RegularPolygon.getName());
                        click = 0;
                        break;
                    case 67:
                        objectsDisplay.delete();
                        window.getLblSelected().setText("Smazáno");
                        break;
                    case 65:
                        if(objectsDisplay.getRenderer() instanceof XWLineRenderer) {
                            objectsDisplay.setRenderer(new DDARenderer(objectsDisplay.getImage()));
                            window.getLblSelected().setText("DDA");
                        } else {
                            objectsDisplay.setRenderer(new XWLineRenderer(objectsDisplay.getImage()));
                            window.getLblSelected().setText("Antialiasing");
                        }
                        objectsDisplay.refresh();
                        break;
                }
            }
        });
    }
}
