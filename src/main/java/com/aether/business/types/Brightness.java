package com.aether.business.types;
import com.aether.business.Exceptions.InvalidBrightnessException;

public class Brightness {
    private Integer brightness;
    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int MIDDLE = 50;


    public Brightness(Integer brightness) {
        this.brightness = validBrightness(brightness);
    }

    private Integer validBrightness(Integer current) {
        if (current < MIN || current > MAX) throw new InvalidBrightnessException("Assigned brightness is invalid: " + current + ".");
        return current;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = validBrightness(brightness);
    }

    public Integer getBrightness() {
        return brightness;
    }

    public Integer getMAX() {
        return this.MAX;
    }

    public Integer getMIN() {
        return MIN;
    }

}
