package com.aether.core.types.wrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Wrapper of color temperature
 */
public class ColorTemperature {
    private Integer temperature;

    /**
     * Основной конструктор
     * <p>Minimal value: 2700</p>
     * <p>Maximal value: 6500</p>
     * @param temperature Integer value
     */
    @JsonCreator
    public ColorTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    /**
     * ColorTemperature setter
     * @param temperature Integer value
     */
    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
    }

    /**
     * ColorTemperature getter
     * @return Color temperature Integer value
     */
    @JsonValue
    public Integer getColorTemperature() {
        return temperature;
    }



    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        ColorTemperature that = (ColorTemperature) o;
        return Objects.equals(temperature, that.temperature);
    }

    @Override
    public int hashCode() {
        return 27 * Objects.hashCode(temperature);
    }
}
