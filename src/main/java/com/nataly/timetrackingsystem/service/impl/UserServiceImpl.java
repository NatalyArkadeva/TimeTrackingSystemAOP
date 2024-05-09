package com.nataly.timetrackingsystem.service.impl;

import com.nataly.timetrackingsystem.aspect.annotation.TrackAsyncTime;
import com.nataly.timetrackingsystem.aspect.annotation.TrackTime;
import com.nataly.timetrackingsystem.model.UserEntity;
import com.nataly.timetrackingsystem.repository.UserJpaRepository;
import com.nataly.timetrackingsystem.service.UserService;
import lombok.RequiredArgsConstructor;
import org.jeasy.random.EasyRandom;
import org.jeasy.random.EasyRandomParameters;
import org.springframework.stereotype.Service;

import static org.jeasy.random.FieldPredicates.named;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserJpaRepository userJpaRepository;
    private final EasyRandomParameters randomParameters = new EasyRandomParameters()
            .excludeField(named("id"));
    private final EasyRandom random = new EasyRandom(randomParameters);

    @Override
    @TrackTime
    public void saveUser(int countUser) {
        for (int i = 0; i < countUser; i++) {
            userJpaRepository.save(random.nextObject(UserEntity.class));
        }
    }

    @Override
    @TrackAsyncTime
    public void saveUserAsync(int countUser) {
        for (int i = 0; i < countUser; i++) {
            userJpaRepository.save(random.nextObject(UserEntity.class));
        }
    }
}
