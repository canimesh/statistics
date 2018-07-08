package com.assignment.transaction.stats.api.builder;

import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.DoubleSummaryStatistics;
import java.util.stream.Stream;

public class StatisticsResponseBuilderTest {

    private StatisticsResponseBuilder statisticsResponseBuilder;

    @Before
    public void setUp() throws Exception {
        statisticsResponseBuilder = new StatisticsResponseBuilder();
    }

    @Test(expected = IllegalArgumentException.class)
    public void testBuild_illegalArgument() {
        statisticsResponseBuilder.build(null);
    }

    @Test
    public void testBuild_validValues() {
        final DoubleSummaryStatistics summaryStatistics = Stream.of(10.0, 10.0, 10.0, 40.0, 10.0).mapToDouble(x -> x).summaryStatistics();
        final String buildContent = statisticsResponseBuilder.build(summaryStatistics);

        Assert.assertEquals("{\"count\":5,\"sum\":80.000000,\"min\":10.000000,\"avg\":16.000000,\"max\":40.000000}", buildContent);
    }
}