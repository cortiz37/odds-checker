package com.odds.checker.service;

import com.odds.checker.model.output.Match;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.PrintStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OutputService {

    private final Map<String, Boolean> cache;
    private final String dataDateFormat;

    public OutputService(@Value("${data.date.format}") String dataDateFormat) {
        this.dataDateFormat = dataDateFormat;
        cache = new HashMap<>();
    }

    public void print(Match match, PrintStream stream) {
        String eventName = String.format("Event: %s", match.getName());
        String eventOdds = match.getOdds()
                .stream()
                .map(odds -> String.format("%s: %8.2f", odds.getLabel(), odds.getValue()))
                .collect(Collectors.joining(" | ", "", " |"));

        printNew(eventName, false, stream);
        printNew(eventOdds, true, stream);
    }

    private void printNew(String data, boolean includeTimestamp, PrintStream stream) {

        if (!cache.containsKey(data)) {
            StringBuilder stringBuilder = new StringBuilder();
            if(includeTimestamp) {
                stringBuilder.append(String.format("[%s]", LocalDateTime.now().format(DateTimeFormatter.ofPattern(dataDateFormat))));
                stringBuilder.append(" | ");
            }
            cache.put(data, true);
            stringBuilder.append(data);
            stream.println(stringBuilder.toString());
        }
    }
}
