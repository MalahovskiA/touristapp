package by.malahovski.mappers;


import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.model.Attraction;
import by.malahovski.model.TourService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", uses = {AttractionMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TourServiceMapper {

    // Маппим список аттракционов в список их ID
    @Mapping(target = "attractionsIDs", source = "attractions")
    TourServiceDTO toDto(TourService entity);

    // Маппим список ID в список аттракционов
    @Mapping(target = "attractions", source = "attractionsIDs")
    TourService toEntity(TourServiceDTO dto);

    // Метод для маппинга списка аттракционов в список их ID
    default List<Long> mapAttractionsToIds(List<Attraction> attractions) {
        if (attractions == null) {
            return null;
        }
        return attractions.stream().map(Attraction::getId).toList();
    }

    // Метод для маппинга списка ID в список аттракционов
    default List<Attraction> mapIdsToAttractions(List<Long> ids) {
        if (ids == null) {
            return null;
        }
        return ids.stream().map(id -> {
            Attraction attraction = new Attraction();
            attraction.setId(id);
            return attraction;
        }).toList();
    }
}