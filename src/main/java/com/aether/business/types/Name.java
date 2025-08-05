package com.aether.business.types;

import com.aether.business.Exceptions.InvalidDeviceNameException;
import com.aether.business.Exceptions.valid.NameException;

import java.util.Objects;

/**
 * @ru Имя объекта
 * @en Object name
 */
public class Name {
    private final String name;

    /**
     * @ru Конструктор
     * @en Constructor
     * @param name
     */
    public Name(String name) {
        this.name = validName(name);
    }

    /**
     * @ru Проверка валидности имени объекта
     * @en Object name validator
     * @param name
     * @return String
     * @throws NameException
     */
    private String validName(String name) {
        if (name.isEmpty()) throw new NameException("Assigned empty name to device.");
        if (name.matches("\\d+")) throw new NameException("The device name consists entirely of numbers.");
        return name;
    }

    /**
     * @ru Вернуть Name в формате String
     * @en Return Name in String format
     * @return String
     */
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
