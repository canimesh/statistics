package com.assignment.transaction.stats.api.controller;

import com.assignment.transaction.stats.api.builder.StatisticsResponseBuilder;
import com.assignment.transaction.stats.api.service.TransactionService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.DoubleSummaryStatistics;

public class StatisticsControllerTest {

    private StatisticsController statisticsController;

    @Mock
    private TransactionService transactionService;

    @Mock
    private StatisticsResponseBuilder responseBuilder;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        statisticsController = new StatisticsController(transactionService, responseBuilder);
    }

    @Test
    public void testGetStatistics_successful() {
        final DoubleSummaryStatistics doubleSummaryStatistics = Mockito.mock(DoubleSummaryStatistics.class);
        Mockito.when(transactionService.getStatistics()).thenReturn(doubleSummaryStatistics);
        Mockito.when(responseBuilder.build(doubleSummaryStatistics)).thenReturn("response for statistics api");
        final String actualResponse = statisticsController.getStatistics();
        Assert.assertEquals("response for statistics api", actualResponse);
    }
}