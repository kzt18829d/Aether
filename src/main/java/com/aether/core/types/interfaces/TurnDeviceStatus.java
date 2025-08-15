package com.aether.core.types.interfaces;

import com.aether.core.types.enums.DeviceStatus;

/**
 * Управление питанием
 */
public interface TurnDeviceStatus {
    /**
     * Включить
     * @return boolean
     */
    boolean turnStatusOn();

    /**
     * Выключить
     * @return boolean
     */
    boolean turnStatusOff();

    /**
     * Статус питания
     * @return >Template type<
     */
    DeviceStatus getDeviceStatus();
}
