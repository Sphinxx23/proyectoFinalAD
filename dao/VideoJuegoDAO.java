package app.dao;

import app.modelos.VideoJuego;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VideoJuegoDAO implements DAO<VideoJuego> {

    @Override
    public boolean guardar(VideoJuego videojuego) {
        String sql = "INSERT INTO videojuego (titulo, genero, precio) VALUES (?, ?, ?)";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS)) {

            stmt.setString(1, videojuego.getTitulo());
            stmt.setString(2, videojuego.getGenero());
            stmt.setDouble(3, videojuego.getPrecio());
            stmt.executeUpdate();

            ResultSet rs = stmt.getGeneratedKeys();
            if (rs.next()) {
                videojuego.setId(rs.getInt(1));
            }
            return true;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    @Override
    public VideoJuego buscarPorId(int id) {
        String sql = "SELECT * FROM videojuego WHERE id = ?";
        VideoJuego videojuego = null;

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id);
            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                videojuego = new VideoJuego(rs.getInt("id"), rs.getString("titulo"),
                        rs.getString("genero"), rs.getDouble("precio"));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videojuego;
    }

    @Override
    public List<VideoJuego> listarTodos() {
        String sql = "SELECT * FROM videojuego";
        List<VideoJuego> videojuegos = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                videojuegos.add(new VideoJuego(rs.getInt("id"), rs.getString("titulo"),
                        rs.getString("genero"), rs.getDouble("precio")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videojuegos;
    }

    public List<VideoJuego> listarDeMasCaroAMasBarato() {
        String sql = "SELECT * FROM videojuego ORDER BY precio DESC";
        List<VideoJuego> videojuegos = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                videojuegos.add(new VideoJuego(rs.getInt("id"), rs.getString("titulo"),
                        rs.getString("genero"), rs.getDouble("precio")));
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videojuegos;
    }

    public List<VideoJuego> listarPorGenero(String genero) {
        String sql = "SELECT * FROM videojuego WHERE genero = ?";
        List<VideoJuego> videojuegos = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, genero);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                videojuegos.add(new VideoJuego(rs.getInt("id"), rs.getString("titulo"),
                        rs.getString("genero"), rs.getDouble("precio")));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
        return videojuegos;
    }

    public List<VideoJuego> listarVideojuegosDeJugadorID(int id_jug) {
        String sql = "SELECT id_vid FROM partida WHERE id_jug = ?";
        List<Integer> videojuegos = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setInt(1, id_jug);
            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                videojuegos.add(rs.getInt("id_vid"));
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }

        List<VideoJuego> nombreVideojuegos = obtenerVideojuegos(videojuegos);

        return nombreVideojuegos;
    }

    public List<VideoJuego> obtenerVideojuegos(List<Integer> ids) {
        List<VideoJuego> videojuegos = new ArrayList<>();
        for (int id : ids) {
            VideoJuego videojuego = buscarPorId(id);
            if (videojuego != null) {
                videojuegos.add(videojuego);
            }
        }
        return videojuegos;
    }


    @Override
    public boolean eliminar(int id) {
        String sql = "DELETE FROM videojuego WHERE id = ?";

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
