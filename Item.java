import java.util.Arrays;

/**
 * Representa un item LR(0) de la forma:
 *   [ A -> alpha . beta ]
 *
 * Internamente guarda:
 *   - lado izquierdo (no terminal)
 *   - lado derecho como array de simbolos
 *   - posicion del punto (indice dentro del array derecho)
 *     posicion 0 = punto antes de todo
 *     posicion = length = punto al final
 */
public class Item {

    private String izq;           // lado izquierdo (ej: "E")
    private String[] der;         // lado derecho separado en simbolos
    private int posPunto;         // donde esta el punto

    public Item(String izq, String[] der, int posPunto) {
        this.izq = izq;
        this.der = der;
        this.posPunto = posPunto;
    }

    /**
     * Parsea un item escrito como "E -> E + .T" y lo convierte en objeto Item.
     * El punto "." debe estar pegado al simbolo que le sigue (o solo si esta al final).
     */
    public static Item parsear(String linea) {
        linea = linea.trim();
        String[] partes = linea.split("->");
        String izq = partes[0].trim();
        String der = partes[1].trim();

        String[] tokens = der.split("\\s+");
        // Buscar el token que contiene el punto
        int pos = -1;
        String[] simbolos;

        // Si el unico token es "." entonces es un item vacio con punto al final (S -> .)
        if (tokens.length == 1 && tokens[0].equals(".")) {
            return new Item(izq, new String[0], 0);
        }

        // Contamos cuantos simbolos reales hay (sin contar el ".")
        int cuenta = 0;
        for (String t : tokens) {
            if (!t.equals(".") && !t.startsWith(".")) cuenta++;
            else if (t.startsWith(".") && t.length() > 1) cuenta++;
            // si es solo "." no suma (el punto esta solo entre simbolos)
        }

        simbolos = new String[cuenta];
        int idx = 0;
        for (int i = 0; i < tokens.length; i++) {
            String t = tokens[i];
            if (t.equals(".")) {
                // el punto esta aqui, antes del siguiente simbolo
                pos = idx;
            } else if (t.startsWith(".")) {
                // el punto esta pegado al simbolo, ej: ".T"
                pos = idx;
                simbolos[idx] = t.substring(1);
                idx++;
            } else {
                simbolos[idx] = t;
                idx++;
            }
        }

        // Si no encontramos el punto, esta al final
        if (pos == -1) pos = cuenta;

        return new Item(izq, simbolos, pos);
    }

    /**
     * Devuelve el simbolo inmediatamente despues del punto, o null si el punto
     * esta al final.
     */
    public String simboloDespuesDelPunto() {
        if (posPunto >= der.length) return null;
        return der[posPunto];
    }

    public String getIzq() { return izq; }
    public String[] getDer() { return der; }
    public int getPosPunto() { return posPunto; }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(izq).append(" -> ");
        for (int i = 0; i < der.length; i++) {
            if (i == posPunto) sb.append(". ");
            sb.append(der[i]).append(" ");
        }
        if (posPunto == der.length) sb.append(".");
        return sb.toString().trim();
    }

    @Override
    public boolean equals(Object o) {
        if (!(o instanceof Item)) return false;
        Item otro = (Item) o;
        return this.izq.equals(otro.izq)
                && Arrays.equals(this.der, otro.der)
                && this.posPunto == otro.posPunto;
    }

    @Override
    public int hashCode() {
        int h = izq.hashCode();
        h = 31 * h + Arrays.hashCode(der);
        h = 31 * h + posPunto;
        return h;
    }
}