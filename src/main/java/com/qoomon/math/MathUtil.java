package com.qoomon.math;

public final class MathUtil {

    private MathUtil() {
    }

    /**
     * calculate the digit sum of a given number
     * 
     * @param number
     *            positive and negative numbers allowed
     * @return digit sum. always positive
     */
    public static int digitSum(final long number) {
        long remainingNumber = Math.abs(number);
        int checksum = 0;
        while (remainingNumber != 0) {
            // add last digit to the sum
            checksum += remainingNumber % 10;
            // cut last digit
            remainingNumber /= 10;
        }
        return checksum;
    }
}
