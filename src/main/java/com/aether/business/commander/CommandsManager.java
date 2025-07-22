package com.aether.business.commander;

import com.aether.business.Exceptions.*;
import com.aether.business.commander.commands.*;
import com.aether.business.commander.commands.Add.CommandAdd;
import com.aether.business.commander.commands.Add.CommandAddDevice;
import com.aether.business.commander.commands.Add.CommandAddLocation;
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
    private final String own;
    private final String cmdl;
    private final String cmdlERR;
    private final String cmdlINFO;
    private final Scanner scanner;

    private JCommander jCommander;

    private CommandExit commandExit;
    private CommandLoad commandLoad;
    private CommandSave commandSave;

    private CommandAdd commandAdd;
    private CommandAddDevice commandAddDevice;
    private CommandAddLocation commandAddLocation;

    private CommandStatus commandStatus;
    private CommandStatusDevice commandStatusDevice;
    private CommandStatusAll commandStatusAll;

    // others commands

    public CommandsManager(Object main_) {
        own = "<Owner> ";
        cmdl = "<Aether> ";
        cmdlERR = "<Aether> [ERROR] ";
        cmdlINFO = "<Aether> [INFO] ";
        scanner = new Scanner(System.in);

        commandExit = new CommandExit();
        commandLoad = new CommandLoad();
        commandSave = new CommandSave();

        commandAdd = new CommandAdd();
        commandAddDevice = new CommandAddDevice();
        commandAddLocation = new CommandAddLocation();

        commandStatus = new CommandStatus();
        commandStatusDevice = new CommandStatusDevice();
        commandStatusAll = new CommandStatusAll();

        jCommander = JCommander.newBuilder().addObject(main_)
                .addCommand(commandExit)
                .addCommand(commandLoad)
                .addCommand(commandSave)
                .addCommand(commandAdd)
                .addCommand(commandStatus)
                .build();


        jCommander.getCommands().get("add").addCommand("device", commandAddDevice);
        jCommander.getCommands().get("add").addCommand("location", commandAddLocation);
        jCommander.getCommands().get("status").addCommand("all", commandStatusAll);
        jCommander.getCommands().get("status").addCommand("device", commandStatusDevice);
    }

    public void Commander(SmartHomeController smartHomeController) {
        boolean cycleController = true;
        while (cycleController) {
            // <Aether>
            System.out.print(own);
            String writtenCommand = scanner.nextLine();

            try {
                jCommander.parse(writtenCommand.split("\\s+"));
                String parsedCommand = jCommander.getParsedCommand();

                switch (parsedCommand) {
                    case "exit":
                    case "Exit":
                        System.out.println(cmdlINFO + "Exiting");
                        cycleController = false;
                        break;
                    case "load":
                    case "Load":
                        System.out.println(cmdlINFO + "Loading Data from \"XXXX\"");
                        // Затычка
                        break;
                    case "save":
                    case "Save":
                        System.out.println(cmdlINFO + "Save Data to \"XXXX\"");
                        // Затычка
                        break;

                    case "add":
                        // TODO Инкапсулировать действия в отдельные классы для разгрузки кода класса

                        String addSubCommand = jCommander.getCommands().get("add").getParsedCommand();
                        if ("device".equals(addSubCommand)) {
                            List<String> parameters = commandAddDevice.getDeviceParameters();

                            Device newDevice = ObjectsFactory.createDevice(parameters.getFirst(), parameters.get(1), parameters.getLast());
                            smartHomeController.addDevice(newDevice);

                        }
                        else if ("location".equals(addSubCommand)) {
                            var locationTemplate = commandAddLocation.getLocationName();
                            smartHomeController.addLocation(new Location(locationTemplate));

                        } else {
                            if (addSubCommand.isEmpty() || addSubCommand == null) throw new InvalidCommandException("Empty subCommand");
                            else throw new InvalidCommandException("Invalid subCommand " + addSubCommand);
                        }
                        break;

                    case "status":
                    case "Status":
                        String statusSubCommand = jCommander.getCommands().get("status").getParsedCommand();
                        if ("all".equals(statusSubCommand)) {
                            System.out.println(smartHomeController.getSystemStatusReport_ByString());
                        }
                        else if ("device".equals(statusSubCommand)) {
                            System.out.println(smartHomeController.getDeviceStatusReport_ByString(UUID.fromString(commandStatusDevice.getUuid())));
                        }
                        else {
                            throw new SmartHomeControllerException("Invalid or empty command's argument");
                        }
                        break;
                    default:
                        throw new ParseException("Invalid command \"" + writtenCommand, 0);
                }
            } catch (CreateDeviceException exception) {
                System.out.println(cmdlERR + "Device wasn't created. " + exception.getMessage());
            }
            catch (InvalidDeviceNameException exception){
                System.out.println(cmdlERR + "Invalid name of Device. " + exception.getMessage());
            }
            catch (InvalidLocationNameException exception) {
                System.out.println(cmdlERR + "Invalid Location name. " + exception.getMessage());
            }
            catch (InvalidCommandException | ParseException exception) {
                System.out.println("Invalid command. " + exception.getMessage());
            }
            catch (Exception exception) {
                System.out.println(cmdlERR + "Other Error: " + exception.getMessage());
            }
            finally {
                // только те, у которых это нужно
                commandAddDevice.clearCommand();
                commandAddLocation.clearCommand();
                commandStatusDevice.clearCommand();
            }
            if (!cycleController) return;
        }
    }

    /**
     * если нужен будет извне
     * */
    public JCommander getCommander() { return jCommander; }

    public String getCmdl() {
        return cmdl;
    }
}
