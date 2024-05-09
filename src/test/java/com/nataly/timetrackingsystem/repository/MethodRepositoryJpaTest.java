package com.nataly.timetrackingsystem.repository;

import com.nataly.timetrackingsystem.TimetrackingsystemApplication;
import com.nataly.timetrackingsystem.model.MethodEntity;
import org.junit.After;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(classes = TimetrackingsystemApplication.class)
@AutoConfigureMockMvc
public class MethodRepositoryJpaTest {

    @Autowired
    private MethodJpaRepository methodJpaRepository;

    @After
    void clear(){
        methodJpaRepository.deleteAll();
    }

    @Test
    public void findByMethodNameAndClassName() {
        String methodName = "methodName";
        String className = "className";
        var methodEntity = methodJpaRepository.save(MethodEntity.builder()
                .methodName(methodName)
                .className(className)
                .build());

        var resultOpt = methodJpaRepository.findByMethodNameAndClassName(methodName, className);

        assertThat(resultOpt.isPresent()).isTrue();
        assertThat(resultOpt.get()).isEqualTo(methodEntity);
    }
}
