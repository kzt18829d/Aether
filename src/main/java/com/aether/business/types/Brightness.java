package com.aether.business.types;
import com.aether.business.Exceptions.InvalidBrightnessException;
import com.aether.business.Exceptions.valid.BrightnessException;

import java.util.Objects;

/**
 * Класс уровня яркости освещения
 */
public class Brightness {
    private Integer brightness;
    public static final int MIN = 0;
    public static final int MAX = 100;
    public static final int MIDDLE = 50;


    public Brightness(Integer brightness) {
        this.brightness = validBrightness(brightness);
    }

    private Integer validBrightness(Integer current) {
        if (current < MIN || current > MAX) throw new BrightnessException("Assigned brightness is invalid: " + current + ".");
        return current;
    }

    public void setBrightness(Integer brightness) {
        this.brightness = validBrightness(brightness);
    }

    public Integer getBrightness() {
        return brightness;
    }

    public Integer getMAX() {
        return MAX;
    }

    public Integer getMIN() { return MIN; }

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
