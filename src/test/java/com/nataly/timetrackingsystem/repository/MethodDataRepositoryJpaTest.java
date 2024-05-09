package com.nataly.timetrackingsystem.repository;

import com.nataly.timetrackingsystem.TimetrackingsystemApplication;
import com.nataly.timetrackingsystem.model.MethodDataEntity;
import com.nataly.timetrackingsystem.model.MethodEntity;
import com.nataly.timetrackingsystem.model.enums.MethodType;
import jakarta.annotation.PostConstruct;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimetrackingsystemApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class MethodDataRepositoryJpaTest {

    @Autowired
    private MethodJpaRepository methodJpaRepository;

    @Autowired
    private MethodDataJpaRepository methodDataJpaRepository;

    private MethodEntity methodEntity;

    @PostConstruct
    void init() {
        methodEntity = MethodEntity.builder()
                .methodName("methodName")
                .className("className")
                .build();
        methodDataJpaRepository.saveAll(List.of(MethodDataEntity.builder()
                .method(methodEntity)
                .executionTime(10L)
                .type(MethodType.SYNC)
                .build(), MethodDataEntity.builder()
                .method(methodEntity)
                .executionTime(20L)
                .type(MethodType.SYNC)
                .build()));
    }

    @Test
    @Transactional
    public void findAllByMethodName() {

        var result = methodDataJpaRepository.findAllByMethodName(methodEntity.getMethodName());

        assertThat(result.size()).isEqualTo(2);
        assertThat(result.get(0).getMethod().getMethodName()).isEqualTo(methodEntity.getMethodName());
    }

    @Test
    public void findLongestMethodByType() {

        var result = methodDataJpaRepository.findLongestMethodByType(MethodType.SYNC);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getExecutionTime()).isEqualTo(20L);
    }

    @Test
    public void findFastestMethodByType() {

        var result = methodDataJpaRepository.findFastestMethodByType(MethodType.SYNC);

        assertThat(result.isPresent()).isTrue();
        assertThat(result.get().getExecutionTime()).isEqualTo(10L);
    }
}
