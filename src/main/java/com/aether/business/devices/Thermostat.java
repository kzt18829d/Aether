package com.aether.business.devices;

import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.aether.business.types.Temperature;

public class Thermostat extends Device {
    private final Temperature temperature;

    public Thermostat(Name name, Location location, Temperature temperature) {
        super(name, location);
        this.temperature = temperature;
    }

    public Thermostat(Name name, Location location, int temperature) {
        super(name, location);
        this.temperature = new Temperature(temperature);
    }

    public Integer getTemperature_int() {
        return temperature.getTemperature();
    }

    public Temperature getTemperature() {
        return temperature;
    }

    public void setTemperature(Integer temperature) {
        this.temperature.setTemperature(temperature);
    }

    @Override
    public String getType() {
        return "Thermostat";
    }
}
