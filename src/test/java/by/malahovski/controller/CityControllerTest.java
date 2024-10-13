package by.malahovski.controller;

import by.malahovski.dtos.CityDTO;
import by.malahovski.service.CityService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

class CityControllerTest {

    @Mock
    private CityService cityService;

    @InjectMocks
    private CityController cityController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addCity() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1L);
        cityDTO.setName("Moscow");
        cityDTO.setPopulation(12615882);
        cityDTO.setHasMetro(true);

        when(cityService.addCity(any(CityDTO.class))).thenReturn(cityDTO);

        ResponseEntity<CityDTO> responseEntity = cityController.addCity(cityDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Moscow", responseEntity.getBody().getName());

        ArgumentCaptor<CityDTO> argumentCaptor = ArgumentCaptor.forClass(CityDTO.class);
        verify(cityService).addCity(argumentCaptor.capture());
        assertEquals("Moscow", argumentCaptor.getValue().getName());
    }

    @Test
    void getCityById() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1L);
        cityDTO.setName("Moscow");
        cityDTO.setPopulation(12615882);
        cityDTO.setHasMetro(true);

        when(cityService.getCityById(1L)).thenReturn(cityDTO);

        ResponseEntity<CityDTO> responseEntity = cityController.getCityById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Moscow", responseEntity.getBody().getName());

        verify(cityService).getCityById(1L);
    }

    @Test
    void getAllCities() {
        CityDTO city1 = new CityDTO();
        city1.setId(1L);
        city1.setName("Moscow");
        city1.setPopulation(12615882);
        city1.setHasMetro(true);

        CityDTO city2 = new CityDTO();
        city2.setId(2L);
        city2.setName("Saint Petersburg");
        city2.setPopulation(5351935);
        city2.setHasMetro(true);

        List<CityDTO> cities = Arrays.asList(city1, city2);
        when(cityService.getAllCities()).thenReturn(cities);

        List<CityDTO> responseCities = cityController.getAllCities();

        assertNotNull(responseCities);
        assertEquals(2, responseCities.size());
        assertEquals("Moscow", responseCities.get(0).getName());
        assertEquals("Saint Petersburg", responseCities.get(1).getName());

        verify(cityService).getAllCities();
    }

    @Test
    void updateCity() {
        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1L);
        cityDTO.setName("Moscow");
        cityDTO.setPopulation(12615882);
        cityDTO.setHasMetro(true);

        when(cityService.updateCity(any(CityDTO.class))).thenReturn(cityDTO);

        ResponseEntity<CityDTO> responseEntity = cityController.updateCity(1L, cityDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Moscow", responseEntity.getBody().getName());

        verify(cityService).updateCity(any(CityDTO.class));
    }

    @Test
    void deleteCity() {
        doNothing().when(cityService).deleteCity(1L);

        ResponseEntity<Void> responseEntity = cityController.deleteCity(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(cityService).deleteCity(1L);
    }
}