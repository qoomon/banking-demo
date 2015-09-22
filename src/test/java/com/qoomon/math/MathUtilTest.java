package com.qoomon.math;

import static org.assertj.core.api.StrictAssertions.assertThat;

import org.junit.Test;

public class MathUtilTest {

    @Test
    public void digitSum_ofPositiveNumber() throws Exception {
        // GIVEN
        long number = 31337;

        // WHEN
        int digitSum = MathUtil.digitSum(number);

        // THEN
        assertThat(digitSum).isEqualTo(17);
    }

    @Test
    public void digitSum_ofNegativeNumber() throws Exception {
        // GIVEN
        long number = -31337;

        // WHEN
        int digitSum = MathUtil.digitSum(number);

        // THEN
        assertThat(digitSum).isEqualTo(17);
    }

}
