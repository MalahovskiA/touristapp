package by.malahovski.controller;

import by.malahovski.dtos.CityDTO;
import by.malahovski.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cities")
public class CityController {

    private final CityService cityService;

    @Autowired
    public CityController(CityService cityService) {
        this.cityService = cityService;
    }

    @PostMapping
    public void addCity(@RequestBody CityDTO cityDTO) {
        cityService.addCity(cityDTO);
    }

    @GetMapping
    public List<CityDTO> getAllCities() {
        return cityService.getAllCities();
    }

    @PutMapping("/{id}")
    public void updateCity(@PathVariable Long id, @RequestBody CityDTO cityDTO) {
        cityDTO.setId(id);
        cityService.updateCity(cityDTO);
    }

    @DeleteMapping("/{id}")
    public void deleteCity(@PathVariable Long id) {
        cityService.deleteCity(id);
    }
}
