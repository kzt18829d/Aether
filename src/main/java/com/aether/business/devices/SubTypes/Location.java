package com.aether.business.devices.SubTypes;

import java.util.Objects;
import com.aether.business.Exceptions.InvalidLocationNameException;

public class Location {
    private final String location;

    public Location(String location) {
        if (location.isEmpty()) throw new InvalidLocationNameException("Location has invalid name: \"" + location + "\".");
        this.location = location;
    }

    public String getLocation() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Location location1 = (Location) o;
        return Objects.equals(location, location1.location);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(location);
    }
}
