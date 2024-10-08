package by.malahovski.service;

import by.malahovski.dtos.CityDTO;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

public interface CityService {

    CityDTO getCityById(Long id);

    CityDTO addCity(CityDTO cityDTO);

    CityDTO updateCity(CityDTO cityDTO);

    List<CityDTO> getAllCities();

    @Transactional
    CityDTO updateCityDetails(Long cityId, Integer population, Boolean hasMetro);

    void deleteCity(Long id);
}

