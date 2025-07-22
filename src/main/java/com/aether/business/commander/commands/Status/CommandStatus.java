package com.aether.business.commander.commands.Status;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = {"status", "Status"},
        commandDescription = "Get device/s status"
)
public class CommandStatus extends ICommand {
}
