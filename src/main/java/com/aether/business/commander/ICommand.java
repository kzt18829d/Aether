package com.aether.business.commander;

/**
 * Абстрактный класс комманд, принимаемых парсеров.
 * Содержит общие для всех комманд методы, переопределяемые в каждой.
 *
 */
public abstract class ICommand {

    /**
     * Очистка аргументов команды
     * */
    public void clearCommand() { }
}
