package app.controladores;

import app.dao.PartidaDAO;
import app.modelos.Partida;
import app.modelos.Jugador;
import app.modelos.VideoJuego;

import java.util.List;

public class PartidaControlador {

    public boolean agregarPartida(int id_jug, int id_vid, int tiempo) {
        PartidaDAO dao = new PartidaDAO();
        Partida partida = new Partida( id_jug, id_vid, tiempo);

        return dao.guardar(partida);
    }

    public List<Partida> listarPartidas() {
        PartidaDAO dao = new PartidaDAO();
        return dao.listarTodos();
    }

    public Partida buscarPartidaPorId(int id) {
        PartidaDAO dao = new PartidaDAO();
        return dao.buscarPorId(id);
    }

    public boolean eliminarPartida(int id) {
        PartidaDAO dao = new PartidaDAO();
        return dao.eliminar(id);
    }

}
