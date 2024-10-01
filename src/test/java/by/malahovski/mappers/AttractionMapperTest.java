package by.malahovski.mappers;


import by.malahovski.config.SpringTestConfig;
import by.malahovski.dtos.CityDTO;
import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.model.Attraction;
import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.AttractionType;
import by.malahovski.model.City;
import by.malahovski.model.TourService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertEquals;

class AttractionMapperTest {

    private AttractionMapper attractionMapper;

    @BeforeEach
    void setup() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringTestConfig.class);
        attractionMapper = context.getBean(AttractionMapper.class);
    }

    @Test
    void testToDto() {
        Attraction attraction = getAttraction();

        AttractionDTO attractionDTO = attractionMapper.toDto(attraction);

        assertEquals(attraction.getId(), attractionDTO.getId());
        assertEquals(attraction.getName(), attractionDTO.getName());
        assertEquals(attraction.getCreationDate(), attractionDTO.getCreationDate());
        assertEquals(attraction.getDescription(), attractionDTO.getDescription());
        assertEquals(attraction.getType(), attractionDTO.getType());
        assertEquals(attraction.getCity().getId(), attractionDTO.getId());

        // Проверяем список услуг
        assertEquals(2, attractionDTO.getTourServices().size());
        assertEquals(attraction.getTourServices().getFirst().getId(), attractionDTO.getTourServices().getFirst().getId());
        assertEquals(attraction.getTourServices().get(1).getId(), attractionDTO.getTourServices().get(1).getId());
    }

    private static Attraction getAttraction() {
        City city = new City();
        city.setId(1L);
        city.setName("Moscow");
        city.setPopulation(12615882);
        city.setHasMetro(true);

        TourService service1 = new TourService();
        service1.setId(1L);
        service1.setName("Guided Tour");
        service1.setDescription("A comprehensive guided tour of the city.");

        TourService service2 = new TourService();
        service2.setId(2L);
        service2.setName("Photography Tour");
        service2.setDescription("A tour focused on capturing beautiful moments.");

        Attraction attraction = new Attraction();
        attraction.setId(1L);
        attraction.setName("Kremlin");
        attraction.setCreationDate(LocalDate.of(1482, 1, 1));
        attraction.setDescription("The Moscow Kremlin is a fortified complex at the heart of Moscow.");
        attraction.setType(AttractionType.PALACE);
        attraction.setCity(city);
        attraction.setTourServices(Arrays.asList(service1, service2)); // Список услуг
        return attraction;
    }

    @Test
    void testToEntity() {
        CityDTO city = new CityDTO();
        city.setId(1L);
        city.setName("Moscow");
        city.setPopulation(12615882);
        city.setHasMetro(true);

        TourServiceDTO serviceDTO1 = new TourServiceDTO();
        serviceDTO1.setId(1L);
        serviceDTO1.setName("Guided Tour");
        serviceDTO1.setDescription("A comprehensive guided tour of the city.");

        TourServiceDTO serviceDTO2 = new TourServiceDTO();
        serviceDTO2.setId(2L);
        serviceDTO2.setName("Photography Tour");
        serviceDTO2.setDescription("A tour focused on capturing beautiful moments.");

        AttractionDTO attractionDTO = new AttractionDTO();
        attractionDTO.setId(1L);
        attractionDTO.setName("Kremlin");
        attractionDTO.setCreationDate(LocalDate.of(1482, 1, 1));
        attractionDTO.setDescription("The Moscow Kremlin is a fortified complex at the heart of Moscow.");
        attractionDTO.setType(AttractionType.PALACE);
        attractionDTO.setCityID(1L);
        attractionDTO.setTourServices(Arrays.asList(serviceDTO1,serviceDTO2));

        Attraction attraction = attractionMapper.toEntity(attractionDTO);

        assertEquals(attractionDTO.getId(), attraction.getId());
        assertEquals(attractionDTO.getName(), attraction.getName());
        assertEquals(attractionDTO.getCreationDate(), attraction.getCreationDate());
        assertEquals(attractionDTO.getDescription(), attraction.getDescription());
        assertEquals(attractionDTO.getType(), attraction.getType());
        assertEquals(attractionDTO.getCityID(), attraction.getCity().getId());

        // Проверяем список услуг
        assertEquals(2, attraction.getTourServices().size());
        assertEquals(attractionDTO.getTourServices().get(0).getId(), attraction.getTourServices().get(0).getId());
        assertEquals(attractionDTO.getTourServices().get(1).getId(), attraction.getTourServices().get(1).getId());
    }
}