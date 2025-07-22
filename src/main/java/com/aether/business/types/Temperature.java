package com.aether.business.types;

import com.aether.business.Exceptions.InvalidTemperatureException;

public class Temperature {
    public static final int MIN = -15;
    public static final int MAX = 80;
    public static final int BASED = 40;
    private Integer temperature;

    public Temperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
    }

    private Integer validTemperature(Integer temperature) {
        if (temperature < MIN || temperature > MAX) throw new InvalidTemperatureException("Assigned invalid temperature.");
        return temperature;
    }

    public Integer getTemperature() {
        return temperature;
    }

    public int getMIN() {
        return MIN;
    }

    public int getMAX() {
        return MAX;
    }

    public void setTemperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
    }

}
