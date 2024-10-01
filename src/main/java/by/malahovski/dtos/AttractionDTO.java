package by.malahovski.dtos;

import by.malahovski.model.AttractionType;
import lombok.Data;

import java.time.LocalDate;


@Data
public class AttractionDTO {

    private Long id;
    private String name;
    private LocalDate creationDate;
    private String description;
    private AttractionType type;
}