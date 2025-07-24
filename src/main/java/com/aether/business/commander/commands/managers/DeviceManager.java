package com.aether.business.commander.commands.managers;

import com.aether.business.Exceptions.SmartHomeControllerException;
import com.aether.business.commander.commands.Add.CommandAddDevice;
import com.aether.business.commander.commands.Add.CommandAddDeviceHelp;
import com.aether.business.commander.commands.Remove.CommandRemoveDevice;
import com.aether.business.constaints.Terminal;
import com.aether.business.core.SmartHomeController;
import com.aether.business.devices.Device;
import com.aether.business.factory.ObjectsFactory;

import java.util.List;
import java.util.UUID;

public class DeviceManager extends IManager {

    // TODO по нарастанию добавлять методы управления устройствами

    public static void addDevice(SmartHomeController controller, CommandAddDevice commandAddDevice, CommandAddDeviceHelp commandAddDeviceHelp) {
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

    public static void deviceHelp(CommandAddDeviceHelp commandAddDeviceHelp) {
        Terminal.info(commandAddDeviceHelp.help_s());
    }

    public static void removeDevice(SmartHomeController smartHomeController, String uuid_s, CommandRemoveDevice commandRemoveDevice) {
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


}
