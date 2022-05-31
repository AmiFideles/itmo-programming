package com.itmo.programming.controller.command.withArgument;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;
import com.itmo.programming.model.Location;

import java.io.IOException;


public class CountLessThanLocation extends Command {
    private final ReceiverService receiverService;

    public CountLessThanLocation(ReceiverService receiverService) {
        super("count_less_than_location", ": выводит количество элементов, значение поля location которых меньше заданного", 0);
        this.receiverService = receiverService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        Location location = Location.transformToLocation(argumentHolder.getInputLocation());
        reply.addCommandResponseBody("количество элементов, значение поля location которых меньше заданного : " + receiverService.countLessThanLocation(location));
        return reply;
    }
}
