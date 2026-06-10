package org.example.slipclass_demo_1.configuration;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class SQLDataAccess {

    private static String DRIVER;
    private static String URL;
    private static String SCHEMA;
    private static String USUARIO;
    private static String CLAVE;

    static {

        try {
            InputStream is = SQLDataAccess.class.getResourceAsStream("/inicio.dat");

            if (is == null) {
                throw new RuntimeException("No se encontró el archivo inicio.dat en resources.");
            }

            BufferedReader reader = new BufferedReader(new InputStreamReader(is));

            String line;

            while ((line = reader.readLine()) != null) {

                String[] parts = line.split("=");

                if (parts.length == 2) {

                    switch (parts[0].trim()) {

                        case "driver":
                            DRIVER = parts[1].trim();
                            break;

                        case "url":
                            URL = parts[1].trim();
                            break;

                        case "schema":
                            SCHEMA = parts[1].trim();
                            break;

                        case "usuario":
                            USUARIO = parts[1].trim();
                            break;

                        case "clave":
                            CLAVE = parts[1].trim();
                            break;
                    }
                }
            }

            reader.close();

        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static Connection getConnection() {

        try {

            Class.forName(DRIVER);

            Connection conn = DriverManager.getConnection(
                    URL + SCHEMA,
                    USUARIO,
                    CLAVE
            );

            return conn;

        } catch (ClassNotFoundException e) {

            System.out.println("Error al cargar el driver JDBC:");
            e.printStackTrace();

        } catch (SQLException e) {

            System.out.println("Error al conectar a la base de datos:");
            e.printStackTrace();

        }

        return null;
    }
}