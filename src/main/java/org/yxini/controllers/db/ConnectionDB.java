package org.yxini.controllers.db;


import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;

public class ConnectionDB {
    private Connection conexion;
    private Properties prop;

    /**
     * Establece una conexión a la BD con los datos del secret.properties en resources/secret.properties
     * @throws SQLException Si no se ha podido crear la conexion.
     */
    public ConnectionDB() throws SQLException {
        prop = new Properties();
        try (FileInputStream fis = new FileInputStream(this.getClass().getResource("/secret.properties").getPath())) {
            prop.load(fis);
        } catch (IOException e) {
            e.printStackTrace();
        }
        String host = prop.getProperty("db.url");
        String baseDatos = prop.getProperty("db.name");
        String usuario = prop.getProperty("db.user");
        String password = prop.getProperty("db.pwd");
        String cadenaConexion = "jdbc:mysql://" + host + ":3306/" + baseDatos;
        conexion = DriverManager.getConnection(cadenaConexion, usuario, password);
        conexion.setAutoCommit(true);
    }

    public Connection getConexion() {
        return conexion;
    }

    public void closeConexion() {
        try {
            conexion.close();
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
