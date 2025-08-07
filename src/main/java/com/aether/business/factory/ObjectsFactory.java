package com.aether.business.factory;

import com.aether.business.Exceptions.CreateDeviceException;
import com.aether.business.devices.*;
import com.aether.business.types.*;

public class ObjectsFactory {

    public static Location createLocation(String name) {
        return new Location(name);
    }

    public static Device createDevice(String type, String name_s, String loc_s) {
        Name name = new Name(name_s);
        Location location = new Location(loc_s);
        switch (type) {
            case "Light": return createLight(name, location);
            case "SecurityCamera": return createSecurityCamera(name, location);
            case "SmartLock": return createSmartLock(name, location);
            case "Thermostat": return createThermostat(name, location);
            default: throw new CreateDeviceException("Invalid Device type \"" + type + "\"");
        }
    }

    private static Light createLight(Name name, Location loc_s) {
        return new Light(name, loc_s, Brightness.MIDDLE, ColorTemperature.MIN);
    }

    private static SecurityCamera createSecurityCamera(Name name, Location location) {
        return new SecurityCamera(name, location);
    }

    private static SmartLock createSmartLock(Name name, Location location) {
        return new SmartLock(name, location);
    }

    private static Thermostat createThermostat(Name name, Location location) {
        return new Thermostat(name, location, new Temperature(Temperature.BASED));
    }


}
