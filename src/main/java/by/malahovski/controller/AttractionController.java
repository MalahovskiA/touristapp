package by.malahovski.controller;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.service.AttractionService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * Controller for managing attractions.
 * This controller handles HTTP requests related to attractions, including creating,
 * retrieving, updating, and deleting attractions.
 */
@RestController
@RequestMapping("/attractions")
@Tag(name = "Attractions", description = "Operations related to attractions")
public class AttractionController {

    private final AttractionService attractionService;

    /**
     * Constructor for the controller to inject {@link AttractionService} dependency.
     *
     * @param attractionService The service for managing attractions
     */
    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    /**
     * Adds a new attraction.
     *
     * @param attractionDTO The {@link AttractionDTO} object containing information about the new attraction
     * @return ResponseEntity containing the created attraction and a status of 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Add a new attraction", description = "Creates a new attraction in the system.")
    public ResponseEntity<AttractionDTO> addAttraction(
            @Parameter(description = "Attraction data", required = true)
            @RequestBody AttractionDTO attractionDTO) {
        AttractionDTO createdAttraction = attractionService.addAttraction(attractionDTO);
        return ResponseEntity.status(201).body(createdAttraction);
    }

    /**
     * Updates an existing attraction.
     *
     * @param attractionDTO The {@link AttractionDTO} object containing updated information about the attraction
     * @return ResponseEntity containing the updated attraction and a status of 200 (OK)
     */
    @PutMapping
    @Operation(summary = "Update an existing attraction", description = "Updates an attraction with the provided data.")
    public ResponseEntity<AttractionDTO> updateAttraction(
            @Parameter(description = "Updated attraction data", required = true)
            @RequestBody AttractionDTO attractionDTO) {
        AttractionDTO updatedAttraction = attractionService.updateAttraction(attractionDTO);
        return ResponseEntity.ok(updatedAttraction);
    }

    /**
     * Retrieves a list of all attractions.
     *
     * @param sortBy       The field to sort attractions by (optional)
     * @param filterByType The type to filter attractions by (optional)
     * @return ResponseEntity containing a list of {@link AttractionDTO} objects and a status of 200 (OK)
     */
    @GetMapping
    @Operation(summary = "Get all attractions", description = "Retrieves a list of all attractions.")
    public ResponseEntity<List<AttractionDTO>> getAllAttractions(
            @Parameter(description = "Field to sort attractions by (optional)")
            @RequestParam(required = false) String sortBy,
            @Parameter(description = "Type to filter attractions by (optional)")
            @RequestParam(required = false) String filterByType) {
        List<AttractionDTO> attractions = attractionService.getAllAttractions(sortBy, filterByType);
        return ResponseEntity.ok(attractions);
    }

    /**
     * Retrieves an attraction by its identifier.
     *
     * @param id The identifier of the attraction
     * @return ResponseEntity containing the attraction and a status of 200 (OK) if found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get attraction by ID", description = "Retrieves an attraction by its identifier.")
    public ResponseEntity<AttractionDTO> getAttractionById(
            @Parameter(description = "ID of the attraction to retrieve", required = true)
            @PathVariable Long id) {
        AttractionDTO attraction = attractionService.getAttractionById(id);
        return ResponseEntity.ok(attraction);
    }

    /**
     * Retrieves attractions by city name.
     *
     * @param cityName The name of the city for which to retrieve attractions
     * @return ResponseEntity containing a list of {@link AttractionDTO} objects and a status of 200 (OK)
     */
    @GetMapping("/city/{cityName}")
    @Operation(summary = "Get attractions by city name", description = "Retrieves attractions by city name.")
    public ResponseEntity<List<AttractionDTO>> getAttractionsByCityName(
            @Parameter(description = "Name of the city to filter attractions by", required = true)
            @PathVariable String cityName) {
        List<AttractionDTO> attractions = attractionService.getAttractionsByCityName(cityName);
        return ResponseEntity.ok(attractions);
    }

    /**
     * Updates the description of an attraction by its identifier.
     *
     * @param id             The identifier of the attraction
     * @param newDescription The new description for the attraction
     * @return ResponseEntity containing the updated attraction and a status of 200 (OK)
     */
    @PatchMapping("/{id}/description")
    @Operation(summary = "Update attraction description", description = "Updates the description of an attraction.")
    public ResponseEntity<AttractionDTO> updateAttractionDescription(
            @Parameter(description = "ID of the attraction", required = true)
            @PathVariable Long id,
            @Parameter(description = "New description for the attraction", required = true)
            @RequestBody String newDescription) {
        AttractionDTO updatedAttraction = attractionService.updateDescription(id, newDescription);
        return ResponseEntity.ok(updatedAttraction);
    }

    /**
     * Deletes an attraction by its identifier.
     *
     * @param id The identifier of the attraction
     * @return ResponseEntity with an empty body and a status of 204 (No Content) after successful deletion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete attraction", description = "Deletes an attraction by its ID.")
    public ResponseEntity<Void> deleteAttraction(
            @Parameter(description = "ID of the attraction to delete", required = true)
            @PathVariable Long id) {
        attractionService.deleteAttraction(id);
        return ResponseEntity.noContent().build();
    }
}