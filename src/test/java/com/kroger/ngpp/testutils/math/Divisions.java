package com.kroger.ngpp.testutils.math;

public class Divisions {
    public static long LongRoundUp(long a, long b) {
        final double division =
                Long.valueOf(a).doubleValue() / Long.valueOf(b).doubleValue();
        return Double.valueOf(
                Math.ceil(division)
        ).longValue();
    }
}
