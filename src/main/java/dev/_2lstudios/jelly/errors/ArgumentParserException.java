package dev._2lstudios.jelly.errors;

import dev._2lstudios.jelly.utils.NumberUtils;

public class ArgumentParserException extends Exception {

    private final int index;
    private final String required;

    public ArgumentParserException(final int index, final String required) {
        super(NumberUtils.formatNumber(index) + " argument must be a " + required);

        this.index = index;
        this.required = required;
    }

    public int getIndex () {
        return this.index;
    }

    public String getRequired () {
        return this.required;
    }
}
