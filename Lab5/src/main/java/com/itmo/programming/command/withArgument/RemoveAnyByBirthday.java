package com.itmo.programming.command.withArgument;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.Command;
import com.itmo.programming.command.exceptions.IncorrectNumberOfArgumentsException;
import com.itmo.programming.console.ConsoleInterface;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;


public class RemoveAnyByBirthday extends Command {
    private final ReceiverService receiverService;

    public RemoveAnyByBirthday(ReceiverService storageService) {
        super("remove_any_by_birthday", ": удаляет из коллекции один элемент, значение поля birthday которого эквивалентно заданному", 1);
        this.receiverService = storageService;
    }

    @Override
    public void execute(ConsoleInterface consoleManager, String[] argument) throws IncorrectNumberOfArgumentsException {
        checkCountOfArgument(argument.length);
        try {
            LocalDateTime localDateTime = LocalDate.parse(argument[0], DateTimeFormatter.ISO_LOCAL_DATE).atStartOfDay();
            receiverService.removeAnyByBirthday(localDateTime);
            consoleManager.write("элементы коллекции, значение birthday которых равен " + localDateTime.toString() + " удалены");
        } catch (DateTimeParseException e) {
            consoleManager.write("Вы указали неверный формат даты. Введите дату дня рождения в таком формате (YYYY-MM-DD)");
        }
    }
}
