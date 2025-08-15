package com.aether.adapters.factory;

import com.aether.core.ports.functionalInterfaces.DeviceCreator;
import com.aether.core.types.devices.AbstractDevice;
import com.aether.exceptions.DeviceRegistryException;

import java.util.Map;
import java.util.Objects;
import java.util.concurrent.ConcurrentHashMap;

public class DeviceRegistry {
    private static final DeviceRegistry Instance = new DeviceRegistry();
    private final Map<String, DeviceCreator> registryMap = new ConcurrentHashMap<>();;

    public DeviceRegistry() {}

    public void registry(String typeName, DeviceCreator function) {
        Objects.requireNonNull(typeName, "TypeName cannot be null");
        Objects.requireNonNull(function, "Function cannot be null");
        if (typeName.isEmpty()) throw new DeviceRegistryException("Typename cannot be empty");
        registryMap.put(typeName, function);
    }

    public boolean contains(String typeName) {
        return registryMap.containsKey(typeName);
    }

    public void removeRegistry(String key) {
        if (!contains(key)) throw new DeviceRegistryException("Type \"" + key + "\" not found");
        registryMap.remove(key);
    }

    public DeviceCreator getRegistry(String key) {
        return registryMap.get(key);
    }

}
