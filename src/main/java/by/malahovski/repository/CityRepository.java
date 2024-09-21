package by.malahovski.repository;

import by.malahovski.model.City;

import java.util.List;

public interface CityRepository {
    void save(City city);
    void update(City city);
    City findById(Long id);
    List<City> findAll();
    void delete(Long id);
}
