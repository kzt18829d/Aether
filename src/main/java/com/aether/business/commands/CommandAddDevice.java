package com.aether.business.commands;

import java.util.List;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "device",
        commandDescription = "Add new Device"
)
public class CommandAddDevice {
    @Parameter(
            arity = 3,
            required = true,
            description = "<deviceType> <deviceName> <deviceLocation>"
    )
    private List<String> deviceParameters;

    public List<String> getDeviceParameters() {
        return deviceParameters;
    }
}
