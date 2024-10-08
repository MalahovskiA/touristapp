package by.malahovski.mappers;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.Attraction;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


/**
 * Mapper interface for converting between {@link Attraction} entity and {@link AttractionDTO}.
 * This interface uses MapStruct to generate the mapping code. It defines methods for
 * converting an {@link Attraction} entity to a {@link AttractionDTO} and vice versa.
 * It also utilizes the {@link TourServiceMapper} for mapping tour services and applies
 * constructor injection for its dependencies.
 */
@Mapper(componentModel = "spring", uses = {TourServiceMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AttractionMapper {

    /**
     * Converts an {@link Attraction} entity to a {@link AttractionDTO}.
     *
     * @param entity the {@link Attraction} entity to be converted
     * @return the corresponding {@link AttractionDTO}
     */
    @Mapping(target = "cityID", source = "city.id")
    @Mapping(target = "tourServices", source = "tourServices")
    AttractionDTO toDto(Attraction entity);


    /**
     * Converts a {@link AttractionDTO} to an {@link Attraction} entity.
     * The mapping for the city is ignored, as it may require additional logic to map the city object properly.
     *
     * @param dto the {@link AttractionDTO} to be converted
     * @return the corresponding {@link Attraction} entity
     */
    @Mapping(target = "city", ignore = true)
    Attraction toEntity(AttractionDTO dto);
}