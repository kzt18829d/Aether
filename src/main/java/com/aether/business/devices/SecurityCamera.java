package com.aether.business.devices;

import com.aether.business.devices.deviceInterfaces.NightVision;
import com.aether.business.devices.deviceInterfaces.Recording;
import com.aether.business.enums.DeviceStatus;
import com.aether.business.types.Location;
import com.aether.business.types.Name;

public class SecurityCamera extends Device implements Recording, NightVision {
    private boolean isRecording;
    private boolean nightVision;

    /**
     * Конструктор для преобразования из JSON/db
     *
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     */
    protected SecurityCamera(String deviceUUID, String deviceName, String deviceLocation, String deviceStatus) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
    }

    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    protected SecurityCamera(Name deviceName, Location deviceLocation) {
        super(deviceName, deviceLocation);
        this.isRecording = false;
        this.nightVision = false;
    }

    /**
     * Enable night vision
     * @return boolean
     */
    @Override
    public boolean enableNightVision() {
        return false;
    }

    /**
     * Disable night vision
     * @return boolean
     */
    @Override
    public boolean disableNightVision() {
        return false;
    }

    /**
     * Start recording
     * @return boolean
     */
    @Override
    public boolean startRecording() {
        if (deviceStatus != DeviceStatus.ONLINE || isRecording) return false;
        isRecording = true;
        return true;
    }

    /**
     * Stop recording
     * @return boolean
     */
    @Override
    public boolean stopRecording() {
        if (deviceStatus != DeviceStatus.ONLINE || !isRecording) return false;
        isRecording = false;
        return true;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        if (!super.equals(o)) return false;
        SecurityCamera that = (SecurityCamera) o;
        return isRecording == that.isRecording && nightVision == that.nightVision;
    }

    @Override
    public int hashCode() {
        return hashCoder() + getClass().hashCode();
    }
}
