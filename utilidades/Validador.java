package app.utilidades;

public class Validador {

    /**
     * Método que valida si una cadena de texto puede convertirse en un número entero.
     *
     * @param numero la cadena de texto a validar.
     * @return true si la cadena puede convertirse en un número entero, false en caso contrario.
     */
    public static boolean esNumero(String numero) {
        try {
            Integer.parseInt(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * Método que valida si una cadena de texto está vacía.
     *
     * @param cadena la cadena de texto a validar.
     * @return true si la cadena está vacía, false en caso contrario.
     */
    public static boolean esStringVacio(String cadena) {
        return cadena.isEmpty();
    }

    /**
     * Método que valida si una cadena de texto puede convertirse en un número en formato double.
     *
     * @param numero la cadena de texto a validar.
     * @return true si la cadena puede convertirse en un número en formato double, false en caso contrario.
     */
    public static boolean esDouble(String numero) {
        try {
            Double.parseDouble(numero);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }
}
