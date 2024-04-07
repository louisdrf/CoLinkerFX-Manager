package com.colinker.database;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
public class ConnexionTester {

    public void tryConnexionTo(LocalDatabase database) {
        Connection connection = null;
        Statement statement = null;
        ResultSet resultSet = null;
        try {
            Class.forName("org.h2.Driver");
            connection = DriverManager.getConnection("jdbc:h2:~/test", "sa", "");
            statement = connection.createStatement();

            statement.executeUpdate("CREATE TABLE IF NOT EXISTS test (id INT PRIMARY KEY)");

            resultSet = statement.executeQuery("SELECT 1");

            while (resultSet.next()) {
                int result = resultSet.getInt(1);
                System.out.println("Result: " + result);
            }
        } catch (ClassNotFoundException | SQLException e) {
            e.printStackTrace();
        } finally {
            try {
                if (resultSet != null) resultSet.close();
                if (statement != null) statement.close();
                if (connection != null) connection.close();
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
    }
}

