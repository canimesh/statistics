package com.assignment.transaction.stats.api.controller;

import com.assignment.transaction.stats.api.builder.StatisticsResponseBuilder;
import com.assignment.transaction.stats.api.service.TransactionService;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.DoubleSummaryStatistics;

@RestController
@RequiredArgsConstructor
public class StatisticsController {

    private final TransactionService transactionService;

    private final StatisticsResponseBuilder statisticsResponseBuilder;

    @GetMapping("/statistics")
    public String getStatistics() {
        final DoubleSummaryStatistics statistics = transactionService.getStatistics();
        return statisticsResponseBuilder.build(statistics);
    }
}

