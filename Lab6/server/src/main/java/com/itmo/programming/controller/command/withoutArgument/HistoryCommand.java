package com.itmo.programming.controller.command.withoutArgument;

import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;
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
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        reply.addCommandResponseBody(String.join("\n", historyListOfCommand));
        return reply;
    }
}
