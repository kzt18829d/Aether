package com.aether.business.commander;

import com.aether.business.Exceptions.*;
import com.aether.business.commander.commands.*;
import com.aether.business.commander.commands.Add.CommandAdd;
import com.aether.business.commander.commands.Add.CommandAddDevice;
import com.aether.business.commander.commands.Add.CommandAddDeviceHelp;
import com.aether.business.commander.commands.Add.CommandAddLocation;
import com.aether.business.commander.commands.Remove.CommandRemove;
import com.aether.business.commander.commands.Remove.CommandRemoveDevice;
import com.aether.business.commander.commands.Remove.CommandRemoveLocation;
import com.aether.business.commander.commands.Status.CommandStatus;
import com.aether.business.commander.commands.Status.CommandStatusAll;
import com.aether.business.commander.commands.Status.CommandStatusDevice;
import com.aether.business.constaints.Terminal;
import com.aether.business.core.SmartHomeController;
import com.aether.business.devices.Device;
import com.aether.business.factory.ObjectsFactory;
import com.aether.business.types.Location;
import com.beust.jcommander.JCommander;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;
import java.util.UUID;

public class CommandsManager {
    private final String cmdl;
    private final String cmdlERR;
    private final String cmdlINFO;
    private final Scanner scanner;

    private final CommandExit commandExit;
    private final CommandLoad commandLoad;
    private final CommandSave commandSave;

    private final CommandAdd commandAdd;
    private final CommandAddDevice commandAddDevice;
    private final CommandAddDeviceHelp commandAddDeviceHelp;
    private final CommandAddLocation commandAddLocation;

    private final CommandStatus commandStatus;
    private final CommandStatusDevice commandStatusDevice;
    private final CommandStatusAll commandStatusAll;

    private final CommandRemove commandRemove;
    private final CommandRemoveDevice commandRemoveDevice;
    private final CommandRemoveLocation commandRemoveLocation;
    // others commands

    public CommandsManager(Object main_) {
        cmdl = "<Aether> ";
        cmdlERR = "<Aether> [ERROR] ";
        cmdlINFO = "<Aether> [INFO] ";
        scanner = new Scanner(System.in);

        commandExit = new CommandExit();
        commandLoad = new CommandLoad();
        commandSave = new CommandSave();

        commandAdd = new CommandAdd();
        commandAddDevice = new CommandAddDevice();
        commandAddDeviceHelp = new CommandAddDeviceHelp();
        commandAddLocation = new CommandAddLocation();

        commandStatus = new CommandStatus();
        commandStatusDevice = new CommandStatusDevice();
        commandStatusAll = new CommandStatusAll();

        commandRemove = new CommandRemove();
        commandRemoveDevice = new CommandRemoveDevice();
        commandRemoveLocation = new CommandRemoveLocation();
    }

    public void Commander(SmartHomeController smartHomeController, Object main_object) {
        boolean cycleController = true;
        while (cycleController) {
            // <Aether>
            Terminal.user();
            String writtenCommand = scanner.nextLine();

            try {
                JCommander jCommander = constructJCommander(main_object);

                jCommander.parse(writtenCommand.split("\\s+"));
                String parsedCommand = jCommander.getParsedCommand();

                switch (parsedCommand) {
                    case "exit":
                    case "Exit":
                        funcExit(cycleController);
                        break;
                    case "load":
                    case "Load":
                        funcLoad();
                        break;
                    case "save":
                    case "Save":
                        funcSave();
                        break;
                    case "add":
                        String addSubCommand = jCommander.getCommands().get("add").getParsedCommand();
                        if ("device".equals(addSubCommand)) {
                            String deviceHelpSubCommand = jCommander.getCommands().get("add").getCommands().get("device").getParsedCommand();
                            if ("help".equals(deviceHelpSubCommand)) Terminal.info(commandAddDeviceHelp.help_s());
                            addDevice(smartHomeController);
                        }
                        else if ("location".equals(addSubCommand)) addLocation(smartHomeController);
                        else {
                            if (addSubCommand.isEmpty() || addSubCommand == null) throw new InvalidCommandException("Empty subCommand");
                            else throw new InvalidCommandException("Invalid subCommand " + addSubCommand);
                        }
                        break;
                    case "status":
                    case "Status":
                        String statusSubCommand = jCommander.getCommands().get("status").getParsedCommand();
                        if ("all".equals(statusSubCommand)) getStatusAll(smartHomeController);
                        else if ("device".equals(statusSubCommand)) getStatusDevice(smartHomeController);
                        else {
                            throw new SmartHomeControllerException("Invalid or empty command's argument");
                        }
                        break;
                    case "remove":
                    case "Remove":
                        String removeCommand = jCommander.getCommands().get("remove").getParsedCommand();
                        if ("deivce".equals(removeCommand)) {
                            removeDevice(smartHomeController, commandRemoveDevice.getUuid());
                        }
                        else if ("location".equals(removeCommand)) {
                            removeLocation(smartHomeController, commandRemoveLocation.getLocationName());
                        }
                        else {
                            throw new InvalidCommandException("Invalid command");
                        }
                    default:
                        throw new ParseException("Invalid command \"" + writtenCommand, 0);
                }
            } catch (CreateDeviceException exception) {
                Terminal.error("Device wasn't created. " + exception.getMessage());
            }
            catch (InvalidDeviceNameException exception){
                Terminal.error("Invalid name of Device. " + exception.getMessage());
            }
            catch (InvalidLocationNameException exception) {
                Terminal.error("Invalid Location name. " + exception.getMessage());
            }
            catch (InvalidCommandException | ParseException exception) {
                Terminal.error("Invalid command. " + exception.getMessage());
            }
            catch (Exception exception) {
                Terminal.error("Other Error: " + exception.getMessage());
            }

            if (!cycleController) return;
        }
    }

