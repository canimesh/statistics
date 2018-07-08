package com.assignment.transaction.stats.api.validator;

import com.assignment.transaction.stats.api.util.Utilities;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.test.util.ReflectionTestUtils;

public class TimeStampRangeValidatorTest {

    private TimeStampRangeValidator timeStampRangeValidator;

    @Before
    public void setUp() throws Exception {
        timeStampRangeValidator = new TimeStampRangeValidator();
        ReflectionTestUtils.setField(timeStampRangeValidator, "transactionDataTtl", 2000L);
    }

    @Test
    public void testIsWithinRange_true() {
        final boolean withinRange = timeStampRangeValidator.isWithinRange(Utilities.getUTCEpochInMillis() - 1500L);
        Assert.assertTrue(withinRange);
    }

    @Test
    public void testIsWithinRange_false() {
        final boolean withinRange = timeStampRangeValidator.isWithinRange(Utilities.getUTCEpochInMillis() - 5000L);
        Assert.assertFalse(withinRange);
    }
}