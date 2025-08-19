/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package practicaUnoIPC2;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.Properties;
import java.io.InputStream;
import java.io.IOException;

/**
 *
 * @author aguil
 */
public class ConexionBD {

    public static Connection getConnection() {
        Connection conexion = null;
        try (InputStream input = ConexionBD.class.getClassLoader().getResourceAsStream("db.properties")) {

            if (input == null) {
                throw new IOException("No se encontró el archivo db.properties");
            }

            Properties prop = new Properties();
            prop.load(input);

            String host = prop.getProperty("db.host");
            String port = prop.getProperty("db.port");
            String name = prop.getProperty("db.name");
            String user = prop.getProperty("db.user");
            String password = prop.getProperty("db.password");
            String params = prop.getProperty("db.params");
            String url = String.format("jdbc:mysql://%s:%s/%s?%s", host, port, name, params);

            conexion = DriverManager.getConnection(url, user, password);
            System.out.println(" Conexión establecida con la base de datos");
        } catch (IOException | SQLException ex) {
            System.out.println(" Error al conectar con la BD: " + ex.getMessage());
        }
        return conexion;
    }
}

