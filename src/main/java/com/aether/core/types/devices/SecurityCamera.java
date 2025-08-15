package com.aether.core.types.devices;

import com.aether.core.types.enums.DeviceStatus;
import com.aether.core.types.enums.NightVisionStatus;
import com.aether.core.types.enums.RecordingStatus;
import com.aether.core.types.interfaces.NightVision;
import com.aether.core.types.interfaces.Recording;
import com.aether.core.types.wrappers.Location;
import com.aether.core.types.wrappers.Name;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicReference;

public class SecurityCamera extends AbstractDevice implements NightVision, Recording {
    protected final AtomicReference<RecordingStatus> recordingStatus;
    protected final AtomicReference<NightVisionStatus> nightVisionStatus;

    /**
     * Основной конструктор
     *
     * @param deviceName
     * @param deviceLocation
     */
    public SecurityCamera(Name deviceName, Location deviceLocation) {
        super(deviceName, deviceLocation);
        this.recordingStatus = new AtomicReference<>(RecordingStatus.ENABLED);
        this.nightVisionStatus = new AtomicReference<>(NightVisionStatus.DISABLED);
    }

    /**
     * Deserialization Constructor
     * @param deviceUUID
     * @param deviceName
     * @param deviceLocation
     * @param deviceStatus
     * @param deviceNightVisionStatus
     * @param deviceRecordingStatus
     */
    public SecurityCamera(
            UUID deviceUUID,
            Name deviceName,
            Location deviceLocation,
            DeviceStatus deviceStatus,
            NightVisionStatus deviceNightVisionStatus,
            RecordingStatus deviceRecordingStatus) {
        super(deviceUUID, deviceName, deviceLocation, deviceStatus);
        this.recordingStatus = new AtomicReference<>(deviceRecordingStatus);
        this.nightVisionStatus = new AtomicReference<>(deviceNightVisionStatus);
    }
    /**
     * Enable night vision
     * @return enabled
     */
    @Override
    public boolean enableNightVision() {
        nightVisionStatus.set(NightVisionStatus.ENABLED);
        return true;
    }

    /**
     * Disable night vision
     * @return
     */
    @Override
    public boolean disableNightVision() {
        nightVisionStatus.set(NightVisionStatus.DISABLED);
        return true;
    }

    /**
     * @return
     */
    @Override
    public NightVisionStatus getNightVisionStatus() {
        return nightVisionStatus.get();
    }

    /**
     * @return
     */
    @Override
    public boolean startRecording() {
        recordingStatus.set(RecordingStatus.ENABLED);
        return true;
    }

    /**
     * @return
     */
    @Override
    public boolean stopRecording() {
        recordingStatus.set(RecordingStatus.DISABLED);
        return true;
    }

    /**
     * @return
     */
    @Override
    public RecordingStatus getRecordingStatus() {
        return recordingStatus.get();
    }
}
