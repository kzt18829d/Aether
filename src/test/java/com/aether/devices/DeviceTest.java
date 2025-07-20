package com.aether.devices;

import com.aether.business.devices.Device;
import com.aether.business.devices.SubTypes.Name;
import com.aether.business.devices.SubTypes.Location;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;


public class DeviceTest {


    /**
     * Класс-обёртка для тестов
     * @see Device
     */
    private class DeviceClass extends Device {
        public DeviceClass(Name name, Location location) {
            super(name, location);
        }
    }

    String nameDevice = "Device1";
    Name name = new Name(nameDevice);

    String location_ = "MainLoc";
    Location location = new Location(location_);

    Device device;

    @Test
    @DisplayName("Проверка создания объекта")
    void createDevice() {
        this.device = new DeviceClass(name, location);
        assertNotNull(device);
        assertEquals(nameDevice, device.getDeviceNameString());
        assertEquals(location_, device.getLocation().getLocation());
    }


}
