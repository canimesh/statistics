package com.assignment.transaction.stats.api.model;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.DecimalMin;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Setter
@Getter
public class Transaction implements Serializable {

    @NotNull
    @DecimalMin(value = "0", message = "Transaction amount has to be greater than 0.")
    private Double amount;

    @NotNull
    private Long timestamp;
}