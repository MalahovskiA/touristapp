package by.malahovski.mappers;

import by.malahovski.dtos.CityDTO;
import by.malahovski.model.City;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AttractionMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {

    @Mapping(target = "attractionDTOList", source = "attractions")
    CityDTO toDto(City entity);

    @Mapping(target = "attractions", source = "attractionDTOList")
    City toEntity(CityDTO dto);
}