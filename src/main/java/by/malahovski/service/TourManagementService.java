package by.malahovski.service;

import by.malahovski.dtos.TourServiceDTO;

import java.util.List;

public interface TourManagementService {

    TourServiceDTO save(TourServiceDTO tourServiceDTO);

    TourServiceDTO findById(Long id);

    List<TourServiceDTO> findAll();

    void deleteById(Long id);

    void addAttractionToTourService(Long tourServiceId, Long attractionId);

    void removeAttractionFromTourService(Long tourServiceId, Long attractionId);
}
