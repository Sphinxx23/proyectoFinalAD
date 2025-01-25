package app.controladores;

import app.dao.JugadorDAO;
import app.modelos.Jugador;

import java.util.ArrayList;
import java.util.List;

public class JugadorControlador {

    public boolean agregarJugador(String nombre, int nivel, int puntuacion) {
        JugadorDAO dao = new JugadorDAO();
        Jugador jugador = new Jugador(nombre, nivel, puntuacion);

        return dao.guardar(jugador);

    }

    public boolean editarJugador(int id, String nombre, int nivel, int puntuacion) {
        JugadorDAO dao = new JugadorDAO();
        Jugador jugador = new Jugador(id, nombre, nivel, puntuacion);

        return dao.editarJugador(jugador);
    }

    public List<Jugador> listarJugadores() {
        JugadorDAO dao = new JugadorDAO();
        return dao.listarTodos();
    }


    public Jugador buscarJugadorPorId(int id) {
        JugadorDAO dao = new JugadorDAO();
        return dao.buscarPorId(id);
    }

    public boolean eliminarJugador(int id) {
        JugadorDAO dao = new JugadorDAO();
        return dao.eliminar(id);
    }

    public Jugador buscarJugadorPorNombre(String nombre) {
        JugadorDAO dao = new JugadorDAO();
        return dao.buscarPorNombre(nombre);
    }

    public List<Object[]> listarJugadoresMayorPuntuacion() {
        JugadorDAO dao = new JugadorDAO();
        return dao.listarJugadoresMayorPuntuacion();
    }

    public List<Object[]> listarJugadoresMasHoras() {
        JugadorDAO dao = new JugadorDAO();
        return dao.listarJugadoresMasHoras();
    }
}
