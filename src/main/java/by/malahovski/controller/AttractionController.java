package by.malahovski.controller;


import by.malahovski.dtos.AttractionDTO;
import by.malahovski.service.AttractionService;
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
    public ResponseEntity<AttractionDTO> addAttraction(@RequestBody AttractionDTO attractionDTO) {
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
    public ResponseEntity<AttractionDTO> updateAttraction(@RequestBody AttractionDTO attractionDTO) {
        AttractionDTO updatedAttraction = attractionService.updateAttraction(attractionDTO);
        return ResponseEntity.ok(updatedAttraction);
    }

    /**
     * Retrieves a list of all attractions.
     *
     * @param sortBy The field to sort attractions by (optional)
     * @param filterByType The type to filter attractions by (optional)
     * @return ResponseEntity containing a list of {@link AttractionDTO} objects and a status of 200 (OK)
     */
    @GetMapping
    public ResponseEntity<List<AttractionDTO>> getAllAttractions(
            @RequestParam(required = false) String sortBy,
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
    public ResponseEntity<AttractionDTO> getAttractionById(@PathVariable Long id) {
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
    public ResponseEntity<List<AttractionDTO>> getAttractionsByCityName(@PathVariable String cityName) {
        List<AttractionDTO> attractions = attractionService.getAttractionsByCityName(cityName);
        return ResponseEntity.ok(attractions);
    }

    /**
     * Updates the description of an attraction by its identifier.
     *
     * @param id The identifier of the attraction
     * @param newDescription The new description for the attraction
     * @return ResponseEntity containing the updated attraction and a status of 200 (OK)
     */
    @PatchMapping("/{id}/description")
    public ResponseEntity<AttractionDTO> updateAttractionDescription(@PathVariable Long id, @RequestBody String newDescription) {
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
    public ResponseEntity<Void> deleteAttraction(@PathVariable Long id) {
        attractionService.deleteAttraction(id);
        return ResponseEntity.noContent().build();
    }
}