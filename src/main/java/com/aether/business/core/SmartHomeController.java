package com.aether.business.core;

import com.aether.business.devices.Device;
import com.aether.business.devices.SubTypes.Location;
import com.johncsinclair.consoletable.*;

import java.util.*;

public class SmartHomeController {
    private final Map<UUID, Device> deviceMap;
    private final Map<String, Location> locationsMap;

    public SmartHomeController() {
        this.deviceMap = new HashMap<UUID, Device>();
        this.locationsMap = new HashMap<String, Location>();
    }

    public boolean addDevice(Device device) {
        if (deviceMap.containsKey(device.getUuid())) return false;
        deviceMap.putIfAbsent(device.getUuid(), device);
        return true;
    }

    public boolean removeDevice(UUID uuid) {
        if (!deviceMap.containsKey(uuid)) return false;
        deviceMap.remove(uuid);
        return true;
    }

    public Device getDeviceByID(UUID uuid) {
        return deviceMap.get(uuid);
    }

    public ArrayList<Device> getAllDevices() {
        return new ArrayList<Device>(deviceMap.values());
    }

    public boolean addLocation(Location location) {
        if (locationsMap.containsKey(location.getLocation())) return false;
        locationsMap.putIfAbsent(location.getLocation(), location);
        return true;
    }

    public boolean removeLocation(Location location) {
        if (!locationsMap.containsKey(location.getLocation())) return false;
        locationsMap.remove(location.getLocation());
        return true;
    }

    public boolean removeLocation(String name) {
        if (!locationsMap.containsKey(name)) return false;
        locationsMap.remove(name);
        return true;
    }

    public Location getLocation(String name) {
        return locationsMap.get(name);
    }

    public ArrayList<Location> getLocationsList() {
        return new ArrayList<Location>(locationsMap.values());
    }

    public String getSystemStatusReport_ByString() {
        ConsoleTable SystemStatusReportTable = new ConsoleTable();
        String Header1 = "Device UUID";
        String Header2 = "Device name";
        String Header3 = "Device Location";
        String Header4 = "Device Status";
        SystemStatusReportTable.setHeaders(Header1, Header2, Header3, Header4).withAlignment(ColumnFormat.Aligned.CENTRE);

        for (var entry : deviceMap.entrySet()) {
            var value = entry.getValue();
            SystemStatusReportTable.addRow(
                    entry.getKey(),
                    value.getDeviceNameString(),
                    value.getLocationString(),
                    value.getStatus()
                    );
        }

        return SystemStatusReportTable.toString();

    }
}
