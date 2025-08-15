package com.aether.core.logging;

import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

public class LoggerMarkers {
    public static final Marker BASE = MarkerFactory.getMarker("Base");
    public static final Marker INFO = MarkerFactory.getMarker("Info");
    public static final Marker WARN = MarkerFactory.getMarker("Warn");
    public static final Marker ERROR = MarkerFactory.getMarker("Error");
}
