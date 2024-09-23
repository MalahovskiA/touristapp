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
    public List<AttractionDTO> getAttractionsByCity(City city) {
        if (city == null) {
            throw new IllegalArgumentException("Город не должен быть null");
        }
        logger.info("Получение достопримечательностей для города: {}", city.getName());

        List<AttractionDTO> attractions = attractionRepository.findByCity(city).stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());

        if (attractions.isEmpty()) {
            logger.warn("Не найдено достопримечательностей для города {}", city.getName());
        } else {
            logger.info("Найдено {} достопримечательностей для города {}", attractions.size(), city.getName());
        }
        return attractions;
    }

    @Override
    public AttractionDTO getAttractionById(Long id) {
        logger.info("Получение достопримечательности с ID: {}", id);
        Attraction attraction = attractionRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Достопримечательность с id " + id + " не найдена."));
        return attractionMapper.toDto(attraction);
    }

    @Override
    public AttractionDTO addAttraction(AttractionDTO attractionDTO) {
        logger.info("Добавление достопримечательности: {}", attractionDTO.getName());
        Attraction attraction = attractionMapper.toEntity(attractionDTO);
        attraction = attractionRepository.save(attraction);
        logger.info("Достопримечательность добавлена: {}", attraction.getName());
        return attractionMapper.toDto(attraction); // Возвращаем обновленное значение
    }

    @Override
    public List<AttractionDTO> getAllAttractions() {
        logger.info("Получение списка всех достопримечательностей");
        return attractionRepository.findAll().stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public AttractionDTO updateAttraction(AttractionDTO attractionDTO) {
        logger.info("Обновление достопримечательности: {}", attractionDTO.getName());
        Attraction attraction = attractionMapper.toEntity(attractionDTO);
        attraction = attractionRepository.save(attraction); // Используем save для обновления
        logger.info("Достопримечательность обновлена: {}", attraction.getName());
        return attractionMapper.toDto(attraction); // Возвращаем обновленное значение
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