package app.dao;

import app.modelos.Jugador;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class JugadorDAO implements DAO<Jugador> {

    @Override
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

    // Método para listar jugadores con más horas jugadas
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

    // Método para listar jugadores con mayor puntuación
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
