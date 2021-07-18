package com.beanbeanjuice.beanrtp.managers.commands.object;

import com.beanbeanjuice.beanrtp.BeanRTP;

public enum ArgumentAmount {

    TOO_MANY(BeanRTP.getHelper().getConfigString("too-many-arguments")),
    NOT_ENOUGH(BeanRTP.getHelper().getConfigString("not-enough-arguments")),
    CORRECT_AMOUNT("Correct Amount");

    private final String message;

    /**
     * Create a new {@link ArgumentAmount} enum static class.
     *
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
