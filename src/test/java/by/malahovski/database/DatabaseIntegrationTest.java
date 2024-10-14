package by.malahovski.database;

import org.junit.jupiter.api.AfterAll;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.testcontainers.containers.PostgreSQLContainer;

import javax.sql.DataSource;
import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertNotNull;

public class DatabaseIntegrationTest {

    private static PostgreSQLContainer<?> postgresContainer;
    private static JdbcTemplate jdbcTemplate;

    @BeforeAll
    public static void setUp() {
        postgresContainer = new PostgreSQLContainer<>("postgres:16.4-alpine3.20")
                .withDatabaseName("testDB")
                .withUsername("test")
                .withPassword("test");
        postgresContainer.start();

        DataSource dataSource = new DriverManagerDataSource(
                postgresContainer.getJdbcUrl(),
                postgresContainer.getUsername(),
                postgresContainer.getPassword()
        );

        jdbcTemplate = new JdbcTemplate(dataSource);

        jdbcTemplate.execute("CREATE TABLE city (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, population INT NOT NULL, metro BOOLEAN NOT NULL)");
        jdbcTemplate.execute("CREATE TABLE attraction (id SERIAL PRIMARY KEY, name VARCHAR(255) NOT NULL, creation_date DATE NOT NULL, description TEXT, city_id INT NOT NULL, type VARCHAR(50), FOREIGN KEY (city_id) REFERENCES city(id))");
        jdbcTemplate.execute("CREATE TABLE tour_service (id SERIAL PRIMARY KEY, name VARCHAR(255), description TEXT)");
        jdbcTemplate.execute("CREATE TABLE attraction_tour_service (attraction_id INT, tour_service_id INT, PRIMARY KEY (attraction_id, tour_service_id), FOREIGN KEY (attraction_id) REFERENCES attraction(id), FOREIGN KEY (tour_service_id) REFERENCES tour_service(id))");
    }

    @AfterAll
    public static void tearDown() {

        postgresContainer.stop();
    }

    @Test
    void testInsertCityAndAttraction() {
        jdbcTemplate.update("INSERT INTO city (name, population, metro) VALUES (?, ?, ?)",
                "Sample City", 1000000, true);

        Long cityId = jdbcTemplate.queryForObject("SELECT id FROM city WHERE name = 'Sample City'", Long.class);
        assertNotNull(cityId);

        LocalDate creationDate = LocalDate.of(2024, 2, 1);


        jdbcTemplate.update("INSERT INTO attraction (name, creation_date, description, city_id, type) VALUES (?, ?, ?, ?, ?)",
                "Another Attraction", creationDate, "Another beautiful attraction", cityId, "PARK");

        Integer count = jdbcTemplate.queryForObject("SELECT COUNT(*) FROM attraction WHERE city_id = ?", Integer.class, cityId);
        Assertions.assertEquals(Integer.valueOf(1), count);
    }

    @Test
    void testInsertTourServiceAndLinkToAttraction() {

        jdbcTemplate.update("INSERT INTO city (name, population, metro) VALUES (?, ?, ?)",
                "Another City", 500000, false);

        Long cityId = jdbcTemplate.queryForObject("SELECT id FROM city WHERE name = 'Another City'", Long.class);
        assertNotNull(cityId);

        LocalDate creationDate = LocalDate.of(2024, 2, 1);

        Long attractionId = jdbcTemplate.queryForObject(
                "SELECT id FROM attraction WHERE name = ?", Long.class, "Another Attraction");

        if (attractionId == null) {
            jdbcTemplate.update("INSERT INTO attraction (name, creation_date, description, city_id, type) VALUES (?, ?, ?, ?, ?)",
                    "Another Attraction", creationDate, "Another beautiful attraction", cityId, "PARK");

            attractionId = jdbcTemplate.queryForObject("SELECT id FROM attraction WHERE name = ?", Long.class, "Another Attraction");
        }

        assertNotNull(attractionId);

        jdbcTemplate.update("INSERT INTO tour_service (name, description) VALUES (?, ?)",
                "Sample Tour Service", "A tour service description");

        Long tourServiceId = jdbcTemplate.queryForObject("SELECT id FROM tour_service WHERE name = 'Sample Tour Service'", Long.class);
        assertNotNull(tourServiceId);

        jdbcTemplate.update("INSERT INTO attraction_tour_service (attraction_id, tour_service_id) VALUES (?, ?) ON CONFLICT DO NOTHING",
                attractionId, tourServiceId);

        Integer linkCount = jdbcTemplate.queryForObject(
                "SELECT COUNT(*) FROM attraction_tour_service WHERE attraction_id = ? AND tour_service_id = ?",
                Integer.class, attractionId, tourServiceId);
        Assertions.assertEquals(Integer.valueOf(1), linkCount);
    }


