package by.malahovski.repository;


import by.malahovski.model.Attraction;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Repository interface for accessing {@link Attraction} entities.
 * This interface extends {@link JpaRepository} and provides methods for performing
 * CRUD operations and custom queries on the Attraction entity.
 */
@Repository
public interface AttractionRepository extends JpaRepository<Attraction, Long> {

    /**
     * Finds a list of attractions based on the specified city name.
     *
     * @param cityName the name of the city to filter attractions.
     * @return a list of {@link Attraction} entities associated with the specified city.
     */
    List<Attraction> findByCityName(String cityName);

}

