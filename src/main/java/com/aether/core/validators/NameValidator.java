package com.aether.core.validators;

import com.aether.exceptions.NameException;

/**
 * Object name validator
 * <p>For classes Name and Location</p>
 */
public class NameValidator {
    /// Minimal name length
    private static int MINIMAL_LENGTH = 3;
    /// Maximal name length
    private static int MAXIMAL_LENGTH = 256;
    // TODO добавить MINIMAL_LENGTH в .env


    /**
     * Validation method
     * @param string Name of object
     * @return Valid name of object
     * @throws NameException Why invalid
     */
    public static String validate(String string) {
        if (string == null) throw new NameException("Object name cannot be null object");
        if (string.isEmpty()) throw new NameException("Object name cannot be empty");
        if (string.length() < MINIMAL_LENGTH) throw new NameException("Minimal name length is " + MINIMAL_LENGTH + ". Current " + string.length());
        if (string.length() < MAXIMAL_LENGTH) throw new NameException("Maximal name length is " + MAXIMAL_LENGTH + ". Current " + string.length());
        if (string.matches("\\s+") || string.matches("\\d+")) throw new NameException("Object name cannot contains only from spaces or digits");
        return string;
    }

    /**
     * Minimal string length getter
     * @return minimal length
     */
    public static int getMinimalLength() {
        return MINIMAL_LENGTH;
    }

    /**
     * Minimal string length setter
     * <p>For env</p>
     * @param minimalLength Minimal length
     */
    public static void setMinimalLength(int minimalLength) {
        MINIMAL_LENGTH = minimalLength;
    }

    /**
     * Minimal string length getter
     * @return minimal length
     */
    public static int getMaximalLength() {
        return MAXIMAL_LENGTH;
    }

    /**
     * Minimal string length setter
     * <p>For env</p>
     * @param maximalLength Minimal length
     */
    public static void setMaximalLength(int maximalLength) {
        MAXIMAL_LENGTH = maximalLength;
    }
}
