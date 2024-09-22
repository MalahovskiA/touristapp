package by.malahovski.connection;

import org.junit.jupiter.api.Test;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import static org.assertj.core.api.Assertions.assertThat;


public class DatabaseConnectionTest {

    @Test
    public void testDatabaseConnection() {
        String url = "jdbc:postgresql://localhost:788/tur_database";
        String user = "postgres";
        String password = "admin";
        try (Connection connection = DriverManager.getConnection(url, user, password)) {
            assertThat(connection).isNotNull();
            assertThat(connection.isValid(2)).isTrue();
        } catch (SQLException e) {
            e.printStackTrace();
            assertThat(false).isTrue();
        }
    }
}