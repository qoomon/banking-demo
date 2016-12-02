package com.qoomon.banking.service.banking.domain.value;

import java.util.regex.Pattern;

import com.qoomon.domainvalue.type.StringDV;

/**
 * represent a simple BIC
 * <h2>Format</h2>
 * <ul>
 * <li>group 1: 4-letter bank code</li>
 * <li>group 2: 2-letter country code</li>
 * <li>group 3: 2-digit location code</li>
 * <li>group 4: 3-digit branch code</li>
 * </ul>
 * 
 * @author qoomon
 */
public class SimpleBIC extends StringDV {

    public static Pattern BANK_IDENTIFIER_CODE_PATTERN = Pattern.compile("^([A-Z]{4})([A-Z]{2})([0-9]{2})([0-9]{3})$");

    protected SimpleBIC(String value) {
        super(value);
    }

    /**
     * Creates {@link #SimpleBIC} instance of given primitive value
     * 
     * @param value
     *            primitive value
     *            <br>
     *            &emsp;<b>require</b> {@link #isValid(String)} == true
     * @return {@link #SimpleBIC} instance
     */
    public static SimpleBIC of(String value) {
        return new SimpleBIC(value);
    }

    /**
     * Checks if {@code value} is valid {@link #SimpleBIC}
     * 
     * @param value
     *            primitive value
     * @return true if valid false else
     */
    public static boolean isValid(String value) {
        boolean isValid = StringDV.isValid(value);
        if (isValid) {
            isValid = BANK_IDENTIFIER_CODE_PATTERN.matcher(value).matches();
        }
        return isValid;
    }

}
