package by.malahovski.repository;

import by.malahovski.model.Attraction;
import by.malahovski.model.City;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;


import java.util.List;


@Repository
public interface AttractionRepository extends JpaRepository<Attraction,Long> {

    List<Attraction> findByCityId(Long cityId);

    List<Attraction> findByCity(City city);

}

