package com.aether.business.commander.commands.Status;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = {"device"},
        commandDescription = "Get device's status"
)
public class CommandStatusDevice extends ICommand {
    @Parameter (
            description = "<Device UUID>"
    )
    private String uuid;

    public String getUuid() {
        return uuid;
    }

    @Override
    public void clearCommand() {
        this.uuid = null;
    }
}
