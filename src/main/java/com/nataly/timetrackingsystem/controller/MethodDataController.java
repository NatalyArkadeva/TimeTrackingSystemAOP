package com.nataly.timetrackingsystem.controller;

import com.nataly.timetrackingsystem.dto.MethodDataDto;
import com.nataly.timetrackingsystem.service.MethodDataService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/v1/statistics")
@RequiredArgsConstructor
public class MethodDataController {

    private final MethodDataService methodDataService;

    @Operation(summary = "Получение самого длительного метода по типу")
    @GetMapping("/longest/{type}")
    public ResponseEntity<MethodDataDto> getLongestMethodByType(@PathVariable(value = "type") String type) {
        return ResponseEntity.ok(methodDataService.getLongestMethodByType(type));
    }

    @Operation(summary = "Получение самого быстрого метода по типу")
    @GetMapping("/fastest/{type}")
    public ResponseEntity<MethodDataDto> getFastestMethodByType(@PathVariable(value = "type") String type) {
        return ResponseEntity.ok(methodDataService.getFastestMethodByType(type));
    }

    @Operation(summary = "Получение всех методов по типу")
    @GetMapping("/all/{type}")
    public ResponseEntity<List<MethodDataDto>> getAllMethodsByType(@PathVariable(value = "type") String type) {
        return ResponseEntity.ok(methodDataService.getAllMethodsByType(type));
    }

    @Operation(summary = "Получение среднего значения времени выполнения методов по типу")
    @GetMapping("/average/{type}")
    public ResponseEntity<Double> getAverageTimeExecutionByType(@PathVariable(value = "type") String type) {
        return ResponseEntity.ok(methodDataService.getAverageTimeExecutionByType(type));
    }

    @Operation(summary = "Получение среднего значения времени выполнения всех методов")
    @GetMapping("/average")
    public ResponseEntity<Double> getAverageTimeExecution() {
        return ResponseEntity.ok(methodDataService.getAverageTimeExecution());
    }

    @Operation(summary = "Получение среднего значения времени выполнения методов по названию")
    @GetMapping("/average/method")
    public ResponseEntity<Double> getAverageTimeExecutionByMethod(@RequestParam(name = "methodName") String methodName) {
        return ResponseEntity.ok(methodDataService.getAverageTimeExecutionByMethod(methodName));
    }
}
