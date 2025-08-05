package com.aether.business.types;

import java.util.Objects;
import com.aether.business.Exceptions.InvalidLocationNameException;
import com.aether.business.Exceptions.LocationException;

/**
 * Класс местоположения
 */
public class Location {
    private final String location;

    /**
     * Конструктор
     * @param location
     */
    public Location(String location) {
        if (location.isEmpty()) throw new LocationException("Location has invalid name: \"" + location + "\".");
        this.location = location;
    }

    /**
     * Вернуть Location в формате String
     * @return String
     */
    public String getString() {
        return location;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Location location1 = (Location) o;
        return Objects.equals(location, location1.location);
    }

    @Override
    public int hashCode() {
        return 27 * location.hashCode();
    }

    @Override
    public String toString() {
        return "Location{" +
                "location='" + location + '\'' +
                '}';
    }
}
