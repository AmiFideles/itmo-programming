package com.itmo.programming.controller.command.withoutArgument;

import com.itmo.programming.controller.collectionutils.ReceiverService;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.Command;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;


public class HelpCommand extends Command {
    private final ReceiverService storageService;
    private HashMap<String, String> helpManual;

    public HelpCommand(ReceiverService storageService) {
        super("help", ": вывести справку по доступным командам", 0);
        this.storageService = storageService;
    }

    @Override
    public Reply execute(ArgumentHolder argumentHolder) throws IOException {
        Reply reply = new Reply();
        List<String> tempList = new ArrayList<>();
        helpManual.forEach((key,value)->tempList.add(key+value));
        reply.addCommandResponseBody(String.join("\n", tempList));
/*        helpManual.forEach((key, value) -> sb.append(key).append(value).append("\n"));
        reply.addCommandResponseBody(sb.toString());*/
        return reply;
    }

    public void setHelpManual(HashMap<String, String> helpManual) {
        this.helpManual = helpManual;
    }
}

