package com.nataly.timetrackingsystem.service;

import com.nataly.timetrackingsystem.dto.MethodDataDto;

import java.util.List;

public interface MethodDataService {

    MethodDataDto getLongestMethodByType(String type);

    MethodDataDto getFastestMethodByType(String type);

    List<MethodDataDto> getAllMethodsByType(String type);

    Double getAverageTimeExecutionByType(String type);

    Double getAverageTimeExecution();

    Double getAverageTimeExecutionByMethod(String methodName);

    void saveMethodData(Long executionTime, String methodName, String className, String type);
}
