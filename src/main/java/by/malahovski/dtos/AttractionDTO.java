package by.malahovski.dtos;

import by.malahovski.model.AttractionType;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;


@Data
public class AttractionDTO {

    private Long id;
    private String name;
    private LocalDate creationDate;
    private String description;
    private AttractionType type;
    private CityDTO city;
    private List<TourServiceDTO> tourServices;
}