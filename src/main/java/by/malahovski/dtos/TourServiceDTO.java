package by.malahovski.dtos;



import lombok.Data;

import java.util.List;


@Data
public class TourServiceDTO {

    private Long id;
    private String name;
    private String description;
    private List<Long> attractionsIDs;
}