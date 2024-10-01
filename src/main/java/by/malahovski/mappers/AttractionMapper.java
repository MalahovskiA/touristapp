package by.malahovski.mappers;

import by.malahovski.dtos.AttractionDTO;
import by.malahovski.model.Attraction;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;


@Mapper(componentModel = "spring", uses = {TourServiceMapper.class}, injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface AttractionMapper {

    @Mapping(target = "cityID", source = "city.id")
    @Mapping(target = "tourServices", source = "tourServices")
    AttractionDTO toDto(Attraction entity);

    @Mapping(target = "city", ignore = true) // Игнорируем полное маппирование города
    Attraction toEntity(AttractionDTO dto);
}