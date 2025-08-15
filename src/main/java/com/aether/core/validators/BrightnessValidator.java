package com.aether.core.validators;

import com.aether.core.types.wrappers.Brightness;
import com.aether.exceptions.BrightnessException;

import java.util.Locale;

/**
 * Brightness validator
 * @see Brightness
 */
public class BrightnessValidator {
    /// Minimal value
    public static int MIN = 0;
    /// Maximal value
    public static int MAX = 100;

    public static int BASE = 50;

    /**
     * Validation method
     * @param integer Integer value
     * @return Valid value
     * @throws BrightnessException Invalid brightness value
     */
    public static Integer validate(Integer integer) {
        if (integer >= MIN && integer <= MAX) return integer;
        else throw new BrightnessException("Invalid brightness level: " + integer
                + ". \n\t\tMinimum: "+ MIN +"; Maximum: " + MAX);
    }

    /**
     * Validation method if using string data type
     * @param value String value
     * @return Valid value
     * @throws BrightnessException Invalid brightness value
     */
    public static Integer validate(String value) {
        var val = value.trim().toLowerCase(Locale.ROOT);
        if ("min".equals(val) || "minimal".equals(val)) return MIN;
        else if ("max".equals(val) || "maximal".equals(val)) return MAX;
        else if (val.matches("\\d+")) return validate(Integer.getInteger(val));
        else throw new BrightnessException("No valid value: \"" + value + "\". Value must be Integer");
    }

}
