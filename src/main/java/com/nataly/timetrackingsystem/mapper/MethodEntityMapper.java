package com.nataly.timetrackingsystem.mapper;

import com.nataly.timetrackingsystem.dto.MethodDto;
import com.nataly.timetrackingsystem.model.MethodEntity;
import org.mapstruct.BeanMapping;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring",
        unmappedSourcePolicy = ReportingPolicy.ERROR,
        unmappedTargetPolicy = ReportingPolicy.ERROR
)
public interface MethodEntityMapper {

    @Mapping(target = "id", ignore = true)
    MethodEntity toMethodEntity(MethodDto methodDto);

    @BeanMapping(ignoreUnmappedSourceProperties = "id")
    MethodDto toMethodDto(MethodEntity methodEntity);
}
