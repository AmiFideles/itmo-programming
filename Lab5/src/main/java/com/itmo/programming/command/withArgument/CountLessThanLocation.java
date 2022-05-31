package com.itmo.programming.command.withArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.console.ArgumentQuestioner;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.entity.Location;


public class CountLessThanLocation extends Command {
    private final ReceiverService receiverService;
    private final ArgumentQuestioner asker;

    public CountLessThanLocation(ReceiverService receiverService, ArgumentQuestioner askerArgument) {
        super("count_less_than_location", ": выводит количество элементов, значение поля location которых меньше заданного", 0);
        this.receiverService = receiverService;
        this.asker = askerArgument;

    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        Location location = asker.readLocation();
        consoleManager.write("количество элементов, значение поля location которых меньше заданного : " + receiverService.countLessThanLocation(location));
    }
}
