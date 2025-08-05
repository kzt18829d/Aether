package com.aether.business.core;

import com.aether.business.Exceptions.SmartHomeControllerException;
import com.aether.business.constaints.Terminal;
import com.aether.business.devices.Device;
import com.aether.business.types.Location;
import com.johncsinclair.consoletable.ColumnFormat;
import com.johncsinclair.consoletable.ConsoleTable;

import java.util.*;

/**
 * Основной класс.
 * Управляет устройствами и локациями умного дома.
 */
public class SmartHomeController {
    /**
     * Карта устройств
     * @see Device
     */
    private final Map<UUID, Device> deviceMap;

    /**
     * Карта локаций
     * @see Location
     */
    private final Map<String, Location> locationMap;

    /**
     * Конструктор по умолчанию
     */
    public SmartHomeController() {
        this.deviceMap = new HashMap<UUID, Device>();
        this.locationMap = new HashMap<String, Location>();
    }

    /**
     * Запросить устройство по его UUID
     * @param uuid
     * @return Искомый Device || null
     * @see Device
     */
    public Device getDeviceByID(UUID uuid) { return deviceMap.get(uuid); }

    /**
     * Запросить все устройства
     * @return ArrayList устройств
     * @see Device
     */
    public List<Device> getAllDevices() { return new ArrayList<Device>(deviceMap.values()); }

    /**
     * Запросить локацию по имени
     * @param location_name
     * @return Искомая Location
     * @see Location
     */
    public Location getLocation(String location_name) { return locationMap.get(location_name); }

    /**
     * Запрос всех локаций
     * @return ArrayList локаций
     * @see Location
     */
    public List<Location> getAllLocations() { return new ArrayList<Location>(locationMap.values()); }

    /**
     * Добавить новую локацию
     * @param location
     * @see Location
     */
    public void addLocation(Location location) {
        if (locationMap.containsKey(location.get())) throw new SmartHomeControllerException("Эта локация уже существует.");
        locationMap.putIfAbsent(location.get(), location);
        Terminal.info("Location \"" + location.get() + "\" added.");
    }

    /**
     * Удалить существующую локацию
     * @param location_name
     */
    public void removeLocation(String location_name) {
        if (!locationMap.containsKey(location_name)) throw new SmartHomeControllerException("Локация не найдена.");
        locationMap.remove(location_name);
        var devices = getAllDevices();
        List<Device> contains = new ArrayList<Device>();

        for (Device device : devices) {
            if (Objects.equals(device.getLocationString(), location_name)) {
                contains.add(device);
            }
        }
        if (contains.isEmpty()) locationMap.remove(location_name);
        else {
            for (Device device : contains) {
                device.turnOff();
//                System.out.println(Terminal.INFO + "Device " + device.getUuid() + " is turned off");
                Terminal.warn("Device " + device.getUuid() + " is turned off");
                device.removeLocation();
                Terminal.warn("Device's " + device.getUuid() + " location was removed. Device cannot being turned on, need to relocate");
            }
        }

        Terminal.info("Location \"" + location_name + "\" was deleted.");
    }

    /**
     * Удалить существующую локацию
     * @param location
     * @see Location
     */
    public void removeLocation(Location location) {
        removeLocation(location.getString());
    }

    /**
     * Проверка наличия локации в locationMap
     * @param location
     * @return boolean
     */
    public boolean findLocation_b(Location location) {
        return locationMap.containsValue(location);
    }

    /**
     * Проверка наличия локации в locationMap
     * @param location_name
     * @return boolean
     */
    public boolean findLocation_b(String location_name) {
        return locationMap.containsKey(location_name);
    }

    /**
     * Добавление нового устройства
     * @param device
     */
    public void addDevice(Device device) {
        final var uuid = device.getDeviceUUID();
        if (deviceMap.containsKey(uuid)) throw new SmartHomeControllerException("Устройство уже существует и не может быть добавлено");
        try {
            if (!findLocation_b(device.getDeviceLocation())) {
                addLocation(device.getDeviceLocation());
            }
            deviceMap.putIfAbsent(uuid, device);
            Terminal.info("Device " + uuid + " \"" + device.getDeviceName_string() + "\" created");
        }
        catch (Exception exception) {
            Terminal.error(exception.getMessage());
        }
    }

    /**
     * Удаление устройства
     * @param uuid
     */
    public void removeDevice(UUID uuid) {
        if (!deviceMap.containsKey(uuid)) throw new SmartHomeControllerException("Device " + uuid.toString() + "wasn't find");
        deviceMap.remove(uuid);
        Terminal.info("Device " + uuid + " is deleted");
    }

    /**
     * Запрос статуса устройства по UUID
     * @param uuid
     * @return String (Table)
     */
    public String getDeviceStatusReport_ByString(UUID uuid) {
        Device device = getDeviceByID(uuid);
        if (device == null) {
            throw new SmartHomeControllerException("Device " + uuid.toString() + " wasn't find");
        }

        ConsoleTable SystemStatusReportTable = new ConsoleTable();
        String Header1 = "Device UUID";
        String Header2 = "Device name";
        String Header3 = "Device Location";
        String Header4 = "Device Status";
        SystemStatusReportTable.setHeaders(Header1, Header2, Header3, Header4).withAlignment(ColumnFormat.Aligned.CENTRE);

        SystemStatusReportTable.addRow(
                device.getDeviceUUID(),
                device.getDeviceName_string(),
                device.getDeviceLocation_string(),
                device.getPowerStatus().toString()
        );
        return SystemStatusReportTable.toString();
    }

    /**
     * Запрос статуса всех устройств
     * @return String (Table)
     */
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
                    value.getDeviceName_string(),
                    value.getDeviceLocation_string(),
                    value.getPowerStatus().toString()
            );
        }

        return SystemStatusReportTable.toString();
    }
}
