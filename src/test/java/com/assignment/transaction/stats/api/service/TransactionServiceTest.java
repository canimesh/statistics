package com.assignment.transaction.stats.api.service;

import com.assignment.transaction.stats.api.model.Transaction;
import com.assignment.transaction.stats.api.store.TransactionsStore;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;

import java.util.DoubleSummaryStatistics;

import static org.mockito.ArgumentMatchers.any;

public class TransactionServiceTest {

    private TransactionService transactionService;

    @Mock
    private TransactionsStore transactionsStore;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionService = new TransactionService(transactionsStore);
    }

    @Test
    public void testAdd() {
        final Transaction transaction = new Transaction();
        Mockito.doNothing().when(transactionsStore).add(any(Transaction.class));
        transactionService.add(transaction);

        Mockito.verify(transactionsStore, Mockito.times(1)).add(transaction);
    }


    @Test
    public void testGetStatistics() {
        DoubleSummaryStatistics mockSummaryStatistics = Mockito.mock(DoubleSummaryStatistics.class);
        Mockito.when(transactionsStore.getSummaryStatistics()).thenReturn(mockSummaryStatistics);
        final DoubleSummaryStatistics statistics = transactionService.getStatistics();
        Mockito.verify(transactionsStore, Mockito.times(1)).getSummaryStatistics();
        Assert.assertEquals(mockSummaryStatistics, statistics);
    }
}