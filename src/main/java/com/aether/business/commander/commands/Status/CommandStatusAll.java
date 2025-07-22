package com.aether.business.commander.commands.Status;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "all",
        commandDescription = "Get all devices's status"
)
public class CommandStatusAll extends ICommand {
}
