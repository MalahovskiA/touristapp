package by.malahovski.controller;


import by.malahovski.dtos.AttractionDTO;
import by.malahovski.service.AttractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attractions")
public class AttractionController {

    private final AttractionService attractionService;
    private static final Logger logger = LogManager.getLogger(AttractionController.class);

    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @PostMapping
    public ResponseEntity<AttractionDTO> addAttraction(@RequestBody AttractionDTO attractionDTO) {
        logger.info("Adding attraction: {}", attractionDTO.getName());
        AttractionDTO createdAttraction = attractionService.addAttraction(attractionDTO);
        return ResponseEntity.status(201).body(createdAttraction);
    }

    @PutMapping
    public ResponseEntity<AttractionDTO> updateAttraction(@RequestBody AttractionDTO attractionDTO) {
        logger.info("Updating attraction with ID: {}", attractionDTO.getId());
        AttractionDTO updatedAttraction = attractionService.updateAttraction(attractionDTO);
        return ResponseEntity.ok(updatedAttraction);
    }

    @GetMapping
    public ResponseEntity<List<AttractionDTO>> getAllAttractions(
            @RequestParam(required = false) String sortBy,
            @RequestParam(required = false) String filterByType) {
        logger.info("Fetching all attractions sorted by: {} and filtered by type: {}", sortBy, filterByType);
        List<AttractionDTO> attractions = attractionService.getAllAttractions(sortBy, filterByType);
        return ResponseEntity.ok(attractions);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttractionDTO> getAttractionById(@PathVariable Long id) {
        logger.info("Fetching attraction by ID: {}", id);
        AttractionDTO attraction = attractionService.getAttractionById(id);
        return ResponseEntity.ok(attraction);
    }

    @GetMapping("/city/{cityName}")
    public ResponseEntity<List<AttractionDTO>> getAttractionsByCityName(@PathVariable String cityName) {
        logger.info("Fetching attractions for city: {}", cityName);
        List<AttractionDTO> attractions = attractionService.getAttractionsByCityName(cityName);
        return ResponseEntity.ok(attractions);
    }

    @PatchMapping("/{id}/description")
    public ResponseEntity<AttractionDTO> updateAttractionDescription(@PathVariable Long id, @RequestBody String newDescription) {
        logger.info("Updating description for attraction ID: {}", id);
        AttractionDTO updatedAttraction = attractionService.updateDescription(id, newDescription);
        return ResponseEntity.ok(updatedAttraction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable Long id) {
        logger.info("Deleting attraction with ID: {}", id);
        attractionService.deleteAttraction(id);
        return ResponseEntity.noContent().build();
    }
}