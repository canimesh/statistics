package com.assignment.transaction.stats.api.store;

import com.assignment.transaction.stats.api.model.Transaction;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.DoubleSummaryStatistics;

public class TransactionsStoreTest {

    private TransactionsStore transactionsStore;


    @Before
    public void setUp() throws Exception {
        transactionsStore = new TransactionsStore();
    }

    @Test
    public void testAdd() {
        Transaction transactionOne = new Transaction();
        transactionOne.setTimestamp(123L);
        transactionOne.setAmount(200D);

        Transaction transactionTwo = new Transaction();
        transactionTwo.setTimestamp(123L);
        transactionTwo.setAmount(400D);

        transactionsStore.add(transactionOne);
        transactionsStore.add(transactionTwo);

        final DoubleSummaryStatistics summaryStatistics = transactionsStore.getSummaryStatistics();
        Assert.assertEquals(200, summaryStatistics.getMin(), 0);
        Assert.assertEquals(400, summaryStatistics.getMax(), 0);
        Assert.assertEquals(2, summaryStatistics.getCount());
        Assert.assertEquals(600, summaryStatistics.getSum(), 0);
        Assert.assertEquals(300.0, summaryStatistics.getAverage(), 0);
    }

    @Test
    public void cleanUpOldTransactions() {
        Transaction one = new Transaction();
        one.setTimestamp(1000L);
        one.setAmount(200D);
        Transaction two = new Transaction();
        two.setTimestamp(100L);
        two.setAmount(400D);
        Transaction three = new Transaction();
        three.setTimestamp(700L);
        three.setAmount(667D);

        transactionsStore.add(one);
        transactionsStore.add(two);
        transactionsStore.add(three);

        transactionsStore.cleanUpTransactions(transaction -> transaction.getTimestamp() < 400L);

        final DoubleSummaryStatistics summaryStatistics = transactionsStore.getSummaryStatistics();

        Assert.assertEquals(200, summaryStatistics.getMin(), 0);
        Assert.assertEquals(667, summaryStatistics.getMax(), 0);
        Assert.assertEquals(2, summaryStatistics.getCount());
        Assert.assertEquals(867, summaryStatistics.getSum(), 0);
        Assert.assertEquals(433.5, summaryStatistics.getAverage(), 0);

    }

    @Test
    public void testGetSummaryStatistics_no_transactions() {
        final DoubleSummaryStatistics summaryStatistics = transactionsStore.getSummaryStatistics();
        Assert.assertEquals("DoubleSummaryStatistics{count=0, sum=0.000000, min=Infinity, average=0.000000, max=-Infinity}", summaryStatistics.toString());
    }
}