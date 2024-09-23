package by.malahovski.service;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.City;

import java.util.List;


public interface AttractionService {

    List<AttractionDTO> getAttractionsByCity(City city);

    AttractionDTO getAttractionById(Long id);

    AttractionDTO addAttraction(AttractionDTO attractionDTO);

    AttractionDTO updateAttraction(AttractionDTO attractionDTO);

    List<AttractionDTO> getAllAttractions();

    void deleteAttraction(Long id);
}

