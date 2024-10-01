package by.malahovski.database;

import by.malahovski.config.DatabaseConfig;
import by.malahovski.model.City;


import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.test.context.junit.jupiter.SpringJUnitConfig;

import java.util.ArrayList;
import java.util.List;


import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringJUnitConfig(DatabaseConfig.class)
class CityDatabaseTest {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    void testGetCityById() {
        // Получаем город по ID
        long cityId = 1; // Укажите ID города, который существует в вашей базе
        String sql = "SELECT id, name, population, metro FROM city WHERE id = ?";

        City city = jdbcTemplate.query(sql, new Object[]{cityId}, rs -> {
            if (rs.next()) {
                City c = new City();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setPopulation(rs.getInt("population"));
                c.setHasMetro(rs.getBoolean("metro"));
                return c;
            }
            return null; // Если город не найден
        });

        // Проверяем, что объект города не равен null
        assert city != null;
        assertEquals(cityId, city.getId(), "ID города должен совпадать");
        assertEquals("Moscow", city.getName(), "Имя города должно совпадать"); // Укажите ожидаемое имя
        assertEquals(12615882, city.getPopulation(), "Численность населения должна совпадать"); // Укажите ожидаемую численность
        assertTrue(city.isHasMetro(), "Город должен иметь метро"); // Укажите ожидаемое значение
    }

    @Test
    void testGetAllCities() {
        // Получаем всех городов
        String sql = "SELECT id, name, population, metro FROM city";

        List<City> cities = jdbcTemplate.query(sql, rs -> {
            List<City> cityList = new ArrayList<>();
            while (rs.next()) {
                City c = new City();
                c.setId(rs.getLong("id"));
                c.setName(rs.getString("name"));
                c.setPopulation(rs.getInt("population"));
                c.setHasMetro(rs.getBoolean("metro"));
                cityList.add(c);
            }
            return cityList;
        });

        // Проверяем, что список городов не пустой
        assert cities != null;
        assertEquals(5, cities.size(), "Должно быть 3 города в базе данных");
    }
}