package com.itmo.programming.controller.command.withoutArgument;

import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;
import com.itmo.programming.model.Person;

import java.io.IOException;
import java.util.List;


public class PrintAscendingCommand extends Command {
    private final ReceiverService storageService;

    public PrintAscendingCommand(ReceiverService storageService) {
        super("print_ascending", ": выводит все элементы в порядке возрастания", 0);
        this.storageService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        List<Person> sortedPersonList = storageService.sortAscendingOrder();
        if (sortedPersonList.size() == 0) {
            reply.addCommandResponseBody("В коллекции нету элементов. Вы можете добавить и потом вывести в порядке возрастания");
            return reply;
        }
        StringBuilder sb = new StringBuilder();
        sortedPersonList.forEach(sb::append);
        reply.addCommandResponseBody(sb.toString());
        return reply;
    }

}
