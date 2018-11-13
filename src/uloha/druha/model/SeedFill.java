package uloha.druha.model;

import java.awt.image.BufferedImage;

/**
 * Trida implementujici seed fill
 */
public class SeedFill {

    private BufferedImage image;
    /**
     * barva pozadi - zjisti se pri kliknuti
     */
    private int background;
    /**
     * pole pouzitich barev
     */
    private int[] color;
    /**
     * vzor vyplnovane plochy
     */
    private int[][] matrix;

    public SeedFill(BufferedImage image) {
        this.image = image;
        //sachovnice
        /*matrix = new int[][]{
                {1,0},
                {0,1}
        };*/
        //puntiky
        /*matrix = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 0},
                {0, 0, 1, 1, 1, 1, 1, 1, 1, 1, 0, 0},
                {0, 0, 0, 1, 1, 1, 1, 1, 1, 0, 0, 0},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0}
        };*/
        // tribarevne pruhy
        /*matrix = new int[][]{
                {0,0,1,1,2,2},
                {2,0,0,1,1,2},
                {2,2,0,0,1,1},
                {1,2,2,0,0,1},
                {1,1,2,2,0,0},
                {0,1,1,2,2,0}
        };*/
        //napis SEED FILL v ramecku
        matrix = new int[][]{
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 1, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 0, 1, 0, 0, 0, 1, 1, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 1, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1, 0, 0, 1, 0, 0, 0, 1, 0, 0, 0, 0, 1},
                {0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 0, 0, 0, 0, 1, 0, 0, 0, 1, 1, 1, 0, 1, 1, 1, 0, 1, 1, 1, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 1},
                {1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1}
        };
    }

    /**
     * vyplneni plochy pomoci seed fill algoritmu a nastaveni barev
     *
     * @param x     souradnice bodu
     * @param y     souradnice bodu
     * @param color seznam pouzitich barev
     */
    public void fill(int x, int y, int... color) {
        background = image.getRGB(x, y);
        this.color = color.clone();
        fill(x, y);
    }

    /**
     * samotny seed fill algoritmus s vykreslenim vzoru
     * [(x/y) / (n) % (pocet radku/sloupcu)] - n -> zvetseni
     *
     * @param x souradnice bodu
     * @param y souradnice bodu
     */
    private void fill(int x, int y) {
        if (x >= 0 && y >= 0 && x < image.getWidth() && y < image.getHeight()) {
            if (background == image.getRGB(x, y)) {
                int index = matrix[y / 2 % 10][x / 2 % 38];
                image.setRGB(x, y, color[index]);

                fill(x + 1, y);
                fill(x - 1, y);
                fill(x, y + 1);
                fill(x, y - 1);
            }
        }
    }
}
