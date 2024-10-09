package by.malahovski.service;

import by.malahovski.dtos.AttractionDTO;
import java.util.List;


/**
 * Service interface for managing attractions.
 * Provides methods to perform operations related to attractions,
 * including retrieving, adding, updating, and deleting attractions.
 */
public interface AttractionService {

    /**
     * Retrieves all attractions, optionally sorted and filtered by type.
     *
     * @param sortBy     the attribute to sort attractions by (e.g., name, creation date)
     * @param filterByType the type of attractions to filter by (e.g., museum, park)
     * @return a list of {@link AttractionDTO} representing all attractions
     */
    List<AttractionDTO> getAllAttractions(String sortBy, String filterByType);

    /**
     * Retrieves attractions by the name of the city they are located in.
     *
     * @param cityName the name of the city to filter attractions by
     * @return a list of {@link AttractionDTO} representing attractions in the specified city
     */
    List<AttractionDTO> getAttractionsByCityName(String cityName);

    /**
     * Retrieves an attraction by its unique identifier.
     *
     * @param id the unique identifier of the attraction
     * @return an {@link AttractionDTO} representing the specified attraction, or null if not found
     */
    AttractionDTO getAttractionById(Long id);

    /**
     * Adds a new attraction.
     *
     * @param attractionDTO the {@link AttractionDTO} object containing the details of the attraction to be added
     * @return the added {@link AttractionDTO} with its generated ID
     */
    AttractionDTO addAttraction(AttractionDTO attractionDTO);

    /**
     * Updates the details of an existing attraction.
     *
     * @param attractionDTO the {@link AttractionDTO} object containing the updated details
     * @return the updated {@link AttractionDTO}
     */
    AttractionDTO updateAttraction(AttractionDTO attractionDTO);

    /**
     * Updates the description of an existing attraction identified by its ID.
     *
     * @param id the unique identifier of the attraction to be updated
     * @param newDescription the new description for the attraction
     * @return the updated {@link AttractionDTO} with the new description
     */
    AttractionDTO updateDescription(Long id, String newDescription);

    /**
     * Deletes an attraction by its unique identifier.
     *
     * @param id the unique identifier of the attraction to be deleted
     */
    void deleteAttraction(Long id);
}