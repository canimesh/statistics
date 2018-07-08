package com.assignment.transaction.stats.api.scheduler;

import com.assignment.transaction.stats.api.model.Transaction;
import com.assignment.transaction.stats.api.store.TransactionsStore;
import com.assignment.transaction.stats.api.util.Utilities;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;


@Component
@RequiredArgsConstructor
public class ScheduledOldTransactionCleaner {

    private final TransactionsStore transactionsStore;

    @Value("${transaction.data.ttl:60000}")
    private Long transactionDataTtl;

    @Scheduled(fixedRate = 1000)
    public void cleanOldData() {
        transactionsStore.cleanUpTransactions(this::isStale);
    }

    private boolean isStale(final Transaction transaction) {
        return Utilities.getUTCEpochInMillis() - transaction.getTimestamp() > transactionDataTtl;
    }
}