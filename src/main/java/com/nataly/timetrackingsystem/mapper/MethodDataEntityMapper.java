package com.nataly.timetrackingsystem.mapper;

import com.nataly.timetrackingsystem.dto.MethodDataDto;
import com.nataly.timetrackingsystem.model.MethodDataEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.InjectionStrategy;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR,
        injectionStrategy = InjectionStrategy.CONSTRUCTOR,
        uses = MethodEntityMapper.class
)
public interface MethodDataEntityMapper {

    @Mapping(target = "id", ignore = true)
    MethodDataEntity toMethodDataEntity(MethodDataDto methodDataDto);

    @BeanMapping(ignoreUnmappedSourceProperties = "id")
    MethodDataDto toMethodDataDto(MethodDataEntity methodDataEntity);

    List<MethodDataEntity> toMethodDataEntities(List<MethodDataDto> methodDataDtos);

    List<MethodDataDto> toMethodDataDtos(List<MethodDataEntity> methodDataEntities);
}
