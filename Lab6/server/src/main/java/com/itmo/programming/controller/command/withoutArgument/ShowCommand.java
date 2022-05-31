package com.itmo.programming.controller.command.withoutArgument;

import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.Reply;
import com.itmo.programming.controller.command.Command;


public class ShowCommand extends Command {
    private final ReceiverService storageService;

    public ShowCommand(ReceiverService storageService) {
        super("show", ": выводит в стандартный поток вывода все элементы коллекции в строковом представлении", 0);
        this.storageService = storageService;

    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) {
        Reply reply = new Reply();
        reply.addCommandResponseBody(storageService.showStorageData());
        return reply;
    }
}
