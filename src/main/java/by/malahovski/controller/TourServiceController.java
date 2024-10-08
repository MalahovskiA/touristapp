package by.malahovski.controller;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.service.TourManagementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;


/**
 * Controller for managing tour services.
 * This controller handles HTTP requests related to tour services, including creating,
 * retrieving, and deleting tour services as well as managing attractions associated with them.
 */
@RestController
@RequestMapping("/tour-services")
public class TourServiceController {

    private final TourManagementService tourService;

    /**
     * Constructor for the controller to inject {@link TourManagementService} dependency.
     *
     * @param tourService The service for managing tour services
     */
    @Autowired
    public TourServiceController(TourManagementService tourService) {
        this.tourService = tourService;
    }

    /**
     * Creates a new tour service.
     *
     * @param tourServiceDTO The {@link TourServiceDTO} object containing information about the new tour service
     * @return ResponseEntity containing the created tour service and a status of 200 (OK)
     */
    @PostMapping
    public ResponseEntity<TourServiceDTO> createTourService(@RequestBody TourServiceDTO tourServiceDTO) {
        TourServiceDTO createdTourService = tourService.save(tourServiceDTO);
        return ResponseEntity.ok(createdTourService);
    }

    /**
     * Retrieves a tour service by its identifier.
     *
     * @param id The identifier of the tour service
     * @return ResponseEntity containing the tour service and a status of 200 (OK) if found
     */
    @GetMapping("/{id}")
    public ResponseEntity<TourServiceDTO> getTourServiceById(@PathVariable Long id) {
        TourServiceDTO tourServiceDTO = tourService.findById(id);
        return ResponseEntity.ok(tourServiceDTO);
    }

    /**
     * Retrieves a list of all tour services.
     *
     * @return ResponseEntity containing a list of {@link TourServiceDTO} objects and a status of 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<TourServiceDTO>> getAllTourServices() {
        List<TourServiceDTO> tourServices = tourService.findAll();
        return ResponseEntity.ok(tourServices);
    }


    /**
     * Adds an attraction to a tour service.
     *
     * @param tourServiceId The identifier of the tour service
     * @param attractionId The identifier of the attraction to add
     * @return ResponseEntity with an empty body and a status of 200 (OK) after successful addition
     */
    @PostMapping("/{tourServiceId}/attractions/{attractionId}")
    public ResponseEntity<Void> addAttractionToTourService(@PathVariable Long tourServiceId, @PathVariable Long attractionId) {
        tourService.addAttractionToTourService(tourServiceId, attractionId);
        return ResponseEntity.ok().build();
    }

    /**
     * Deletes a tour service by its identifier.
     *
     * @param id The identifier of the tour service
     * @return ResponseEntity with an empty body and a status of 204 (No Content) after successful deletion
     */
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourService(@PathVariable Long id) {
        tourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Removes an attraction from a tour service.
     *
     * @param tourServiceId The identifier of the tour service
     * @param attractionId The identifier of the attraction to remove
     * @return ResponseEntity with an empty body and a status of 200 (OK) after successful removal
     */
    @DeleteMapping("/{tourServiceId}/attractions/{attractionId}")
    public ResponseEntity<Void> removeAttractionFromTourService(@PathVariable Long tourServiceId, @PathVariable Long attractionId) {
        tourService.removeAttractionFromTourService(tourServiceId, attractionId);
        return ResponseEntity.ok().build();
    }
}