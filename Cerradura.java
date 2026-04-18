import java.util.ArrayList;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;


public class Cerradura {

    private Gramatica gramatica;

    public Cerradura(Gramatica gramatica) {
        this.gramatica = gramatica;
    }

    /**
     * Calcula la cerradura del conjunto de items dado.
     * Va imprimiendo el proceso paso a paso.
     */
    public Set<Item> calcular(List<Item> itemsIniciales) {
        // LinkedHashSet para conservar el orden de insercion al imprimir
        Set<Item> resultado = new LinkedHashSet<>(itemsIniciales);

        System.out.println("PROCESO:");

        boolean cambio = true;
        int pasada = 1;
        while (cambio) {
            cambio = false;
            // Copiamos a lista para poder iterar sin problema
            List<Item> actuales = new ArrayList<>(resultado);

            for (Item item : actuales) {
                String sig = item.simboloDespuesDelPunto();

                if (sig == null) {
                    System.out.println("  Item " + item + " -> punto al final, no agrega nada");
                    continue;
                }

                if (!gramatica.esNoTerminal(sig)) {
                    System.out.println("  Item " + item + " -> despues del punto hay terminal '" + sig + "', no agrega nada");
                    continue;
                }

                // Es no terminal: agregar todas las producciones de ese no terminal
                System.out.println("  Item " + item + " -> despues del punto hay no terminal '" + sig + "'");
                List<String[]> producciones = gramatica.getProducciones(sig);
                for (String[] prod : producciones) {
                    // prod[0] es el lado izquierdo, el resto es el lado derecho
                    String[] der = new String[prod.length - 1];
                    for (int i = 1; i < prod.length; i++) {
                        der[i - 1] = prod[i];
                    }
                    Item nuevo = new Item(prod[0], der, 0);

                    if (!resultado.contains(nuevo)) {
                        resultado.add(nuevo);
                        System.out.println("      -> agrego " + nuevo);
                        cambio = true;
                    } else {
                        System.out.println("      -> " + nuevo + " ya estaba");
                    }
                }
            }

            if (cambio) {
                System.out.println("  (se agregaron items, reviso de nuevo)");
                pasada++;
            } else {
                System.out.println("  (no se agregaron items nuevos -> FIN)");
            }
        }

        return resultado;
    }
}