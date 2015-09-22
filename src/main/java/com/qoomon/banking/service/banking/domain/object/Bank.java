package com.qoomon.banking.service.banking.domain.object;

import static com.google.common.base.Preconditions.checkArgument;
import static com.google.common.base.Preconditions.checkNotNull;

import com.google.common.base.Strings;
import com.qoomon.banking.service.banking.domain.value.SimpleBIC;

/**
 * represent a Bank Object
 * 
 * @author qoomon
 *
 */
public class Bank {

    private final SimpleBIC bic;

    private final String name;

    /**
     * 
     * @param bic
     * @param name
     * @require {@link #bic} not null
     * @require {@link #name} not null or empty
     */
    public Bank(SimpleBIC bic, String name) {
        super();
        this.bic = checkNotNull(bic);
        checkArgument(!Strings.isNullOrEmpty(name));
        this.name = name;
    }

    public SimpleBIC getBic() {
        return this.bic;
    }

    public String getName() {
        return this.name;
    }

}
