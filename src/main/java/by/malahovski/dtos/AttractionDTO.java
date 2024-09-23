package by.malahovski.dtos;

import by.malahovski.model.AttractionType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;
import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AttractionDTO {

    private Long id;
    private String name;
    private LocalDate creationDate;
    private String description;
    private AttractionType type;
    private Long cityId;
    private List<TourServiceDTO> tourServices;
}