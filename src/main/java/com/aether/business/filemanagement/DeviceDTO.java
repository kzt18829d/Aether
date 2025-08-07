package com.aether.business.filemanagement;

import com.aether.business.types.Brightness;

/**
 * Data Transfer Object класс.
 * Служит посредником между JSON-объектом и Device-классом, а так же его наследниками
 */
public class DeviceDTO {
    /**
     * Device UUID
     * @see com.aether.business.devices.Device
     */
    private String deviceUUID;
    /**
     * Device name
     * @see com.aether.business.devices.Device
     */
    private String deviceName;
    /**
     * Device location
     * @see com.aether.business.devices.Device
     */
    private String deviceLocation;
    /**
     * Device status
     * @
     * @see com.aether.business.devices.Device
     */
    private String deviceStatus;
    // Light

    /**
     * Light brightness
     * @see com.aether.business.devices.Light
     */
    private Integer deviceBrightness;
    /**
     * Light color temperature
     * @see com.aether.business.devices.Light
     */
    private Integer deviceColorTemperature;
    // SecurityCamera
    /**
     * SecurityCamera recording
     * @see com.aether.business.devices.SecurityCamera
     */
    private boolean isRecording;
    /**
     * SecurityCamera night vision
     * @see com.aether.business.devices.SecurityCamera
     */
    private boolean nightVision;
    // SmartLock
    /**
     * SmartLock LockStatus
     * @see com.aether.business.devices.SmartLock
     */
    private boolean deviceLockStatus;
    // Thermostat
    /**
     * Thermostat temperature
     * @see com.aether.business.devices.Thermostat
     */
    private Integer deviceTemperature;

    /**
     * Конструктор по умолчанию
     */
    public DeviceDTO() {}

    public String getDeviceUUID() {
        return deviceUUID;
    }

    public String getDeviceName() {
        return deviceName;
    }

    public String getDeviceLocation() {
        return deviceLocation;
    }

    public String getDeviceStatus() {
        return deviceStatus;
    }

    public Integer getDeviceBrightness() {
        return deviceBrightness;
    }

    public Integer getDeviceColorTemperature() {
        return deviceColorTemperature;
    }

    public boolean getIsRecording() {
        return isRecording;
    }

    public boolean getNightVision() {
        return nightVision;
    }

    public Integer getDeviceTemperature() {
        return deviceTemperature;
    }

    public void setDeviceUUID(String deviceUUID) {
        this.deviceUUID = deviceUUID;
    }

    public void setDeviceName(String deviceName) {
        this.deviceName = deviceName;
    }

    public void setDeviceLocation(String deviceLocation) {
        this.deviceLocation = deviceLocation;
    }

    public void setDeviceStatus(String deviceStatus) {
        this.deviceStatus = deviceStatus;
    }

    public void setDeviceBrightness(Integer deviceBrightness) {
        this.deviceBrightness = deviceBrightness;
    }

    public void setDeviceColorTemperature(Integer deviceColorTemperature) {
        this.deviceColorTemperature = deviceColorTemperature;
    }

    public void setNightVision(boolean nightVision) {
        this.nightVision = nightVision;
    }

    public void setRecording(boolean recording) {
        isRecording = recording;
    }

    public void setDeviceLockStatus(boolean deviceLockStatus) {
        this.deviceLockStatus = deviceLockStatus;
    }

    public void setDeviceTemperature(Integer deviceTemperature) {
        this.deviceTemperature = deviceTemperature;
    }
}
