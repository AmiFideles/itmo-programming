package com.itmo.programming.server;

import com.itmo.programming.communication.Request;
import com.itmo.programming.controller.command.InvokerCommand;
import com.itmo.programming.controller.command.Reply;

import java.io.IOException;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class RequestHandler {
    private InvokerCommand invokerCommand;

    public RequestHandler(InvokerCommand invokerCommand) {
        this.invokerCommand = invokerCommand;
    }

    public Reply handle(Request request) throws IOException {
        return invokerCommand.execute(request.getCommandName(), request.getArgumentHolder());
    }
}
