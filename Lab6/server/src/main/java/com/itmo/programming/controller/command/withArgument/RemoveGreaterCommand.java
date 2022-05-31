package com.itmo.programming.controller.command.withArgument;


import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.Reply;
import com.itmo.programming.model.Person;
import com.itmo.programming.controller.command.Command;

import java.io.IOException;


public class RemoveGreaterCommand extends Command {
    private final ReceiverService receiverService;


    public RemoveGreaterCommand(ReceiverService storageService) {
        super("remove_greater", ": удаляет все элементы из коллекции, превышающие заданный", 0);
        this.receiverService = storageService;

    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        Person person = Person.transferToPerson(argumentHolder.getInputPerson());
        receiverService.removeGreater(person);
        reply.addCommandResponseBody("Элементы, превышающие заданный удалены");
        return reply;
    }

}
