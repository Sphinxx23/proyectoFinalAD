package app.utilidades;

public class Validador {

    public static boolean esNumero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    public static boolean esStringVacio(String cadena) {
        return cadena.isEmpty();
    }

    public static boolean esDouble(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
