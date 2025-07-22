package com.aether.business.devices;
import com.aether.business.types.Brightness;
import com.aether.business.types.ColorTemperature;
import com.aether.business.types.Location;
import com.aether.business.types.Name;

/**
 * Класс элементов освещения
 *
 *
 * @see Device
 * */
public class Light extends Device {

    private final Brightness brightness;
    private final ColorTemperature colorTemperature;

    public Light(Name deviceName, Location location, Brightness brightness, ColorTemperature colorTemperature) {
        super(deviceName, location);
        this.brightness = brightness;
        this.colorTemperature = colorTemperature;
    }

    public Light(Name deviceName, Location location, Integer brightness, Integer colorTemperature) {
        super(deviceName, location);
        this.brightness = new Brightness(brightness);
        this.colorTemperature = new ColorTemperature(colorTemperature);
    }

    public Brightness getBrightness() {
        return brightness;
    }

    public ColorTemperature getColorTemperature() {
        return colorTemperature;
    }

    public void setBrightness(Integer brightness) {
        this.brightness.setBrightness(brightness);
    }

    public void setColorTemperature(Integer colorTemperature) {
        this.colorTemperature.setTemperature(colorTemperature);
    }

    @Override
    public String getType() {
        return "Light";
    }
}
