package com.qoomon.banking.service.banking.domain.value;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleBICTest {

    @Test
    public void validate_validBIC() throws Exception {
        // GIVEN
        String bic = "XXXX" + "DE" + "12" + "789";

        // WHEN
        boolean isValid = SimpleBIC.isValid(bic);

        // THEN
        assertThat(isValid).isTrue();
    }

    @Test
    public void validate_invalidBICWrongLength() throws Exception {
        // GIVEN
        String bic = "XXXX" + "DEE" + "12" + "789";

        // WHEN
        boolean isValid = SimpleBIC.isValid(bic);

        // THEN
        assertThat(isValid).isFalse();
    }

}
