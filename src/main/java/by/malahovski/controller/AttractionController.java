package by.malahovski.controller;


import by.malahovski.dtos.AttractionDTO;
import by.malahovski.service.AttractionService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
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
@Api(value = "Attractions", tags = "Attractions")
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
    @ApiOperation(value = "Add a new attraction", notes = "Creates a new attraction in the system.")
    public ResponseEntity<AttractionDTO> addAttraction(
            @ApiParam(value = "Attraction data", required = true)
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
    @ApiOperation(value = "Update an existing attraction", notes = "Updates an attraction with the provided data.")
    public ResponseEntity<AttractionDTO> updateAttraction(
            @ApiParam(value = "Updated attraction data", required = true)
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
    @ApiOperation(value = "Get all attractions", notes = "Retrieves a list of all attractions.")
    public ResponseEntity<List<AttractionDTO>> getAllAttractions(
            @ApiParam(value = "Field to sort attractions by (optional)")
            @RequestParam(required = false) String sortBy,
            @ApiParam(value = "Type to filter attractions by (optional)")
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
    @ApiOperation(value = "Get attraction by ID", notes = "Retrieves an attraction by its identifier.")
    public ResponseEntity<AttractionDTO> getAttractionById(
            @ApiParam(value = "ID of the attraction to retrieve", required = true)
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
    @ApiOperation(value = "Get attractions by city name", notes = "Retrieves attractions by city name.")
    public ResponseEntity<List<AttractionDTO>> getAttractionsByCityName(
            @ApiParam(value = "Name of the city to filter attractions by", required = true)
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
    @ApiOperation(value = "Update attraction description", notes = "Updates the description of an attraction.")
    public ResponseEntity<AttractionDTO> updateAttractionDescription(
            @ApiParam(value = "ID of the attraction", required = true)
            @PathVariable Long id,
            @ApiParam(value = "New description for the attraction", required = true)
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
    @ApiOperation(value = "Delete attraction", notes = "Deletes an attraction by its ID.")
    public ResponseEntity<Void> deleteAttraction(
            @ApiParam(value = "ID of the attraction to delete", required = true)
            @PathVariable Long id) {
        attractionService.deleteAttraction(id);
        return ResponseEntity.noContent().build();
    }
}