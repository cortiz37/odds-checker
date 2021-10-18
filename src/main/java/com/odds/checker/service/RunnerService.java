package com.odds.checker.service;

import com.odds.checker.exception.CheckerException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class RunnerService implements CommandLineRunner {

    private final ExecutorService executorService;

    public RunnerService(ExecutorService executorService) {
        this.executorService = executorService;
    }

    @Override
    public void run(String... args) {
        if (args.length > 0) {
            try {
                executorService.start(Long.parseLong(args[0]), System.out);
            } catch (NumberFormatException e) {
                log.error("parameter eventId is invalid", e);
            } catch (CheckerException e) {
                log.error("data is missing", e);
            }
        } else {
            log.error("parameter eventId is required");
        }
    }
}
