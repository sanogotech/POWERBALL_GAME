package com.spiritbeing.powerball.service;

import com.spiritbeing.powerball.model.PowerBall;
import com.spiritbeing.powerball.repo.PowerBallRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.HashSet;
import java.util.Optional;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.*;
import static org.springframework.test.util.AssertionErrors.assertNotNull;

@ExtendWith(MockitoExtension.class)
class PowerBallServiceImplTest {

    @Mock
    PowerBallRepository repository;

    PowerBallServiceImpl powerBallService;

    PowerBall powerBall;

    Long powerBallId;

    @BeforeEach
    void setUp() {
        powerBallId = 1L;
        powerBall = new PowerBall();
        powerBallService = new PowerBallServiceImpl(repository);
    }

    @Test
    void findAll() {
        Set<PowerBall> powerBallSet = new HashSet<>();
        powerBallSet.add(new PowerBall());

        when(repository.findAll()).thenReturn(powerBallSet);

        Set<PowerBall> pb = new HashSet<>();
        repository.findAll().forEach(pb::add);

        assertEquals(1, pb.size());
    }

    @Test
    void findById() {
        powerBall.setId(1L);
        Optional<PowerBall> powerBallOptional = Optional.of(powerBall);

        when(repository.findById(anyLong())).thenReturn(powerBallOptional);

        PowerBall powerBallReturned = powerBallService.findById(1L);

        assertNotNull("Null recipe returned", powerBallReturned);
        verify(repository, times(1)).findById(anyLong());
    }

    @Test
    void save() {
        Set<PowerBall> powerBallSet = new HashSet<>();
        powerBallSet.add(new PowerBall());
        powerBallSet.add(new PowerBall());

        when(repository.findAll()).thenReturn(powerBallSet);

        Set<PowerBall> pb = new HashSet<>();
        repository.findAll().forEach(pb::add);

        assertEquals(2, pb.size());
    }

    @Test
    void delete() {
        //when
        powerBallService.delete(powerBall);

        verify(repository, times(1)).delete(any());
    }
}