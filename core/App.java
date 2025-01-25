package app.core;

import app.controladores.JugadorControlador;
import app.controladores.PartidaControlador;
import app.controladores.VideoJuegoControlador;
import app.vistas.VistaConsolaRouter;
import app.vistas.VistaSwingRouter;
import javax.swing.JOptionPane;

public class App {
    public static void main(String[] args) {
        iniciar();
    }
    public static void iniciar() {
        Router router = new Router();

        // Registrar rutas
        router.registrarRuta("jugadores", new JugadorControlador());
        router.registrarRuta("videojuegos", new VideoJuegoControlador());
        router.registrarRuta("partidas", new PartidaControlador());

        // Mostrar JOptionPane para seleccionar la vista
        String[] opciones = {"Vista Swing", "Vista Consola"};
        int seleccion = JOptionPane.showOptionDialog(
                null,
                "¿Qué vista desea abrir?",
                "Seleccionar Vista",
                JOptionPane.DEFAULT_OPTION,
                JOptionPane.QUESTION_MESSAGE,
                null,
                opciones,
                opciones[0] // opción predeterminada
        );

        // Abrir la vista seleccionada
        if (seleccion == 0) {
            // Si selecciona "Vista Swing"
            VistaSwingRouter vista = new VistaSwingRouter(router);
            vista.mostrarMenu();
        } else if (seleccion == 1) {
            // Si selecciona "Vista Consola"
            VistaConsolaRouter vista = new VistaConsolaRouter(router);
            vista.mostrarMenu();
        }
    }
}
