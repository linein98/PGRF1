package uloha.prvni.model;

/**
 * Vyctovy typ usnadnujici praci pri vykreslovani jednotlivich druhu objektu
 *
 * @author Tomas Brabec
 */
public enum ObjectType {
    Line(49, "Úsečka"),
    Polygon(50, "Mnohoúhelník"),
    RegularPolygon(51, "Pravidelný mnohoúhelník");

    /**
     * kod klavesy na virtualni klavesnici
     */
    private final int key;
    /**
     * nazev objektu
     */
    private final String name;

    ObjectType(int key, String name) {
        this.key = key;
        this.name = name;
    }

    public String getName() {
        return name;
    }
}
