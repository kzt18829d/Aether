package com.aether.business.devices.SubTypes;

import com.aether.business.Exceptions.InvalidColorTemperatureException;

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

    public boolean setTemperature(Integer temperature) {
        this.temperature = validTemperature(temperature);
        return true;
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

}
