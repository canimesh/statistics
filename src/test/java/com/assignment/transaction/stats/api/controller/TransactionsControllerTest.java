package com.assignment.transaction.stats.api.controller;

import com.assignment.transaction.stats.api.model.Transaction;
import com.assignment.transaction.stats.api.service.TransactionService;
import com.assignment.transaction.stats.api.validator.TimeStampRangeValidator;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Objects;

import static org.mockito.ArgumentMatchers.anyLong;

public class TransactionsControllerTest {

    private TransactionsController transactionsController;

    @Mock
    private TimeStampRangeValidator timeStampRangeValidator;

    @Mock
    private TransactionService transactionService;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        transactionsController = new TransactionsController(transactionService, timeStampRangeValidator);
    }

    @Test
    public void testGetTransaction_stale_timestamp() {
        Transaction transaction = buildTransaction();

        Mockito.when(timeStampRangeValidator.isWithinRange(anyLong())).thenReturn(false);

        final ResponseEntity<String> actualResponse = transactionsController.createTransactionEntry(transaction);

        Assert.assertEquals(HttpStatus.NO_CONTENT, actualResponse.getStatusCode());
        Assert.assertTrue(Objects.isNull(actualResponse.getBody()));
    }

    private Transaction buildTransaction() {
        Transaction transaction = new Transaction();
        transaction.setAmount(120d);
        transaction.setTimestamp(100L);
        return transaction;
    }

    @Test
    public void testGetTransaction_valid_timestamp() {
        Transaction transaction = buildTransaction();

        Mockito.when(timeStampRangeValidator.isWithinRange(anyLong())).thenReturn(true);

        final ResponseEntity<String> actualResponse = transactionsController.createTransactionEntry(transaction);

        Assert.assertEquals(HttpStatus.CREATED, actualResponse.getStatusCode());
        Assert.assertTrue(Objects.isNull(actualResponse.getBody()));
    }
}