package com.aether.business.commander.commands.managers;

import com.aether.business.Exceptions.SmartHomeControllerException;
import com.aether.business.commander.commands.Add.CommandAddLocation;
import com.aether.business.commander.commands.Remove.CommandRemoveLocation;
import com.aether.business.core.SmartHomeController;
import com.aether.business.types.Location;

public class LocationManager extends IManager {
    public static void addLocation(SmartHomeController controller, CommandAddLocation commandAddLocation) {
        try {
            var locationTemplate = commandAddLocation.getLocationName();
            controller.addLocation(new Location(locationTemplate));
        } finally {
            commandAddLocation.clearCommand();
        }
    }

    public static void removeLocation(SmartHomeController smartHomeController, String loc_name, CommandRemoveLocation commandRemoveLocation) {
        try {
            if (!smartHomeController.findLocation_b(loc_name)) throw new SmartHomeControllerException("Location " + loc_name + " wasn't find.");
            smartHomeController.removeLocation(loc_name);
        } finally {
            commandRemoveLocation.clearCommand();
        }
    }
}
