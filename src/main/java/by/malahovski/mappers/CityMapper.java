package by.malahovski.mappers;

import by.malahovski.dtos.CityDTO;
import by.malahovski.model.City;
import org.mapstruct.*;


/**
 * Mapper interface for converting between {@link City} entity and {@link CityDTO}.
 * This interface leverages MapStruct to automatically generate the mapping logic.
 * It defines methods for converting a {@link City} entity to a {@link CityDTO} and
 * for converting a {@link CityDTO} back to a {@link City} entity.
 * The {@link AttractionMapper} is used to map nested attractions within the city,
 * and constructor-based dependency injection is applied.
 */
@Mapper(componentModel = "spring", uses = {AttractionMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {

    /**
     * Converts a {@link City} entity to a {@link CityDTO}.
     *
     * @param entity the {@link City} entity to be converted
     * @return the corresponding {@link CityDTO}
     */
    CityDTO toDto(City entity);

    /**
     * Converts a {@link CityDTO} to a {@link City} entity.
     *
     * @param dto the {@link CityDTO} to be converted
     * @return the corresponding {@link City} entity
     */
    City toEntity(CityDTO dto);
}