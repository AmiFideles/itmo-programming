package com.itmo.programming.client;

import com.itmo.programming.commands.Command;
import com.itmo.programming.commands.CommandResponse;
import com.itmo.programming.commands.CommandHolder;
import com.itmo.programming.commands.exceptions.NoSuchCommandException;
import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.console.ConsoleInterface;

import java.util.Arrays;

/**
 * @author Iskandarov Shakhzodbek P3133
 */
public class UserHandler {
    private CommandHolder commandHolder;

    public UserHandler(CommandHolder commandHolder) {
        this.commandHolder = commandHolder;
    }

    public CommandResponse execute(String inputLine, ConsoleInterface consoleInterface){
        String[] args = simplifyIncomingLine(inputLine);
        Command command = commandHolder.getCommand(args[0]);
        command.setName(args[0]);
        CommandResponse commandResponse = command.execute(new ArgumentHolder(prepareParameters(args)), consoleInterface);
        commandResponse.setCommandName(args[0]);
        return commandResponse;
    }

    private String[] simplifyIncomingLine(String line) {
        if (line == null){
            throw new NoSuchCommandException("Команды  не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды");
        }
        if (line.isEmpty() || line.trim().isEmpty()) {
            throw new NoSuchCommandException("Команды  не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды");
        }
        return line.trim().replaceAll("\\s+", " ").split(" ");
    }

    private String[] prepareParameters(String[] arr){
        return Arrays.copyOfRange(arr, 1, arr.length);
    }

}
