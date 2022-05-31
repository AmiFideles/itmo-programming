package com.itmo.programming.controller.command.withArgument;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;

public class RemoveGreaterKeyCommand extends Command {
    private final ReceiverService storageService;

    public RemoveGreaterKeyCommand(ReceiverService storageService) {
        super("remove_greater_key", ": удаляет из коллекции все элементы, ключ которых превышает заданный", 1);
        this.storageService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        long key = argumentHolder.getKey();
        storageService.removeGreaterKey(key);
        reply.addCommandResponseBody("Элементы, ключи которых больше заданного удалены");
        return reply;
    }
}
