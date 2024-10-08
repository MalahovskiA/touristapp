package by.malahovski.controller;

import by.malahovski.dtos.CityDTO;
import by.malahovski.service.CityService;
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
    public ResponseEntity<CityDTO> addCity(@RequestBody CityDTO cityDTO) {
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
    public ResponseEntity<CityDTO> getCityById(@PathVariable Long id) {
        CityDTO cityDTO = cityService.getCityById(id);
        return ResponseEntity.ok(cityDTO);
    }

    /**
     * Retrieves a list of all cities.
     *
     * @return A list of {@link CityDTO} objects representing all cities
     */
    @GetMapping
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    /**
     * Updates a city's information by its identifier.
     *
     * @param id The identifier of the city
     * @param cityDTO The {@link CityDTO} object containing updated information about the city
     * @return ResponseEntity containing the updated city and a status of 200 (OK)
     */
    @PutMapping("/{id}")
    public ResponseEntity<CityDTO> updateCity(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
        cityDTO.setId(id);
        CityDTO updatedCity = cityService.updateCity(cityDTO);
        return ResponseEntity.ok(updatedCity);
    }

    /**
     * Partially updates details of a city by its identifier.
     *
     * @param cityId The identifier of the city
     * @param population The new population value (can be null)
     * @param hasMetro The new metro availability status (can be null)
     * @return ResponseEntity containing the updated city and a status of 200 (OK)
     */
    @PatchMapping("/{cityId}")
    public ResponseEntity<CityDTO> updateCityDetails(
            @PathVariable Long cityId,
            @RequestParam(required = false) Integer population,
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
    public ResponseEntity<Void> deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
        return ResponseEntity.noContent().build();
    }
}