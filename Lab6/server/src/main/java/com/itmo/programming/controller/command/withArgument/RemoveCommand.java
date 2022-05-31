package com.itmo.programming.controller.command.withArgument;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;


public class RemoveCommand extends Command {
    private final ReceiverService receiverService;


    public RemoveCommand(ReceiverService storageService) {
        super("remove", ": удалить элемент из коллекции по его ключу", 1);
        this.receiverService = storageService;

    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        long key = argumentHolder.getKey();
        receiverService.removeKey(key);
        reply.addCommandResponseBody("Элемент с ключом " + key + " удален");
        return reply;
    }

}
