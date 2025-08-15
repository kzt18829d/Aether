package com.aether.core.validators;

import com.aether.core.types.wrappers.ColorTemperature;
import com.aether.exceptions.ColorTemperatureException;

import java.util.Locale;

/**
 * Color Temperature validator
 * @see ColorTemperature
 */
public class ColorTemperatureValidator {
    /// Minimal value
    public static final int MIN = 2700;
    /// Maximal value
    public static final int MAX = 6500;

    /**
     * Validation method
     * @param integer value for validation
     * @return Valid value
     * @throws ColorTemperatureException Invalid color temperature value
     */
    public static Integer validate(Integer integer) {
        if (integer >= MIN && integer <= MAX) return integer;
        else throw new ColorTemperatureException("Invalid Color Temperature value: " + integer +
                "K.\n\t\tValid values in diapason from 2700K to 6500K");
    }

    /**
     * Validation method if using string data type
     * @param value Value for validation
     * @return Valid value
     * @throws ColorTemperatureException Invalid color temperature value
     */
    public static Integer validate(String value) {
        var valueL = value.trim().toLowerCase(Locale.ROOT);
        if ("min".equals(valueL) || "minimal".equals(valueL)) return MIN;
        else if ("max".equals(valueL) || "maximal".equals(valueL)) return MAX;
        else if (valueL.matches("\\d+")) return validate(Integer.getInteger(valueL));
        else throw new ColorTemperatureException("No valid value: \"" + value + "\". Value must be Integer");
    }

}
