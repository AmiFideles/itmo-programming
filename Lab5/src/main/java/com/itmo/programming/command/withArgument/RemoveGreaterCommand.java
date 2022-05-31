package com.itmo.programming.command.withArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.console.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.entity.Person;


public class RemoveGreaterCommand extends Command {
    private final ReceiverService receiverService;
    private final ArgumentQuestioner asker;

    public RemoveGreaterCommand(ReceiverService storageService, ArgumentQuestioner askerArgument) {
        super("remove_greater", ": удаляет все элементы из коллекции, превышающие заданный", 0);
        this.receiverService = storageService;
        this.asker = askerArgument;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        Person person = asker.readPerson();
        receiverService.removeGreater(person);
        consoleManager.write("Элементы, превышающие заданный удалены");
    }
}
