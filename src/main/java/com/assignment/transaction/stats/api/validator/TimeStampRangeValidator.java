package com.assignment.transaction.stats.api.validator;

import com.assignment.transaction.stats.api.util.Utilities;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

/**
 * @author animesh.chakraborty on 07.07.18
 */
@Component
public class TimeStampRangeValidator {

    @Value("${transaction.data.ttl:60000}")
    public Long transactionDataTtl;


    public boolean isWithinRange(final long timestamp) {
        return Utilities.getUTCEpochInMillis() - timestamp <= transactionDataTtl;
    }
}
