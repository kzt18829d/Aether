package com.aether.business.filemanagement;

import com.aether.business.constaints.Terminal;
import com.aether.business.devices.Device;
import com.aether.business.filemanagement.JSON.AetherCryptoFiles;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.jsontype.BasicPolymorphicTypeValidator;

import java.io.File;
import java.io.FileWriter;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * Менеджер для сохранения и загрузки данных устройств.
 * Поддерживает сохранение в зашифрованном формате .aether и обычном JSON.
 */
public class DeviceDataManager {
    AetherCryptoFiles aetherCryptoFiles = new AetherCryptoFiles();

    private static final String defaultDirectoryJSON = "AetherDeviceData.json";
    private static final String defaultDirectoryBinary = "AetherDeviceData.aether";
    private ObjectMapper deviceObjectMapper;
    private FileWriter dataFileWriter;

    /**
     * Конструктор по умолчанию
     */
    public DeviceDataManager() {
        deviceObjectMapper = new ObjectMapper();
        deviceObjectMapper.enable(SerializationFeature.INDENT_OUTPUT);

    }

    /**
     * Map в JSON_String
     * @param deviceMap
     * @return
     * @throws JsonProcessingException
     */
    private String mapToJsonString(Map<UUID, Device> deviceMap) throws JsonProcessingException {
        if (deviceMap == null) return "{}";
        return deviceObjectMapper.writeValueAsString(deviceMap);
    }

    /**
     * JSON-String в Map
     * @param json
     * @return
     * @throws JsonProcessingException
     */
    private Map<UUID, Device> jsonStringToMap(String json) throws JsonProcessingException {
        if (json == null || json.trim().isEmpty() || "{}".equals(json.trim())) return new HashMap<>();
        TypeReference<Map<UUID, Device>> typeReference = new TypeReference<Map<UUID, Device>>() {};
        return deviceObjectMapper.readValue(json, typeReference);
    }

    /**
     * Сохранение информации в .aether
     * @param deviceMap
     */
    public boolean binarySave(Map<UUID, Device> deviceMap, String fileName) {
        try {
            String json = mapToJsonString(deviceMap);
            File file = new File(fileName);

            aetherCryptoFiles.secureDataSave(json,file);
            Terminal.info("Device data saved to " + file.getPath());
            return true;
        } catch (Exception e) {
            Terminal.error("Fail to save device data: " + e.getMessage());
            return false;
        }
    }

    /**
     * Сохранение в .aether по умолчанию
     * @param deviceMap
     * @return
     */
    public boolean binarySave(Map<UUID, Device> deviceMap) {
        return binarySave(deviceMap, defaultDirectoryBinary);
    }

    /**
     * Чтение информации из .aether
     * @return
     */
    public Map<UUID, Device> binaryRead(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                Terminal.warn("File" + fileName + " does not exist. Created new database");
                return new HashMap<>();
            }

            String json = aetherCryptoFiles.secureDataLoad(file);
            Map<UUID, Device> deviceMap = jsonStringToMap(json);
            Terminal.info("Device data loaded from " + fileName + ". \n\t\t Loaded " + deviceMap.size() + " devices");
            return deviceMap;
        } catch (Exception e) {
            Terminal.error("Fail to load device data from " + fileName + ": " + e.getMessage());
            return new HashMap<>();
        }
    }

    public Map<UUID, Device> binaryRead() {
        return binaryRead(defaultDirectoryBinary);
    }

    public boolean jsonSave(Map<UUID, Device> deviceMap, String fileName) {
        try {
            File file = new File(fileName);
            deviceObjectMapper.writeValue(file, deviceMap);
            Terminal.info("Device data saved to " + file.getPath());
            return true;
        } catch (Exception e) {
            Terminal.error("Fail to save device data: " + e.getMessage());
            return false;
        }
    }

    public boolean jsonSave(Map<UUID, Device> deviceMap) {
        return jsonSave(deviceMap, defaultDirectoryJSON);
    }

    public Map<UUID,Device> jsonRead(String fileName) {
        try {
            File file = new File(fileName);
            if (!file.exists()) {
                Terminal.warn("File " + fileName + " doesn't exist. Created new database");
                return new HashMap<>();
            }

            TypeReference<Map<UUID, Device>> typeReference = new TypeReference<Map<UUID, Device>>() {};
            Map<UUID, Device> deviceMap = deviceObjectMapper.readValue(file, typeReference);
            Terminal.info("Device data loaded from " + fileName + ". Loaded " + deviceMap.size() + " devices");
            return deviceMap;
        } catch (Exception e) {
            Terminal.error("Fail to load device data from " + fileName + ": " + e.getMessage());
            return new HashMap<>();
        }
    }

    public Map<UUID, Device> jsonRead() {
        return jsonRead(defaultDirectoryJSON);
    }


}
