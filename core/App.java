package app.core;

import app.controladores.JugadorControlador;
import app.controladores.PartidaControlador;
import app.controladores.VideoJuegoControlador;
import app.vistas.VistaConsolaRouter;
import app.vistas.VistaSwingRouter;


public class App {
    public void iniciar() {
        Router router = new Router();

        // Registrar rutas
        router.registrarRuta("jugadores", new JugadorControlador());
        router.registrarRuta("videojuegos", new VideoJuegoControlador());
        router.registrarRuta("partidas", new PartidaControlador());

        // Iniciar vista con el router
        //VistaSwingRouter vista = new VistaSwingRouter(router);
        VistaConsolaRouter vista = new VistaConsolaRouter(router);
        vista.mostrarMenu();
    }
}
