package by.malahovski.controller;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.service.AttractionService;
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

class AttractionControllerTest {

    @Mock
    private AttractionService attractionService;

    @InjectMocks
    private AttractionController attractionController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }

    @Test
    void addAttraction() {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(1L);
        attractionDTO.setName("Eiffel Tower");
        attractionDTO.setDescription("Iconic tower in Paris.");

        when(attractionService.addAttraction(any(AttractionDTO.class))).thenReturn(attractionDTO);

        ResponseEntity<AttractionDTO> responseEntity = attractionController.addAttraction(attractionDTO);

        assertEquals(HttpStatus.CREATED, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Eiffel Tower", responseEntity.getBody().getName());

        ArgumentCaptor<AttractionDTO> argumentCaptor = ArgumentCaptor.forClass(AttractionDTO.class);
        verify(attractionService).addAttraction(argumentCaptor.capture());
        assertEquals("Eiffel Tower", argumentCaptor.getValue().getName());
    }

    @Test
    void updateAttraction() {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(1L);
        attractionDTO.setName("Eiffel Tower");
        attractionDTO.setDescription("Iconic tower in Paris.");

        when(attractionService.updateAttraction(any(AttractionDTO.class))).thenReturn(attractionDTO);

        ResponseEntity<AttractionDTO> responseEntity = attractionController.updateAttraction(attractionDTO);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Eiffel Tower", responseEntity.getBody().getName());

        verify(attractionService).updateAttraction(any(AttractionDTO.class));
    }

    @Test
    void getAllAttractions() {
        AttractionDTO attraction1 = new AttractionDTO();
        attraction1.setId(1L);
        attraction1.setName("Eiffel Tower");
        attraction1.setDescription("Iconic tower in Paris.");

        AttractionDTO attraction2 = new AttractionDTO();
        attraction2.setId(2L);
        attraction2.setName("Louvre Museum");
        attraction2.setDescription("Famous art museum in Paris.");

        List<AttractionDTO> attractions = Arrays.asList(attraction1, attraction2);
        when(attractionService.getAllAttractions(null, null)).thenReturn(attractions);

        ResponseEntity<List<AttractionDTO>> responseEntity = attractionController.getAllAttractions(null, null);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals("Eiffel Tower", responseEntity.getBody().get(0).getName());
        assertEquals("Louvre Museum", responseEntity.getBody().get(1).getName());

        verify(attractionService).getAllAttractions(null, null);
    }

    @Test
    void getAttractionById() {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(1L);
        attractionDTO.setName("Eiffel Tower");
        attractionDTO.setDescription("Iconic tower in Paris.");

        when(attractionService.getAttractionById(1L)).thenReturn(attractionDTO);

        ResponseEntity<AttractionDTO> responseEntity = attractionController.getAttractionById(1L);

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Eiffel Tower", responseEntity.getBody().getName());

        verify(attractionService).getAttractionById(1L);
    }

    @Test
    void getAttractionsByCityName() {
        AttractionDTO attraction1 = new AttractionDTO();
        attraction1.setId(1L);
        attraction1.setName("Eiffel Tower");
        attraction1.setDescription("Iconic tower in Paris.");

        AttractionDTO attraction2 = new AttractionDTO();
        attraction2.setId(2L);
        attraction2.setName("Louvre Museum");
        attraction2.setDescription("Famous art museum in Paris.");

        List<AttractionDTO> attractions = Arrays.asList(attraction1, attraction2);
        when(attractionService.getAttractionsByCityName("Paris")).thenReturn(attractions);

        ResponseEntity<List<AttractionDTO>> responseEntity = attractionController.getAttractionsByCityName("Paris");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals(2, responseEntity.getBody().size());
        assertEquals("Eiffel Tower", responseEntity.getBody().get(0).getName());
        assertEquals("Louvre Museum", responseEntity.getBody().get(1).getName());

        verify(attractionService).getAttractionsByCityName("Paris");
    }

    @Test
    void updateAttractionDescription() {
        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(1L);
        attractionDTO.setName("Eiffel Tower");
        attractionDTO.setDescription("Iconic tower in Paris.");

        when(attractionService.updateDescription(1L, "Updated Description")).thenReturn(attractionDTO);

        ResponseEntity<AttractionDTO> responseEntity = attractionController.updateAttractionDescription(1L, "Updated Description");

        assertEquals(HttpStatus.OK, responseEntity.getStatusCode());
        assertNotNull(responseEntity.getBody());
        assertEquals("Eiffel Tower", responseEntity.getBody().getName());

        verify(attractionService).updateDescription(1L, "Updated Description");
    }

    @Test
    void deleteAttraction() {
        doNothing().when(attractionService).deleteAttraction(1L);

        ResponseEntity<Void> responseEntity = attractionController.deleteAttraction(1L);

        assertEquals(HttpStatus.NO_CONTENT, responseEntity.getStatusCode());

        verify(attractionService).deleteAttraction(1L);
    }
}