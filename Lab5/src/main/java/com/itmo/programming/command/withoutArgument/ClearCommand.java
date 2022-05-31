package com.itmo.programming.command.withoutArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;


public class ClearCommand extends Command {
    private final ReceiverService storageService;

    public ClearCommand(ReceiverService storageService) {
        super("clear", ": очистить коллекцию", 0);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        storageService.clear();
        consoleManager.write("Коллекция очищена");
    }
}
