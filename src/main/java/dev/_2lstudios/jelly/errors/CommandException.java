package dev._2lstudios.jelly.errors;

public class CommandException extends Exception {
    private final String i18nKey;

    public CommandException(final String message, final String i18nKey) {
        super(message);
        this.i18nKey = i18nKey;
    }

    public CommandException(final String message) {
        this(message, null);
    }

    public String getI18nKey () {
        return this.i18nKey;
    }
}
