package com.odds.checker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odds.checker.exception.CheckerException;
import com.odds.checker.model.input.DataWrapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.net.URL;

@Service
public class DataReaderService {

    private final ObjectMapper objectMapper;
    private final String dataSource;

    public DataReaderService(ObjectMapper objectMapper,
                             @Value("${data.source}") String dataSource) {
        this.objectMapper = objectMapper;
        this.dataSource = dataSource;
    }

    public DataWrapper getDataWrapper() throws CheckerException {
        try {
            return objectMapper.readValue(new URL(dataSource), DataWrapper.class);
        } catch (IOException e) {
            throw new CheckerException(String.format("data cannot be loaded from: %s", dataSource));
        }
    }
}