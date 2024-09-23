package by.malahovski.dtos;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;


@Data
@NoArgsConstructor
@AllArgsConstructor
public class TourServiceDTO {

    private Long id;
    private String name;
    private String description;
    private List<AttractionDTO> attractions;
}