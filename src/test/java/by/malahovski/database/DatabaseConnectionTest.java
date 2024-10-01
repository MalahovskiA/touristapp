package by.malahovski.database;

import by.malahovski.config.DatabaseConfig;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import javax.sql.DataSource;

import static org.junit.jupiter.api.Assertions.*;


@SpringJUnitConfig(DatabaseConfig.class)
class DatabaseConnectionTest {

    @Autowired
    private DataSource dataSource;

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testDataSourceIsNotNull() {
        // Проверяем, что DataSource настроен и не равен null
        assertNotNull(dataSource, "DataSource должен быть настроен");
    }

    @Test
    void testConnection() throws Exception {
        // Пробуем получить соединение с базой данных
        try (var connection = dataSource.getConnection()) {
            assertFalse(connection.isClosed(), "Соединение с базой данных должно быть открыто");
        }
    }

    @Test
    void testQueryExecution() {
        // Пробуем выполнить простой SQL-запрос
        Integer result = jdbcTemplate.queryForObject("SELECT 1", Integer.class);
        assertEquals(1, result, "Запрос должен вернуть 1");
    }
}