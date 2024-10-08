package by.malahovski.repository;

import by.malahovski.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {


    @Query("SELECT c FROM City c LEFT JOIN FETCH c.attractions")
    List<City> findAllWithAttractions();
}
