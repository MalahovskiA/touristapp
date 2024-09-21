package by.malahovski.service;

import by.malahovski.dtos.CityDTO;

import java.util.List;

public interface CityService {
    CityDTO getCityById(Long id);

    void addCity(CityDTO cityDTO);
    void updateCity(CityDTO cityDTO);
    List<CityDTO> getAllCities();
    void deleteCity(Long id);
}
