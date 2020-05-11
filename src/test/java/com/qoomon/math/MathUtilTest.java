package com.qoomon.math;


import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class MathUtilTest {

    @Test
    public void digitSum_ofPositiveNumber() throws Exception {
        // GIVEN
        int number = 31337;

        // WHEN
        int digitSum = MathUtil.digitSum(number);

        // THEN
        assertThat(digitSum).isEqualTo(17);
    }

    @Test
    public void digitSum_ofNegativeNumber() throws Exception {
        // GIVEN
        int number = -31337;

        // WHEN
        int digitSum = MathUtil.digitSum(number);

        // THEN
        assertThat(digitSum).isEqualTo(17);
    }

}
