package com.aether.business.commander.commands.help;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "help",
        commandDescription = "Get help information"
)
public class CommandHelp extends ICommand {
    @Parameter(
            description = "Command name for detailed help"
    )
    private String commandName;

    public String getCommandName() {
        return commandName;
    }

    @Override
    public void clearCommand() {
        this.commandName = null;
    }
}
