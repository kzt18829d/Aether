package com.aether.business.commands;

import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = {"device"},
        commandDescription = "Get device's status"
)
public class CommandStatusDevice {
    @Parameter (
            description = "<Device UUID>"
    )
    private String uuid;

    public String getUuid() {
        return uuid;
    }
}
