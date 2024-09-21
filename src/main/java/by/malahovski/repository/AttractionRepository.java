package by.malahovski.repository;

import by.malahovski.model.Attraction;

import java.util.List;

public interface AttractionRepository {
    void save(Attraction attraction);
    void update(Attraction attraction);
    Attraction findById(Long id);
    List<Attraction> findAll();
    List<Attraction> findByCity(Long cityId);
    void delete(Long id);
}
