package by.malahovski.mappers;


import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.model.TourService;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;


@Mapper(componentModel = "spring", injectionStrategy = InjectionStrategy.CONSTRUCTOR)
public interface TourServiceMapper {

    TourServiceDTO toDto(TourService entity);

    TourService toEntity(TourServiceDTO dto);
}