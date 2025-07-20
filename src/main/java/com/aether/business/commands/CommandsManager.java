package com.aether.business.commands;

import com.aether.business.Exceptions.CreateDeviceException;
import com.aether.business.Exceptions.InvalidCommandException;
import com.aether.business.Exceptions.InvalidDeviceNameException;
import com.aether.business.Exceptions.InvalidLocationNameException;
import com.aether.business.core.SmartHomeController;
import com.aether.business.devices.Device;
import com.aether.business.devices.SubTypes.Location;
import com.aether.business.devices.SubTypes.Name;
import com.aether.business.factory.ObjectsFactory;
import com.beust.jcommander.JCommander;

import java.text.ParseException;
import java.util.List;
import java.util.Scanner;

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

                        String subCommand = jCommander.getCommands().get("add").getParsedCommand();
                        if ("device".equals(subCommand)) {
                            List<String> parameters = commandAddDevice.getDeviceParameters();

                            System.out.println(cmdlINFO + "Adding Device " + parameters.get(1) + " --Location \"" + parameters.getLast());
                            Device newDevice = ObjectsFactory.createDevice(parameters.getFirst(), parameters.get(1), parameters.getLast());

                            if (smartHomeController.getLocation(newDevice.getLocationString()) == null) {
                                smartHomeController.addLocation(newDevice.getLocation());
                                System.out.println(cmdlINFO + "Added Location \"" + newDevice.getLocationString() + "\"");
                            }

                            smartHomeController.addDevice(newDevice);
                            System.out.println(cmdlINFO + "Created Device " + newDevice.getDeviceNameString() + " in " + newDevice.getLocationString() + " with UUID " + newDevice.getUuid());
                        }
                        else if ("location".equals(subCommand)) {
                            System.out.println(cmdlINFO + "Adding Location " + commandAddLocation.getLocationName());

                            // TODO написать добавление локации. Инкапсулировать в отдельный класс

                        } else {
                            if (subCommand.isEmpty() || subCommand == null) throw new InvalidCommandException("Empty subCommand");
                            else throw new InvalidCommandException("Invalid subCommand " + subCommand);
                        }
                        break;

                    case "status":
                    case "Status":
                        break;
                    default:
                        throw new ParseException("Invalid command \"" + writtenCommand, 0);
                }
            } catch (CreateDeviceException exception) {
                System.out.println(cmdlERR + "Device wasn't created. " + exception.toString());
            }
            catch (InvalidDeviceNameException exception){
                System.out.println(cmdlERR + "Invalid name of Device. " + exception.toString());
            }
            catch (InvalidLocationNameException exception) {
                System.out.println(cmdlERR + "Invalid Location name. " + exception.toString());
            }
            catch (InvalidCommandException exception) {
                System.out.println("Invalid command. " + exception.toString());
            }
            catch (Exception exception) {
                System.out.println(cmdlERR + "Other Error: " + exception.getMessage());
            }

            // TODO Сделать очистку аргументов команд в конце цикла
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
