package com.itmo.programming.command.withArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.console.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;


public class UpdateCommand extends Command {
    private final ReceiverService storageService;
    private final ArgumentQuestioner asker;

    public UpdateCommand(ReceiverService storageService, ArgumentQuestioner askerArgument) {
        super("update", ": обновляет значение элемента коллекции, id которого равен заданному", 1);
        this.storageService = storageService;
        this.asker = askerArgument;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        long id = Long.parseLong(argument[0]);
        if (storageService.checkId(id)) {
            asker.updatePerson(storageService.getPersonById(id));
            //storageService.updateID(id, person);
            consoleManager.write("Элемент успешно изменен");
        } else {
            consoleManager.write("Элемент с таким ID отсутсвует");
            //
        }
    }
}
