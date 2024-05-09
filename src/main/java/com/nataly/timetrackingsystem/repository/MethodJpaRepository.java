package com.nataly.timetrackingsystem.repository;

import com.nataly.timetrackingsystem.model.MethodEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MethodJpaRepository extends JpaRepository<MethodEntity, Long> {

    Optional<MethodEntity> findByMethodNameAndClassName(String methodName, String className);
}
