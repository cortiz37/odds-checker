package com.odds.checker.service;

import com.odds.checker.exception.CheckerException;
import com.odds.checker.model.input.*;
import com.odds.checker.model.output.Odds;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentMatchers;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.*;

@ExtendWith(MockitoExtension.class)
class ExecutorServiceTest {

    private static final String dataFilterTag = "MATCH";

    private ExecutorService executorService;

    @Mock
    private DataReaderService dataReaderService;

    @Mock
    private OutputService outputService;

    @Mock
    private Timer timer;

    @BeforeEach
    public void setUp() {
        executorService = new ExecutorService(dataReaderService, outputService, timer, dataFilterTag, 2500);
    }

    @Test
    public void shouldStart() throws CheckerException {
        executorService.start(1000, System.out);

        Mockito.verify(timer, Mockito.times(1)).scheduleAtFixedRate(ArgumentMatchers.any(TimerTask.class), ArgumentMatchers.anyLong(), ArgumentMatchers.anyLong());
    }

    @Test
    public void shouldGetEventWrapper() {
        final long eventId = 1000;

        Event event = new Event();
        event.setId(eventId);
        event.setTags(Collections.singleton(dataFilterTag));

        EventWrapper eventWrapper = new EventWrapper();
        eventWrapper.setEvent(event);
        eventWrapper.setMainBetOffer(new MainBetOffer());

        DataWrapper dataWrapper = new DataWrapper();
        dataWrapper.setLiveEvents(Collections.singletonList(eventWrapper));

        final Optional<EventWrapper> wrapperOptional = executorService.getEventWrapper(dataWrapper, eventId);

        Assertions.assertNotNull(wrapperOptional);
        Assertions.assertTrue(wrapperOptional.isPresent());
    }

    @Test
    public void shouldGetOddsList() {
        Outcome outcome = new Outcome();
        outcome.setLabel("odd 1");
        outcome.setOdds(3000);

        MainBetOffer mainBetOffer = new MainBetOffer();
        mainBetOffer.setOutcomes(Collections.singletonList(outcome));

        final List<Odds> odds = executorService.getOddsList(mainBetOffer);

        Assertions.assertNotNull(odds);
        Assertions.assertEquals(1, odds.size());
        Assertions.assertEquals(3.0, odds.get(0).getValue());
    }
}