package by.malahovski.service;

import by.malahovski.dtos.AttractionDTO;

import java.util.List;


public interface AttractionService {

    List<AttractionDTO> getAllAttractions(String sortBy, String filterByType);

    List<AttractionDTO> getAttractionsByCityName(String cityName);

    AttractionDTO getAttractionById(Long id);

    AttractionDTO addAttraction(AttractionDTO attractionDTO);

    AttractionDTO updateAttraction(AttractionDTO attractionDTO);

    AttractionDTO updateDescription(Long id, String newDescription);

    void deleteAttraction(Long id);
}