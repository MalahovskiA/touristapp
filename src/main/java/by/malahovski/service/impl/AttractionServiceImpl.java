package by.malahovski.service.impl;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.AttractionMapper;
import by.malahovski.model.Attraction;
import by.malahovski.repository.AttractionRepository;
import by.malahovski.service.AttractionService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AttractionServiceImpl implements AttractionService {
    private final AttractionRepository attractionRepository;
    private final AttractionMapper attractionMapper;

    public AttractionServiceImpl(AttractionRepository attractionRepository, AttractionMapper attractionMapper) {
        this.attractionRepository = attractionRepository;
        this.attractionMapper = attractionMapper;
    }

    @Override
    public AttractionDTO getAttractionById(Long id) {
        Attraction attraction = attractionRepository.findById(id);
        if (attraction == null) {
            throw new EntityNotFoundException("Достопримечательность с id " + id + " не найдена.");
        }
        return attractionMapper.toDto(attraction);
    }

    @Override
    public void addAttraction(AttractionDTO attractionDTO) {
        Attraction attraction = attractionMapper.toEntity(attractionDTO);
        attractionRepository.save(attraction);
    }

    @Override
    public List<AttractionDTO> getAllAttractions() {
        return attractionRepository.findAll().stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public List<AttractionDTO> getAttractionsByCity(Long cityId) {
        return attractionRepository.findByCity(cityId).stream()
                .map(attractionMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public void updateAttraction(AttractionDTO attractionDTO) {
        Attraction attraction = attractionMapper.toEntity(attractionDTO);
        attractionRepository.update(attraction);
    }

    @Override
    public void deleteAttraction(Long id) {
        attractionRepository.delete(id);
    }
}
