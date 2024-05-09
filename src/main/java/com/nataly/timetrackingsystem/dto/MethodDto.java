package com.nataly.timetrackingsystem.dto;

import lombok.Builder;

@Builder(toBuilder = true)
public record MethodDto(
        String methodName,
        String className
) {
}
