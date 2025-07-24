package com.aether.business.commander.commands.Add;

import com.aether.business.commander.ICommand;
import com.aether.business.devices.Device;
import com.beust.jcommander.Parameters;

import java.util.*;

@Parameters(
        commandNames = "help",
        commandDescription = "Command's group 'add device' helper"
)
public class CommandAddDeviceHelp extends ICommand {
    private List<String> deviceTypes;

    {
        deviceTypes = new ArrayList<String>(5);
        deviceTypes.add("Light");
        deviceTypes.add("SecurityCamera");
        deviceTypes.add("SmartLock");
        deviceTypes.add("Thermostat");
    }

    public void addType(Device device) {
        deviceTypes.add(device.getType());
    }

    public void addType(String type) {
        deviceTypes.add(type);
    }

    public List<String> getDeviceTypes() {
        return deviceTypes;
    }

    public void help_v() {
        StringBuilder message = new StringBuilder("You can create and use next device types:");
        for (String type: deviceTypes) message.append("\n\t\t").append(type);
        System.out.println(message);
    }

    public String help_s() {
        StringBuilder message = new StringBuilder("You can create and use next device types:");
        for (String type: deviceTypes) message.append("\n\t\t").append(type);
        return message.toString();
    }
}
