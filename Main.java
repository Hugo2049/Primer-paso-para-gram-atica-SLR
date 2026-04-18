import java.io.BufferedReader;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;


public class Main {

    public static void main(String[] args) throws Exception {
        String archivoGramatica = "gramatica.txt";
        String archivoItems = "items.txt";

        // Permitir pasar rutas por argumento (opcional)
        if (args.length >= 1) archivoGramatica = args[0];
        if (args.length >= 2) archivoItems = args[1];

        // 1. Cargar la gramatica
        Gramatica gramatica = new Gramatica(archivoGramatica);

        System.out.println("===========================================");
        System.out.println("GRAMATICA CARGADA DESDE: " + archivoGramatica);
        System.out.println("===========================================");
        for (String[] prod : gramatica.getTodasLasProducciones()) {
            StringBuilder sb = new StringBuilder();
            sb.append(prod[0]).append(" -> ");
            for (int i = 1; i < prod.length; i++) {
                sb.append(prod[i]).append(" ");
            }
            System.out.println("  " + sb.toString().trim());
        }
        System.out.println();

        // 2. Cargar los items iniciales
        List<Item> itemsIniciales = leerItems(archivoItems);

        System.out.println("===========================================");
        System.out.println("ITEMS INICIALES (entrada):");
        System.out.println("===========================================");
        for (Item it : itemsIniciales) {
            System.out.println("  " + it);
        }
        System.out.println();

        // 3. Calcular cerradura
        System.out.println("===========================================");
        Cerradura cerradura = new Cerradura(gramatica);
        Set<Item> resultado = cerradura.calcular(itemsIniciales);

        // 4. Imprimir resultado final
        System.out.println();
        System.out.println("===========================================");
        System.out.println("CERRADURA FINAL:");
        System.out.println("===========================================");
        for (Item it : resultado) {
            System.out.println("  " + it);
        }
        System.out.println("===========================================");
    }

    private static List<Item> leerItems(String archivo) throws Exception {
        List<Item> items = new ArrayList<>();
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;
            items.add(Item.parsear(linea));
        }
        br.close();
        return items;
    }
}