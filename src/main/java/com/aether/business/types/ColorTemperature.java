package com.aether.business.types;

import com.aether.business.Exceptions.InvalidColorTemperatureException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Wrapper-класс температуры цвета
 */
public class ColorTemperature {
    private Integer temperature;
    public static final int MIN = 2700;
    public static final int MAX = 6500;


    /**
     * Основной конструктор
     * <p>Minimal value: 2700</p>
     * <p>Maximal value: 6500</p>
     * @param temperature
     */
    @JsonCreator
    public ColorTemperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
    }

    /**
     * Проверка валидности
     * @param temperature
     * @return
     */
    private Integer validTemperature(Integer temperature) {
        if (temperature < MIN || temperature > MAX) throw new InvalidColorTemperatureException("Assigned color temperature is invalid: " + temperature + ".");
        return temperature;
    }

    /**
     * ColorTemperature setter
     * @param temperature
     */
    public void setTemperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
    }

    /**
     * ColorTemperature getter
     * @return
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
