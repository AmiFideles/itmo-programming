package com.itmo.programming.controller.command.withArgument;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;
import java.time.LocalDateTime;


public class RemoveAnyByBirthday extends Command {
    private final ReceiverService receiverService;

    public RemoveAnyByBirthday(ReceiverService storageService) {
        super("remove_any_by_birthday", ": удаляет из коллекции один элемент, значение поля birthday которого эквивалентно заданному", 1);
        this.receiverService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        LocalDateTime localDateTime = argumentHolder.getBirthday();
        receiverService.removeAnyByBirthday(localDateTime);
        reply.addCommandResponseBody("элементы коллекции, значение birthday которых равен " + localDateTime.toString() + " удалены");
        return reply;
    }
}
