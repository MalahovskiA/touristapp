package by.malahovski.mappers;

import by.malahovski.config.SpringTestConfig;
import by.malahovski.dtos.AttractionDTO;
import by.malahovski.dtos.CityDTO;
import by.malahovski.model.Attraction;
import by.malahovski.model.AttractionType;
import by.malahovski.model.City;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

class CityMapperTest {

    private CityMapper cityMapper;

    @BeforeEach
    public void setup() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SpringTestConfig.class);
        cityMapper = context.getBean(CityMapper.class);
    }

    @Test
    void testCityToDto() {
        City city = new City();
        city.setId(1L);
        city.setName("Moscow");
        city.setPopulation(12615882);
        city.setHasMetro(true);

        Attraction attraction1 = new Attraction();
        attraction1.setId(1L);
        attraction1.setName("Kremlin");
        attraction1.setCreationDate(LocalDate.of(1482, 1, 1));
        attraction1.setDescription("The Moscow Kremlin is a fortified complex at the heart of Moscow.");
        attraction1.setType(AttractionType.PALACE);

        Attraction attraction2 = new Attraction();
        attraction2.setId(2L);
        attraction2.setName("Red Square");
        attraction2.setCreationDate(LocalDate.of(1500, 5, 5));
        attraction2.setDescription("Red Square is one of the oldest and largest squares in Moscow.");
        attraction2.setType(AttractionType.PARK);

        List<Attraction> attractions = new ArrayList<>();
        attractions.add(attraction1);
        attractions.add(attraction2);
        city.setAttractions(attractions);


        CityDTO cityDTO = cityMapper.toDto(city);

        assertNotNull(cityDTO);
        assertEquals(city.getId(), cityDTO.getId());
        assertEquals(city.getName(), cityDTO.getName());
        assertEquals(city.getPopulation(), cityDTO.getPopulation());
        assertEquals(city.isHasMetro(), cityDTO.isHasMetro());

        List<AttractionDTO> attractionDTOList = cityDTO.getAttractions();
        assertNotNull(attractionDTOList);
        assertEquals(2, attractionDTOList.size());

        AttractionDTO attractionDTO1 = attractionDTOList.get(0);
        assertEquals(attraction1.getId(), attractionDTO1.getId());
        assertEquals(attraction1.getName(), attractionDTO1.getName());
        assertEquals(attraction1.getCreationDate(), attractionDTO1.getCreationDate());
        assertEquals(attraction1.getDescription(), attractionDTO1.getDescription());
        assertEquals(attraction1.getType(), attractionDTO1.getType());

        AttractionDTO attractionDTO2 = attractionDTOList.get(1);
        assertEquals(attraction2.getId(), attractionDTO2.getId());
        assertEquals(attraction2.getName(), attractionDTO2.getName());
        assertEquals(attraction2.getCreationDate(), attractionDTO2.getCreationDate());
        assertEquals(attraction2.getDescription(), attractionDTO2.getDescription());
        assertEquals(attraction2.getType(), attractionDTO2.getType());
    }

    @Test
    void testDtoToCity() {
        AttractionDTO attractionDTO1 = new AttractionDTO();
        attractionDTO1.setId(1L);
        attractionDTO1.setName("Kremlin");
        attractionDTO1.setCreationDate(LocalDate.of(1482, 1, 1));
        attractionDTO1.setDescription("The Moscow Kremlin is a fortified complex at the heart of Moscow.");
        attractionDTO1.setType(AttractionType.PALACE);

        AttractionDTO attractionDTO2 = new AttractionDTO();
        attractionDTO2.setId(2L);
        attractionDTO2.setName("Red Square");
        attractionDTO2.setCreationDate(LocalDate.of(1500, 5, 5));
        attractionDTO2.setDescription("Red Square is one of the oldest and largest squares in Moscow.");
        attractionDTO2.setType(AttractionType.PARK);

        List<AttractionDTO> attractionDTOList = new ArrayList<>();
        attractionDTOList.add(attractionDTO1);
        attractionDTOList.add(attractionDTO2);

        CityDTO cityDTO = new CityDTO();
        cityDTO.setId(1L);
        cityDTO.setName("Moscow");
        cityDTO.setPopulation(12615882);
        cityDTO.setHasMetro(true);
        cityDTO.setAttractions(attractionDTOList);

        City city = cityMapper.toEntity(cityDTO);

        assertNotNull(city);
        assertEquals(cityDTO.getId(), city.getId());
        assertEquals(cityDTO.getName(), city.getName());
        assertEquals(cityDTO.getPopulation(), city.getPopulation());
        assertEquals(cityDTO.isHasMetro(), city.isHasMetro());

        List<Attraction> attractions = city.getAttractions();
        assertNotNull(attractions);
        assertEquals(2, attractions.size());

        Attraction attraction1 = attractions.get(0);
        assertEquals(attractionDTO1.getId(), attraction1.getId());
        assertEquals(attractionDTO1.getName(), attraction1.getName());
        assertEquals(attractionDTO1.getCreationDate(), attraction1.getCreationDate());
        assertEquals(attractionDTO1.getDescription(), attraction1.getDescription());
        assertEquals(attractionDTO1.getType(), attraction1.getType());

        Attraction attraction2 = attractions.get(1);
        assertEquals(attractionDTO2.getId(), attraction2.getId());
        assertEquals(attractionDTO2.getName(), attraction2.getName());
        assertEquals(attractionDTO2.getCreationDate(), attraction2.getCreationDate());
        assertEquals(attractionDTO2.getDescription(), attraction2.getDescription());
        assertEquals(attractionDTO2.getType(), attraction2.getType());
    }
}