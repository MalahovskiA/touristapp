package by.malahovski.service.impl;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.AttractionMapper;
import by.malahovski.model.Attraction;
import by.malahovski.model.City;
import by.malahovski.repository.AttractionRepository;
import by.malahovski.service.AttractionService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttractionServiceImpl implements AttractionService {

    private final AttractionRepository attractionRepository;
    private final AttractionMapper attractionMapper;
    private static final Logger logger = LogManager.getLogger(AttractionServiceImpl.class);

    @Autowired
    public AttractionServiceImpl(AttractionRepository attractionRepository, AttractionMapper attractionMapper) {
        this.attractionRepository = attractionRepository;
        this.attractionMapper = attractionMapper;
    }

    @Override
    public AttractionDTO addAttraction(AttractionDTO attractionDTO) {
        logger.info("Добавление достопримечательности: {}", attractionDTO.getName());
        Attraction attraction = attractionMapper.toEntity(attractionDTO);
        attraction = attractionRepository.save(attraction);
        logger.info("Достопримечательность добавлена: {}", attraction.getName());
        return attractionMapper.toDto(attraction);
    }

    @Override
    public AttractionDTO updateAttraction(AttractionDTO attractionDTO) {
        return null;
    }

    @Override
    public List<AttractionDTO> getAllAttractions() {
        return List.of();
    }

    @Override
    public List<AttractionDTO> getAllAttractions(String sortBy, String filterByType) {
        logger.info("Получение всех достопримечательностей с сортировкой по: {} и фильтрацией по типу: {}", sortBy, filterByType);

        List<Attraction> attractions = attractionRepository.findAll();

        if (filterByType != null) {
            attractions = attractions.stream()
                    .filter(attraction -> attraction.getType().name().equalsIgnoreCase(filterByType))
                    .collect(Collectors.toList());
        }

        if ("name".equalsIgnoreCase(sortBy)) {
            attractions = attractions.stream()
                    .sorted(Comparator.comparing(Attraction::getName))
                    .collect(Collectors.toList());
        }

        logger.info("Найдено {} достопримечательностей", attractions.size());
        return attractions.stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttractionDTO> getAttractionsByCity(City city) {
        logger.info("Получение достопримечательностей города: {}", city.getName());
        List<Attraction> attractions = attractionRepository.findByCity(city);

        if (attractions.isEmpty()) {
            logger.warn("Не найдено достопримечательностей для города {}", city.getName());
        }

        return attractions.stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttractionDTO getAttractionById(Long id) {
        return null;
    }

    @Override
    public AttractionDTO updateDescription(Long id, String newDescription) {
        logger.info("Обновление описания достопримечательности с ID: {}", id);
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Достопримечательность с id " + id + " не найдена."));

        attraction.setDescription(newDescription);
        attraction = attractionRepository.save(attraction);

        logger.info("Описание обновлено для достопримечательности: {}", attraction.getName());
        return attractionMapper.toDto(attraction);
    }

    @Override
    public void deleteAttraction(Long id) {
        logger.info("Удаление достопримечательности с ID: {}", id);
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Достопримечательность с id " + id + " не найдена."));
        attractionRepository.delete(attraction);
        logger.info("Достопримечательность удалена ID: {}", id);
    }
}