package by.malahovski.service.impl;

import by.malahovski.dtos.CityDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.CityMapper;
import by.malahovski.model.City;
import by.malahovski.repository.CityRepository;
import by.malahovski.service.CityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;


@Service
@Transactional
public class CityServiceImpl implements CityService {

    private final CityRepository cityRepository;
    private final CityMapper cityMapper;

    public static final String CITY_NOT_FOUND = "City not found with id";


    @Autowired
    public CityServiceImpl(CityRepository cityRepository, CityMapper cityMapper) {
        this.cityRepository = cityRepository;
        this.cityMapper = cityMapper;
    }

    @Override
    public CityDTO getCityById(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CITY_NOT_FOUND + id));
        return cityMapper.toDto(city);
    }

    @Override
    public CityDTO addCity(CityDTO cityDTO) {
        City city = cityMapper.toEntity(cityDTO);
        city = cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    @Override
    public List<CityDTO> getAllCities() {
        return cityRepository.findAll().stream()
                .map(cityMapper::toDto)
                .toList();
    }

    @Override
    public CityDTO updateCity(CityDTO cityDTO) {
        City city = cityRepository.findById(cityDTO.getId())
                .orElseThrow(() -> new EntityNotFoundException(CITY_NOT_FOUND + cityDTO.getId()));

        city.setName(cityDTO.getName());
        city.setPopulation(cityDTO.getPopulation());
        city.setHasMetro(cityDTO.isHasMetro());

        city = cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    @Override
    @Transactional
    public CityDTO updateCityDetails(Long cityId, Integer population, Boolean hasMetro) {
        City city = cityRepository.findById(cityId)
                .orElseThrow(() -> new EntityNotFoundException(CITY_NOT_FOUND + cityId));

        if (population != null) {
            city.setPopulation(population);
        }

        if (hasMetro != null) {
            city.setHasMetro(hasMetro);
        }

        city = cityRepository.save(city);
        return cityMapper.toDto(city);
    }

    @Override
    public void deleteCity(Long id) {
        City city = cityRepository.findById(id)
                .orElseThrow(() -> new EntityNotFoundException(CITY_NOT_FOUND + id));
        cityRepository.delete(city);
    }
}