package by.malahovski.controller;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.service.TourManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/tour-services")
public class TourServiceController {

    private static final Logger logger = LogManager.getLogger(TourServiceController.class);

    private final TourManagementService tourService;

    @Autowired
    public TourServiceController(TourManagementService tourService) {
        this.tourService = tourService;
    }

    @PostMapping
    public ResponseEntity<TourServiceDTO> createTourService(@RequestBody TourServiceDTO tourServiceDTO) {
        logger.info("Создание новой услуги: {}", tourServiceDTO);
        TourServiceDTO createdTourService = tourService.save(tourServiceDTO);
        return ResponseEntity.ok(createdTourService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourServiceDTO> getTourServiceById(@PathVariable Long id) {
        logger.info("Получение услуги по ID: {}", id);
        TourServiceDTO tourServiceDTO = tourService.findById(id);
        return ResponseEntity.ok(tourServiceDTO);
    }

    @GetMapping
    public ResponseEntity<List<TourServiceDTO>> getAllTourServices() {
        logger.info("Получение всех услуг");
        List<TourServiceDTO> tourServices = tourService.findAll();
        return ResponseEntity.ok(tourServices);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourService(@PathVariable Long id) {
        logger.info("Удаление услуги по ID: {}", id);
        tourService.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tourServiceId}/attractions/{attractionId}")
    public ResponseEntity<Void> addAttractionToTourService(@PathVariable Long tourServiceId, @PathVariable Long attractionId) {
        logger.info("Добавление достопримечательности с ID: {} к услуге с ID: {}", attractionId, tourServiceId);
        tourService.addAttractionToTourService(tourServiceId, attractionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tourServiceId}/attractions/{attractionId}")
    public ResponseEntity<Void> removeAttractionFromTourService(@PathVariable Long tourServiceId, @PathVariable Long attractionId) {
        logger.info("Удаление достопримечательности с ID: {} из услуги с ID: {}", attractionId, tourServiceId);
        tourService.removeAttractionFromTourService(tourServiceId, attractionId);
        return ResponseEntity.ok().build();
    }
}