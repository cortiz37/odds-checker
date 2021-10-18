package com.odds.checker.service;

import com.odds.checker.exception.CheckerException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.PrintStream;

@ExtendWith(MockitoExtension.class)
class RunnerServiceTest {

    @Mock
    private ExecutorService executorService;

    private RunnerService runnerService;

    @BeforeEach
    public void setUp() {
        runnerService = new RunnerService(executorService);
    }

    @Test
    public void shouldRun() throws CheckerException {
        runnerService.run("10000");

        Mockito.verify(executorService, Mockito.times(1)).start(ArgumentMatchers.anyLong(), ArgumentMatchers.any(PrintStream.class));
    }

    @Test
    public void shouldNotRunWhenNoParameterProvided() throws CheckerException {
        runnerService.run();

        Mockito.verify(executorService, Mockito.times(0)).start(ArgumentMatchers.anyLong(), ArgumentMatchers.any(PrintStream.class));
    }

    @Test
    public void shouldNotRunWhenWrongParameterTypeProvided() throws CheckerException {
        runnerService.run("123_not_a_long_value");

        Mockito.verify(executorService, Mockito.times(0)).start(ArgumentMatchers.anyLong(), ArgumentMatchers.any(PrintStream.class));
    }
}