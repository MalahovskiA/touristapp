package by.malahovski.service;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.handler.EntityNotFoundException;
import by.malahovski.mappers.AttractionMapper;
import by.malahovski.model.Attraction;
import by.malahovski.model.AttractionType;
import by.malahovski.repository.AttractionRepository;
import by.malahovski.service.impl.AttractionServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

class AttractionServiceImplTest {

    @InjectMocks
    private AttractionServiceImpl attractionService;

    @Mock
    private AttractionRepository attractionRepository;

    @Mock
    private AttractionMapper attractionMapper;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void getAllAttractions_ShouldReturnListOfAttractionDTOs() {
        Attraction attraction1 = new Attraction();
        attraction1.setId(1L);
        attraction1.setName("Attraction One");
        attraction1.setCreationDate(LocalDate.now());
        attraction1.setDescription("Description One");

        Attraction attraction2 = new Attraction();
        attraction2.setId(2L);
        attraction2.setName("Attraction Two");
        attraction2.setCreationDate(LocalDate.now());
        attraction2.setDescription("Description Two");

        List<Attraction> attractions = Arrays.asList(attraction1, attraction2);

        when(attractionRepository.findAll()).thenReturn(attractions);
        when(attractionMapper.toDto(attraction1)).thenReturn(new AttractionDTO(attraction1.getId(), attraction1.getName(), attraction1.getCreationDate(), attraction1.getDescription(), AttractionType.PARK, 1L, new ArrayList<>()));
        when(attractionMapper.toDto(attraction2)).thenReturn(new AttractionDTO(attraction2.getId(), attraction2.getName(), attraction2.getCreationDate(), attraction2.getDescription(), AttractionType.PALACE, 2L, new ArrayList<>()));

        List<AttractionDTO> result = attractionService.getAllAttractions(null, null);

        assertNotNull(result);
        assertEquals(2, result.size());
        verify(attractionRepository).findAll();
    }

    @Test
    void getAttractionById_ShouldReturnAttractionDTO_WhenAttractionExists() {
        Long attractionId = 1L;
        Attraction attraction = new Attraction();
        attraction.setId(attractionId);
        attraction.setName("Attraction One");
        attraction.setCreationDate(LocalDate.now());
        attraction.setDescription("Description One");

        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(attractionId);
        attractionDTO.setName("Attraction One");
        attractionDTO.setCreationDate(LocalDate.now());
        attractionDTO.setDescription("Description One");

        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(attraction));
        when(attractionMapper.toDto(attraction)).thenReturn(attractionDTO);

        AttractionDTO result = attractionService.getAttractionById(attractionId);

        assertNotNull(result);
        assertEquals(attractionId, result.getId());
        verify(attractionRepository).findById(attractionId);
    }

    @Test
    void getAttractionById_ShouldThrowEntityNotFoundException_WhenAttractionDoesNotExist() {
        Long attractionId = 1L;
        when(attractionRepository.findById(attractionId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> attractionService.getAttractionById(attractionId));
        assertEquals("Attraction not found with ID " + attractionId, exception.getMessage());
    }

    @Test
    void addAttraction_ShouldReturnAttractionDTO_WhenAttractionIsAdded() {
        // Arrange
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setName("New Attraction");
        attractionDTO.setCreationDate(LocalDate.now());
        attractionDTO.setDescription("New Attraction Description");

        Attraction attraction = new Attraction();
        attraction.setId(2L);
        attraction.setName("New Attraction");
        attraction.setCreationDate(LocalDate.now());
        attraction.setDescription("New Attraction Description");

        when(attractionMapper.toEntity(attractionDTO)).thenReturn(attraction);
        when(attractionRepository.save(attraction)).thenReturn(attraction);
        when(attractionMapper.toDto(attraction)).thenReturn(attractionDTO);

        AttractionDTO result = attractionService.addAttraction(attractionDTO);

        assertNotNull(result);
        assertEquals("New Attraction", result.getName());
        verify(attractionRepository).save(attraction);
    }

    @Test
    void updateAttraction_ShouldReturnUpdatedAttractionDTO_WhenAttractionExists() {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(1L);
        attractionDTO.setName("Updated Attraction");
        attractionDTO.setDescription("Updated Description");
        attractionDTO.setCreationDate(LocalDate.now());
        attractionDTO.setType(AttractionType.MUSEUM);

        Attraction existingAttraction = new Attraction();
        existingAttraction.setId(1L);
        existingAttraction.setName("Old Attraction");
        existingAttraction.setDescription("Old Description");
        existingAttraction.setCreationDate(LocalDate.now().minusDays(1));
        existingAttraction.setType(AttractionType.PARK);

        when(attractionRepository.findById(attractionDTO.getId())).thenReturn(Optional.of(existingAttraction));
        when(attractionMapper.toDto(existingAttraction)).thenReturn(attractionDTO);
        when(attractionRepository.save(existingAttraction)).thenReturn(existingAttraction);

       AttractionDTO result = attractionService.updateAttraction(attractionDTO);

        assertNotNull(result, "The result should not be null");
        assertEquals("Updated Attraction", result.getName());
        verify(attractionRepository).save(existingAttraction);
    }

    @Test
    void updateAttraction_ShouldThrowEntityNotFoundException_WhenAttractionDoesNotExist() {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(1L);

        when(attractionRepository.findById(attractionDTO.getId())).thenReturn(Optional.empty());


        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> attractionService.updateAttraction(attractionDTO));
        assertEquals("Attraction not found with ID " + attractionDTO.getId(), exception.getMessage());
    }

    @Test
    void deleteAttraction_ShouldDeleteAttraction_WhenAttractionExists() {
        Long attractionId = 1L;
        Attraction attraction = new Attraction();
        attraction.setId(attractionId);

        when(attractionRepository.findById(attractionId)).thenReturn(Optional.of(attraction));

        attractionService.deleteAttraction(attractionId);

        verify(attractionRepository).delete(attraction);
    }

    @Test
    void deleteAttraction_ShouldThrowEntityNotFoundException_WhenAttractionDoesNotExist() {
        Long attractionId = 1L;
        when(attractionRepository.findById(attractionId)).thenReturn(Optional.empty());

        EntityNotFoundException exception = assertThrows(EntityNotFoundException.class, () -> attractionService.deleteAttraction(attractionId));
        assertEquals("Attraction not found with ID " + attractionId, exception.getMessage());
    }
}