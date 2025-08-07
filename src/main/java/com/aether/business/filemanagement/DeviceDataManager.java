package com.aether.business.filemanagement;

import com.aether.business.constaints.Terminal;
import com.aether.business.devices.Device;
import com.aether.business.filemanagement.JSON.AetherCryptoFiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileWriter;
import java.util.Map;
import java.util.UUID;

public class DeviceDataManager {
    AetherCryptoFiles aetherCryptoFiles = new AetherCryptoFiles();

    private static final String defaultDirectoryJSON = "AetherDeviceData.json";
    private static final String defaultDirectoryBinary = "AetherDeviceData.aether";
    private File deviceData;
    private ObjectMapper deviceObjectMapper;
    private FileWriter dataFileWriter;

    public DeviceDataManager() {
        deviceObjectMapper = new ObjectMapper();
        deviceObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);
    }

    private String infoToString(Map<UUID, Device> deviceMap) throws JsonProcessingException {
        return deviceObjectMapper.writeValueAsString(deviceMap);
    }

    /**
     * Сохранение информации в .aether
     * @param deviceMap
     */
    public void binarySave(Map<UUID, Device> deviceMap) {
        this.deviceData = new File(defaultDirectoryBinary);
        try {
            aetherCryptoFiles.secureDataSave(infoToString(deviceMap), this.deviceData);
        } catch (Exception e) {
            Terminal.error(e.getMessage());
        }
    }

    /**
     * Чтение информации из .aether
     * @return
     */
    public Map<UUID, Device> binaryRead() {
        if (deviceData == null || !deviceData.getName().equals(defaultDirectoryBinary)) this.deviceData = new File(defaultDirectoryBinary);
        TypeReference<Map<UUID, Device>> typeReference = new TypeReference<Map<UUID, Device>>() {};
        try {

           return deviceObjectMapper.readValue(aetherCryptoFiles.secureDataLoad(this.deviceData), typeReference);
        } catch (Exception e) {
            Terminal.error(e.getMessage());
        }

        return null;
    }

}
