import java.util.*;

public class ConsolaWordlango {

    public static void main(String[] args) {

        Diccionario dicEsp = new Diccionario();
        dicEsp.cargarArchivo("espanol.txt");

        Diccionario dicIng = new Diccionario();
        dicIng.cargarArchivo("ingles.txt");

        Scanner sc = new Scanner(System.in);

        // 🔹 idioma
        System.out.println("Selecciona idioma:");
        System.out.println("1) Español");
        System.out.println("2) Inglés");

        int idioma = sc.nextInt();
        sc.nextLine();

        // 🔹 dificultad
        System.out.println("Selecciona dificultad:");
        System.out.println("1) 5 letras");
        System.out.println("2) 6 letras");
        System.out.println("3) 7 letras");

        int opcion = sc.nextInt();
        sc.nextLine();

        int longitud = opcion + 4;

        JuegoWordlango juego;
        Diccionario dicActual;

        if (idioma == 1) {
            juego = new JuegoWordlango(dicEsp.getDiccionario(), "Español");
            dicActual = dicEsp;
        } else {
            juego = new JuegoWordlango(dicIng.getDiccionario(), "Inglés");
            dicActual = dicIng;
        }

        juego.iniciarJuego(longitud);

        // 🔹 historial de intentos
        List<String> historial = new ArrayList<>();

        String alfabeto = "abcdefghijklmnopqrstuvwxyz";

        while (!juego.terminoJuego()) {

            System.out.println("\nIntentos restantes: " + juego.getIntentosRestantes());

            // 🔹 mostrar historial
            System.out.println("Intentos:");
            for (String h : historial) {
                System.out.println(h);
            }

            // 🔹 letras usadas
            System.out.println("Usadas: " + new TreeSet<>(juego.getLetrasUsadas()));

            // 🔹 letras no usadas
            System.out.print("No usadas: ");
            for (char c : alfabeto.toCharArray()) {
                if (!juego.getLetrasUsadas().contains(c)) {
                    System.out.print(c + " ");
                }
            }
            System.out.println();

            // 🔹 menú
            System.out.println("\nOpciones:");
            System.out.println("1) Intentar palabra");
            System.out.println("2) Pista vocal");
            System.out.println("3) Pista consonante");

            String opcionMenu = sc.nextLine();

            if (opcionMenu.equals("2")) {
                System.out.println("Vocal: " + juego.darPistaVocal());
                continue;
            }

            if (opcionMenu.equals("3")) {
                System.out.println("Consonante: " + juego.darPistaConsonante());
                continue;
            }

            // 🔹 intento
            System.out.print("Ingresa palabra: ");
            String intento = sc.nextLine();

            if (!juego.validarPalabra(intento)) {

                System.out.println("No está en el diccionario.");
                System.out.print("¿Puedes demostrar que es válida? (s/n): ");

                String op = sc.nextLine();

                if (op.equalsIgnoreCase("s")) {
                    System.out.print("Definición: ");
                    String def = sc.nextLine();

                    dicActual.agregarPalabra(intento, def);
                    System.out.println("Palabra agregada.");
                }

                continue;
            }

            // 🔹 procesar intento
            String resultado = juego.procesarIntento(intento);
            historial.add(resultado);

            if (juego.gano(intento)) {

                System.out.println("\nIntentos:");
                for (String h : historial) {
                    System.out.println(h);
                }

                System.out.println("¡Ganaste!");
                return;
            }
        }

        System.out.println("\nIntentos:");
        historial.forEach(h -> System.out.println(h));

        System.out.println("Perdiste. La palabra era: " + juego.getPalabraSecreta());
    }
}