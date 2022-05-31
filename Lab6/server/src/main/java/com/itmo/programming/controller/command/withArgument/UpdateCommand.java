package com.itmo.programming.controller.command.withArgument;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;
import com.itmo.programming.model.Person;

import java.io.IOException;


public class UpdateCommand extends Command {
    private final ReceiverService storageService;

    public UpdateCommand(ReceiverService storageService) {
        super("update", ": обновляет значение элемента коллекции, id которого равен заданному", 1);
        this.storageService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        long id = argumentHolder.getId();
        if (storageService.checkId(id)) {
            Person person = Person.transferToPerson(argumentHolder.getInputPerson());
            long key = storageService.getKeyById(id);
            storageService.updatePerson(key, person);
            reply.addCommandResponseBody("Элемент успешно изменен");
        } else {
            reply.addCommandResponseBody("Элемент с таким ID отсутсвует");
        }
        return reply;
    }

}
