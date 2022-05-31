package com.itmo.programming.command.withArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.console.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.entity.Person;


public class InsertCommand extends Command {
    private ReceiverService receiverService;
    private ArgumentQuestioner asker;

    public InsertCommand(ReceiverService storageService, ArgumentQuestioner askerArgument) {
        super("insert", ": добавляет новый элемент с заданны ключом", 1);
        this.receiverService = storageService;
        this.asker = askerArgument;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        long key = Long.parseLong(argument[0]);
        if (receiverService.checkKey(key)) {
            Person person = asker.readPerson();
            receiverService.insertKey(key, person);
            consoleManager.write("Элемент успешно добавлен в коллекцию");
        } else {
            consoleManager.write("Такой элемент уже существует");
        }
    }
}
