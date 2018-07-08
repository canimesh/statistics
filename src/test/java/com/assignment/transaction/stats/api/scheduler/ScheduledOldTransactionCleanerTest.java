package com.assignment.transaction.stats.api.scheduler;

import com.assignment.transaction.stats.api.model.Transaction;
import com.assignment.transaction.stats.api.store.TransactionsStore;
import com.assignment.transaction.stats.api.util.Utilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.test.util.ReflectionTestUtils;

import java.util.function.Predicate;

import static org.mockito.ArgumentMatchers.any;

public class ScheduledOldTransactionCleanerTest {

    private ScheduledOldTransactionCleaner scheduledOldTransactionCleaner;

    @Mock
    private TransactionsStore lastTransactionsStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        scheduledOldTransactionCleaner = new ScheduledOldTransactionCleaner(lastTransactionsStore);
        ReflectionTestUtils.setField(scheduledOldTransactionCleaner, "transactionDataTtl", 1999L);
    }

    @Test
    public void test_cleanOldData_predicate_true() {
        Mockito.doNothing().when(lastTransactionsStore).cleanUpTransactions(any(Predicate.class));
        scheduledOldTransactionCleaner.cleanOldData();

        ArgumentCaptor<Predicate> predicateArgumentCaptor = ArgumentCaptor.forClass(Predicate.class);
        Mockito.verify(lastTransactionsStore).cleanUpTransactions(predicateArgumentCaptor.capture());

        Predicate<Transaction> actualPredicate = (Predicate<Transaction>) predicateArgumentCaptor.getValue();

        Transaction transaction = new Transaction();
        transaction.setTimestamp(Utilities.getUTCEpochInMillis() - 2000L);
        final boolean actualTest = actualPredicate.test(transaction);

        Assert.assertTrue(actualTest);
    }

    @Test
    public void test_cleanOldData_predicate_false() {
        Mockito.doNothing().when(lastTransactionsStore).cleanUpTransactions(any(Predicate.class));
        scheduledOldTransactionCleaner.cleanOldData();

        ArgumentCaptor<Predicate> predicateArgumentCaptor = ArgumentCaptor.forClass(Predicate.class);
        Mockito.verify(lastTransactionsStore).cleanUpTransactions(predicateArgumentCaptor.capture());

        Predicate<Transaction> actualPredicate = (Predicate<Transaction>) predicateArgumentCaptor.getValue();

        Transaction transaction = new Transaction();
        transaction.setTimestamp(Utilities.getUTCEpochInMillis() - 200L);
        final boolean actualTest = actualPredicate.test(transaction);

        Assert.assertFalse(actualTest);
    }
}