package com.aether.core.types.wrappers;

import com.fasterxml.jackson.annotation.JsonCreator;
import com.fasterxml.jackson.annotation.JsonValue;

import java.util.Objects;

/**
 * Wrapper-класс имён
 */
public class Name {
    private final String name;

    /**
     * Конструктор
     * @param name
     */
    @JsonCreator
    public Name(String name) {
        this.name = name;
    }

    /**
     * @ru Вернуть Name в формате String
     * @en Return Name in String format
     * @return String
     */
    @JsonValue
    public String getString() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Name other = (Name) o;
        return Objects.equals(name, other.name);
    }

    @Override
    public int hashCode() {
        return 27 * name.hashCode();
    }

    @Override
    public String toString() {
        return "Name{" +
                "name='" + name + '\'' +
                '}';
    }
}
