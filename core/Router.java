package app.core;

import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

public class Router {
    private final Map<String, Object> rutas = new HashMap<>();

    // Registrar una ruta con un controlador asociado
    public void registrarRuta(String nombre, Object controlador) {
        rutas.put(nombre, controlador);
    }

    // Obtener un controlador según su ruta
    public Object obtenerRuta(String nombre) {
        return rutas.get(nombre);
    }

    /**
     * Método que ejecuta una acción en un controlador basado en la ruta y el nombre de la acción proporcionados.
     *
     * @param ruta la ruta del controlador.
     * @param accion el nombre de la acción a ejecutar.
     * @param parametros los parámetros a pasar al método de la acción.
     * @return el resultado de la ejecución del método de la acción, o null si ocurre un error.
     */
    public Object ejecutarAccion(String ruta, String accion, Object... parametros) {
        Object controlador = obtenerRuta(ruta);

        if (controlador == null) {
            System.err.println("Ruta no encontrada: " + ruta);
            return null;
        }

        try {
            // Obtener el método en el controlador
            Method metodo = controlador.getClass().getMethod(accion, convertirParametros(parametros));
            return metodo.invoke(controlador, parametros); // Invocar el método
        } catch (NoSuchMethodException e) {
            System.err.println("Método no encontrado: " + accion);
            System.err.println("Controlador: " + controlador.getClass().getName());
            System.err.println("Parámetros esperados: " + java.util.Arrays.toString(convertirParametros(parametros)));
        } catch (Exception e) {
            System.err.println("Error al ejecutar la acción: " + e.getMessage());
        }
        return null;
    }

    /**
     * Método que convierte un arreglo de objetos en un arreglo de clases correspondientes, manejando correctamente las primitivas.
     *
     * @param parametros el arreglo de objetos a convertir.
     * @return un arreglo de clases correspondientes a los objetos.
     */
    private Class<?>[] convertirParametros(Object[] parametros) {
        if (parametros == null) {
            return new Class<?>[0];
        }

        // Mapear cada parámetro a su clase, manejando primitivas correctamente
        return java.util.Arrays.stream(parametros)
                .map(parametro -> {
                    if (parametro instanceof Integer) return int.class; // Convertir Integer a int
                    if (parametro instanceof Double) return double.class; // Convertir Double a double
                    if (parametro instanceof Float) return float.class; // Convertir Float a float
                    if (parametro instanceof Boolean) return boolean.class; // Convertir Boolean a boolean
                    if (parametro instanceof Long) return long.class; // Convertir Long a long
                    return parametro.getClass();
                })
                .toArray(Class<?>[]::new);
    }

}