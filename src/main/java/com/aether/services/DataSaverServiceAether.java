package com.aether.services;

import com.aether.core.ports.services.DataSaverService;
import com.aether.dto.DeviceDTO;
import com.aether.exceptions.DataSaverServiceException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import javax.crypto.BadPaddingException;
import javax.crypto.IllegalBlockSizeException;
import javax.crypto.NoSuchPaddingException;
import java.io.*;
import java.security.InvalidAlgorithmParameterException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.security.spec.InvalidKeySpecException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class DataSaverServiceAether implements DataSaverService {
    private static String DEFAULT_FILE_DIRECTORY;
    private static String FILE_FORMAT = ".aether";
    private final ObjectMapper objectMapper;
    private final CryptoService cryptoService;

    public DataSaverServiceAether() {
        this.objectMapper = objectMapperSettings();
        this.cryptoService = new CryptoService();
    }

    private ObjectMapper objectMapperSettings() {
        return new ObjectMapper().enable(SerializationFeature.INDENT_OUTPUT);
    }

    public void loadDefaultFileDirectory(String filePath) {
        DEFAULT_FILE_DIRECTORY = filePath;
    }

    public void setFileFormat(String fileFormat) {
        Objects.requireNonNull(fileFormat, "File format cannot be null");
        if (fileFormat.isEmpty()) throw new DataSaverServiceException("File format cannot be empty");
        if (!fileFormat.startsWith(".") || fileFormat.endsWith(".") || fileFormat.endsWith(".json"))
            throw new DataSaverServiceException("Invalid file format");
        FILE_FORMAT = fileFormat;
    }

    private void checkFile(String filePath) {
        Objects.requireNonNull(filePath, "File directory wasn't load");
        if (filePath.isEmpty()) throw new DataSaverServiceException("File directory cannot be empty");
        if (!filePath.endsWith(FILE_FORMAT)) throw new DataSaverServiceException("Invalid file format. Expected format: \"~" + FILE_FORMAT +"\". Using: \"" + DEFAULT_FILE_DIRECTORY);
    }

    /**
     * @param deviceList
     * @throws IOException
     */
    @Override
    public void save(List<DeviceDTO> deviceList) throws IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        save(deviceList, DEFAULT_FILE_DIRECTORY);
    }

    /**
     * @param deviceList
     * @param path
     * @throws UnsupportedOperationException
     * @throws IOException
     */
    @Override
    public void save(List<DeviceDTO> deviceList, String path) throws UnsupportedOperationException, IOException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        checkFile(path);
        File saveFile = new File(path);
        String jsonString = objectMapper.writeValueAsString(deviceList);

        try(FileOutputStream fileOutputStream = new FileOutputStream(saveFile)) {
            fileOutputStream.write(cryptoService.encrypt(jsonString));
        }

    }

    /**
     * @return
     * @throws IOException
     */
    @Override
    public List<DeviceDTO> load() throws IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        return load(DEFAULT_FILE_DIRECTORY);
    }

    /**
     * @param path
     * @return
     * @throws UnsupportedOperationException
     * @throws IOException
     */
    @Override
    public List<DeviceDTO> load(String path) throws UnsupportedOperationException, IOException, InvalidAlgorithmParameterException, NoSuchPaddingException, IllegalBlockSizeException, NoSuchAlgorithmException, InvalidKeySpecException, BadPaddingException, InvalidKeyException {
        checkFile(path);
        File loadFile = new File(path);
        if (!loadFile.exists() || !loadFile.canRead()) throw new FileNotFoundException("File \"" + path + "\" not found or cannot read");

        String jsonString;
        try(FileInputStream fileInputStream = new FileInputStream(loadFile); BufferedInputStream bufferedInputStream = new BufferedInputStream(fileInputStream)) {
            jsonString = cryptoService.decrypt(bufferedInputStream);
        }

        TypeReference<List<DeviceDTO>> typeReference = new TypeReference<>(){};
        return objectMapper.readValue(jsonString, typeReference);
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
        return "AETHER";
    }
}
