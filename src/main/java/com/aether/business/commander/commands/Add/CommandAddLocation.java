package com.aether.business.commander.commands.Add;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "location",
        commandDescription = "Add new location"
)
public class CommandAddLocation extends ICommand {
    @Parameter(
            required = true,
            description = "<LocationName>"
    )
    private String locationName;

    public String getLocationName() {
        return locationName;
    }

    @Override
    public void clearCommand() {
        locationName = null;
    }
}
