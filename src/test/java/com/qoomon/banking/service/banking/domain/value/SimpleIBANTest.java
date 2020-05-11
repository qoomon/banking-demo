package com.qoomon.banking.service.banking.domain.value;

import org.junit.Test;

import static org.assertj.core.api.Assertions.assertThat;

public class SimpleIBANTest {

    @Test
    public void validate_validNumberRightCheckSum() throws Exception {
        // GIVEN
        String number = "DE" + "21" + "123456";

        // WHEN
        boolean isValid = SimpleIBAN.isValid(number);

        // THEN
        assertThat(isValid).isTrue();
    }

    @Test
    public void validate_validNumberWrongCheckSum() throws Exception {
        // GIVEN
        String number = "DE" + "123456" + "9";

        // WHEN
        boolean isValid = SimpleIBAN.isValid(number);

        // THEN
        assertThat(isValid).isFalse();
    }

    @Test
    public void validate_shortNumberRightCheckSum() throws Exception {
        // GIVEN
        String number = "DE" + "12345" + "5";

        // WHEN
        boolean isValid = SimpleIBAN.isValid(number);

        // THEN
        assertThat(isValid).isFalse();
    }

    @Test
    public void validate_moreCharactersRightCheckSum() throws Exception {
        // GIVEN
        String number = "DEX" + "123456" + "1";

        // WHEN
        boolean isValid = SimpleIBAN.isValid(number);

        // THEN
        assertThat(isValid).isFalse();
    }

}
