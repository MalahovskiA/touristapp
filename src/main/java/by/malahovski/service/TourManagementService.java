package by.malahovski.service;

import by.malahovski.dtos.TourServiceDTO;

import java.util.List;


/**
 * Service interface for managing tour services and their associated attractions.
 * Provides methods to create, retrieve, update, and delete tour services, as well as
 * manage relationships between tour services and attractions.
 */
public interface TourManagementService {

    /**
     * Saves a new or existing tour service.
     *
     * @param tourServiceDTO the {@link TourServiceDTO} object containing the details of the tour service.
     * @return the saved {@link TourServiceDTO} with updated information (e.g., ID if newly created).
     */
    TourServiceDTO save(TourServiceDTO tourServiceDTO);

    /**
     * Retrieves a tour service by its unique ID.
     *
     * @param id the ID of the tour service to retrieve.
     * @return the {@link TourServiceDTO} representing the tour service.
     */
    TourServiceDTO findById(Long id);

    /**
     * Retrieves a list of all available tour services.
     *
     * @return a list of {@link TourServiceDTO} objects representing all tour services.
     */
    List<TourServiceDTO> findAll();

    /**
     * Deletes a tour service by its unique ID.
     *
     * @param id the ID of the tour service to delete.
     */
    void deleteById(Long id);

    /**
     * Adds an attraction to a tour service.
     *
     * @param tourServiceId the ID of the tour service to which the attraction will be added.
     * @param attractionId  the ID of the attraction to be added.
     */
    void addAttractionToTourService(Long tourServiceId, Long attractionId);

    /**
     * Removes an attraction from a tour service.
     *
     * @param tourServiceId the ID of the tour service from which the attraction will be removed.
     * @param attractionId  the ID of the attraction to be removed.
     */
    void removeAttractionFromTourService(Long tourServiceId, Long attractionId);
}
