package com.nataly.timetrackingsystem.repository;

import com.nataly.timetrackingsystem.model.MethodDataEntity;
import com.nataly.timetrackingsystem.model.enums.MethodType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MethodDataJpaRepository extends JpaRepository<MethodDataEntity, Long> {

    @Query("select md from MethodDataEntity md where md.method.methodName =:methodName")
    List<MethodDataEntity> findAllByMethodName(String methodName);

    @Query("select md from MethodDataEntity md where md.type =:methodType")
    List<MethodDataEntity> findAllByType(MethodType methodType);

    @Query("select md from MethodDataEntity md where md.type =:methodType order by md.executionTime desc limit 1")
    Optional<MethodDataEntity> findLongestMethodByType(MethodType methodType);

    @Query("select md from MethodDataEntity md where md.type =:methodType order by md.executionTime asc limit 1")
    Optional<MethodDataEntity> findFastestMethodByType(MethodType methodType);
}
