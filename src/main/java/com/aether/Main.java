package com.aether;

import com.aether.business.commands.CommandsManager;
import com.aether.business.core.SmartHomeController;
import com.aether.business.devices.Device;
import com.aether.business.devices.Light;
import com.aether.business.devices.SubTypes.Location;
import com.aether.business.devices.SubTypes.Name;

import java.util.Scanner;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {
    private static final Main main_ = new Main();
    private static final SmartHomeController smartHomeController = new SmartHomeController();

    public static void main(String[] args) {

        CommandsManager cmdManager = new CommandsManager(main_);
        cmdManager.Commander(smartHomeController);

    }


}