package com.aether.business.commander.commands.Remove;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "remove location",
        commandDescription = "Command for remove location"
)
public class CommandRemoveLocation extends ICommand {
    @Parameter(
            description = "Location name"
    )
    String locationName;

    public String getLocationName() {
        return this.locationName;
    }

    @Override
    public void clearCommand() {
        this.locationName = null;
    }
}
