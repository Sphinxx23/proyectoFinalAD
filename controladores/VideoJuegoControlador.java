package app.controladores;

import app.dao.DatabaseConfig;
import app.dao.VideoJuegoDAO;
import app.modelos.VideoJuego;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class VideoJuegoControlador {

    public Boolean agregarVideojuego(String titulo, String genero, double precio) {
        VideoJuegoDAO dao = new VideoJuegoDAO();
        VideoJuego videojuego = new VideoJuego(titulo, genero, precio);

        return dao.guardar(videojuego);
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
}