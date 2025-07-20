package com.aether.business.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "location",
        commandDescription = "Add new location"
)
public class CommandAddLocation {
    @Parameter(
            required = true,
            description = "<LocationName>"
    )
    private String locationName;

    public String getLocationName() {
        return locationName;
    }
}
