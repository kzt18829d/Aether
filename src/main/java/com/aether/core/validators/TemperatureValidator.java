package com.aether.core.validators;


import com.aether.exceptions.TemperatureException;

import java.util.Locale;

public class TemperatureValidator {
    /// Minimal temperature
    public static final int MIN = -15;
    /// Maximal temperature
    public static final int MAX = 80;
    /// Based temperature
    public static final int BASED = 40;

    /**
     * Validate method
     * @param integer Integer value
     * @return Valid value
     * @throws com.aether.exceptions.TemperatureException Invalid temperature value
     */
    public static Integer validate(Integer integer) {
        if (integer >= MIN && integer <= MAX) return integer;
        else throw new TemperatureException("Invalid temperature value: " + integer
                + ".\n\t\tMinimum: " + MIN + "; Maximum: " + MAX);
    }

    /**
     * Validate method if using string data type
     * @param string String value
     * @return Valid Integer value
     * @throws com.aether.exceptions.TemperatureException Invalid temperature value
     */
    public static Integer validate(String string) {
        var value = string.trim().toLowerCase(Locale.ROOT);
        if ("min".equals(value) || "minimal".equals(value)) return MIN;
        else if ("max".equals(value) || "maximal".equals(value)) return MAX;
        else if ("based".equals(value) || "base".equals(value)) return BASED;
        else if (value.matches("\\d+")) return validate(Integer.getInteger(value));
        else throw new TemperatureException("No valid value: \\\"\" + value + \"\\\". Value must be Integer");
    }
}
