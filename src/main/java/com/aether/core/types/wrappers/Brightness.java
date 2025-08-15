package com.aether.core.types.wrappers;
import com.aether.exceptions.BrightnessException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Wrapper-класс уровня освещения
 */
public class Brightness {
    private Integer brightness;

    /**
     * <p>Основной конструктор</p>
     * <p>Minimal value: 0</p>
     * <p>Maximal value: 1000</p>
     * @param brightness
     */
    @JsonCreator
    public Brightness(Integer brightness) {
        this.brightness = brightness;
    }

    /**
     * Brightness setter
     * @param brightness
     */
    public void setBrightness(Integer brightness) {
        this.brightness = brightness;
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
