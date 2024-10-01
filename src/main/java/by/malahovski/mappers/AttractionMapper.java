package by.malahovski.mappers;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.Attraction;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AttractionMapper {

    AttractionDTO toDto(Attraction entity);

    Attraction toEntity(AttractionDTO dto);
}