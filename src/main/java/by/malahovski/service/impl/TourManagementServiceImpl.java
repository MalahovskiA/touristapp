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
        TourService tourService = tourServiceMapper.toEntity(tourServiceDTO);
        tourService = tourServiceRepository.save(tourService);
        return tourServiceMapper.toDto(tourService);
    }

    @Override
    public TourServiceDTO findById(Long id) {
        return tourServiceRepository.findById(id)
                .map(tourServiceMapper::toDto)
                .orElseThrow(() -> new EntityNotFoundException("Service with ID " + id + " not found"));
    }

    @Override
    public List<TourServiceDTO> findAll() {
        List<TourService> tourServices = tourServiceRepository.findAll();
        return tourServices.stream()
                .map(tourServiceMapper::toDto)
                .toList();
    }

    @Override
    public void deleteById(Long id) {
        if (tourServiceRepository.existsById(id)) {
            tourServiceRepository.deleteById(id);
        } else {
            throw new EntityNotFoundException(SERVICE_NOT_FOUND + id);
        }
    }

    @Transactional
    @Override
    public void addAttractionToTourService(Long tourServiceId, Long attractionId) {

        Attraction attraction = attractionMapper.toEntity(attractionService.getAttractionById(attractionId));

        TourService tourService = tourServiceRepository.findById(tourServiceId)
                .orElseThrow(() -> new EntityNotFoundException(SERVICE_NOT_FOUND + tourServiceId));

        tourService.getAttractions().add(attraction);
        tourServiceRepository.save(tourService);
    }

    @Transactional
    @Override
    public void removeAttractionFromTourService(Long tourServiceId, Long attractionId) {

        Attraction attraction = attractionMapper.toEntity(attractionService.getAttractionById(attractionId));

        TourService tourService = tourServiceRepository.findById(tourServiceId)
                .orElseThrow(() -> new EntityNotFoundException(SERVICE_NOT_FOUND + tourServiceId));

        tourService.getAttractions().remove(attraction);
        tourServiceRepository.save(tourService);
    }
}