package by.malahovski.service.impl;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.handler.ServiceNotFoundException;
import by.malahovski.mappers.AttractionMapper;
import by.malahovski.mappers.TourServiceMapper;
import by.malahovski.model.Attraction;
import by.malahovski.model.TourService;
import by.malahovski.repository.TourServiceRepository;
import by.malahovski.service.AttractionService;
import by.malahovski.service.TourService_Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional // Если хотите делать транзакции на уровне класса, оставьте это здесь.
public class TourService_ServiceImpl implements TourService_Service {

    private final TourServiceRepository tourServiceRepository;
    private final TourServiceMapper tourServiceMapper;
    private final AttractionService attractionService; // Внедряем зависимость от сервиса достопримечательностей

    private static final Logger logger = LogManager.getLogger(TourService_ServiceImpl.class);
    private final AttractionMapper attractionMapper;

    @Autowired
    public TourService_ServiceImpl(TourServiceRepository tourServiceRepository,
                                   TourServiceMapper tourServiceMapper,
                                   AttractionService attractionService,
                                   AttractionMapper attractionMapper) { // Добавляем зависимость
        this.tourServiceRepository = tourServiceRepository;
        this.tourServiceMapper = tourServiceMapper;
        this.attractionService = attractionService; // Инициализация нового сервиса
        this.attractionMapper = attractionMapper;
    }

    @Override
    public TourServiceDTO save(TourServiceDTO tourServiceDTO) {
        logger.info("Saving service: {}", tourServiceDTO);
        TourService tourService = tourServiceMapper.toEntity(tourServiceDTO);
        tourService = tourServiceRepository.save(tourService);
        return tourServiceMapper.toDto(tourService);
    }

    @Override
    public TourServiceDTO findById(Long id) {
        logger.info("Fetching service by ID: {}", id);
        return tourServiceRepository.findById(id)
                .map(tourServiceMapper::toDto)
                .orElseThrow(() -> new ServiceNotFoundException("Service with ID " + id + " not found"));
    }

    @Override
    public List<TourServiceDTO> findAll() {
        logger.info("Fetching all services");
        List<TourService> tourServices = tourServiceRepository.findAll();
        return tourServices.stream()
                .map(tourServiceMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Deleting service by ID: {}", id);
        if (tourServiceRepository.existsById(id)) {
            tourServiceRepository.deleteById(id);
            logger.info("Service with ID {} has been deleted", id);
        } else {
            logger.error("Service not found by ID: {}", id);
            throw new ServiceNotFoundException("Service not found with ID " + id);
        }
    }

    @Transactional
    @Override
    public void addAttractionToTourService(Long tourServiceId, Long attractionId) {
        logger.info("Adding attraction ID {} to service ID {}", attractionId, tourServiceId);

        // Используем сервис достопримечательностей вместо прямого доступа к репозиторию
        Attraction attraction = attractionMapper.toEntity(attractionService.getAttractionById(attractionId));

        TourService tourService = tourServiceRepository.findById(tourServiceId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID " + tourServiceId));

        tourService.getAttractions().add(attraction);
        tourServiceRepository.save(tourService);

        logger.info("Attraction added to service successfully");
    }

    @Transactional
    @Override
    public void removeAttractionFromTourService(Long tourServiceId, Long attractionId) {
        logger.info("Removing attraction ID {} from service ID {}", attractionId, tourServiceId);

        // Снова используем сервис
        Attraction attraction = attractionMapper.toEntity(attractionService.getAttractionById(attractionId));

        TourService tourService = tourServiceRepository.findById(tourServiceId)
                .orElseThrow(() -> new ServiceNotFoundException("Service not found with ID " + tourServiceId));

        tourService.getAttractions().remove(attraction);
        tourServiceRepository.save(tourService);

        logger.info("Attraction removed from service successfully");
    }
}