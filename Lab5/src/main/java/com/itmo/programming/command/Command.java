package com.itmo.programming.command;

import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;

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

    public abstract void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException, IOException;

    public void checkCountOfArgument(int countOfArguments) throws IncorrectNumberOfArgumentsException {
        if (this.countOfArguments != countOfArguments) {
            throw new IncorrectNumberOfArgumentsException("Неверное количество аргументов:" + "Пришло " + countOfArguments + " ожидалось " + this.countOfArguments);
        }
    }

    public String getName() {
        return name;
    }

    public String getDescription() {
        return description;
    }

}
