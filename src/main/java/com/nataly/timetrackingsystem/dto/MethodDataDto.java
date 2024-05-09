package com.nataly.timetrackingsystem.dto;

import com.nataly.timetrackingsystem.model.enums.MethodType;
import lombok.Builder;

import java.time.LocalDateTime;

@Builder(toBuilder = true)
public record MethodDataDto(
        LocalDateTime createdAt,
        Long executionTime,
        MethodType type,
        MethodDto method
) {
}
