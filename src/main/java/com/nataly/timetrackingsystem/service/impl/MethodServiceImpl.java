package com.nataly.timetrackingsystem.service.impl;

import com.nataly.timetrackingsystem.model.MethodEntity;
import com.nataly.timetrackingsystem.repository.MethodJpaRepository;
import com.nataly.timetrackingsystem.service.MethodService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class MethodServiceImpl implements MethodService {

    private final MethodJpaRepository methodJpaRepository;

    @Override
    public MethodEntity createOrGetMethodIfExist(String methodName, String className) {
        Optional<MethodEntity> methodOpt = methodJpaRepository.findByMethodNameAndClassName(methodName, className);
        return methodOpt.orElseGet(() -> methodJpaRepository.save(MethodEntity.builder()
                .methodName(methodName)
                .className(className)
                .build()));
    }
}
