package com.aether.business.types;

import com.aether.business.Exceptions.InvalidTemperatureException;
import com.aether.business.Exceptions.valid.TemperatureException;
import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Класс-обёртка температуры
 */
public class Temperature {
    public static final int MIN = -15;
    public static final int MAX = 80;
    public static final int BASED = 40;
    private Integer temperature;

    /**
     * Конструктор
     * @param temperature
     */
    @JsonCreator
    public Temperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
    }

    /**
     * Проверка валидности температуры
     * @param temperature
     * @return
     */
    private Integer validTemperature(Integer temperature) {
        if (temperature < MIN || temperature > MAX) throw new TemperatureException("Assigned invalid temperature: " + temperature + ".");
        return temperature;
    }

    @JsonValue
    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Temperature that = (Temperature) o;
        return Objects.equals(temperature, that.temperature);
    }

    @Override
    public int hashCode() {
        return 27 * Objects.hashCode(temperature);
    }
}
