package com.itmo.programming.command.withoutArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;

import java.util.LinkedList;


public class HistoryCommand extends Command {
    private final ReceiverService storageService;
    private LinkedList<String> historyListOfCommand = new LinkedList<>();

    public HistoryCommand(ReceiverService storageService) {
        super("history", ": выводит последние 15 команд (без их аргументов)", 0);
        this.storageService = storageService;
    }

    public void addToCommandList(String commandName) {
        historyListOfCommand.add(commandName);
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        historyListOfCommand.forEach(consoleManager::write);
    }
}
