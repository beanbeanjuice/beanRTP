package com.github.beanbeanjuice.beanrtp.utilities.usages.enums;

import com.github.beanbeanjuice.beanrtp.beanRTP;

/**
 * The {@link ArgumentAmount} enum.
 * Used for displaying what is wrong with that argument.
 */
public enum ArgumentAmount {

    TOO_MANY(beanRTP.getHelper().getConfigString("too-many-arguments")),
    NOT_ENOUGH(beanRTP.getHelper().getConfigString("not-enough-arguments")),
    CORRECT_AMOUNT("Correct Amount");

    private final String message;

    /**
     * Create a new {@link ArgumentAmount} enum static class.
     * @param message The message to be used.
     */
    ArgumentAmount(String message) {
        this.message = message;
    }

    /**
     * @return The message associated with the particular {@link ArgumentAmount}.
     */
    public String getMessage() {
        return message;
    }

}
