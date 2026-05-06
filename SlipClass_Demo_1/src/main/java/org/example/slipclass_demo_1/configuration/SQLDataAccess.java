package org.example.slipclass_demo_1.configuration;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;

public class SQLDataAccess {

    public static Connection databaseLink;

    private static String DRIVER;
    private static String URL;
    private static String SCHEMA;
    private static String USUARIO;
    private static String CLAVE;

    static{
        try (
            BufferedReader reader = new BufferedReader(new FileReader("src/main/resources/inicio.dat"))){
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
            System.out.println("Error al leer el archivo de configuración: " + e.getMessage());
        }

    }


    public static Connection getConnection() {

        try {
            Class.forName(DRIVER);
            databaseLink = DriverManager.getConnection(URL + SCHEMA, USUARIO, CLAVE);
        } catch (ClassNotFoundException e) {
            System.out.println("Error al cargar el driver JDBC: " + e.getMessage());
        } catch (Exception e) {
            System.out.println("Error al conectar a la base de datos: " + e.getMessage());
        }


        return databaseLink;
    }

}


