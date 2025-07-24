package com.aether.business.commander.commands.Remove;

import com.aether.business.commander.ICommand;
import com.beust.jcommander.Parameter;
import com.beust.jcommander.Parameters;

@Parameters(
        commandNames = "Remove Scenario",
        commandDescription = "Command for remove scenario"
)
public class CommandRemoveScenario extends ICommand {
    @Parameter(
            description = "Scenario's name"
    )
    String scenarioName;

    public String getScenarioName() {
        return this.scenarioName;
    }

    @Override
    public void clearCommand() {
        this.scenarioName = null;
    }
}


