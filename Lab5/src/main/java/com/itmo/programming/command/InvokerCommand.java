package com.itmo.programming.command;

import com.itmo.programming.command.exceptions.NoSuchCommandException;
import com.itmo.programming.command.withoutArgument.HistoryCommand;
import com.itmo.programming.console.ConsoleInterface;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;

import java.io.IOException;
import java.util.Arrays;

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
     *
     * @param consoleManager интерфейс обмена данными между пользователем и программой
     * @param line           введенная строка
     * @throws IncorrectNumberOfArgumentsException пробрасывается, если пришло неверное количество аргументов команды
     */
    public void execute(ConsoleInterface consoleManager, String line) throws IncorrectNumberOfArgumentsException, IOException {
        String[] args = simplifyIncomingLine(line);
        historyCommand.addToCommandList(args[0]);
        Command command = commandManager.getCommand(args[0]);
        command.execute(consoleManager, prepareParameters(args));
    }

    private String[] prepareParameters(String[] arguments) {
        return Arrays.copyOfRange(arguments, 1, arguments.length);
    }

    private String[] simplifyIncomingLine(String line) {
        if (line == null) {
            throw new NoSuchCommandException("\nКоманды  не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды");
        }
        if (line.isEmpty() || line.trim().isEmpty()) {
            throw new NoSuchCommandException("Команды  не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды");
        }
        return line.trim().replaceAll("\\s+", " ").split(" ");
    }
}
