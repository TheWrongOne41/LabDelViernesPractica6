public class Main {
    public static void main(String[] args) {

        Diccionario dic = new Diccionario();

        dic.cargarArchivo("espanol.txt");

        System.out.println(dic.existePalabra("abeja"));
    }
}
