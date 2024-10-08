package by.malahovski.dtos;

import by.malahovski.model.AttractionType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


/**
 * Data Transfer Object (DTO) for representing an attraction.
 * This class holds information about an attraction, including its ID, name, creation date,
 * description, type, and associated tour services. It is used to transfer attraction data
 * between different layers of the application.
 */
@Data
public class AttractionDTO {

    /**
     * The unique identifier of the attraction.
     */
    private Long id;

    /**
     * The name of the attraction.
     */
    private String name;

    /**
     * The date the attraction was created.
     */
    private LocalDate creationDate;

    /**
     * A description of the attraction.
     */
    private String description;

    /**
     * The type of the attraction, represented by the {@link AttractionType} enum.
     */
    private AttractionType type;

    /**
     * The ID of the city where the attraction is located.
     */
    private Long cityID;

    /**
     * A list of tour services associated with the attraction.
     * This field contains a list of {@link TourServiceDTO} objects, representing
     * the tour services that include this attraction.
     */
    private List<TourServiceDTO> tourServices;
}