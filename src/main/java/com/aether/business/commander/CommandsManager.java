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
import com.aether.business.commander.commands.managers.DeviceManager;
import com.aether.business.commander.commands.managers.LocationManager;
import com.aether.business.commander.commands.managers.StatusManager;
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
                            DeviceManager.addDevice(smartHomeController, commandAddDevice, commandAddDeviceHelp);
                        }
                        else if ("location".equals(addSubCommand)) LocationManager.addLocation(smartHomeController, commandAddLocation);
                        else {
                            if (addSubCommand.isEmpty() || addSubCommand == null) throw new InvalidCommandException("Empty subCommand");
                            else throw new InvalidCommandException("Invalid subCommand " + addSubCommand);
                        }
                        break;
                    case "status":
                    case "Status":
                        String statusSubCommand = jCommander.getCommands().get("status").getParsedCommand();
                        if ("all".equals(statusSubCommand)) StatusManager.getStatusAll(smartHomeController);
                        else if ("device".equals(statusSubCommand)) StatusManager.getStatusDevice(smartHomeController, commandStatusDevice);
                        else {
                            throw new SmartHomeControllerException("Invalid or empty command's argument");
                        }
                        break;
                    case "remove":
                    case "Remove":
                        String removeCommand = jCommander.getCommands().get("remove").getParsedCommand();
                        if ("deivce".equals(removeCommand)) {
                            DeviceManager.removeDevice(smartHomeController, commandRemoveDevice.getUuid(), commandRemoveDevice);
                        }
                        else if ("location".equals(removeCommand)) {
                            LocationManager.removeLocation(smartHomeController, commandRemoveLocation.getLocationName(), commandRemoveLocation);
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

}
