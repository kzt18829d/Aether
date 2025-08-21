package com.aether.services;

import com.aether.core.validators.NameValidator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.regex.Pattern;

public class ConfigurationService {
    private final static Logger logger = LoggerFactory.getLogger(ConfigurationService.class);

    private static final Object lock = new Object();

    private final Map<String, String> configuration = new ConcurrentHashMap<>();
    private final List<String> envFilesPaths = List.of(
            "aetherSettings.env"
    );

    private ConfigurationService() {
        logger.info("Environment manager initialized with configuration from: {}", envFilesPaths);
    }

    private static final class InstanceHolder {
        private static final ConfigurationService instance = new ConfigurationService();
    }

    public static ConfigurationService getInstance() {
        return InstanceHolder.instance;
    }

    private static final Pattern ENV_LINE = Pattern.compile(
            "^\\s*([A-Za-z_][A-Za-z0-9_]*)\\s*=\\s*(.*)\\s*$"
    );

    private String clearValue(String value) {
        if (value.length() >= 2) {
            char first = value.charAt(0);
            char last = value.charAt(value.length() - 1);

            if ((first == '"' && last == '"') || (first == '\'' && last == '\'')) {
                return value.substring(1, value.length() - 1);
            }
        }
        return value;
    }

    private void processEnvLine(String line, String filePath, int lineNumber) {
        line = line.trim();

        if (line.isEmpty() || line.startsWith("#")) {
            return;
        }

        var matcher = ENV_LINE.matcher(line);
        if (matcher.matches()) {
            String key = matcher.group(1);
            String value = matcher.group(2);

            value = clearValue(value);
            configuration.put(key, value);

        } else {
            logger.warn("Invalid .env line in {}:{}: {}", filePath, lineNumber, line);
        }
    }

    public Optional<String> getString(String key) {
        return Optional.ofNullable(configuration.get(key));
    }

    public String getString(String key, String defaultValue) {
        return configuration.getOrDefault(key, defaultValue);
    }

    public Optional<Integer> getInt(String key) {
        return getString(key).map(value -> {
            try {
                return Integer.parseInt(value);
            } catch (NumberFormatException e) {
                logger.warn("Invalid integer value for key '{}': {}", key, value);
                return null;
            }
        });
    }

    private void loadFromFile(String filePath) {
        Path path = Paths.get(filePath);

        if (!Files.exists(path)) {
            logger.info("Configuration file not found: {}", filePath);
            return;
        }

        try (BufferedReader reader = Files.newBufferedReader(path)) {
            String line;
            int lineNumber = 0;

            while ((line = reader.readLine()) != null) {
                lineNumber++;
                processEnvLine(line, filePath, lineNumber);
            }

            logger.info("Successfully loaded configuration from: {}", filePath);

        } catch (IOException e) {
            logger.warn("Failed to read configuration file '{}': {}", filePath, e.getMessage());
        }
    }

    private void loadConfiguration() {
        configuration.clear();

        for (String filePath : envFilesPaths) {
            loadFromFile(filePath);
        }

        logger.info("Loaded {} configuration properties", configuration.size());
    }

    private void setValidatorSettings() {
        // Настройка валидатора имён
        getInt("NAME_MIN_LENGTH").ifPresent(NameValidator::setMinimalLength);
        getInt("NAME_MAX_LENGTH").ifPresent(NameValidator::setMaximalLength);

        logger.info("Applied configuration to validators");
    }

    private void setCryptoServiceSettings() {
        getString("SECURE_KEY").ifPresent(CryptoService::loadSecureKey);
        getInt("READ_DATA_BUFFER_SIZE").ifPresent(CryptoService::loadBufferSize);

        logger.info("Applied configuration to crypto service");
    }

    private void setDefaultFilePaths() {
        getString("DEFAULT_JSON").ifPresent(DataSaverServiceJSON::setDefaultJsonFile);
        getString("AETHER_FILE_FORMAT").ifPresent(DataSaverServiceAether::setFileFormat);
        getString("DEFAULT_AETHER").ifPresent(DataSaverServiceAether::loadDefaultFileDirectory);

        logger.info("Applied configuration to save services");

    }

}
