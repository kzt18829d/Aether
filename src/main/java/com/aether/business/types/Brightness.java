package com.aether.business.types;
import com.aether.business.Exceptions.InvalidBrightnessException;
import com.aether.business.Exceptions.valid.BrightnessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Wrapper-класс уровня освещения
 */
public class Brightness {
    private Integer brightness;
    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int MIDDLE = 50;


    /**
     * <p>Основной конструктор</p>
     * <p>Minimal value: 0</p>
     * <p>Maximal value: 1000</p>
     * @param brightness
     */
    @JsonCreator
    public Brightness(Integer brightness) {
        this.brightness = validBrightness(brightness);
    }

    /**
     * Проверка валидности уровня освещения
     * @param current
     * @return
     */
    private Integer validBrightness(Integer current) {
        if (current < MIN || current > MAX) throw new BrightnessException("Assigned brightness is invalid: " + current + ".");
        return current;
    }

    /**
     * Brightness setter
     * @param brightness
     */
    public void setBrightness(Integer brightness) {
        this.brightness = validBrightness(brightness);
    }

    /**
     * Brightness getter
     * @return Integer
     */
    @JsonValue
    public Integer getBrightness() {
        return brightness;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Brightness that = (Brightness) o;
        return Objects.equals(brightness, that.brightness);
    }

    @Override
    public int hashCode() {
        return 27 * Objects.hashCode(brightness);
    }
}
