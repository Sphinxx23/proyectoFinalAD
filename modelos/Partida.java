package app.modelos;

import java.time.LocalDate;
import java.util.concurrent.ThreadLocalRandom;

public class Partida {
    private int id;
    private int id_jugador;
    private int id_videojuego;
    private int tiempo;
    private LocalDate fecha;

    public Partida() {}

    public Partida(int id, int id_jugador, int id_videojuego, int tiempo) {
        this.id = id;
        this.id_jugador = id_jugador;
        this.id_videojuego = id_videojuego;
        this.tiempo = tiempo;
        this.fecha = LocalDate.now();
    }

    public Partida(int id_jugador, int id_videojuego, int tiempo) {
        this.id_jugador = id_jugador;
        this.id_videojuego = id_videojuego;
        this.tiempo = tiempo;
        this.fecha = LocalDate.now();
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public LocalDate getFecha() {
        return fecha;
    }

    public void setFecha(LocalDate fecha) {
        this.fecha = fecha;
    }

    public int getId_jugador() {
        return id_jugador;
    }

    public void setId_jugador(int id_jugador) {
        this.id_jugador = id_jugador;
    }

    public int getId_videojuego() {
        return id_videojuego;
    }

    public void setId_videojuego(int id_videojuego) {
        this.id_videojuego = id_videojuego;
    }

    public int getTiempo() {
        return tiempo;
    }

    public void setTiempo(int tiempo) {
        this.tiempo = tiempo;
    }

    @Override
    public String toString() {
        return "Partida{" +
                "id=" + id +
                ", id_jugador=" + id_jugador +
                ", id_videojuego=" + id_videojuego +
                ", tiempo=" + tiempo +
                ", fecha=" + fecha +
                '}';
    }
}

