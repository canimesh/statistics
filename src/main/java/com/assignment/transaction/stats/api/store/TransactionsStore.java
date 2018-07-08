package com.assignment.transaction.stats.api.store;

import com.assignment.transaction.stats.api.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.DoubleSummaryStatistics;
import java.util.List;
import java.util.concurrent.locks.StampedLock;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * @author animesh.chakraborty on 06.07.18
 */
@Slf4j
@Component
public class TransactionsStore {

    private final StampedLock stampedLock = new StampedLock();

    private final List<Transaction> transactions = new ArrayList<>();

    private DoubleSummaryStatistics summaryStatistics = new DoubleSummaryStatistics();

    public void add(final Transaction transaction) {
        final long stamp = stampedLock.writeLock();
        try {
            transactions.add(transaction);
        } finally {
            stampedLock.unlock(stamp);
        }
        this.computeAndSetSummaryStatistics();
    }

    private void computeAndSetSummaryStatistics() {
        this.summaryStatistics = this.getAllTransactionAmount().stream().mapToDouble(x -> x).summaryStatistics();
    }

    private List<Double> getAllTransactionAmount() {
        long stamp = stampedLock.readLock();
        List<Double> amounts;
        try {
            amounts = extractAmounts(transactions);
        } finally {
            stampedLock.unlock(stamp);
        }
        return amounts;
    }

    private List<Double> extractAmounts(List<Transaction> transactions) {
        return transactions.stream().map(Transaction::getAmount).collect(Collectors.toList());
    }

    public void cleanUpTransactions(final Predicate<Transaction> condition) {
        long stamp = stampedLock.writeLock();
        List<Transaction> validValues = new ArrayList<>();
        try {
            transactions.forEach((ts) -> {
                if (!condition.test(ts)) {
                    validValues.add(ts);
                }
            });
            transactions.clear();
            transactions.addAll(validValues);
        } finally {
            stampedLock.unlock(stamp);
        }

        this.computeAndSetSummaryStatistics();
    }

    public DoubleSummaryStatistics getSummaryStatistics() {
        return this.summaryStatistics;
    }

}
