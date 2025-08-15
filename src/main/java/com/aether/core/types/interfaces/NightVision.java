package com.aether.core.types.interfaces;

import com.aether.core.types.enums.NightVisionStatus;

public interface NightVision {
    boolean enableNightVision();
    boolean disableNightVision();
    NightVisionStatus getNightVisionStatus();
}
