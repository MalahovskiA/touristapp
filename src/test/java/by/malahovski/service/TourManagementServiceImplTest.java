package by.malahovski.service;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.TourServiceMapper;
import by.malahovski.model.TourService;
import by.malahovski.repository.TourServiceRepository;
import by.malahovski.service.impl.TourManagementServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.util.Collections;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;


class TourManagementServiceImplTest {

    @Mock
    private TourServiceRepository tourServiceRepository;

    @Mock
    private TourServiceMapper tourServiceMapper;

    @InjectMocks
    private TourManagementServiceImpl tourManagementService;

    private TourService tourService;
    private TourServiceDTO tourServiceDTO;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);

        tourService = new TourService();
        tourService.setId(1L);
        tourService.setName("City Tour");
        tourService.setDescription("A wonderful tour of the city.");

        tourServiceDTO = new TourServiceDTO();
        tourServiceDTO.setId(1L);
        tourServiceDTO.setName("City Tour");
        tourServiceDTO.setDescription("A wonderful tour of the city.");
    }

    @Test
    void save_ShouldReturnSavedTourServiceDTO() {
        when(tourServiceMapper.toEntity(tourServiceDTO)).thenReturn(tourService);
        when(tourServiceRepository.save(tourService)).thenReturn(tourService);
        when(tourServiceMapper.toDto(tourService)).thenReturn(tourServiceDTO);

        TourServiceDTO result = tourManagementService.save(tourServiceDTO);

        assertNotNull(result);
        assertEquals("City Tour", result.getName());
        verify(tourServiceRepository).save(tourService);
    }

    @Test
    void findById_ShouldReturnTourServiceDTO_WhenServiceExists() {
        when(tourServiceRepository.findById(1L)).thenReturn(Optional.of(tourService));
        when(tourServiceMapper.toDto(tourService)).thenReturn(tourServiceDTO);

        TourServiceDTO result = tourManagementService.findById(1L);

        assertNotNull(result);
        assertEquals("City Tour", result.getName());
    }

    @Test
    void findById_ShouldThrowEntityNotFoundException_WhenServiceDoesNotExist() {
        when(tourServiceRepository.findById(1L)).thenReturn(Optional.empty());

        Exception exception = assertThrows(EntityNotFoundException.class, () -> tourManagementService.findById(1L));

        assertEquals("Service with ID 1 not found", exception.getMessage());
    }

    @Test
    void findAll_ShouldReturnListOfTourServiceDTOs() {
        when(tourServiceRepository.findAll()).thenReturn(Collections.singletonList(tourService));
        when(tourServiceMapper.toDto(tourService)).thenReturn(tourServiceDTO);

        List<TourServiceDTO> result = tourManagementService.findAll();

        assertNotNull(result);
        assertEquals(1, result.size());
        assertEquals("City Tour", result.get(0).getName());
    }

    @Test
    void deleteById_ShouldDeleteTourService_WhenServiceExists() {
        when(tourServiceRepository.existsById(1L)).thenReturn(true);

        tourManagementService.deleteById(1L);

        verify(tourServiceRepository).deleteById(1L);
    }

    @Test
    void deleteById_ShouldThrowEntityNotFoundException_WhenServiceDoesNotExist() {
        when(tourServiceRepository.existsById(1L)).thenReturn(false);

        Exception exception = assertThrows(EntityNotFoundException.class, () -> tourManagementService.deleteById(1L));

        assertEquals("Service not found with ID1", exception.getMessage());
    }
}