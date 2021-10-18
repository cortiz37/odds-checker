package com.odds.checker.service;

import com.odds.checker.exception.CheckerException;
import com.odds.checker.model.input.DataWrapper;
import com.odds.checker.model.input.EventWrapper;
import com.odds.checker.model.input.MainBetOffer;
import com.odds.checker.model.output.Match;
import com.odds.checker.model.output.Odds;
import lombok.SneakyThrows;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class ExecutorService {

    private final DataReaderService dataReaderService;
    private final OutputService outputService;
    private final Timer timer;

    private final String dataFilterTag;

    private final long dataUpdatePeriod;

    public ExecutorService(DataReaderService dataReaderService,
                           OutputService outputService,
                           Timer timer,
                           @Value("${data.filter.tag}") String dataFilterTag,
                           @Value("${data.update.period}") long dataUpdatePeriod) {
        this.dataReaderService = dataReaderService;
        this.outputService = outputService;
        this.timer = timer;
        this.dataFilterTag = dataFilterTag;
        this.dataUpdatePeriod = dataUpdatePeriod;
    }

    public void start(long eventId, PrintStream stream) throws CheckerException {
        timer.scheduleAtFixedRate(new TimerTask() {
            @SneakyThrows
            @Override
            public void run() {
                Match match = getEventWrapper(dataReaderService.getDataWrapper(), eventId)
                    .map(eventWrapper ->
                        Match.builder()
                            .name(eventWrapper.getEvent().getName())
                            .odds(getOddsList(eventWrapper.getMainBetOffer()))
                            .build()
                    ).orElseThrow(() -> new CheckerException(String.format("match %s not found", eventId)));

                outputService.print(match, stream);
            }
        }, 0, dataUpdatePeriod);
    }

    protected Optional<EventWrapper> getEventWrapper(DataWrapper dataWrapper, long eventId) {
        return dataWrapper.getLiveEvents()
            .stream()
            .filter(l -> l.getEvent().getTags().contains(dataFilterTag))
            .filter(l -> eventId == l.getEvent().getId())
            .findFirst();
    }

    protected List<Odds> getOddsList(MainBetOffer mainBetOffer) {
        return mainBetOffer != null ? mainBetOffer.getOutcomes()
            .stream()
            .map(o ->
                Odds.builder()
                    .label(o.getLabel())
                    .value(o.getOdds() / 1000.0)
                    .build())
            .collect(Collectors.toList())
            :
            new ArrayList<>();
    }
}
