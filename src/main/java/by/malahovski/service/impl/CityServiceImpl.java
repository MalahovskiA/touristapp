package by.malahovski.service.impl;

import by.malahovski.dtos.CityDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.CityMapper;
import by.malahovski.model.City;
import by.malahovski.repository.CityRepository;
import by.malahovski.service.CityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;

    private final CityMapper cityMapper;

    private static final Logger logger = LogManager.getLogger(CityServiceImpl.class);

    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public CityDTO getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Город с id " + id + " не найден."));
        return cityMapper.toDto(city);
    }

    @Override
    public CityDTO addCity(CityDTO cityDTO) {
        logger.info("Добавление города: {}", cityDTO.getName());
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        logger.info("Город добавлен: {}", city.getName());
        return cityMapper.toDto(city);
    }

    @Override
    public List<CityDTO> getAllCities() {
        logger.info("Получение списка городов");
        return cityRepository.findAll().stream()
                .map(cityMapper::toDto)
                .collect(Collectors.toList());
    }

    @Override
    public CityDTO updateCity(CityDTO cityDTO) {
        logger.info("Изменение города: {}", cityDTO.getName());
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        logger.info("Город изменен: {}", city.getName());
        return cityMapper.toDto(city);
    }

    @Override
    public void deleteCity(Long id) {
        logger.info("Удаление города с ID: {}", id);
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("Город с id " + id + " не найден."));
        cityRepository.delete(city);
        logger.info("Город удален ID: {}", id);
    }
}