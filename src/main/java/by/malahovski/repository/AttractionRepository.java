package by.malahovski.repository;

import by.malahovski.model.Attraction;
import by.malahovski.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;


@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {
    Optional<Attraction> findByCity(City city);

    void update(Attraction attraction);

    void delete(Long id);
}

