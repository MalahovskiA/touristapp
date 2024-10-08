package by.malahovski.mappers;


import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.model.Attraction;
import by.malahovski.model.TourService;
import java.util.Collections;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;


/**
 * Mapper interface for converting between {@link TourService} entity and {@link TourServiceDTO}.
 * This interface uses MapStruct to generate the mapping code. It provides methods for converting
 * between a {@link TourService} entity and a {@link TourServiceDTO}. The attractions within a
 * tour service are mapped by their IDs.
 * It uses constructor-based dependency injection and leverages the {@link AttractionMapper}
 * to handle mappings involving nested attractions.
 */
@Mapper(componentModel = "spring", uses = {AttractionMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TourServiceMapper {

    /**
     * Converts a {@link TourService} entity to a {@link TourServiceDTO}.
     * The attraction IDs are extracted from the {@link Attraction} list in the entity.
     *
     * @param entity the {@link TourService} entity to be converted
     * @return the corresponding {@link TourServiceDTO}
     */
    @Mapping(target = "attractionsIDs", source = "attractions")
    TourServiceDTO toDto(TourService entity);


    /**
     * Converts a {@link TourServiceDTO} to a {@link TourService} entity.
     * The attraction IDs in the DTO are converted to a list of {@link Attraction} entities.
     *
     * @param dto the {@link TourServiceDTO} to be converted
     * @return the corresponding {@link TourService} entity
     */
    @Mapping(target = "attractions", source = "attractionsIDs")
    TourService toEntity(TourServiceDTO dto);

    /**
     * Converts a list of {@link Attraction} entities to a list of their corresponding IDs.
     *
     * @param attractions the list of {@link Attraction} entities
     * @return a list of IDs extracted from the {@link Attraction} entities
     */
    default List<Long> mapAttractionsToIds(List<Attraction> attractions) {
        if (attractions == null) {
            return Collections.emptyList();
        }
        return attractions.stream().map(Attraction::getId).toList();
    }

    /**
     * Converts a list of attraction IDs to a list of {@link Attraction} entities with only IDs set.
     *
     * @param ids the list of attraction IDs
     * @return a list of {@link Attraction} entities with only IDs set
     */
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