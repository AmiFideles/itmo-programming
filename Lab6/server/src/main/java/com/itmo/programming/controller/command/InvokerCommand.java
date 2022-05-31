package com.itmo.programming.controller.command;


import com.itmo.programming.communication.ArgumentHolder;
import com.itmo.programming.controller.command.withoutArgument.HistoryCommand;

import java.io.IOException;

/**
 * Через данный класс происходит выполнение команды по полученной строке от пользователя.
 * Класс вычисляет команду и вызывает соответствующий метод для ее выполнения
 */
public class InvokerCommand {
    private final CommandManager commandManager;
    private final HistoryCommand historyCommand;

    public InvokerCommand(CommandManager commandManager) {
        this.commandManager = commandManager;
        this.historyCommand = (HistoryCommand) commandManager.getCommand("history");
    }

    /**
     * Метод в котором выполняются команды по введенной строке пользователя
     */
    public Reply execute(String commandName, ArgumentHolder argumentHolder) throws IOException {
        historyCommand.addToCommandList(commandName);
        Command command = commandManager.getCommand(commandName);
        Reply reply = command.execute(argumentHolder);
        return reply;
    }


}
