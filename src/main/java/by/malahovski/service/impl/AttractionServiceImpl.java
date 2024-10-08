package by.malahovski.service.impl;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.AttractionMapper;
import by.malahovski.model.Attraction;
import by.malahovski.repository.AttractionRepository;
import by.malahovski.service.AttractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Comparator;
import java.util.List;

@Service
@Transactional
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionMapper attractionMapper;
    private static final Logger logger = LogManager.getLogger(AttractionServiceImpl.class);

    public static final String ATTRACTION_NOT_FOUND = "Attraction not found with ID";

    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository, AttractionMapper attractionMapper) {
        this.attractionRepository = attractionRepository;
        this.attractionMapper = attractionMapper;
    }

    @Override
    public AttractionDTO addAttraction(AttractionDTO attractionDTO) {
        logger.info("Adding a landmark: {}", attractionDTO.getName());
        Attraction attraction = attractionMapper.toEntity(attractionDTO);
        attraction = attractionRepository.save(attraction);
        logger.info("Landmark added: {}", attraction.getName());
        return attractionMapper.toDto(attraction);
    }

    @Override
    public AttractionDTO updateAttraction(AttractionDTO attractionDTO) {
        logger.info("Updating a landmark with ID: {}", attractionDTO.getId());
        Attraction attraction = attractionRepository.findById(attractionDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(ATTRACTION_NOT_FOUND + attractionDTO.getId()));

        attraction.setName(attractionDTO.getName());
        attraction.setDescription(attractionDTO.getDescription());
        attraction.setType(attractionDTO.getType());
        attraction.setCreationDate(attractionDTO.getCreationDate());

        attraction = attractionRepository.save(attraction);
        logger.info("The landmark has been updated: {}", attraction.getName());
        return attractionMapper.toDto(attraction);
    }

    @Override
    public List<AttractionDTO> getAllAttractions(String sortBy, String filterByType) {
        logger.info("Get all attractions sorted by: {} and filtered by type: {}", sortBy, filterByType);
        List<Attraction> attractions = attractionRepository.findAll();

        if (filterByType != null && !filterByType.isEmpty()) {
            attractions = attractions.stream()
                    .filter(attraction -> attraction.getType().name().equalsIgnoreCase(filterByType))
                    .toList();
        }

        if ("name".equalsIgnoreCase(sortBy)) {
            attractions = attractions.stream()
                    .sorted(Comparator.comparing(Attraction::getName))
                    .toList();
        }

        logger.info("Found {} attractions", attractions.size());
        return attractions.stream()
                .map(attractionMapper::toDto)
                .toList();
    }

    @Override
    public List<AttractionDTO> getAttractionsByCityName(String cityName) {
        logger.info("Getting City Attractions: {}", cityName);
        List<Attraction> attractions = attractionRepository.findByCityName(cityName);

        if (attractions.isEmpty()) {
            logger.warn("No attractions found for the city {}", cityName);
        }

        return attractions.stream()
                .map(attractionMapper::toDto)
                .toList();
    }

    @Override
    public AttractionDTO getAttractionById(Long id) {
        logger.info("Getting a landmark by ID: {}", id);
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ATTRACTION_NOT_FOUND + id));
        return attractionMapper.toDto(attraction);
    }

    @Override
    public AttractionDTO updateDescription(Long id, String newDescription) {
        logger.info("Updating the description of the landmark with ID: {}", id);
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ATTRACTION_NOT_FOUND + id));

        attraction.setDescription(newDescription);
        attraction = attractionRepository.save(attraction);

        logger.info("Description updated for landmark: {}", attraction.getName());
        return attractionMapper.toDto(attraction);
    }

    @Override
    public void deleteAttraction(Long id) {
        logger.info("Removing landmarks with ID: {}", id);
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(ATTRACTION_NOT_FOUND + id));
        attractionRepository.delete(attraction);
        logger.info("Landmark removed ID: {}", id);
    }
}