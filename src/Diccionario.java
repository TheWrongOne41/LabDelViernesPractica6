import java.io.*;
import java.text.Normalizer;
import java.util.HashMap;
import java.util.Arrays;

public class Diccionario {

    private HashMap<String, Integer> diccionario;

    public Diccionario() {
        diccionario = new HashMap<>();
    }

    public void cargarArchivo(String ruta) {
        try (BufferedReader br = new BufferedReader(new FileReader(ruta))) {

            String linea;

            while ((linea = br.readLine()) != null) {

                String[] palabras = linea.split("[\\s|]+");

                Arrays.stream(palabras)
                        .map(p -> quitarAcentos(p.trim().toLowerCase()))
                        .filter(p -> p.length() >= 5 && p.length() <= 7)
                        .forEach(p -> diccionario.put(p, p.length()));
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
    public HashMap<String, Integer> getDiccionario() {
        return diccionario;
    }
    public void agregarPalabra(String palabra, String definicion) {
        palabra = palabra.toLowerCase().trim();
        palabra = quitarAcentos(palabra);

        if (palabra.length() >= 5 && palabra.length() <= 7) {
            diccionario.put(palabra, palabra.length());
        }
    }
}