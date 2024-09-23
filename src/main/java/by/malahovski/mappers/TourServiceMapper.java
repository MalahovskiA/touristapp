package by.malahovski.mappers;

import by.malahovski.dtos.TourServiceDTO;
import by.malahovski.model.TourService;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface TourServiceMapper {

    TourService toEntity(TourServiceDTO dto);

    TourServiceDTO toDto(TourService entity);

    List<TourServiceDTO> toDtoList(List<TourService> entities);

    List<TourService> toEntityList(List<TourServiceDTO> dtos);
}