package by.malahovski.mappers;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.Attraction;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface AttractionMapper {
    Attraction toEntity(AttractionDTO dto);
    AttractionDTO toDto(Attraction entity);
}
