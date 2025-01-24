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

    // Ejecutar un método en un controlador dinámicamente
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