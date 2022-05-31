package com.itmo.programming.command.withoutArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;


public class InfoCommand extends Command {
    private final ReceiverService storageService;

    public InfoCommand(ReceiverService storageService) {
        super("info", ": выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        consoleManager.write(storageService.getInfoAboutCollection());
    }
}
