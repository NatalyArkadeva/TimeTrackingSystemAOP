package com.nataly.timetrackingsystem.controller;

import com.nataly.timetrackingsystem.TimetrackingsystemApplication;
import com.nataly.timetrackingsystem.model.enums.MethodType;
import com.nataly.timetrackingsystem.repository.MethodDataJpaRepository;
import com.nataly.timetrackingsystem.repository.UserJpaRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@SpringBootTest(classes = TimetrackingsystemApplication.class)
@AutoConfigureMockMvc
@AutoConfigureTestDatabase(replace= AutoConfigureTestDatabase.Replace.NONE)
public class UserControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private UserJpaRepository userJpaRepository;
    @Autowired
    private MethodDataJpaRepository methodDataJpaRepository;

    @AfterEach
    void clear() {
        userJpaRepository.deleteAll();
        methodDataJpaRepository.deleteAll();
    }

    @Test
    @Transactional
    public void saveUsers() throws Exception {

        this.mockMvc.perform(
                        post("/api/v1/users/save/{count}", 2)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var users = userJpaRepository.findAll();
        var methodData = methodDataJpaRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
        assertThat(methodData).isNotNull();
        assertThat(methodData.get(0).getType()).isEqualTo(MethodType.SYNC);
    }

    @Test
    public void saveUsersAsync() throws Exception {

        this.mockMvc.perform(
                        post("/api/v1/users/save/async/{count}", 2)
                                .contentType(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk());

        var users = userJpaRepository.findAll();
        var methodData = methodDataJpaRepository.findAll();

        assertThat(users).isNotNull();
        assertThat(users.size()).isEqualTo(2);
        assertThat(methodData).isNotNull();
        assertThat(methodData.size()).isEqualTo(1);
        assertThat(methodData.get(0).getType()).isEqualTo(MethodType.ASYNC);
    }
}
