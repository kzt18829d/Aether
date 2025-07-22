package com.aether.business.filemanagement;

import com.aether.business.devices.Device;
import com.aether.business.types.Location;

import java.util.Map;
import java.util.UUID;

public interface FileManager {
    void saveToFile(Map<UUID, Device> map);
    Map.Entry<Map<UUID, Device>, Map<String, Location>> loadFromFile();
}
