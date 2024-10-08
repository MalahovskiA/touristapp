package by.malahovski.repository;

import by.malahovski.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


/**
 * Repository interface for accessing {@link City} entities.
 * This interface extends {@link JpaRepository} and provides methods for performing
 * CRUD operations on the City entity.
 */
@Repository
public interface CityRepository extends JpaRepository<City, Long> {

}
