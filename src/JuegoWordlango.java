import java.util.*;

public class JuegoWordlango {

    private String palabraSecreta;
    private int intentosMaximos;
    private int intentosActuales;
    private int longitudPalabra;

    private HashSet<Character> letrasUsadas;
    private HashMap<String, String> diccionario;

    private Random random;
    private String idioma;

    public JuegoWordlango(HashMap<String, String> diccionario, String idioma) {
        this.diccionario = diccionario;
        this.idioma = idioma;
        this.letrasUsadas = new HashSet<>();
        this.random = new Random();
    }

    public void iniciarJuego(int longitud, int intentos) {

        intentosActuales = 0;
        letrasUsadas.clear();

        this.longitudPalabra = longitud;
        if (intentos < 6 || intentos > 8) {
            throw new IllegalArgumentException("Los intentos deben estar entre 6 y 8.");
        }
        this.intentosMaximos = intentos;

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

        if (lista.isEmpty()) {
            throw new RuntimeException("No hay palabras de esa longitud.");
        }

        return lista.get(random.nextInt(lista.size()));
    }

    public boolean validarPalabra(String palabra) {
        return diccionario.containsKey(palabra.toLowerCase());
    }

    public boolean longitudValida(String intento) {
        return intento.length() == longitudPalabra;
    }

    public String procesarIntento(String intento) {

        intento = intento.toLowerCase();
        intentosActuales++;

        StringBuilder resultado = new StringBuilder();

        // 🔹 conteo de letras en palabra secreta
        HashMap<Character, Integer> conteo = new HashMap<>();

        for (char c : palabraSecreta.toCharArray()) {
            conteo.put(c, conteo.getOrDefault(c, 0) + 1);
        }

        char[] res = new char[intento.length() * 3];
        int idx = 0;
        for (int i = 0; i < intento.length(); i++) {

            char letra = intento.charAt(i);
            letrasUsadas.add(letra);

            if (letra == palabraSecreta.charAt(i)) {

                resultado.append("[" + letra + "]");
                conteo.put(letra, conteo.get(letra) - 1);

            } else {
                resultado.append("_"); // marcador temporal
            }
        }
        String resultadoFinal = "";
        int pos = 0;

        for (int i = 0; i < intento.length(); i++) {

            char letra = intento.charAt(i);

            if (letra == palabraSecreta.charAt(i)) {
                resultadoFinal += "[" + letra + "]";
            } else if (conteo.getOrDefault(letra, 0) > 0) {

                resultadoFinal += "(" + letra + ")";
                conteo.put(letra, conteo.get(letra) - 1);

            } else {
                resultadoFinal += " " + letra + " ";
            }
        }

        return resultadoFinal;
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

    public int getLongitudPalabra() {
        return longitudPalabra;
    }

    public String getIdioma() {
        return idioma;
    }
    public String darPistaDefinicion() {
        return diccionario.getOrDefault(palabraSecreta,
                "Sin definición disponible");
    }
}