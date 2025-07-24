package com.aether.business.commander.commands.Remove;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "device"
)
public class CommandRemoveDevice extends ICommand {
    @Parameter(
            description = "Remove device's UUID"
    )
    private String uuid;

    public String getUuid() {
        return this.uuid;
    }

    @Override
    public void clearCommand() {
        this.uuid = null;
    }
}
