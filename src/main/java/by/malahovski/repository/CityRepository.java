package by.malahovski.repository;

import by.malahovski.model.City;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

@Repository
public interface CityRepository extends JpaRepository<City, Long> {

    @Modifying
    @Query("UPDATE City c SET c.name = :name, c.population = :population, c.hasMetro = :hasMetro WHERE c.id = :id")
    void update(@Param("id") Long id);
}
