package com.nataly.timetrackingsystem.service;

import com.nataly.timetrackingsystem.model.MethodEntity;

public interface MethodService {

    MethodEntity createOrGetMethodIfExist(String methodName, String className);
}
