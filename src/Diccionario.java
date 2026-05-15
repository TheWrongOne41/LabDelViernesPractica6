import java.io.*;
import java.text.Normalizer;
import java.util.HashMap;

public class Diccionario {

    private HashMap<String, String> diccionario;

    public Diccionario() {
        diccionario = new HashMap<>();
    }

    public void cargarArchivo(String ruta) {

        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                if (!linea.contains(":")) continue;

                String[] partes = linea.split(":", 2);

                String palabrasParte = partes[0];
                String definicion = partes[1].trim();

                String[] palabras = palabrasParte.split("\\|");

                java.util.Arrays.stream(palabras)
                        .map(p -> quitarAcentos(p.trim().toLowerCase()))
                        .filter(p -> p.length() >= 5 && p.length() <= 7)
                        .forEach(p -> diccionario.put(p, definicion));
            }

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }
    }

    private String quitarAcentos(String texto) {
        texto = Normalizer.normalize(texto, Normalizer.Form.NFD);
        return texto.replaceAll("\\p{InCombiningDiacriticalMarks}+", "");
    }

    public boolean existePalabra(String palabra) {
        palabra = quitarAcentos(palabra.toLowerCase());
        return diccionario.containsKey(palabra);
    }

    public String getDefinicion(String palabra) {
        palabra = quitarAcentos(palabra.toLowerCase());
        return diccionario.getOrDefault(palabra, "Sin definición");
    }

    public HashMap<String, String> getDiccionario() {
        return diccionario;
    }

    public void guardarEnArchivo(String ruta, String palabra, String definicion) {
        try (FileWriter fw = new FileWriter(ruta, true);
             BufferedWriter bw = new BufferedWriter(fw)) {
            bw.write(palabra + ": " + definicion);
            bw.newLine();

        } catch (IOException e) {
            System.out.println("Error guardando palabra: " + e.getMessage());
        }
    }

    public void agregarPalabra(String palabra, String definicion) {

        palabra = quitarAcentos(palabra.toLowerCase());

        if (palabra.length() >= 5 && palabra.length() <= 7) {
            diccionario.put(palabra, definicion);
        }
    }
}