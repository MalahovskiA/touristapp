package by.malahovski.controller;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.service.TourService_Service;
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

    private final TourService_Service tourService_Service;

    @Autowired
    public TourServiceController(TourService_Service tourService_Service) {
        this.tourService_Service = tourService_Service;
    }

    @PostMapping
    public ResponseEntity<TourServiceDTO> createTourService(@RequestBody TourServiceDTO tourServiceDTO) {
        logger.info("Создание новой услуги: {}", tourServiceDTO);
        TourServiceDTO createdTourService = tourService_Service.save(tourServiceDTO);
        return ResponseEntity.ok(createdTourService);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TourServiceDTO> getTourServiceById(@PathVariable Long id) {
        logger.info("Получение услуги по ID: {}", id);
        TourServiceDTO tourServiceDTO = tourService_Service.findById(id);
        return ResponseEntity.ok(tourServiceDTO);
    }

    @GetMapping
    public ResponseEntity<List<TourServiceDTO>> getAllTourServices() {
        logger.info("Получение всех услуг");
        List<TourServiceDTO> tourServices = tourService_Service.findAll();
        return ResponseEntity.ok(tourServices);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTourService(@PathVariable Long id) {
        logger.info("Удаление услуги по ID: {}", id);
        tourService_Service.deleteById(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{tourServiceId}/attractions/{attractionId}")
    public ResponseEntity<Void> addAttractionToTourService(@PathVariable Long tourServiceId, @PathVariable Long attractionId) {
        logger.info("Добавление достопримечательности с ID: {} к услуге с ID: {}", attractionId, tourServiceId);
        tourService_Service.addAttractionToTourService(tourServiceId, attractionId);
        return ResponseEntity.ok().build();
    }

    @DeleteMapping("/{tourServiceId}/attractions/{attractionId}")
    public ResponseEntity<Void> removeAttractionFromTourService(@PathVariable Long tourServiceId, @PathVariable Long attractionId) {
        logger.info("Удаление достопримечательности с ID: {} из услуги с ID: {}", attractionId, tourServiceId);
        tourService_Service.removeAttractionFromTourService(tourServiceId, attractionId);
        return ResponseEntity.ok().build();
    }
}