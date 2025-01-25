package app.dao;

import app.modelos.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO implements DAO<Jugador> {

    @Override
    /**
     * Método que guarda un jugador en la base de datos.
     *
     * @param jugador el objeto Jugador que se va a guardar.
     * @return true si el jugador se guarda correctamente, false en caso contrario.
     */
    public boolean guardar(Jugador jugador) {
        String sql = "INSERT INTO jugador (nombre, nivel, puntuacion) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, jugador.getNombre());
            stmt.setInt(2, jugador.getNivel());
            stmt.setInt(3, jugador.getPuntuacion());
            stmt.executeUpdate();

            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método que actualiza los datos de un jugador en la base de datos.
     *
     * @param jugador el objeto Jugador con los datos actualizados.
     * @return true si el jugador se actualiza correctamente, false en caso contrario.
     */
    public boolean editarJugador(Jugador jugador) {
        String sql = "UPDATE jugador SET nombre = ?, nivel = ?, puntuacion = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, jugador.getNombre());
            stmt.setInt(2, jugador.getNivel());
            stmt.setInt(3, jugador.getPuntuacion());
            stmt.setInt(4, jugador.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


    @Override
    /**
     * Método que busca un jugador en la base de datos por su ID.
     *
     * @param id el ID del jugador a buscar.
     * @return el objeto Jugador si se encuentra, null en caso contrario.
     */
    public Jugador buscarPorId(int id) {
        String sql = "SELECT * FROM jugador WHERE id = ?";
        Jugador jugador = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                jugador = new Jugador(rs.getInt("id"), rs.getString("nombre"),
                        rs.getInt("nivel"), rs.getInt("puntuacion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jugador;
    }

    /**
     * Método que busca un jugador en la base de datos por su nombre.
     *
     * @param nombre el nombre del jugador a buscar.
     * @return el objeto Jugador si se encuentra, null en caso contrario.
     */
    public Jugador buscarPorNombre(String nombre) {
        String sql = "SELECT * FROM jugador WHERE nombre = ?";
        Jugador jugador = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, nombre);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                jugador = new Jugador(rs.getInt("id"), rs.getString("nombre"),
                        rs.getInt("nivel"), rs.getInt("puntuacion"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jugador;
    }

    @Override
    /**
     * Método que lista todos los jugadores de la base de datos.
     *
     * @return una lista de objetos Jugador.
     */
    public List<Jugador> listarTodos() {
        String sql = "SELECT * FROM jugador";
        List<Jugador> jugadores = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                jugadores.add(new Jugador(rs.getInt("id"), rs.getString("nombre"),
                        rs.getInt("nivel"), rs.getInt("puntuacion")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return jugadores;
    }

    @Override
    /**
     * Método que elimina un jugador de la base de datos por su ID.
     *
     * @param id el ID del jugador a eliminar.
     * @return true si el jugador se elimina correctamente, false en caso contrario.
     */
    public boolean eliminar(int id) {
        String sql = "DELETE FROM jugador WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);

            int rowAffected = stmt.executeUpdate();
            return rowAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    /**
     * Método que lista las estadísticas de los jugadores con el total de horas jugadas.
     *
     * @return una lista de objetos que representan las estadísticas de los jugadores.
     */
    public List<Object[]> listarJugadoresMasHoras() {
        String sql = """
        SELECT 
            j.id AS id_jugador,
            j.nombre,
            COALESCE(SUM(p.tiempo), 0) AS horas_jugadas
        FROM 
            jugador j
        LEFT JOIN 
            partida p ON j.id = p.id_jug
        GROUP BY 
            j.id, j.nombre
        ORDER BY 
            horas_jugadas DESC;
        """;
        List<Object[]> estadisticas = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("id_jugador");    // ID del jugador
                fila[1] = rs.getString("nombre");    // Nombre del jugador
                fila[2] = rs.getInt("horas_jugadas"); // Total de horas jugadas
                estadisticas.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticas;
    }

    /**
     * Método que lista las estadísticas de los jugadores con la mayor puntuación.
     *
     * @return una lista de objetos que representan las estadísticas de los jugadores.
     */
    public List<Object[]> listarJugadoresMayorPuntuacion() {
        String sql = """
        SELECT 
            id AS id_jugador,
            nombre,
            puntuacion
        FROM 
            jugador
        ORDER BY 
            puntuacion DESC;
        """;
        List<Object[]> estadisticas = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql);
             ResultSet rs = stmt.executeQuery()) {

            while (rs.next()) {
                Object[] fila = new Object[3];
                fila[0] = rs.getInt("id_jugador"); // ID del jugador
                fila[1] = rs.getString("nombre"); // Nombre del jugador
                fila[2] = rs.getInt("puntuacion"); // Puntuación
                estadisticas.add(fila);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticas;
    }
}
