package by.malahovski.controller;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.service.AttractionService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public void addAttraction(@RequestBody AttractionDTO attractionDTO) {
        attractionService.addAttraction(attractionDTO);
    }

    @GetMapping
    public List<AttractionDTO> getAllAttractions() {
        return attractionService.getAllAttractions();
    }

    @GetMapping("/city/{cityId}")
    public List<AttractionDTO> getAttractionsByCity(@PathVariable Long cityId) {
        return attractionService.getAttractionsByCity(cityId);
    }

    @PutMapping("/{id}")
    public void updateAttraction(@PathVariable Long id, @RequestBody AttractionDTO attractionDTO) {
        attractionDTO.setId(id);
        attractionService.updateAttraction(attractionDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteAttraction(@PathVariable Long id) {
        attractionService.deleteAttraction(id);
    }
}
