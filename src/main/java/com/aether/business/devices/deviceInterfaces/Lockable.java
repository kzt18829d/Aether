package com.aether.business.devices.deviceInterfaces;

/**
 * Возможность быть заблокированным
 */
public interface Lockable {
    /**
     * Заблокировать.
     * True - был заблокирован, False - не был заблокирован
     * @return boolean
     */
    boolean lock();

    /**
     * Разблокировать.
     * True - был разблокирован, False - не был разблокирован
     * @return boolean
     */
    boolean unlock();

    /**
     * Статус блокировки
     * @return boolean
     */
    boolean getLockStatusBoolean();

    /**
     * Статус блокировки
     * @return String
     */
    String getLockStatusString();
}
