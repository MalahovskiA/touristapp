package by.malahovski.controller;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.service.TourManagementService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
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
@Tag(name = "Tour Services", description = "Operations related to tour services")
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
     * @return ResponseEntity containing the created tour service and a status of 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Create a new tour service", description = "Creates a new tour service in the system.")
    public ResponseEntity<TourServiceDTO> createTourService(
            @Parameter(description = "Tour service data to be created", required = true)
            @RequestBody TourServiceDTO tourServiceDTO) {
        TourServiceDTO createdTourService = tourService.save(tourServiceDTO);
        return ResponseEntity.status(201).body(createdTourService);
    }

    /**
     * Retrieves a tour service by its identifier.
     *
     * @param id The identifier of the tour service
     * @return ResponseEntity containing the tour service and a status of 200 (OK) if found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get tour service by ID", description = "Retrieves a tour service by its identifier.")
    public ResponseEntity<TourServiceDTO> getTourServiceById(
            @Parameter(description = "ID of the tour service to retrieve", required = true)
            @PathVariable Long id) {
        TourServiceDTO tourServiceDTO = tourService.findById(id);
        return ResponseEntity.ok(tourServiceDTO);
    }

    /**
     * Retrieves a list of all tour services.
     *
     * @return ResponseEntity containing a list of {@link TourServiceDTO} objects and a status of 200 (OK)
     */
    @GetMapping
    @Operation(summary = "Get all tour services", description = "Retrieves a list of all tour services.")
    public ResponseEntity<List<TourServiceDTO>> getAllTourServices() {
        List<TourServiceDTO> tourServices = tourService.findAll();
        return ResponseEntity.ok(tourServices);
    }

    /**
     * Adds an attraction to a tour service.
     *
     * @param tourServiceId The identifier of the tour service
     * @param attractionId  The identifier of the attraction to add
     * @return ResponseEntity with an empty body and a status of 200 (OK) after successful addition
     */
    @PostMapping("/{tourServiceId}/attractions/{attractionId}")
    @Operation(summary = "Add attraction to tour service", description = "Adds an attraction to a specified tour service.")
    public ResponseEntity<Void> addAttractionToTourService(
            @Parameter(description = "ID of the tour service", required = true)
            @PathVariable Long tourServiceId,
            @Parameter(description = "ID of the attraction to add", required = true)
            @PathVariable Long attractionId) {
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
    @Operation(summary = "Delete tour service", description = "Deletes a tour service by its identifier.")
    public ResponseEntity<Void> deleteTourService(
            @Parameter(description = "ID of the tour service to delete", required = true)
            @PathVariable Long id) {
        tourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    /**
     * Removes an attraction from a tour service.
     *
     * @param tourServiceId The identifier of the tour service
     * @param attractionId  The identifier of the attraction to remove
     * @return ResponseEntity with an empty body and a status of 200 (OK) after successful removal
     */
    @DeleteMapping("/{tourServiceId}/attractions/{attractionId}")
    @Operation(summary = "Remove attraction from tour service", description = "Removes an attraction from a specified tour service.")
    public ResponseEntity<Void> removeAttractionFromTourService(
            @Parameter(description = "ID of the tour service", required = true)
            @PathVariable Long tourServiceId,
            @Parameter(description = "ID of the attraction to remove", required = true)
            @PathVariable Long attractionId) {
        tourService.removeAttractionFromTourService(tourServiceId, attractionId);
        return ResponseEntity.ok().build();
    }
}