package by.malahovski.service;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.dtos.CityDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.CityMapper;
import by.malahovski.model.Attraction;
import by.malahovski.model.City;
import by.malahovski.repository.CityRepository;
import by.malahovski.service.impl.CityServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;


class CityServiceImplTest {

    @InjectMocks
    private CityServiceImpl cityService;

    @Mock
    private CityRepository cityRepository;

    @Mock
    private CityMapper cityMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getCityById_ShouldReturnCityDTO_WhenCityExists() {
        Long cityId = 1L;
        List<Attraction> attractions = new ArrayList<>();

        List<AttractionDTO> attractionsDto = new ArrayList<>();

        City city = new City();
        city.setId(cityId);
        city.setName("Test City");
        city.setPopulation(100000);
        city.setHasMetro(false);
        city.setAttractions(attractions);

        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(cityId);
        cityDTO.setName("Test City");
        cityDTO.setPopulation(100000);
        cityDTO.setHasMetro(false);
        cityDTO.setAttractions(attractionsDto);

        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));
        when(cityMapper.toDto(city)).thenReturn(cityDTO);

        CityDTO result = cityService.getCityById(cityId);


        assertNotNull(result);
        assertEquals(cityId, result.getId());
        verify(cityRepository).findById(cityId);
    }

    @Test
    void getCityById_ShouldThrowEntityNotFoundException_WhenCityDoesNotExist() {
        Long cityId = 1L;
        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> cityService.getCityById(cityId));
        assertEquals("City not found with id " + cityId, exception.getMessage());
    }

    @Test
    void addCity_ShouldReturnCityDTO_WhenCityIsAdded() {
        // Arrange
        CityDTO cityDTO = new CityDTO();
        cityDTO.setName("New City");
        cityDTO.setPopulation(50000);
        cityDTO.setHasMetro(true);
        cityDTO.setAttractions(new ArrayList<>());

        City city = new City();
        city.setId(2L);
        city.setName("New City");
        city.setPopulation(50000);
        city.setHasMetro(true);
        city.setAttractions(city.getAttractions());

        when(cityMapper.toEntity(cityDTO)).thenReturn(city);
        when(cityRepository.save(city)).thenReturn(city);
        when(cityMapper.toDto(city)).thenReturn(cityDTO);

        CityDTO result = cityService.addCity(cityDTO);

        assertNotNull(result);
        assertEquals("New City", result.getName());
        assertEquals(cityDTO.getAttractions(), result.getAttractions());
        verify(cityRepository).save(city);
    }

    @Test
    void getAllCities_ShouldReturnListOfCityDTOs() {
        City city1 = new City();
        city1.setId(1L);
        city1.setName("City One");
        city1.setPopulation(100000);
        city1.setHasMetro(false);
        city1.setAttractions(new ArrayList<>());

        City city2 = new City();
        city2.setId(2L);
        city2.setName("City Two");
        city2.setPopulation(200000);
        city2.setHasMetro(true);
        city2.setAttractions(new ArrayList<>());

        List<City> cities = Arrays.asList(city1, city2);
        when(cityRepository.findAll()).thenReturn(cities);
        when(cityMapper.toDto(city1)).thenReturn(new CityDTO(city1.getId(), city1.getName(), city1.getPopulation(), city1.isHasMetro(), new ArrayList<>()));
        when(cityMapper.toDto(city2)).thenReturn(new CityDTO(city2.getId(), city2.getName(), city2.getPopulation(), city2.isHasMetro(), new ArrayList<>()));

        List<CityDTO> result = cityService.getAllCities();

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(cityRepository).findAll();
    }

    @Test
    void updateCity_ShouldReturnUpdatedCityDTO_WhenCityExists() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1L);
        cityDTO.setName("Updated City");
        cityDTO.setPopulation(150000);
        cityDTO.setHasMetro(true);

        City existingCity = new City();
        existingCity.setId(1L);
        existingCity.setName("Old City");
        existingCity.setPopulation(100000);
        existingCity.setHasMetro(false);

        when(cityRepository.findById(cityDTO.getId())).thenReturn(Optional.of(existingCity));

        when(cityRepository.save(existingCity)).thenReturn(existingCity);
        when(cityMapper.toDto(existingCity)).thenReturn(cityDTO);

        CityDTO result = cityService.updateCity(cityDTO);

        assertNotNull(result);
        assertEquals("Updated City", result.getName());
        verify(cityRepository).save(existingCity);
    }

    @Test
    void updateCity_ShouldThrowEntityNotFoundException_WhenCityDoesNotExist() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1L);

        when(cityRepository.findById(cityDTO.getId())).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> cityService.updateCity(cityDTO));
        assertEquals("City not found with id " + cityDTO.getId(), exception.getMessage());
    }

    @Test
    void deleteCity_ShouldDeleteCity_WhenCityExists() {
        Long cityId = 1L;
        City city = new City();
        city.setId(cityId);

        when(cityRepository.findById(cityId)).thenReturn(Optional.of(city));

        cityService.deleteCity(cityId);

        verify(cityRepository).delete(city);
    }

    @Test
    void deleteCity_ShouldThrowEntityNotFoundException_WhenCityDoesNotExist() {
        Long cityId = 1L;
        when(cityRepository.findById(cityId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> cityService.deleteCity(cityId));
        assertEquals("City not found with id " + cityId, exception.getMessage());
    }
}