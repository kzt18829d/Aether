package com.aether.business.devices;

import com.aether.business.devices.deviceInterfaces.NightVision;
import com.aether.business.devices.deviceInterfaces.Recording;
import com.aether.business.enums.DeviceStatus;
import com.aether.business.types.Location;
import com.aether.business.types.Name;
import com.fasterxml.jackson.annotation.*;

import java.util.UUID;

@JsonTypeName("SecurityCamera")
@JsonIgnoreProperties(ignoreUnknown = true)
public class SecurityCamera extends Device implements Recording, NightVision {
    private boolean isRecording;
    private boolean nightVision;

    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    public SecurityCamera(Name deviceName, Location deviceLocation) {
        super(deviceName, deviceLocation);
        this.isRecording = false;
        this.nightVision = false;
    }

    /**
     * Конструктор десериализации
     *
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     */
    @JsonCreator
    public SecurityCamera(
            @JsonProperty("deviceUUID")                 UUID deviceUUID,
            @JsonProperty("deviceName")                 Name deviceName,
            @JsonProperty("deviceLocation")             Location deviceLocation,
            @JsonProperty("powerStatus")               DeviceStatus deviceStatus,
            @JsonProperty("deviceNightVisionStatus")    boolean deviceNightVisionStatus,
            @JsonProperty("deviceRecordingStatus")      boolean deviceRecordingStatus) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.isRecording = deviceRecordingStatus;
        this.nightVision = deviceNightVisionStatus;
    }

    @JsonGetter("deviceNightVisionStatus")
    public boolean getNightVision() {
        return nightVision;
    }

    @JsonGetter("deviceRecordingStatus")
    public boolean getRecording() {
        return isRecording;
    }

    @Override
    public String getDeviceType() {
        return "SecurityCamera";
    }

    /**
     * Enable night vision
     *
     * @return boolean
     */
    @Override
    public boolean enableNightVision() {
        if (!(super.deviceStatus == DeviceStatus.ONLINE)) return false;
        nightVision = true;
        return true;
    }

    /**
     * Disable night vision
     *
     * @return boolean
     */
    @Override
    public boolean disableNightVision() {
        if (!(super.deviceStatus == DeviceStatus.ONLINE)) return false;
        nightVision = false;
        return true;
    }

    /**
     * Start recording
     *
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
     *
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
