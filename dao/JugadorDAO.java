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
}
