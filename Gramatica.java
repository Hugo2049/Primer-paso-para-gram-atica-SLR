import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class Gramatica {

    private List<String[]> producciones;
    private Set<String> noTerminales;

    public Gramatica(String archivo) throws IOException {
        this.producciones = new ArrayList<>();
        this.noTerminales = new HashSet<>();
        cargarDesdeArchivo(archivo);
    }

    private void cargarDesdeArchivo(String archivo) throws IOException {
        BufferedReader br = new BufferedReader(new FileReader(archivo));
        String linea;
        while ((linea = br.readLine()) != null) {
            linea = linea.trim();
            if (linea.isEmpty()) continue;

            String[] partes = linea.split("->");
            if (partes.length != 2) continue;

            String izq = partes[0].trim();
            String der = partes[1].trim();

            String[] simbolosDer = der.split("\\s+");

            String[] produccion = new String[simbolosDer.length + 1];
            produccion[0] = izq;
            for (int i = 0; i < simbolosDer.length; i++) {
                produccion[i + 1] = simbolosDer[i];
            }

            producciones.add(produccion);
            noTerminales.add(izq);
        }
        br.close();
    }

    public List<String[]> getProducciones(String noTerminal) {
        List<String[]> resultado = new ArrayList<>();
        for (String[] prod : producciones) {
            if (prod[0].equals(noTerminal)) {
                resultado.add(prod);
            }
        }
        return resultado;
    }

    public boolean esNoTerminal(String simbolo) {
        return noTerminales.contains(simbolo);
    }

    public List<String[]> getTodasLasProducciones() {
        return producciones;
    }
}