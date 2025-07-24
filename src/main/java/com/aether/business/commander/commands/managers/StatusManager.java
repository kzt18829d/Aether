package com.aether.business.commander.commands.managers;

import com.aether.business.commander.commands.Status.CommandStatusAll;
import com.aether.business.commander.commands.Status.CommandStatusDevice;
import com.aether.business.core.SmartHomeController;

import java.util.UUID;

public class StatusManager extends IManager {
    public static void getStatusAll(SmartHomeController smartHomeController) {
        System.out.println(smartHomeController.getSystemStatusReport_ByString());
    }

    public static void getStatusDevice(SmartHomeController smartHomeController, CommandStatusDevice commandStatusDevice) {
        try {
            System.out.println(smartHomeController.getDeviceStatusReport_ByString(UUID.fromString(commandStatusDevice.getUuid())));
        } finally {
            commandStatusDevice.clearCommand();
        }
    }
}
