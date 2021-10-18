package com.odds.checker.service;

import com.odds.checker.model.output.Match;
import com.odds.checker.model.output.Odds;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@ExtendWith(MockitoExtension.class)
class OutputServiceTest {

    private OutputService outputService;

    @BeforeEach
    public void setUp() {
        outputService = new OutputService("yyyy");
    }

    @Test
    public void shouldPrintSingleEntry() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        Match match = Match.builder()
            .name("game 1")
            .odds(
                Collections.singletonList(
                    Odds.builder()
                        .label("X")
                        .value(5.0)
                        .build()
                )
            ).build();

        outputService.print(match, printStream);

        String printResult = new String(outputStream.toByteArray());

        Assertions.assertTrue(printResult.contains("Event: game 1"));
        Assertions.assertTrue(printResult.contains("5.00"));
    }

    @Test
    public void shouldPrintSeveralEntries() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        Odds odds = Odds.builder().label("X").value(5.0).build();
        List<Odds> oddsList = new ArrayList<>();
        oddsList.add(odds);

        Match match = Match.builder()
            .name("game 1")
            .odds(oddsList)
            .build();

        outputService.print(match, printStream);

        match.getOdds().add(Odds.builder().label("X").value(6.0).build());
        outputService.print(match, printStream);

        String printResult = new String(outputStream.toByteArray());

        Assertions.assertTrue(printResult.contains("5.00"));
        Assertions.assertTrue(printResult.contains("6.00"));
    }

    @Test
    public void shouldNotPrintSeveralEntries() {
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        PrintStream printStream = new PrintStream(outputStream);

        Odds odds = Odds.builder().label("X").value(5.0).build();

        Match match = Match.builder()
            .name("game 1")
            .odds(Collections.singletonList(odds))
            .build();

        outputService.print(match, printStream);
        outputService.print(match, printStream);

        String printResult = new String(outputStream.toByteArray());

        Assertions.assertEquals(printResult.indexOf("5.00"), printResult.lastIndexOf("5.00"));
    }

}