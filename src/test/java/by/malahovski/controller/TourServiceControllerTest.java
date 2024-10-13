package by.malahovski.controller;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.service.TourManagementService;
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

class TourServiceControllerTest {

    @Mock
    private TourManagementService tourService;

    @InjectMocks
    private TourServiceController tourServiceController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void createTourService() {
        TourServiceDTO tourServiceDTO = new TourServiceDTO();
        tourServiceDTO.setId(1L);
        tourServiceDTO.setName("City Tour");

        when(tourService.save(any(TourServiceDTO.class))).thenReturn(tourServiceDTO);

        ResponseEntity<TourServiceDTO> responseEntity = tourServiceController.createTourService(tourServiceDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("City Tour", responseEntity.getBody().getName());

        ArgumentCaptor<TourServiceDTO> argumentCaptor = ArgumentCaptor.forClass(TourServiceDTO.class);
        verify(tourService).save(argumentCaptor.capture());
        assertEquals("City Tour", argumentCaptor.getValue().getName());
    }

    @Test
    void getTourServiceById() {
        TourServiceDTO tourServiceDTO = new TourServiceDTO();
        tourServiceDTO.setId(1L);
        tourServiceDTO.setName("City Tour");

        when(tourService.findById(1L)).thenReturn(tourServiceDTO);

        ResponseEntity<TourServiceDTO> responseEntity = tourServiceController.getTourServiceById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("City Tour", responseEntity.getBody().getName());

        verify(tourService).findById(1L);
    }

    @Test
    void getAllTourServices() {
        TourServiceDTO tourService1 = new TourServiceDTO();
        tourService1.setId(1L);
        tourService1.setName("City Tour");

        TourServiceDTO tourService2 = new TourServiceDTO();
        tourService2.setId(2L);
        tourService2.setName("Adventure Tour");

        List<TourServiceDTO> tourServices = Arrays.asList(tourService1, tourService2);
        when(tourService.findAll()).thenReturn(tourServices);

        ResponseEntity<List<TourServiceDTO>> responseEntity = tourServiceController.getAllTourServices();

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals("City Tour", responseEntity.getBody().get(0).getName());
        assertEquals("Adventure Tour", responseEntity.getBody().get(1).getName());

        verify(tourService).findAll();
    }

    @Test
    void addAttractionToTourService() {
        doNothing().when(tourService).addAttractionToTourService(1L, 1L);

        ResponseEntity<Void> responseEntity = tourServiceController.addAttractionToTourService(1L, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(tourService).addAttractionToTourService(1L, 1L);
    }

    @Test
    void deleteTourService() {
        doNothing().when(tourService).deleteById(1L);

        ResponseEntity<Void> responseEntity = tourServiceController.deleteTourService(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());
        verify(tourService).deleteById(1L);
    }

    @Test
    void removeAttractionFromTourService() {
        doNothing().when(tourService).removeAttractionFromTourService(1L, 1L);

        ResponseEntity<Void> responseEntity = tourServiceController.removeAttractionFromTourService(1L, 1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        verify(tourService).removeAttractionFromTourService(1L, 1L);
    }
}