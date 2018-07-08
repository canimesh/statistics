package com.assignment.transaction.stats.api.util;

import java.time.ZoneOffset;
import java.time.ZonedDateTime;

/**
 * @author animesh.chakraborty on 07.07.18
 */
public class Utilities {

    public static long getUTCEpochInMillis() {
        ZonedDateTime utc = ZonedDateTime.now(ZoneOffset.UTC);
        return utc.toInstant().toEpochMilli();
    }
}
