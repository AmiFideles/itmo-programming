package com.itmo.programming.command.withoutArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;

import java.util.HashMap;


public class HelpCommand extends Command {
    private final ReceiverService storageService;
    private HashMap<String, String> helpManual;

    public HelpCommand(ReceiverService storageService) {
        super("help", ": вывести справку по доступным командам", 0);
        this.storageService = storageService;

    }

    public void setHelpManual(HashMap<String, String> helpManual) {
        this.helpManual = helpManual;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        helpManual.forEach((key, value) -> {
            consoleManager.write(key + value);
        });
    }
}
