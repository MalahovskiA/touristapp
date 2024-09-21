package by.malahovski.model;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class Attraction {
    private Long id;
    private String name;
    private LocalDate creationDate;
    private String description;
    private AttractionType type;
    private City city;
}
