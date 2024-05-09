package com.nataly.timetrackingsystem.controller;

import com.nataly.timetrackingsystem.service.UserService;
import io.swagger.v3.oas.annotations.Operation;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/v1/users")
@RequiredArgsConstructor
public class UserController {

    private final UserService userService;

    @Operation(summary = "Создание новых пользователей(синхроное сохранение данных)")
    @PostMapping("/save/{count}")
    public ResponseEntity<Void> saveUsers(@PathVariable(value = "count") int count) {
        userService.saveUser(count);
        return ResponseEntity.ok().build();
    }

    @Operation(summary = "Создание новых пользователей(асинхроное сохранение данных)")
    @PostMapping("/save/async/{count}")
    public ResponseEntity<Void> saveUsersAsync(@PathVariable(value = "count") int count) {
        userService.saveUserAsync(count);
        return ResponseEntity.ok().build();
    }
}
