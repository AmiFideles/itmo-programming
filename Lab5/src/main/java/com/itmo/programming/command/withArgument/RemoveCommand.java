package com.itmo.programming.command.withArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.console.ConsoleInterface;


public class RemoveCommand extends Command {
    private final ReceiverService receiverService;

    public RemoveCommand(ReceiverService storageService) {
        super("remove", ": удалить элемент из коллекции по его ключу", 1);
        this.receiverService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        long key = Long.parseLong(argument[0]);
        receiverService.removeKey(key);
        consoleManager.write("Элемент с ключом " + key + " удален");
    }
}
