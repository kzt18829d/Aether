package com.aether.services;

import com.aether.core.ports.services.DataSaverService;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.dto.DeviceDTO;
import com.aether.exceptions.DataSaverServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.List;
import java.util.Objects;

public class DataSaverServiceJSON implements DataSaverService {
    private static String DEFAULT_JSON_FILE;
    private final ObjectMapper objectMapper;

    public DataSaverServiceJSON() {
        objectMapper = objectMapperSettings();
    }

    private ObjectMapper objectMapperSettings() {
        ObjectMapper obj = new ObjectMapper();
        obj.enable(SerializationFeature.INDENT_OUTPUT);
        return obj;
    }

    public static void setDefaultJsonFile(String defaultJsonFile) {
        DEFAULT_JSON_FILE = defaultJsonFile;
    }

    private void checkDefaultFile() {
        Objects.requireNonNull(DEFAULT_JSON_FILE, "Default json file wasn't loaded");
        if (DEFAULT_JSON_FILE.isEmpty()) throw new DataSaverServiceException("Default json file cannot be empty");
        if (!DEFAULT_JSON_FILE.endsWith(".json")) throw new DataSaverServiceException("Error of default file directory. Invalid filetype");
    }

    private void checkPersonalFile(String path) {
        Objects.requireNonNull(path, "Custom json file wasn't loaded: \"" + path + "\"");
        if (path.isEmpty()) throw new DataSaverServiceException("Custom json file cannot be empty: \"" + path + "\"");
        if (!path.endsWith(".json")) throw new DataSaverServiceException("Error of custom json file directory. Invalid filetype: \"" + path + "\"");
    }

    /**
     * @param deviceList
     */
    @Override
    public void save(List<DeviceDTO> deviceList) throws IOException {
        checkDefaultFile();
        File saveFile = new File(DEFAULT_JSON_FILE);
        objectMapper.writeValue(saveFile, deviceList);
    }

    /**
     * @param deviceList
     * @param path
     * @throws UnsupportedOperationException
     */
    @Override
    public void save(List<DeviceDTO> deviceList, String path) throws UnsupportedOperationException, IOException {
        checkPersonalFile(path);
        File saveFile = new File(path);
        objectMapper.writeValue(saveFile, deviceList);
    }

    /**
     * @return
     */
    @Override
    public List<DeviceDTO> load() throws IOException {
        checkDefaultFile();
        File loadFile = new File(DEFAULT_JSON_FILE);
        if (!loadFile.exists()) throw new FileNotFoundException("File " + DEFAULT_JSON_FILE + " not found");

        TypeReference<List<DeviceDTO>> typeReference = new TypeReference<>() {};
        return objectMapper.readValue(loadFile, typeReference);
    }

    /**
     * @param path
     * @return
     * @throws UnsupportedOperationException
     */
    @Override
    public List<DeviceDTO> load(String path) throws UnsupportedOperationException, IOException {
        checkPersonalFile(path);

        File loadFile = new File(path);

        TypeReference<List<DeviceDTO>> typeReference = new TypeReference<>() {};
        return objectMapper.readValue(loadFile, typeReference);
    }

    /**
     * @return
     */
    @Override
    public boolean supportPathOperations() {
        return true;
    }

    /**
     * @return
     */
    @Override
    public String getStorageType() {
        return "JSON";
    }

}
