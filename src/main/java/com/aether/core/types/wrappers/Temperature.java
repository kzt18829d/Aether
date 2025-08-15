package com.aether.core.types.wrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Класс-обёртка температуры
 */
public class Temperature {
    private Integer temperature;

    /**
     * Конструктор
     * @param temperature
     */
    @JsonCreator
    public Temperature(Integer temperature) {
        this.temperature = temperature;
    }



    @JsonValue
    public Integer getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = temperature;
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
