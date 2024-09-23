package by.malahovski.mappers;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.Attraction;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface AttractionMapper {

    Attraction toEntity(AttractionDTO dto);

    AttractionDTO toDto(Attraction entity);

    List<Attraction> toEntityList(List<AttractionDTO> dtoList);

    List<AttractionDTO> toDtoList(List<Attraction> entityList);
}