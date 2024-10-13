package by.malahovski.mappers;

import by.malahovski.config.SpringTestConfig;
import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.model.Attraction;
import by.malahovski.model.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class TourServiceMapperTest {

    private TourServiceMapper tourServiceMapper;

    @BeforeEach
    public void setup() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringTestConfig.class);
        tourServiceMapper = context.getBean(TourServiceMapper.class);
    }

    @Test
    void testTourServiceToDto() {
        TourService tourService = new TourService();
        tourService.setId(1L);
        tourService.setName("City Tour");
        tourService.setDescription("A comprehensive city tour that covers all major attractions.");

        List<Attraction> attractions = new ArrayList<>();
        tourService.setAttractions(attractions);

        TourServiceDTO tourServiceDTO = tourServiceMapper.toDto(tourService);

        assertNotNull(tourServiceDTO);
        assertEquals(tourService.getId(), tourServiceDTO.getId());
        assertEquals(tourService.getName(), tourServiceDTO.getName());
        assertEquals(tourService.getDescription(), tourServiceDTO.getDescription());

        List<Long> attractionIDs = tourServiceDTO.getAttractionsIDs();
        assertNotNull(attractionIDs);
        assertEquals(0, attractionIDs.size());
    }

    @Test
    void testDtoToTourService() {
        TourServiceDTO tourServiceDTO = new TourServiceDTO();
        tourServiceDTO.setId(1L);
        tourServiceDTO.setName("City Tour");
        tourServiceDTO.setDescription("A comprehensive city tour that covers all major attractions.");

        List<Long> attractionIDs = new ArrayList<>();
        attractionIDs.add(1L);
        attractionIDs.add(2L);
        tourServiceDTO.setAttractionsIDs(attractionIDs);

        TourService tourService = tourServiceMapper.toEntity(tourServiceDTO);

        assertNotNull(tourService);
        assertEquals(tourServiceDTO.getId(), tourService.getId());
        assertEquals(tourServiceDTO.getName(), tourService.getName());
        assertEquals(tourServiceDTO.getDescription(), tourService.getDescription());

        List<Attraction> attractions = tourService.getAttractions();
        assertNotNull(attractions);
        assertEquals(attractionIDs.size(), attractions.size());
        assertEquals(attractionIDs.get(0), attractions.get(0).getId());
        assertEquals(attractionIDs.get(1), attractions.get(1).getId());
    }
}