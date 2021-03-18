package com.github.beanbeanjuice.beanrtp.utilities.usages.enums;

import com.github.beanbeanjuice.beanrtp.beanRTP;

/**
 * The {@link UsageType} enum.
 * Used for displaying what type of argument it is.
 */
public enum UsageType {

    TEXT("text", beanRTP.getHelper().getConfigString("incorrect-syntax-text")),
    NUMBER("number", beanRTP.getHelper().getConfigString("incorrect-syntax-number")),
    PLAYER("player", beanRTP.getHelper().getConfigString("incorrect-syntax-player")),
    WORLD("world", beanRTP.getHelper().getConfigString("incorrect-syntax-world")),
    OTHER("other", beanRTP.getHelper().getConfigString("incorrect-syntax-other"));

    private final String name;
    private final String message;

    /**
     * Create a new {@link UsageType} enum static class.
     * @param name The name of the type of argument.
     * @param message The message to be used.
     */
    UsageType(String name, String message) {
        this.name = name;
        this.message = message;
    }

    /**
     * @return The message associated with the particular {@link UsageType}.
     */
    public String getName() {
        return name;
    }

    /**
     * @return The message associated with the particular {@link UsageType}.
     */
    public String getMessage() {
        return message;
    }

}
