package by.malahovski.service;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.City;

import java.util.List;

public interface AttractionService {
    AttractionDTO getAttractionById(Long id);

    void addAttraction(AttractionDTO attractionDTO);

    List<AttractionDTO> getAttractionsByCity(City city);

    void updateAttraction(AttractionDTO attractionDTO);
    List<AttractionDTO> getAllAttractions();
    List<AttractionDTO> getAttractionsByCity(Long cityId);
    void deleteAttraction(Long id);
}
