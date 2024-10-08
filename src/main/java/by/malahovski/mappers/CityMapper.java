package by.malahovski.mappers;

import by.malahovski.dtos.CityDTO;
import by.malahovski.model.City;
import org.mapstruct.*;

@Mapper(componentModel = "spring", uses = {AttractionMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface CityMapper {


    CityDTO toDto(City entity);


    City toEntity(CityDTO dto);
}