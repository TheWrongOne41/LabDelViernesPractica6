import java.util.*;

public class JuegoWordlango {

    private String palabraSecreta;
    private int intentosMaximos;
    private int intentosActuales;

    private HashSet<Character> letrasUsadas;
    private HashMap<String, Integer> diccionario;

    private Random random;
    private String idioma; // 🔹 NUEVO

    public JuegoWordlango(HashMap<String, Integer> diccionario, String idioma) {
        this.diccionario = diccionario;
        this.idioma = idioma;
        this.letrasUsadas = new HashSet<>();
        this.random = new Random();
    }

    public void iniciarJuego(int longitud) {

        intentosActuales = 0;
        letrasUsadas.clear();

        if (longitud == 5) intentosMaximos = 6;
        else if (longitud == 6) intentosMaximos = 7;
        else if (longitud == 7) intentosMaximos = 8;

        palabraSecreta = obtenerPalabraAleatoria(longitud);
    }

    private String obtenerPalabraAleatoria(int longitud) {

        List<String> lista = new ArrayList<>();

        Iterator<String> it = diccionario.keySet().iterator();

        while (it.hasNext()) {
            String palabra = it.next();
            if (palabra.length() == longitud) {
                lista.add(palabra);
            }
        }

        return lista.get(random.nextInt(lista.size()));
    }

    public boolean validarPalabra(String palabra) {
        palabra = palabra.toLowerCase();
        return diccionario.containsKey(palabra);
    }

    public String procesarIntento(String intento) {

        intento = intento.toLowerCase();
        intentosActuales++;

        StringBuilder resultado = new StringBuilder();

        for (int i = 0; i < intento.length(); i++) {

            char letra = intento.charAt(i);
            letrasUsadas.add(letra);

            if (letra == palabraSecreta.charAt(i)) {
                resultado.append("[" + letra + "]");
            } else if (palabraSecreta.contains(String.valueOf(letra))) {
                resultado.append("(" + letra + ")");
            } else {
                resultado.append(" " + letra + " ");
            }
        }

        return resultado.toString();
    }

    public char darPistaVocal() {
        for (char c : palabraSecreta.toCharArray()) {
            if ("aeiou".indexOf(c) != -1) return c;
        }
        return '?';
    }

    public char darPistaConsonante() {
        for (char c : palabraSecreta.toCharArray()) {
            if ("aeiou".indexOf(c) == -1) return c;
        }
        return '?';
    }

    public boolean gano(String intento) {
        return intento.equals(palabraSecreta);
    }

    public boolean terminoJuego() {
        return intentosActuales >= intentosMaximos;
    }

    public int getIntentosRestantes() {
        return intentosMaximos - intentosActuales;
    }

    public Set<Character> getLetrasUsadas() {
        return letrasUsadas;
    }

    public String getPalabraSecreta() {
        return palabraSecreta;
    }

    public String getIdioma() { // 🔹 opcional
        return idioma;
    }
}