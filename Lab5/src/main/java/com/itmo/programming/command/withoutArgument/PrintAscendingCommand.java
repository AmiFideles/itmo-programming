package com.itmo.programming.command.withoutArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.entity.Person;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;

import java.util.List;


public class PrintAscendingCommand extends Command {
    private final ReceiverService storageService;

    public PrintAscendingCommand(ReceiverService storageService) {
        super("print_ascending", ": выводит все элементы в порядке возрастания", 0);
        this.storageService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        List<Person> sortedPersonList = storageService.sortAscendingOrder();
        if (sortedPersonList.size()==0){
            consoleManager.write("В коллекции нету элементов. Вы можете добавить и потом вывести в порядке возрастания");
        }
        sortedPersonList.forEach(x->consoleManager.write(x.toString()));
    }
}
