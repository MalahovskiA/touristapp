package by.malahovski.service;

import by.malahovski.dtos.CityDTO;

import java.util.List;

public interface CityService {

    CityDTO getCityById(Long id);

    CityDTO addCity(CityDTO cityDTO);

    List<CityDTO> getAllCitiesWithAttractions();

    CityDTO updateCity(CityDTO cityDTO);

    List<CityDTO> getAllCities();

    void deleteCity(Long id);
}

