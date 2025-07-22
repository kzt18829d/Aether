package com.aether.business.commander.commands.Add;

import java.util.ArrayList;
import java.util.List;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "device",
        commandDescription = "Add new Device"
)
public class CommandAddDevice extends ICommand {
    @Parameter(
            arity = 3,
            required = true,
            description = "<deviceType> <deviceName> <deviceLocation>"
    )
    private List<String> deviceParameters;

    public List<String> getDeviceParameters() {
        return deviceParameters;
    }

    @Override
    public void clearCommand() {
        if (deviceParameters != null) deviceParameters.clear();
    }
}
