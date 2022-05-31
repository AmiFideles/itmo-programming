package com.itmo.programming.controller.command.withoutArgument;

import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;


public class InfoCommand extends Command {
    private final ReceiverService storageService;

    public InfoCommand(ReceiverService storageService) {
        super("info", ": выводит в стандартный поток вывода информацию о коллекции (тип, дата инициализации, количество элементов и т.д.)", 0);
        this.storageService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        reply.addCommandResponseBody(storageService.getInfoAboutCollection());
        return reply;
    }
}
