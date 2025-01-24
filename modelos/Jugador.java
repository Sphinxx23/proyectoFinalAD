package app.modelos;

public class Jugador {
    private int id;
    private String nombre;
    private int nivel;
    private int puntuacion;

    public Jugador() {}

    public Jugador(int id, String nombre, int nivel, int puntuacion) {
        this.id = id;
        this.nombre = nombre;
        this.nivel = nivel;
        this.puntuacion = puntuacion;
    }

    public Jugador(String nombre, int nivel, int puntuacion) {
        this.nombre = nombre;
        this.nivel = nivel;
        this.puntuacion = puntuacion;
    }

    // Getters y Setters
    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getNombre() {
        return nombre;
    }

    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    public int getNivel() {
        return nivel;
    }

    public void setNivel(int nivel) {
        this.nivel = nivel;
    }

    public int getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(int puntuacion) {
        this.puntuacion = puntuacion;
    }

    @Override
    public String toString() {
        return "Jugador{" +
                "id=" + id +
                ", nombre='" + nombre + '\'' +
                ", nivel=" + nivel +
                ", puntuacion=" + puntuacion +
                '}';
    }
}
