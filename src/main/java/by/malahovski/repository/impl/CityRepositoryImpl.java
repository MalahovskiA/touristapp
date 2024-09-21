package by.malahovski.repository.impl;

import by.malahovski.model.City;
import by.malahovski.repository.CityRepository;
import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CityRepositoryImpl implements CityRepository {
    private final List<City> cities = new ArrayList<>();
    private long currentId = 1;

    @Override
    public void save(City city) {
        city.setId(currentId++);
        cities.add(city);
    }

    @Override
    public City findById(Long id) {
        return cities.stream()
                .filter(city -> city.getId().equals(id))
                .findFirst()
                .orElse(null);
    }

    @Override
    public List<City> findAll() {
        return new ArrayList<>(cities);
    }

    @Override
    public void update(City city) {
        Optional<City> existingCity = cities.stream()
                .filter(c -> c.getId().equals(city.getId()))
                .findFirst();
        existingCity.ifPresent(c -> {
            c.setName(city.getName());
            c.setPopulation(city.getPopulation());
            c.setHasMetro(city.isHasMetro());
            c.setAttractions(city.getAttractions());
        });
    }

    @Override
    public void delete(Long id) {
        cities.removeIf(city -> city.getId().equals(id));
    }
}
