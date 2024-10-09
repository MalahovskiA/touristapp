package by.malahovski.service;

import by.malahovski.dtos.CityDTO;

import java.util.List;


/**
 * Service interface for managing city-related operations.
 * Provides methods for retrieving, creating, updating, and deleting cities.
 */
public interface CityService {

    /**
     * Retrieves a city by its unique ID.
     *
     * @param id the ID of the city to be retrieved.
     * @return the {@link CityDTO} representing the city.
     */
    CityDTO getCityById(Long id);

    /**
     * Adds a new city to the system.
     *
     * @param cityDTO the {@link CityDTO} containing city details.
     * @return the created {@link CityDTO} object with the generated ID.
     */
    CityDTO addCity(CityDTO cityDTO);

    /**
     * Updates an existing city's details.
     *
     * @param cityDTO the {@link CityDTO} containing updated city details.
     * @return the updated {@link CityDTO}.
     */
    CityDTO updateCity(CityDTO cityDTO);

    /**
     * Retrieves a list of all cities.
     *
     * @return a list of {@link CityDTO} representing all cities.
     */
    List<CityDTO> getAllCities();

    /**
     * Updates specific details of an existing city, such as population or metro availability.
     * This method is transactional to ensure consistency during updates.
     *
     * @param cityId     the ID of the city to update.
     * @param population the new population value to set.
     * @param hasMetro   whether the city has a metro system or not.
     * @return the updated {@link CityDTO} after applying the changes.
     */
    CityDTO updateCityDetails(Long cityId, Integer population, Boolean hasMetro);

    /**
     * Deletes a city from the system by its unique ID.
     *
     * @param id the ID of the city to be deleted.
     */
    void deleteCity(Long id);
}

