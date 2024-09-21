package by.malahovski.mappers;

import by.malahovski.dtos.CityDTO;
import by.malahovski.model.City;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface CityMapper {
    City toEntity(CityDTO dto);
    CityDTO toDto(City entity);
}
