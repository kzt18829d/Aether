package com.aether.core.types.wrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Класс местоположения
 */
public class Location {
    private final String location;

    /**
     * Конструктор
     * @param location
     */
    @JsonCreator
    public Location(String location) {
        this.location = location;
    }

    /**
     * Вернуть Location в формате String
     * @return String
     */
    @JsonValue
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
