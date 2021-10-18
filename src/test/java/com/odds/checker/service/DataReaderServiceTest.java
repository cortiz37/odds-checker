package com.odds.checker.service;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.odds.checker.exception.CheckerException;
import com.odds.checker.model.input.DataWrapper;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.junit.jupiter.MockitoExtension;

import java.io.File;
import java.net.MalformedURLException;

@ExtendWith(MockitoExtension.class)
class DataReaderServiceTest {

    private DataReaderService dataReaderService;

    @Test
    public void shouldReadJson() throws CheckerException, MalformedURLException {
        ClassLoader classLoader = getClass().getClassLoader();
        String source = new File(
            classLoader.getResource("standard.json").getFile()
        ).toURI().toURL().toString();

        dataReaderService = new DataReaderService(new ObjectMapper(), source);

        final DataWrapper dataWrapper = dataReaderService.getDataWrapper();

        Assertions.assertNotNull(dataWrapper);
        Assertions.assertNotNull(dataWrapper.getLiveEvents());
        Assertions.assertFalse(dataWrapper.getLiveEvents().isEmpty());
        Assertions.assertEquals("Real Madrid - Barcelona FC", dataWrapper.getLiveEvents().get(0).getEvent().getName());
    }
}