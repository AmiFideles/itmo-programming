package com.itmo.programming.command.withoutArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;


public class ShowCommand extends Command {
    private final ReceiverService storageService;

    public ShowCommand(ReceiverService storageService) {
        super("show", ": выводит в стандартный поток вывода все элементы коллекции в строковом представлении", 0);
        this.storageService = storageService;

    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        consoleManager.write(storageService.showStorageData());

    }
}
