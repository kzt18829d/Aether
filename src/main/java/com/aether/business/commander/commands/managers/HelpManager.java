package com.aether.business.commander.commands.managers;

import com.aether.business.commander.commands.help.CommandHelp;
import com.aether.business.constaints.Terminal;
import com.aether.business.core.SmartHomeController;
import com.beust.jcommander.JCommander;

import java.util.Map;

public class HelpManager extends IManager {

    private static String notAvailableDescription() {
        return "Command description not available";
    }

    public static void showHelp(SmartHomeController smartHomeController, JCommander jCommander, CommandHelp commandHelp) {
        try {
            String specificCommand = commandHelp.getCommandName();

            if (specificCommand == null || specificCommand.trim().isEmpty()) {
                // Показываем общую справку
                showGeneralHelp(jCommander);
            } else {
                // Показываем детальную справку для конкретной команды
                showCommandHelp(smartHomeController, specificCommand);
            }
        } finally {
            commandHelp.clearCommand();
        }
    }

    private static void showGeneralHelp(JCommander jCommander) {
        StringBuilder helpText = new StringBuilder();
        helpText.append("Available commands:\n");
        helpText.append("==============================================================\n");

        Map<String, JCommander> commandsMap = jCommander.getCommands();

        for(Map.Entry<String, JCommander> entry : commandsMap.entrySet()) {
            String commandName = entry.getKey();
            String commandDescription = entry.getValue().getMainParameterDescription();

            if (commandDescription.isEmpty() || commandDescription == null) commandDescription = notAvailableDescription();

            helpText.append(String.format("  %-15s - %s\n", commandName, commandDescription));

            Map<String, JCommander> subCommands = entry.getValue().getCommands();
            if (!subCommands.isEmpty()) {
                for (String subCommand : subCommands.keySet()) {
                    helpText.append(String.format("    %-13s - %s %s\n",
                            subCommand, commandName, subCommand));
                }
            }

            helpText.append("\nFor detailed help on specific command, use: help <command>");
            Terminal.info(helpText.toString());
        }
    }

    private static void showCommandHelp(SmartHomeController smartHomeController, String commandName) {
        switch (commandName) {
            case "add":
                showAddCommandHelp(smartHomeController);
                break;
            case "remove":
                showRemoveCommandHelp(smartHomeController);
                break;
            case "status":
                showStatusCommandHelp(smartHomeController);
                break;
            case "device":
                // TODO написать справку для девайсов
                break;
            case "scenario":
                // TODO написать справку для сценариев, если таковые будут
                break;
            default:
                Terminal.error("No detailed help available for command: " + commandName);
                Terminal.info("Available commands: add, status, remove, device, load, save, exit");
        }
    }

    //AI generated
    private static void showAddCommandHelp(SmartHomeController controller) {
        StringBuilder helpText = new StringBuilder();
        helpText.append("ADD COMMAND HELP\n");
        helpText.append("================\n\n");

        helpText.append("Available subcommands:\n");
        helpText.append("  add device <deviceType> <deviceName> <deviceLocation>\n");
        helpText.append("  add location <locationName>\n\n");

        helpText.append("DEVICE TYPES:\n");
        helpText.append("  Light          - Smart lighting device\n");
        helpText.append("  SecurityCamera - Security monitoring device\n");
        helpText.append("  SmartLock      - Smart door lock\n");
        helpText.append("  Thermostat     - Temperature control device\n\n");

        helpText.append("EXISTING LOCATIONS:\n");
        if (controller.getAllLocations().isEmpty()) {
            helpText.append("  No locations created yet. You can create new location or\n");
            helpText.append("  device will create location automatically.\n");
        } else {
            controller.getAllLocations().forEach(location ->
                    helpText.append("  ").append(location.get()).append("\n"));
        }

        helpText.append("\nEXAMPLES:\n");
        helpText.append("  add device Light \"Living_Room_Light\" \"Living_Room\"\n");
        helpText.append("  add device SecurityCamera \"Front_Door_Cam\" \"Entrance\"\n");
        helpText.append("  add location \"Kitchen\"\n");

        Terminal.info(helpText.toString());
    }

    //AI Generated
    private static void showStatusCommandHelp(SmartHomeController controller) {
        StringBuilder helpText = new StringBuilder();
        helpText.append("STATUS COMMAND HELP\n");
        helpText.append("===================\n\n");

        helpText.append("Available subcommands:\n");
        helpText.append("  status all                    - Show status of all devices\n");
        helpText.append("  status device <deviceUUID>    - Show status of specific device\n\n");

        if (!controller.getAllDevices().isEmpty()) {
            helpText.append("EXISTING DEVICES:\n");
            controller.getAllDevices().forEach(device ->
                    helpText.append(String.format("  %s - %s (%s)\n",
                            device.getUuid(),
                            device.getDeviceNameString(),
                            device.getType())));
        } else {
            helpText.append("No devices available. Create devices first using 'add device' command.\n");
        }

        Terminal.info(helpText.toString());
    }

    //AI Generated
    private static void showRemoveCommandHelp(SmartHomeController controller) {
        StringBuilder helpText = new StringBuilder();
        helpText.append("REMOVE COMMAND HELP\n");
        helpText.append("===================\n\n");

        helpText.append("Available subcommands:\n");
        helpText.append("  remove device <deviceUUID>      - Remove specific device\n");
        helpText.append("  remove location <locationName>  - Remove location (devices will be turned off)\n\n");

        if (!controller.getAllDevices().isEmpty()) {
            helpText.append("EXISTING DEVICES:\n");
            controller.getAllDevices().forEach(device ->
                    helpText.append(String.format("  %s - %s\n",
                            device.getUuid(),
                            device.getDeviceNameString())));
            helpText.append("\n");
        }

        if (!controller.getAllLocations().isEmpty()) {
            helpText.append("EXISTING LOCATIONS:\n");
            controller.getAllLocations().forEach(location ->
                    helpText.append("  ").append(location.get()).append("\n"));
        }

        Terminal.info(helpText.toString());
    }
}
