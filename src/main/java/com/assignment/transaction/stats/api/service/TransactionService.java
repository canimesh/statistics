package com.assignment.transaction.stats.api.service;

import com.assignment.transaction.stats.api.model.Transaction;
import com.assignment.transaction.stats.api.store.TransactionsStore;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.DoubleSummaryStatistics;

@Service
@RequiredArgsConstructor
public class TransactionService {

    private final TransactionsStore transactionsStore;

    public void add(Transaction transaction) {
        transactionsStore.add(transaction);
    }

    public DoubleSummaryStatistics getStatistics() {
        return transactionsStore.getSummaryStatistics();
    }

}
