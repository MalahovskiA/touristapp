package by.malahovski.service.impl;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.AttractionMapper;
import by.malahovski.mappers.TourServiceMapper;
import by.malahovski.model.Attraction;
import by.malahovski.model.TourService;
import by.malahovski.repository.TourServiceRepository;
import by.malahovski.service.AttractionService;
import by.malahovski.service.TourManagementService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
@Transactional
public class TourManagementServiceImpl implements TourManagementService {

    private final TourServiceRepository tourServiceRepository;
    private final TourServiceMapper tourServiceMapper;
    private final AttractionService attractionService;

    private static final Logger logger = LogManager.getLogger(TourManagementServiceImpl.class);
    private final AttractionMapper attractionMapper;

    public static final String SERVICE_NOT_FOUND = "Service not found with ID";

    @Autowired
    public TourManagementServiceImpl(TourServiceRepository tourServiceRepository,
                                     TourServiceMapper tourServiceMapper,
                                     AttractionService attractionService,
                                     AttractionMapper attractionMapper) {
        this.tourServiceRepository = tourServiceRepository;
        this.tourServiceMapper = tourServiceMapper;
        this.attractionService = attractionService;
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
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + id + " not found"));
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
            throw new EntityNotFoundException(SERVICE_NOT_FOUND + id);
        }
    }

    @Transactional
    @Override
    public void addAttractionToTourService(Long tourServiceId, Long attractionId) {
        logger.info("Adding attraction ID {} to service ID {}", attractionId, tourServiceId);

        // Используем сервис достопримечательностей вместо прямого доступа к репозиторию
        Attraction attraction = attractionMapper.toEntity(attractionService.getAttractionById(attractionId));

        TourService tourService = tourServiceRepository.findById(tourServiceId)
                .orElseThrow(() -> new EntityNotFoundException(SERVICE_NOT_FOUND + tourServiceId));

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
                .orElseThrow(() -> new EntityNotFoundException(SERVICE_NOT_FOUND + tourServiceId));

        tourService.getAttractions().remove(attraction);
        tourServiceRepository.save(tourService);

        logger.info("Attraction removed from service successfully");
    }
}