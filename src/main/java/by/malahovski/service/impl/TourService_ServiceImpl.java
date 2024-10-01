package by.malahovski.service.impl;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.mappers.TourServiceMapper;
import by.malahovski.model.TourService;
import by.malahovski.repository.TourServiceRepository;
import by.malahovski.service.TourService_Service;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

@Service
@Transactional
public class TourService_ServiceImpl implements TourService_Service {

    private final TourServiceRepository tourServiceRepository;

    private final TourServiceMapper tourServiceMapper;

    private static final Logger logger = LogManager.getLogger(TourService_ServiceImpl.class);


    @Autowired
    public TourService_ServiceImpl(TourServiceRepository tourServiceRepository,
                                   TourServiceMapper tourServiceMapper) {
        this.tourServiceRepository = tourServiceRepository;
        this.tourServiceMapper = tourServiceMapper;
    }

    @Override
    public TourServiceDTO save(TourServiceDTO tourServiceDTO) {
        logger.info("Save service: {}", tourServiceDTO);
        TourService tourService = tourServiceMapper.toEntity(tourServiceDTO);
        tourService = tourServiceRepository.save(tourService);
        return tourServiceMapper.toDto(tourService);
    }

    @Override
    public TourServiceDTO findById(Long id) {
        logger.info("Поиск услуги по ID: {}", id);
        Optional<TourService> optionalTourService = tourServiceRepository.findById(id);
        if (optionalTourService.isPresent()) {
            return tourServiceMapper.toDto(optionalTourService.get());
        } else {
            logger.error("Service with ID not found: {}", id);
            throw new RuntimeException("Service with ID not found");
        }
    }

    public List<TourServiceDTO> findAll() {
        logger.info("Поиск всех услуг");
        List<TourService> tourServices = tourServiceRepository.findAll();
        return tourServices.stream()
                .map(tourServiceMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        logger.info("Удаление услуги по ID: {}", id);
        if (tourServiceRepository.existsById(id)) {
            tourServiceRepository.deleteById(id);
            logger.info("Услуга с ID: {} была удалена", id);
        } else {
            logger.error("Услуга не найдена по ID: {}", id);
            throw new RuntimeException("Услуга не найдена");
        }
    }

    @Override
    public void addAttractionToTourService(Long tourServiceId, Long attractionId) {
        logger.info("Добавление достопримечательности с ID: {} к услуге с ID: {}", attractionId, tourServiceId);
        TourService tourService = tourServiceRepository.findById(tourServiceId)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));
        tourServiceRepository.save(tourService);
        logger.info("Достопримечательность успешно добавлена к услуге");
    }

    @Override
    public void removeAttractionFromTourService(Long tourServiceId, Long attractionId) {
        logger.info("Удаление достопримечательности с ID: {} из услуги с ID: {}", attractionId, tourServiceId);
        TourService tourService = tourServiceRepository.findById(tourServiceId)
                .orElseThrow(() -> new RuntimeException("Услуга не найдена"));
        tourServiceRepository.save(tourService);
        logger.info("Достопримечательность успешно удалена из услуги");
    }
}