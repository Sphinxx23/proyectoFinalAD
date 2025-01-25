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
            int rowsAffected = stmt.executeUpdate();

            return rowsAffected != 0;
        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }

    public List<Object[]> listarEstadisticasVideojuegosHoras() {
        String sql = """
        SELECT 
            j.id AS id_videojuego,
            j.titulo,
            COALESCE(SUM(p.tiempo), 0) AS horas_jugadas
        FROM 
            videojuego j
        LEFT JOIN 
            partida p ON j.id = p.id_vid
        GROUP BY 
            j.id, j.titulo
        ORDER BY 
            horas_jugadas DESC;
        """;
        List<Object[]> estadisticas = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Extraemos los datos y los agregamos como un arreglo de objetos
                estadisticas.add(new Object[]{
                        rs.getInt("id_videojuego"),       // ID del videojuego
                        rs.getString("titulo"),           // Título del videojuego
                        rs.getInt("horas_jugadas")         // Tiempo total jugado (en horas)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticas;
    }


    public List<Object[]> listarEstadisticasVideojuegosJugadoresTotales() {
        String sql = """
        SELECT 
            videojuego.id AS id_videojuego,
            videojuego.titulo AS titulo,
            COUNT(DISTINCT partida.id_jug) AS jugadores_totales
        FROM 
            videojuego
        LEFT JOIN 
            partida ON videojuego.id = partida.id_vid
        GROUP BY 
            videojuego.id, videojuego.titulo
        ORDER BY 
            jugadores_totales DESC;
        """;

        List<Object[]> estadisticas = new ArrayList<>();

        try (Connection conn = DatabaseConfig.getConnection();
             Statement stmt = conn.createStatement();
             ResultSet rs = stmt.executeQuery(sql)) {

            while (rs.next()) {
                // Extraemos los datos y los agregamos como un arreglo de objetos
                estadisticas.add(new Object[]{
                        rs.getInt("id_videojuego"),       // ID del videojuego
                        rs.getString("titulo"),           // Título del videojuego
                        rs.getInt("jugadores_totales")    // Jugadores totales (únicos)
                });
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return estadisticas;
    }

    public boolean editarVideojuego(VideoJuego videojuego) {
        String sql = "UPDATE videojuego SET titulo = ?, genero = ?, precio = ? WHERE id = ?";

        try (Connection conn = DatabaseConfig.getConnection();
             PreparedStatement stmt = conn.prepareStatement(sql)) {

            stmt.setString(1, videojuego.getTitulo());
            stmt.setString(2, videojuego.getGenero());
            stmt.setDouble(3, videojuego.getPrecio());
            stmt.setInt(4, videojuego.getId());

            int rowsAffected = stmt.executeUpdate();
            return rowsAffected > 0;

        } catch (SQLException e) {
            e.printStackTrace();
            return false;
        }
    }


}
