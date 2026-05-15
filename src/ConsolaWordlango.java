import java.util.*;

public class ConsolaWordlango {

    public static void main(String[] args) {
        Diccionario dicEsp = new Diccionario();
        dicEsp.cargarArchivo("diccionario_es_categoria.txt");
        Diccionario dicIng = new Diccionario();
        dicIng.cargarArchivo("diccionario_en_categoria.txt");

        Scanner sc = new Scanner(System.in);

        int idioma = 0;

        while (true) {
            System.out.println("Selecciona idioma:");
            System.out.println("1) Español");
            System.out.println("2) Inglés");

            if (sc.hasNextInt()) {
                idioma = sc.nextInt();
                sc.nextLine();

                if (idioma == 1 || idioma == 2) break;
            } else {
                sc.nextLine();
            }

            System.out.println("Entrada inválida. Debes elegir 1 o 2.\n");
        }

        int opcion = 0;
        while (true) {
            System.out.println("Selecciona dificultad:");
            System.out.println("1) 5 letras");
            System.out.println("2) 6 letras");
            System.out.println("3) 7 letras");
            if (sc.hasNextInt()) {
                opcion = sc.nextInt();
                sc.nextLine();
                if (opcion >= 1 && opcion <= 3) break;
            } else {
                sc.nextLine();
            }
            System.out.println("Entrada inválida. Debes elegir 1, 2 o 3.\n");
        }
        int longitud = opcion + 4;
        int intentos = 0;
        while (true) {
            System.out.print("¿Cuántos intentos quieres? (6-8): ");
            if (sc.hasNextInt()) {
                intentos = sc.nextInt();
                sc.nextLine();
                if (intentos >= 6 && intentos <= 8) break;
            } else {
                sc.nextLine();
            }
            System.out.println("Entrada inválida. Debe ser un número entre 6 y 8.\n");
        }
        JuegoWordlango juego;
        Diccionario dicActual;
        String archivoActual;
        if (idioma == 1) {
            juego = new JuegoWordlango(dicEsp.getDiccionario(), "Español");
            dicActual = dicEsp;
            archivoActual = "espanol.txt";
        } else {
            juego = new JuegoWordlango(dicIng.getDiccionario(), "Inglés");
            dicActual = dicIng;
            archivoActual = "ingles.txt";
        }
        juego.iniciarJuego(longitud, intentos);

        System.out.println("\nReglas:");
        System.out.println("[letra] = correcta posición");
        System.out.println("(letra) = existe pero en otra posición");
        System.out.println(" letra  = no existe\n");

        List<String> historial = new ArrayList<>();
        String alfabeto = "abcdefghijklmnopqrstuvwxyz";

        while (!juego.terminoJuego()) {

            System.out.println("\nIntentos restantes: " + juego.getIntentosRestantes());

            System.out.println("Intentos:");
            historial.forEach(System.out::println);

            System.out.println("Usadas: " + new TreeSet<>(juego.getLetrasUsadas()));

            System.out.print("No usadas: ");
            alfabeto.chars()
                    .mapToObj(c -> (char) c)
                    .filter(c -> !juego.getLetrasUsadas().contains(c))
                    .forEach(c -> System.out.print(c + " "));
            System.out.println();

            System.out.println("\nOpciones:");
            System.out.println("1) Intentar palabra");
            System.out.println("2) Pista vocal");
            System.out.println("3) Pista consonante");
            System.out.println("4) Pista definición");

            String opcionMenu = sc.nextLine();

            if (!opcionMenu.matches("[1-4]")) {
                System.out.println("Opción inválida. Elige entre 1 y 4.");
                continue;
            }

            if (opcionMenu.equals("2")) {
                System.out.println("Vocal: " + juego.darPistaVocal());
                continue;
            }

            if (opcionMenu.equals("3")) {
                System.out.println("Consonante: " + juego.darPistaConsonante());
                continue;
            }

            if (opcionMenu.equals("4")) {
                System.out.println("Definición: " + juego.darPistaDefinicion());
                continue;
            }

            // 🔹 intento seguro
            System.out.print("Ingresa palabra: ");
            String intento = sc.nextLine();

            if (!juego.longitudValida(intento)) {
                System.out.println("La palabra debe tener "
                        + juego.getLongitudPalabra() + " letras.");
                continue;
            }

            if (!juego.validarPalabra(intento)) {

                System.out.println("No está en el diccionario.");
                System.out.print("¿Puedes demostrar que es válida? (s/n): ");

                String op = sc.nextLine();

                if (op.equalsIgnoreCase("s")) {
                    System.out.print("Definición: ");
                    String def = sc.nextLine();

                    dicActual.agregarPalabra(intento, def);
                    dicActual.guardarEnArchivo(archivoActual, intento, def);

                    System.out.println("Palabra agregada al diccionario de "
                            + juego.getIdioma());
                }
                continue;
            }

            String resultado = juego.procesarIntento(intento);
            historial.add(resultado);

            if (juego.gano(intento)) {
                System.out.println("\nIntentos:");
                historial.forEach(System.out::println);

                System.out.println("¡Ganaste!");
                return;
            }
        }

        System.out.println("\nIntentos:");
        historial.forEach(System.out::println);

        System.out.println("Perdiste. La palabra era: "
                + juego.getPalabraSecreta());
    }
}