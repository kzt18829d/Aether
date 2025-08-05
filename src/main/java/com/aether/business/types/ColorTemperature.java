package com.aether.business.types;

import com.aether.business.Exceptions.InvalidColorTemperatureException;

import java.util.Objects;

public class ColorTemperature {
    private Integer temperature;
    public static final int MIN = 2700;
    public static final int MAX = 6500;


    public ColorTemperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
    }

    private Integer validTemperature(Integer temperature) {
        if (temperature < MIN || temperature > MAX) throw new InvalidColorTemperatureException("Assigned color temperature is invalid: " + temperature + ".");
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
    }

    public Integer getColorTemperature() {
        return temperature;
    }

    public Integer getMAX() {
        return MAX;
    }

    public Integer getMIN() {
        return MIN;
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
