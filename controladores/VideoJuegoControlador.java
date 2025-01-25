package app.controladores;

import app.dao.VideoJuegoDAO;
import app.modelos.VideoJuego;

import java.util.List;

public class VideoJuegoControlador {

    public Boolean agregarVideojuego(String titulo, String genero, double precio) {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        VideoJuego videojuego = new VideoJuego(titulo, genero, precio);

        return dao.guardar(videojuego);
    }

    public Boolean editarVideojuego(int id, String titulo, String genero, double precio){
        VideoJuegoDAO dao = new VideoJuegoDAO();
        VideoJuego videojuego = new VideoJuego(id, titulo, genero, precio);

        return dao.editarVideojuego(videojuego);
    }

    public List<VideoJuego> listarVideojuegos() {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        return dao.listarTodos();
    }

    public VideoJuego buscarVideojuegoPorId(int id) {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        return dao.buscarPorId(id);
    }

    public boolean eliminarVideojuego(int id) {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        return dao.eliminar(id);
    }

    public List<VideoJuego> listarVideojuegosDeJugadorID(int id_jug) {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        return dao.listarVideojuegosDeJugadorID(id_jug);
    }

    public List<VideoJuego> listarDeMasCaroAMasBarato() {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        return dao.listarDeMasCaroAMasBarato();
    }

    public List<VideoJuego> listarPorGenero(String genero) {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        return dao.listarPorGenero(genero);
    }

    public List<Object[]> listarEstadisticasVideojuegosHoras() {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        return dao.listarEstadisticasVideojuegosHoras();
    }

    public List<Object[]> listarEstadisticasVideojuegosJugadoresTotales() {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        return dao.listarEstadisticasVideojuegosJugadoresTotales();
    }
}