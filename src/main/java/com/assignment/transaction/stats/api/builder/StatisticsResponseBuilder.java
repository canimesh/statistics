package com.assignment.transaction.stats.api.builder;

import org.springframework.stereotype.Component;

import java.util.DoubleSummaryStatistics;
import java.util.Objects;

/**
 * @author animesh.chakraborty on 06.07.18
 */
@Component
public class StatisticsResponseBuilder {

    public String build(final DoubleSummaryStatistics summaryStatistics) {
        if (Objects.isNull(summaryStatistics)) {
            throw new IllegalArgumentException("Invalid input");
        }
        return String.format(
                "{\"count\":%d,\"sum\":%f,\"min\":%f,\"avg\":%f,\"max\":%f}",
                summaryStatistics.getCount(),
                summaryStatistics.getSum(),
                summaryStatistics.getMin(),
                summaryStatistics.getAverage(),
                summaryStatistics.getMax());
    }
}
