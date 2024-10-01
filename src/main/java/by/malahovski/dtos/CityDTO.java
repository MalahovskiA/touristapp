package by.malahovski.dtos;


import lombok.Data;

import java.util.List;


@Data
public class CityDTO {
    private Long id;
    private String name;
    private int population;
    private boolean hasMetro;
    private List<AttractionDTO> attractions;
}