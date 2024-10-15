package by.malahovski.controller;

import by.malahovski.dtos.CityDTO;
import by.malahovski.service.CityService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.List;


/**
 * Controller for managing cities.
 * This controller handles HTTP requests related to cities, including creating,
 * retrieving, updating, and deleting cities.
 */
@RestController
@RequestMapping("/cities")
@Tag(name = "Cities", description = "Operations related to cities")
public class CityController {

    private final CityService cityService;

    /**
     * Constructor for the controller to inject {@link CityService} dependency.
     *
     * @param cityService The service for managing cities
     */
    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    /**
     * Adds a new city.
     *
     * @param cityDTO The {@link CityDTO} object containing information about the new city
     * @return ResponseEntity containing the created city and a status of 201 (Created)
     */
    @PostMapping
    @Operation(summary = "Add a new city", description = "Creates a new city in the system.")
    public ResponseEntity<CityDTO> addCity(
            @Parameter(description = "City data to be added", required = true)
            @RequestBody CityDTO cityDTO) {
        CityDTO createdCity = cityService.addCity(cityDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdCity);
    }

    /**
     * Retrieves a city by its identifier.
     *
     * @param id The identifier of the city
     * @return ResponseEntity containing the city and a status of 200 (OK) if found
     */
    @GetMapping("/{id}")
    @Operation(summary = "Get city by ID", description = "Retrieves a city by its identifier.")
    public ResponseEntity<CityDTO> getCityById(
            @Parameter(description = "ID of the city to retrieve", required = true)
            @PathVariable Long id) {
        CityDTO cityDTO = cityService.getCityById(id);
        return ResponseEntity.ok(cityDTO);
    }

    /**
     * Retrieves a list of all cities.
     *
     * @return A list of {@link CityDTO} objects representing all cities
     */
    @GetMapping
    @Operation(summary = "Get all cities", description = "Retrieves a list of all cities.")
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    /**
     * Updates a city's information by its identifier.
     *
     * @param id      The identifier of the city
     * @param cityDTO The {@link CityDTO} object containing updated information about the city
     * @return ResponseEntity containing the updated city and a status of 200 (OK)
     */
    @PutMapping("/{id}")
    @Operation(summary = "Update city", description = "Updates a city's information by its identifier.")
    public ResponseEntity<CityDTO> updateCity(
            @Parameter(description = "ID of the city to update", required = true)
            @PathVariable Long id,
            @Parameter(description = "Updated city data", required = true)
            @RequestBody CityDTO cityDTO) {
        cityDTO.setId(id);
        CityDTO updatedCity = cityService.updateCity(cityDTO);
        return ResponseEntity.ok(updatedCity);
    }

    /**
     * Partially updates details of a city by its identifier.
     *
     * @param cityId     The identifier of the city
     * @param population The new population value (can be null)
     * @param hasMetro   The new metro availability status (can be null)
     * @return ResponseEntity containing the updated city and a status of 200 (OK)
     */
    @PatchMapping("/{cityId}")
    @Operation(summary = "Partially update city details", description = "Updates specific details of a city.")
    public ResponseEntity<CityDTO> updateCityDetails(
            @Parameter(description = "ID of the city to partially update", required = true)
            @PathVariable Long cityId,
            @Parameter(description = "New population value (optional)")
            @RequestParam(required = false) Integer population,
            @Parameter(description = "New metro availability status (optional)")
            @RequestParam(required = false) Boolean hasMetro) {

        CityDTO updatedCity = cityService.updateCityDetails(cityId, population, hasMetro);
        return ResponseEntity.ok(updatedCity);
    }

    /**
     * Deletes a city by its identifier.
     *
     * @param id The identifier of the city
     * @return ResponseEntity with an empty body and a status of 204 (No Content) after successful deletion
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "Delete city", description = "Deletes a city by its identifier.")
    public ResponseEntity<Void> deleteCity(
            @Parameter(description = "ID of the city to delete", required = true)
            @PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}