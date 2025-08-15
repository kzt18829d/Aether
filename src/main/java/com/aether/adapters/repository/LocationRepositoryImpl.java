package com.aether.adapters.repository;

import com.aether.core.ports.repository.LocationRepository;
import com.aether.core.types.wrappers.Location;
import com.aether.exceptions.RepositoryException;

import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class LocationRepositoryImpl implements LocationRepository {

    private Map<String, Location> repositoryMap;

    public LocationRepositoryImpl() {
        this.repositoryMap = new ConcurrentHashMap<>();
    }
    /**
     * Add new Location
     *
     * @param location Location
     */
    @Override
    public void addLocation(Location location) {
        if (location == null) throw new NullPointerException("Location cannot be null");
        var locName = location.getString();
        if (!repositoryMap.containsKey(locName)) throw new RepositoryException("Location " + locName + " was added earlier");
        repositoryMap.put(locName, location);
    }

    /**
     * Remove Location
     *
     * @param locName Location name
     */
    @Override
    public void removeLocation(String locName) {
        if (locName == null || locName.isEmpty()) throw new NullPointerException("Location cannot be null");
        if (!repositoryMap.containsKey(locName)) throw new RepositoryException("Location " + locName + " not found");
        repositoryMap.remove(locName);
    }

    /**
     * Check contains Location in Repository
     *
     * @param locName Location name
     * @return true if contains, false if not
     */
    @Override
    public boolean containsLocation(String locName) {
        if (locName == null || locName.isEmpty()) throw new NullPointerException("Location cannot be null");
        return repositoryMap.containsKey(locName);
    }

    /**
     * Get All Locations
     *
     * @return List of Locations
     */
    @Override
    public List<Location> getAllLocationList() {
        return repositoryMap.values().stream().toList();
    }

    /**
     * Get Location by name
     *
     * @param locName Location name
     * @return Location or null
     */
    @Override
    public Location getLocation(String locName) {
        if (locName == null || locName.isEmpty()) throw new NullPointerException("Location cannot be null");
        return repositoryMap.get(locName);
    }
}
