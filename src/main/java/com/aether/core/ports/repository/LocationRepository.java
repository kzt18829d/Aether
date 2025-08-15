package com.aether.core.ports.repository;

import com.aether.core.types.wrappers.Location;

import java.util.List;

/**
 * Интерфейс репозитория локаций
 */
public interface LocationRepository {

    /**
     * Add new Location
     * @param location Location
     */
    void addLocation(Location location);

    /**
     * Remove Location
     * @param locName Location name
     */
    void removeLocation(String locName);

    /**
     * Check contains Location in Repository
     * @param locName Location name
     * @return true if contains, false if not
     */
    boolean containsLocation(String locName);

    /**
     * Get All Locations
     * @return List of Locations
     */
    List<Location> getAllLocationList();

    /**
     * Get Location by name
     * @param locName Location name
     * @return Location or null
     */
    Location getLocation(String locName);

}
