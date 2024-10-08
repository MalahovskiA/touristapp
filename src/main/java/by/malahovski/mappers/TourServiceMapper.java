package by.malahovski.mappers;


import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.model.Attraction;
import by.malahovski.model.TourService;
import java.util.Collections;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


@Mapper(componentModel = "spring", uses = {AttractionMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TourServiceMapper {

    @Mapping(target = "attractionsIDs", source = "attractions")
    TourServiceDTO toDto(TourService entity);

    @Mapping(target = "attractions", source = "attractionsIDs")
    TourService toEntity(TourServiceDTO dto);

    default List<Long> mapAttractionsToIds(List<Attraction> attractions) {
        if (attractions == null) {
            return Collections.emptyList();
        }
        return attractions.stream().map(Attraction::getId).toList();
    }

    default List<Attraction> mapIdsToAttractions(List<Long> ids) {
        if (ids == null) {
            return Collections.emptyList();
        }
        return ids.stream().map(id -> {
            Attraction attraction = new Attraction();
            attraction.setId(id);
            return attraction;
        }).toList();
    }
}