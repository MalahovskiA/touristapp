package by.malahovski.controller;



import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.City;
import by.malahovski.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attractions")
public class AttractionController {

    private final AttractionService attractionService;

    @Autowired
    public AttractionController(AttractionService attractionService) {
        this.attractionService = attractionService;
    }

    @PostMapping
    public ResponseEntity<AttractionDTO> addAttraction(@RequestBody AttractionDTO attractionDTO) {
        AttractionDTO createdAttraction = attractionService.addAttraction(attractionDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdAttraction);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AttractionDTO> getAttractionById(@PathVariable Long id) {
        AttractionDTO attractionDTO = attractionService.getAttractionById(id);
        return ResponseEntity.ok(attractionDTO);
    }

    @GetMapping("/city/all")
    public List<AttractionDTO> getAllAttractions() {
        return attractionService.getAllAttractions();
    }

    @GetMapping("/city/{cityId}")
    public List<AttractionDTO> getAttractionsByCity(@PathVariable Long cityId) {
        City city = new City();
        city.setId(cityId);
        return attractionService.getAttractionsByCity(city);
    }

    @PutMapping("/{id}")
    public ResponseEntity<AttractionDTO> updateAttraction(@PathVariable Long id, @RequestBody AttractionDTO attractionDTO) {
        attractionDTO.setId(id);
        AttractionDTO updatedAttraction = attractionService.updateAttraction(attractionDTO);
        return ResponseEntity.ok(updatedAttraction);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAttraction(@PathVariable Long id) {
        attractionService.deleteAttraction(id);
        return ResponseEntity.noContent().build();
    }
}
