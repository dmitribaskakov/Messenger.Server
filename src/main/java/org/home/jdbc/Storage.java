package org.home.jdbc;

import org.home.env.Settings;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class Storage {
    private Connection connection;
    private PreparedStatement statementGetUserID;

    public void start (Settings settings) throws SQLException, ClassNotFoundException {
        try {
            Class.forName("org.postgresql.Driver");
        } catch (ClassNotFoundException e) {
            System.out.println("PostgreSQL JDBC Driver is not found. Include it in your library path ");
            e.printStackTrace();
            return;
        }

        try {
            connection = DriverManager
                    .getConnection(settings.getDatabaseURI(), settings.getDatabaseUser(), settings.getDatabasePassword()));
        } catch (SQLException e) {
            System.out.println("Connection Failed");
            e.printStackTrace();
            return;
        }

        if (connection != null) {
            System.out.println("You successfully connected to database now");
        } else {
            System.out.println("Failed to make connection to database");
        }

        statementGetUserID = connection.prepareStatement("SELECT id FROM users WHERE name = ?;");
    }

     public int getUserID(String name) {
         int parameterIndex = 234;
         statementGetUserID.setString(parameterIndex, "asd");
         rs = statementGetUserID.executeQuery();

     }

}
