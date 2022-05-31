package com.itmo.programming.controller.command.withoutArgument;

import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;


public class ExitCommand extends Command {
    private final ReceiverService storageService;

    public ExitCommand(ReceiverService storageService) {
        super("exit", ": завершает программу без сохранения в файл", 0);
        this.storageService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        reply.addCommandResponseBody("Завершение программы");
        storageService.exit();
        return reply;
    }
}
