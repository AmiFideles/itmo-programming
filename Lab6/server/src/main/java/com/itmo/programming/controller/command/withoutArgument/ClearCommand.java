package com.itmo.programming.controller.command.withoutArgument;

import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;


public class ClearCommand extends Command {
    private final ReceiverService storageService;

    public ClearCommand(ReceiverService storageService) {
        super("clear", ": очистить коллекцию", 0);
        this.storageService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        String verificationResult = checkCountOfArgument(argumentHolder.getCountParameter());
        if (verificationResult != null) {
            reply.addCommandResponseBody(verificationResult);
            return reply;
        }
        storageService.clear();
        reply.addCommandResponseBody("Коллекция очищена");
        return reply;
    }
}

