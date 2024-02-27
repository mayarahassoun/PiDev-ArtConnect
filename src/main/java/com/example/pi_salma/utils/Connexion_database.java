package com.example.pi_salma.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class Connexion_database {

    public static Connexion_database instance;

    private Connexion_database() {
    }
    private Connection connection;
    private final String host = "localhost:3306";
    private final String user = "root";
    private final String password = "";
    private final String database = "salmaPi";

    public static Connexion_database getInstance() {
        if (instance == null) {
            instance = new Connexion_database();
        }
        return instance;
    }

    public Connection getConnection() throws SQLException, ClassNotFoundException {
        if(connection == null) {
            connection = DriverManager.getConnection("jdbc:mysql://" + host + "/" + database, user, password);
            System.out.println("Database is successfully connected.");
        }
        return connection;
    }
}
