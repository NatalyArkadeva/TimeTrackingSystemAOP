package com.nataly.timetrackingsystem.service.impl;

import com.nataly.timetrackingsystem.dto.MethodDataDto;
import com.nataly.timetrackingsystem.exceptions.DataNotFoundException;
import com.nataly.timetrackingsystem.mapper.MethodDataEntityMapper;
import com.nataly.timetrackingsystem.model.MethodDataEntity;
import com.nataly.timetrackingsystem.model.enums.MethodType;
import com.nataly.timetrackingsystem.repository.MethodDataJpaRepository;
import com.nataly.timetrackingsystem.service.MethodDataService;
import com.nataly.timetrackingsystem.service.MethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MethodDataServiceImpl implements MethodDataService {

    private final MethodDataJpaRepository methodDataJpaRepository;
    private final MethodService methodService;
    private final MethodDataEntityMapper methodDataEntityMapper;

    @Override
    public MethodDataDto getLongestMethodByType(String type) {
        return methodDataJpaRepository.findLongestMethodByType(MethodType.valueOf(type)).map(methodDataEntityMapper::toMethodDataDto)
                .orElseThrow(() -> new DataNotFoundException("Method with type " + type + " not found"));
    }

    @Override
    public MethodDataDto getFastestMethodByType(String type) {
        return methodDataJpaRepository.findFastestMethodByType(MethodType.valueOf(type)).map(methodDataEntityMapper::toMethodDataDto)
                .orElseThrow(() -> new DataNotFoundException("Method with type " + type + " not found"));
    }

    @Override
    public List<MethodDataDto> getAllMethodsByType(String type) {
        return methodDataEntityMapper.toMethodDataDtos(methodDataJpaRepository.findAllByType(MethodType.valueOf(type)));
    }

    @Override
    public Double getAverageTimeExecutionByType(String type) {
        var methodDatas = methodDataJpaRepository.findAllByType(MethodType.valueOf(type));
        return methodDatas.stream()
                .mapToLong(MethodDataEntity::getExecutionTime)
                .average().orElseThrow(() -> new DataNotFoundException("Methods execution time not found"));
    }

    @Override
    public Double getAverageTimeExecution() {
        var methodDatas = methodDataJpaRepository.findAll();
        return methodDatas.stream()
                .mapToLong(MethodDataEntity::getExecutionTime)
                .average().orElseThrow(() -> new DataNotFoundException("Methods execution time not found"));
    }

    @Override
    public Double getAverageTimeExecutionByMethod(String methodName) {
        var methodDatas = methodDataJpaRepository.findAllByMethodName(methodName);
        return methodDatas.stream()
                .mapToLong(MethodDataEntity::getExecutionTime)
                .average().orElseThrow(() -> new DataNotFoundException("Method execution time not found"));
    }

    @Override
    public void saveMethodData(Long executionTime, String methodName, String className, String type) {
        var method = methodService.createOrGetMethodIfExist(methodName, className);
        methodDataJpaRepository.save(MethodDataEntity.builder()
                .method(method)
                .executionTime(executionTime)
                .type(MethodType.valueOf(type))
                .build());
    }
}
