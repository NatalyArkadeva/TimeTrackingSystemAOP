package com.nataly.timetrackingsystem.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.nataly.timetrackingsystem.TimetrackingsystemApplication;
import com.nataly.timetrackingsystem.mapper.MethodDataEntityMapper;
import com.nataly.timetrackingsystem.model.MethodDataEntity;
import com.nataly.timetrackingsystem.model.MethodEntity;
import com.nataly.timetrackingsystem.model.enums.MethodType;
import com.nataly.timetrackingsystem.repository.MethodDataJpaRepository;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest(classes = TimetrackingsystemApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class MethodDataControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private ObjectMapper objectMapper;
    @Autowired
    private MethodDataJpaRepository methodDataJpaRepository;
    @Autowired
    private MethodDataEntityMapper methodDataEntityMapper;


    @PostConstruct
    void init() {
        List<MethodDataEntity> methodDataEntities = List.of(MethodDataEntity.builder()
                .type(MethodType.SYNC)
                .executionTime(50L)
                .method(MethodEntity.builder()
                        .className("className1")
                        .methodName("SYNC")
                        .build())
                .build(), MethodDataEntity.builder()
                .type(MethodType.SYNC)
                .executionTime(55L)
                .method(MethodEntity.builder()
                        .className("className1")
                        .methodName("SYNC")
                        .build())
                .build(), MethodDataEntity.builder()
                .type(MethodType.ASYNC)
                .executionTime(0L)
                .method(MethodEntity.builder()
                        .className("className2")
                        .methodName("ASYNC")
                        .build())
                .build(), MethodDataEntity.builder()
                .type(MethodType.ASYNC)
                .executionTime(1L)
                .method(MethodEntity.builder()
                        .className("className2")
                        .methodName("ASYNC")
                        .build())
                .build()
        );
        methodDataJpaRepository.saveAll(methodDataEntities);
    }

    @Test
    public void getLongestMethodByType() throws Exception {

        var result = methodDataJpaRepository.findAllByType(MethodType.SYNC).stream()
                .mapToLong(MethodDataEntity::getExecutionTime)
                .max();

        this.mockMvc.perform(
                        get("/api/v1/statistics/longest/{type}", "SYNC")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("executionTime").value(result.getAsLong()));
    }

    @Test
    public void getFastestMethodByType() throws Exception {

        var result = methodDataJpaRepository.findAllByType(MethodType.ASYNC).stream()
                .mapToLong(MethodDataEntity::getExecutionTime)
                .min();

        this.mockMvc.perform(
                        get("/api/v1/statistics/fastest/{type}", "ASYNC")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("executionTime").value(result.getAsLong()));
    }

    @Test
    @Transactional
    public void getAllMethodsByType() throws Exception {

        var result = methodDataEntityMapper.toMethodDataDtos(methodDataJpaRepository.findAllByType(MethodType.ASYNC));

        this.mockMvc.perform(
                        get("/api/v1/statistics/all/{type}", "ASYNC")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().json(objectMapper.writeValueAsString(result)));
    }

    @Test
    public void getAverageTimeExecutionByType() throws Exception {

        var result = methodDataJpaRepository.findAllByType(MethodType.SYNC).stream()
                .mapToLong(MethodDataEntity::getExecutionTime)
                .average();

        this.mockMvc.perform(
                        get("/api/v1/statistics/average/{type}", "SYNC")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(result.getAsDouble())));
    }

    @Test
    public void getAverageTimeExecution() throws Exception {

        var result = methodDataJpaRepository.findAll().stream()
                .mapToLong(MethodDataEntity::getExecutionTime)
                .average();

        this.mockMvc.perform(
                        get("/api/v1/statistics/average")
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(result.getAsDouble())));
    }

    @Test
    public void getAverageTimeExecutionByMethod() throws Exception {

        var result = methodDataJpaRepository.findAllByMethodName("SYNC").stream()
                .mapToLong(MethodDataEntity::getExecutionTime)
                .average();

        this.mockMvc.perform(
                        get("/api/v1/statistics/average/method")
                                .contentType(MediaType.APPLICATION_JSON)
                                .param("methodName", "SYNC"))
                .andExpect(status().isOk())
                .andExpect(content().string(String.valueOf(result.getAsDouble())));
    }
}
