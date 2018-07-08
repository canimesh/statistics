package com.assignment.transaction.stats.api.controller;


import com.assignment.transaction.stats.api.model.Transaction;
import com.assignment.transaction.stats.api.service.TransactionService;
import com.assignment.transaction.stats.api.validator.TimeStampRangeValidator;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;

@RestController
@RequiredArgsConstructor
public class TransactionsController {

    private final TransactionService transactionService;

    private final TimeStampRangeValidator timeStampRangeValidator;

    @RequestMapping(value = "/transactions", method = RequestMethod.POST)
    public ResponseEntity<String> createTransactionEntry(@RequestBody @Valid @NotNull Transaction transaction) {
        ResponseEntity<String> responseEntity;
        if (timeStampRangeValidator.isWithinRange(transaction.getTimestamp())) {
            transactionService.add(transaction);
            responseEntity = ResponseEntity.status(HttpStatus.CREATED).build();
        } else {
            responseEntity = ResponseEntity.status(HttpStatus.NO_CONTENT).build();
        }
        return responseEntity;
    }
}
