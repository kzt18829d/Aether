package com.aether.business.devices.deviceInterfaces;

/**
 * Управление питанием
 */
public interface TurnPower<T> {
    /**
     * Включить
     * @return boolean
     */
    boolean turnOn();

    /**
     * Выключить
     * @return boolean
     */
    boolean turnOff();

    /**
     * Статус питания
     * @return >Template type<
     */
    T getPowerStatus();
}
