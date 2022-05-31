package com.itmo.programming.command.withArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.console.ConsoleInterface;

public class RemoveGreaterKeyCommand extends Command {
    private final ReceiverService storageService;

    public RemoveGreaterKeyCommand(ReceiverService storageService) {
        super("remove_greater_key", ": удаляет из коллекции все элементы, ключ которых превышает заданный", 1);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        long key = Long.parseLong(argument[0]);
        storageService.removeGreaterKey(key);
        consoleManager.write("Элементы, ключи которых больше заданного удалены");
    }
}
