package com.sgvet.base.boundary;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Paths;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class DbManager {

    private static final String DB_NAME = "sgvetDB";
    private static final String DB_URL = "jdbc:derby:" + DB_NAME + ";create=true";
    private static final String INIT_SQL_RESOURCE = "db/init.sql";

    private static DbManager instance;
    public Connection connection;

    // Constructor privado (solo se ejecuta una vez)
    private DbManager() {
        try {
            this.connection = DriverManager.getConnection(DB_URL);
            System.out.println("Conexión establecida con Derby.");

                System.out.println("Inicializando la base de datos desde init.sql...");
                runSqlScriptFromResources(INIT_SQL_RESOURCE);
        } catch (SQLException | IOException e) {
            throw new RuntimeException("Error al inicializar la base de datos Derby", e);
        }
    }

    // Singleton: devuelve la única instancia
    public static synchronized DbManager getInstance() {
        if (instance == null) {
            instance = new DbManager();
        }
        return instance;
    }

    public Connection getConnection() {
        return connection;
    }

    // Ejecuta el script SQL de inicialización
    private void runSqlScript(String filePath) throws IOException, SQLException {
        try (BufferedReader reader = new BufferedReader(new FileReader(filePath));
             Statement stmt = connection.createStatement()) {

            StringBuilder sql = new StringBuilder();
            String line;

            while ((line = reader.readLine()) != null) {
                line = line.trim();
                if (line.isEmpty() || line.startsWith("--")) continue;

                sql.append(line);
                if (line.endsWith(";")) {
                    String command = sql.toString().replace(";", "");
                    stmt.executeUpdate(command);
                    sql.setLength(0);
                }
            }
        }
    }
    private void runSqlScriptFromResources(String resourcePath) throws IOException, SQLException {
        try (InputStream is = getClass().getClassLoader().getResourceAsStream(resourcePath)) {
            if (is == null) {
                throw new IOException("Archivo no encontrado en resources: " + resourcePath);
            }

            try (BufferedReader reader = new BufferedReader(new InputStreamReader(is));
                 Statement stmt = connection.createStatement()) {

                StringBuilder sql = new StringBuilder();
                String line;

                while ((line = reader.readLine()) != null) {
                    line = line.trim();
                    if (line.isEmpty() || line.startsWith("--")) continue;

                    sql.append(line);
                    if (line.endsWith(";")) {
                        String command = sql.toString().replace(";", "");
                        stmt.executeUpdate(command);
                        sql.setLength(0);
                    }
                }
            }
        }
    }

    private boolean isNewDatabase(String dbName) {
        return !Paths.get(dbName).toFile().exists();
    }

    public static void main(String[] args) {
        DbManager dbConnection = new DbManager();
        System.out.println("Base de datos inicializada correctamente.");
    }
}
