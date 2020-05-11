package com.qoomon.banking.service.banking.domain.value;

import com.qoomon.domainvalue.type.StringDV;
import com.qoomon.math.MathUtil;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * represent a simple IBAN with own checksum implementation
 * <h2>Format</h2>
 * <ul>
 * <li>group 1: 2-letter country code</li>
 * <li>group 2: 2-digit check digits (digit sum modular 100)</li>
 * <li>group 3: up to 30 digits Basic Bank Account Number (BBAN)</li>
 * </ul>
 *
 * @author qoomon
 */
public class SimpleIBAN extends StringDV {

    public static Pattern ACCOUNT_NUMBER_PATTERN = Pattern.compile("^([A-Z]{2})([0-9]{2})([0-9]{1,})$");

    protected SimpleIBAN(String value) {
        super(value);
    }

    /**
     * Creates {@link #SimpleIBAN} instance of given primitive value
     *
     * @param value primitive value
     *              <br>
     *              &emsp;<b>require</b> {@link #isValid(String)} == true
     * @return {@link #SimpleIBAN} instance
     */
    public static SimpleIBAN of(String value) {
        return new SimpleIBAN(value);
    }

    /**
     * Checks if {@code value} is valid {@link SimpleIBAN}
     *
     * @param value primitive value
     * @return true if valid false else
     */
    public static boolean isValid(String value) {
        boolean isValid = StringDV.isValid(value);
        if (isValid) {
            Matcher matcher = ACCOUNT_NUMBER_PATTERN.matcher(value);
            isValid = matcher.matches();
            if (isValid) {
                Integer checkNumber = Integer.valueOf(matcher.group(2));
                Integer accountNumber = Integer.valueOf(matcher.group(3));
                isValid = validateChecksum(accountNumber, checkNumber);
            }
        }
        return isValid;
    }

    private static boolean validateChecksum(Integer number, Integer checkSum) {
        int digitSum = MathUtil.digitSum(number);
        return checkSum == digitSum % 100;
    }

}