    @Test
    void testDeleteAttractionWithDependencies() {

        jdbcTemplate.update("INSERT INTO city (name, population, metro) VALUES (?, ?, ?)", "Dependent City", 1000000, true);
        Long cityId = jdbcTemplate.queryForObject("SELECT id FROM city WHERE name = 'Dependent City'", Long.class);


        jdbcTemplate.update("INSERT INTO attraction (name, creation_date, description, city_id, type) VALUES (?, ?, ?, ?, ?)",
                "Dependent Attraction", LocalDate.of(2024, 2, 1), "This attraction has dependencies", cityId, "PARK");

        Long attractionId = jdbcTemplate.queryForObject("SELECT id FROM attraction WHERE name = 'Dependent Attraction'", Long.class);


        jdbcTemplate.update("INSERT INTO tour_service (name, description) VALUES (?, ?)", "Dependent Tour", "Tour description");
        Long tourServiceId = jdbcTemplate.queryForObject("SELECT id FROM tour_service WHERE name = 'Dependent Tour'", Long.class);


        jdbcTemplate.update("INSERT INTO attraction_tour_service (attraction_id, tour_service_id) VALUES (?, ?)", attractionId, tourServiceId);


        jdbcTemplate.update("DELETE FROM attraction_tour_service WHERE attraction_id = ?", attractionId);


        jdbcTemplate.update("DELETE FROM attraction WHERE id = ?", attractionId);

        Assertions.assertThrows(EmptyResultDataAccessException.class, () -> jdbcTemplate.queryForObject("SELECT id FROM attraction WHERE id = ?", Long.class, attractionId));
    }

    @Test
    void testUpdateAttraction() {
        jdbcTemplate.update("INSERT INTO city (name, population, metro) VALUES (?, ?, ?)",
                "Update City", 1000000, true);

        Long cityId = jdbcTemplate.queryForObject("SELECT id FROM city WHERE name = 'Update City'", Long.class);
        Assertions.assertNotNull(cityId);

        LocalDate creationDate = LocalDate.of(2024, 2, 1);
        jdbcTemplate.update("INSERT INTO attraction (name, creation_date, description, city_id, type) VALUES (?, ?, ?, ?, ?)",
                "Old Attraction", creationDate, "An old attraction", cityId, "PARK");

        Long attractionId = jdbcTemplate.queryForObject("SELECT id FROM attraction WHERE name = 'Old Attraction'", Long.class);
        Assertions.assertNotNull(attractionId);

        jdbcTemplate.update("UPDATE attraction SET name = ?, description = ? WHERE id = ?",
                "Updated Attraction", "A newly updated attraction description", attractionId);

        String updatedAttractionName = jdbcTemplate.queryForObject("SELECT name FROM attraction WHERE id = ?", String.class, attractionId);
        String updatedAttractionDescription = jdbcTemplate.queryForObject("SELECT description FROM attraction WHERE id = ?", String.class, attractionId);

        Assertions.assertEquals("Updated Attraction", updatedAttractionName);
        Assertions.assertEquals("A newly updated attraction description", updatedAttractionDescription);
    }

    @Test
    void testSelectAttractionsByCity() {
        jdbcTemplate.update("INSERT INTO city (name, population, metro) VALUES (?, ?, ?)",
                "Select City", 1000000, true);

        Long cityId = jdbcTemplate.queryForObject("SELECT id FROM city WHERE name = 'Select City'", Long.class);
        Assertions.assertNotNull(cityId);

        LocalDate creationDate1 = LocalDate.of(2024, 2, 1);
        jdbcTemplate.update("INSERT INTO attraction (name, creation_date, description, city_id, type) VALUES (?, ?, ?, ?, ?)",
                "Attraction One", creationDate1, "Description for attraction one", cityId, "PARK");

        LocalDate creationDate2 = LocalDate.of(2024, 3, 1);
        jdbcTemplate.update("INSERT INTO attraction (name, creation_date, description, city_id, type) VALUES (?, ?, ?, ?, ?)",
                "Attraction Two", creationDate2, "Description for attraction two", cityId, "MUSEUM");

        List<String> attractionNames = jdbcTemplate.query(
                "SELECT name FROM attraction WHERE city_id = ?",
                (rs, rowNum) -> rs.getString("name"),
                cityId
        );

        Assertions.assertEquals(2, attractionNames.size());
        Assertions.assertTrue(attractionNames.contains("Attraction One"));
        Assertions.assertTrue(attractionNames.contains("Attraction Two"));
    }
}