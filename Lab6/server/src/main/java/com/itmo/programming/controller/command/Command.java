 package com.itmo.programming.controller.command;


import com.itmo.programming.communication.ArgumentHolder;

import java.io.IOException;

/**
 * Абстрактный класс для команд
 */
public abstract class Command {
    private final String name;
    private final String description;
    private final int countOfArguments;

    public Command(String name, String description, int countOfArguments) {
        this.name = name;
        this.description = description;
        this.countOfArguments = countOfArguments;
    }

    public abstract Reply execute(ArgumentHolder argumentHolder) throws IOException;

    //TODO ужасно. Как я такое написал. Переделать
    public String checkCountOfArgument(int countOfArguments) {
        if (this.countOfArguments != countOfArguments) {
            return "Неверное количество аргументов:" + "Пришло " + countOfArguments + " ожидалось " + this.countOfArguments;
        } else return null;
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
