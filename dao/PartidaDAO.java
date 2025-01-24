package app.dao;

import app.modelos.Partida;
import app.modelos.Jugador;
import app.modelos.VideoJuego;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class PartidaDAO implements DAO<Partida> {

    @Override
    public boolean guardar(Partida partida) {
        String sql = "INSERT INTO partida (id_jug, id_vid, tiempo, fecha) VALUES (?, ?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setInt(1, partida.getId_jugador());
            stmt.setInt(2, partida.getId_videojuego());
            stmt.setInt(3, partida.getTiempo());
            stmt.setDate(4, Date.valueOf(partida.getFecha()));
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                partida.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public Partida buscarPorId(int id) {
        String sql = "SELECT * FROM partida WHERE id = ?";
        Partida partida = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                partida = new Partida(rs.getInt("id"), rs.getInt("id_jug"), rs.getInt("id_vid"), rs.getInt("tiempo"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partida;
    }

    @Override
    public List<Partida> listarTodos() {
        String sql = "SELECT * FROM partida";
        List<Partida> partidas = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                Partida partida = new Partida(rs.getInt("id"), rs.getInt("id_jug"), rs.getInt("id_vid"), rs.getInt("tiempo"));
                partidas.add(partida);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return partidas;
    }

    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM partida WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            stmt.executeUpdate();

            int rowsAffected = stmt.executeUpdate();

            return rowsAffected > 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }
}
