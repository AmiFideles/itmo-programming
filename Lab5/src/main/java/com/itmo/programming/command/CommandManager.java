package com.itmo.programming.command;

import com.itmo.programming.collection.ReceiverService;
import com.itmo.programming.command.exceptions.NoSuchCommandException;
import com.itmo.programming.command.withArgument.*;
import com.itmo.programming.command.withoutArgument.*;
import com.itmo.programming.console.ArgumentQuestioner;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import java.util.stream.Stream;

/**
 * Менеджер команд. Данный класс инициализирует команд и предоставляет возможность получить команду по ее названию
 */
public class CommandManager {
    private final ReceiverService storageService;
    private final ArgumentQuestioner argumentQuestioner;
    private final String path;
    private Map<String, Command> allCommand = new HashMap<>();


    /**
     * Конструктор, в котором инициализируются все команды
     * @param storageService приемник команд
     * @param argumentQuestioner помощник для получения данных
     * @param pathToFile путь до файла
     */
    public CommandManager(ReceiverService storageService, ArgumentQuestioner argumentQuestioner, String pathToFile) {
        this.storageService = storageService;
        this.argumentQuestioner = argumentQuestioner;
        this.path = pathToFile;
        initializeAllCommand();
    }

    /**
     * Метод позволяет получить объект Command по ее названию в виде string
     * @param nameCommand название команды
     * @return объекта Command
     * @throws NoSuchCommandException выбрасывается, если не сущетсвует такой команды
     */
    public Command getCommand(String nameCommand) throws NoSuchCommandException {
        if (allCommand.containsKey(nameCommand)) {
            return allCommand.get(nameCommand);
        } else {
            throw new NoSuchCommandException(String.format("Команды %s не существует. Воспользуйтесь  командой help, чтобы узнать доступные доступные команды", nameCommand));
        }
    }

    /**
     * Инициализации всех команд и добавление мануаля для команды help
     */
    private void initializeAllCommand() {
        HelpCommand helpCommand = new HelpCommand(storageService);
        List<Command> commands = Stream.of(new ClearCommand(storageService), new CountLessThanLocation(storageService, argumentQuestioner), new ExecuteScriptCommand(storageService,path), new ExitCommand(storageService), new HistoryCommand(storageService), new InfoCommand(storageService), new InsertCommand(storageService, argumentQuestioner), new PrintAscendingCommand(storageService), new RemoveAnyByBirthday(storageService), new RemoveCommand(storageService), new RemoveGreaterCommand(storageService, argumentQuestioner), new RemoveGreaterKeyCommand(storageService), new SaveCommand(storageService, path), new ShowCommand(storageService), new UpdateCommand(storageService, argumentQuestioner), helpCommand).collect(Collectors.toList());
        convertToMap(commands);
        helpCommand.setHelpManual(prepareHelpManual());

    }

    /**
     * @return подготовка текста команды help
     */
    private HashMap<String, String> prepareHelpManual() {
        HashMap<String, String> helpText = new HashMap<>();
        allCommand.forEach((key, value) -> helpText.put(key, value.getDescription()));
        return helpText;
    }

    /**
     * Конвертирует лист команды в map
     * @param commands лист команд
     */
    private void convertToMap(List<Command> commands) {
        commands.forEach(x -> allCommand.put(x.getName(), x));
    }
}