    public JCommander constructJCommander(Object main_object) {
        JCommander jCommander = JCommander.newBuilder().addObject(main_object)
                .addCommand(commandExit)
                .addCommand(commandLoad)
                .addCommand(commandSave)
                .addCommand(commandAdd)
                .addCommand(commandStatus)
                .addCommand(commandRemove)
                .build();

        jCommander.getCommands().get("add").addCommand("device", commandAddDevice);
        jCommander.getCommands().get("add").getCommands().get("device").addCommand("help", commandAddDeviceHelp);
        jCommander.getCommands().get("add").addCommand("location", commandAddLocation);

        jCommander.getCommands().get("status").addCommand("all", commandStatusAll);
        jCommander.getCommands().get("status").addCommand("device", commandStatusDevice);

        jCommander.getCommands().get("remove").addCommand("device", commandRemoveDevice);
        jCommander.getCommands().get("remove").addCommand("location", commandRemoveLocation);

        return jCommander;
    }

    private void funcExit(boolean b) {
        funcSave();
        Terminal.info("Exiting...");
        b = false;
        // опционально
        System.exit(0);
    }

    private void funcSave() {
        Terminal.info("Save Data to \"XXXX\"");
    }

    private void funcLoad() {
        Terminal.info("\"Loading Data from \"XXXX\"...");
    }

    private void addDevice(SmartHomeController controller) {
        try {
            List<String> parameters = commandAddDevice.getDeviceParameters();
            if (parameters.isEmpty() ) {
                Terminal.info(commandAddDeviceHelp.help_s());
                return;
            }

            Device newDevice = ObjectsFactory.createDevice(parameters.getFirst(), parameters.get(1), parameters.getLast());
            controller.addDevice(newDevice);
        } finally {
            commandAddDevice.clearCommand();
        }
    }

    public void deviceHelp() {
        Terminal.info(commandAddDeviceHelp.help_s());
    }

    private void addLocation(SmartHomeController controller) {
        try {
            var locationTemplate = commandAddLocation.getLocationName();
            controller.addLocation(new Location(locationTemplate));
        } finally {
            commandAddLocation.clearCommand();
        }
    }

    private void getStatusAll(SmartHomeController smartHomeController) {
        System.out.println(smartHomeController.getSystemStatusReport_ByString());
    }

    private void getStatusDevice(SmartHomeController smartHomeController) {
        try {
            System.out.println(smartHomeController.getDeviceStatusReport_ByString(UUID.fromString(commandStatusDevice.getUuid())));
        } finally {
            commandStatusDevice.clearCommand();
        }
    }

    private void removeDevice(SmartHomeController smartHomeController, String uuid_s) {
        try {
            UUID uuid = UUID.fromString(uuid_s);
            smartHomeController.removeDevice(uuid);

        } catch (SmartHomeControllerException exception) {
            Terminal.error("Device " + uuid_s + " wasn't deleted. " + exception.getMessage());
        }
        catch (RuntimeException exception) {
            Terminal.error(exception.getMessage());
        } finally {
            commandRemoveDevice.clearCommand();
        }
    }

    public void removeLocation(SmartHomeController smartHomeController, String loc_name) {
        try {
            if (!smartHomeController.findLocation_b(loc_name)) throw new SmartHomeControllerException("Location " + loc_name + " wasn't find.");
            smartHomeController.removeLocation(loc_name);
        } finally {
            commandRemoveLocation.clearCommand();
        }
    }
}
