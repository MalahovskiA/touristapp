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
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
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
                .orElseThrow(() -> new EntityNotFoundException("City with id " + id + " not found."));
        return cityMapper.toDto(city);
    }

    @Override
    public CityDTO addCity(CityDTO cityDTO) {
        logger.info("Adding a city: {}", cityDTO.getName());
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        logger.info("City added: {}", city.getName());
        return cityMapper.toDto(city);
    }

    @Override
    public List<CityDTO> getAllCities() {
        logger.info("Getting a list of cities");
        List<CityDTO> cities = cityRepository.findAll().stream()
                .map(cityMapper::toDto)
                .toList();
        logger.info("Cities found: {}", cities);
        return cities;
    }

    @Override
    public CityDTO updateCity(CityDTO cityDTO) {
        logger.info("Change of city: {}", cityDTO.getName());
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        logger.info("The city has been changed: {}", city.getName());
        return cityMapper.toDto(city);
    }

    @Override
    public void deleteCity(Long id) {
        logger.info("Removing city with ID: {}", id);
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException("City with id " + id + " not found."));
        cityRepository.delete(city);
        logger.info("City removed ID: {}", id);
    }
}