package app.dao;

import java.io.FileReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;

public class DatabaseConfig {
    private static String connectionString;

    static {
        try (FileReader reader = new FileReader("main/java/app/configuracion/Config.json")) {
            JSONParser parser = new JSONParser();
            JSONObject config = (JSONObject) parser.parse(reader);

            connectionString = config.get("url") +
                    "?user=" + config.get("user") +
                    "&password=" + config.get("password") +
                    "&sslmode=" + config.get("sslMode");
        } catch (Exception e) {
            System.err.println("Error al cargar la configuración: " + e.getMessage());
            e.printStackTrace();
            throw new RuntimeException("No se pudo cargar la configuración de la base de datos.");
        }
    }

    /**
     * Devuelve una conexión a la base de datos utilizando la cadena de conexión cargada.
     *
     * @return Connection instancia de conexión a la base de datos.
     * @throws SQLException si ocurre un error al establecer la conexión.
     */
    public static Connection getConnection() throws SQLException {
        if (connectionString == null || connectionString.isEmpty()) {
            throw new IllegalStateException("La cadena de conexión no está configurada.");
        }
        return DriverManager.getConnection(connectionString);
    }
}
