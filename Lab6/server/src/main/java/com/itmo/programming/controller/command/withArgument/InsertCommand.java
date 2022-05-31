package com.itmo.programming.controller.command.withArgument;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;
import com.itmo.programming.dto.PersonDTO;
import com.itmo.programming.model.Person;

import java.io.IOException;


public class InsertCommand extends Command {
    private final ReceiverService receiverService;

    public InsertCommand(ReceiverService storageService) {
        super("insert", ": добавляет новый элемент с заданны ключом", 1);
        this.receiverService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        long key = Long.parseLong(argumentHolder.getInputParameterLine()[0]);
        if (receiverService.checkKey(key)) {
            PersonDTO personDTO = argumentHolder.getInputPerson();
            receiverService.insertKey(key, Person.transferToPerson(personDTO));
            reply.addCommandResponseBody("Элемент успешно добавлен в коллекцию");
        } else {
            reply.addCommandResponseBody("Такой элемент уже существует");
        }
        return reply;
    }
}

