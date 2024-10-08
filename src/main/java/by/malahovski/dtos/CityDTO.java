package by.malahovski.dtos;


import lombok.Data;

import java.util.List;

/**
 * Data Transfer Object (DTO) for representing a city.
 * This class holds information about a city, including its ID, name, population,
 * whether it has a metro, and a list of associated attractions. It is used to transfer
 * data between layers in the application.
 */
@Data
public class CityDTO {

    /**
     * The unique identifier of the city.
     */
    private Long id;

    /**
     * The name of the city.
     */
    private String name;

    /**
     * The population of the city.
     */
    private int population;

    /**
     * Indicates whether the city has a metro system.
     */
    private boolean hasMetro;

    /**
     * A list of attractions associated with the city.
     * This field contains a list of {@link AttractionDTO} objects, representing
     * the attractions located in the city.
     */
    private List<AttractionDTO> attractions;
}