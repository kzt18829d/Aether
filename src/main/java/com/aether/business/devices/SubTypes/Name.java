package com.aether.business.devices.SubTypes;

import com.aether.business.Exceptions.InvalidDeviceNameException;

import java.util.Objects;

public class Name {
    private String name;

    public Name(String name) {
        this.name = validName(name);
    }

    private String validName(String name) {
        if (name.isEmpty()) throw new InvalidDeviceNameException("Assigned empty name to device.");
        if (name.matches("\\d+")) throw new InvalidDeviceNameException("The device name consists entirely of numbers.");
        return name;
    }

    public void setName(String newName) throws Exception {
        this.name = validName(newName);
    }

    public String getName() {
        return name;
    }

    @Override
    public boolean equals(Object o) {
        if (o == null || getClass() != o.getClass()) return false;
        Name name1 = (Name) o;
        return Objects.equals(name, name1.name);
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(name);
    }
}
