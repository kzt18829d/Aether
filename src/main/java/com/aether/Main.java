package com.aether;

import com.aether.business.core.CommandsManager;
import com.aether.business.core.SmartHomeController;
import com.aether.old.JacksonTEst;

//TIP To <b>Run</b> code, press <shortcut actionId="Run"/> or
// click the <icon src="AllIcons.Actions.Execute"/> icon in the gutter.
public class Main {

    private static final Main main_ = new Main();
    private static final SmartHomeController smartHomeController = new SmartHomeController();

    public static void main(String[] args) {

        testJSON();

    }

    public static void Aether() {
        CommandsManager cmdManager = new CommandsManager(main_);
        cmdManager.Commander(smartHomeController, main_);
    }

    public static void testJSON() {
        JacksonTEst test1 = new JacksonTEst();
        test1.test1();

    }

}